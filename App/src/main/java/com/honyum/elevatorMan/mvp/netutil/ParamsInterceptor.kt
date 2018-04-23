package com.honyum.elevatorMan.mvp.netutil

import android.util.Log
import com.honyum.elevatorMan.mvp.jsonFormat
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.internal.http.HttpHeaders
import okhttp3.internal.platform.Platform
import okio.Buffer
import java.io.EOFException
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit

/**
 * add all the common header   eg. token or userId
 */

class ParamsInterceptor(private val log: Boolean = false) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        if (log) {
            // 日志拦截
            Log.d("NET_INTERCEPTOR", "---------->> Intercept to add parameters <<----------")
        }

        // 获取请求信息
        val oldRequest = chain.request()

        // 添加新的参数
        val url = oldRequest.url()
                .newBuilder()
                .scheme(oldRequest.url().scheme())
                .host(oldRequest.url().host())
//                .addQueryParameter("version", BuildConfig.VERSION_NAME)         // 版本名
//                .addQueryParameter("platform", "android")                // 应用平台
//                .addQueryParameter("imei", "")                           // 手机IMEI
                .build()

        // 生成新的请求
        val request = oldRequest.newBuilder()
                .method(oldRequest.method(), oldRequest.body())
                .url(url)
                .build()

        return chain.proceed(request)
    }
}

/**
 * 网络请求拦截器，打印网络请求相关信息
 *
 * @param level 日志打印等级[Level], 默认[Level.BASIC]
 * @param logger 日志打印接口[InterceptorLogger], 默认实现[InterceptorLogger.DEFAULT]
 */
class LogInterceptor(private var level: Level = Level.BASIC, private val logger: InterceptorLogger = InterceptorLogger.DEFAULT) : Interceptor {

    companion object {
        /** 默认字符集 UTF-8 */
        private val UTF8 = Charset.forName("UTF-8")
    }

    override fun intercept(chain: Interceptor.Chain): Response {

        // 获取请求对象
        val request = chain.request()

        // 判断是否打印
        if (level == Level.NONE) {
            // 不打印日志，直接返回
            return chain.proceed(request)
        }

        // 标记-是否打印数据实体
        val logBody = level == Level.BODY
        // 标记-是否打印头
        val logHeaders = logBody || level == Level.HEADERS

        // 声明打印文本
        val logStr = StringBuilder()

        // 获取请求数据
        val requestBody = request.body()

        // 获取连接
        val connection = chain.connection()
        // 获取协议
        val protocol = if (null == connection) Protocol.HTTP_1_1 else connection.protocol()
        // 拼接文本
        logStr.append("--> ${request.method()} ${request.url()} $protocol")
        if (!logHeaders && requestBody != null) {
            logStr.append(" (${requestBody.contentLength()}-byte body\n")
        }

        if (logHeaders) {
            if (requestBody != null) {
                if (requestBody.contentType() != null) {
                    logStr.append("Content-Type ${requestBody.contentType()}\n")
                }
                if (requestBody.contentLength() != -1L) {
                    logStr.append("Content-Length: ${requestBody.contentLength()}\n")
                }
            }

            // 获取请求头
            val headers = request.headers()
            for (i in 0 until headers.size()) {
                val name = headers.name(i)
                if (!"Content-Type".equals(name, true) && !"Content-Length".equals(name, true)) {
                    logStr.append("$name: ${headers.value(i)}\n")
                }
            }

            when {
                !logBody || requestBody == null ->
                    logStr.append("--> END ${request.method()}\n")
                bodyEncoded(request.headers()) ->
                    logStr.append("--> END ${request.method()} (encoded body omitted)\n")
                else -> {
                    val buffer = Buffer()
                    requestBody.writeTo(buffer)

                    var charset = UTF8
                    val contentType = requestBody.contentType()
                    if (contentType != null) {
                        charset = contentType.charset(UTF8)
                    }

                    logStr.append("\n")
                    if (isPlaintext(buffer)) {
                        logStr.append("${buffer.readString(charset)}\n")
                        logStr.append("--> END ${request.method()} (${requestBody.contentLength()}-byte body)\n")
                    } else {
                        logStr.append("--> END ${request.method()} (binary ${requestBody.contentLength()}-byte body omitted")
                    }
                }
            }
        }

        // 记录请求开始时间
        val startNs = System.nanoTime()
        val response = try {
            chain.proceed(request)
        } catch (e: Exception) {
            logStr.append("<-- HTTP FAILED: $e\n")
            throw e
        }
        // 计算请求耗时
        val tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs)

        // 获取响应体
        val responseBody = response.body()

        if (responseBody != null) {
            logStr.append("<-- ${response.code()} ${response.message()} ${response.request().url()}" +
                    " (${tookMs}ms${if (!logHeaders)
                        ", ${if (responseBody.contentLength() != -1L) "$responseBody-byte"
                        else "unknown-length"} body"
                    else ""})\n")

            if (logHeaders) {
                val headers = response.headers()
                for (i in 0 until headers.size()) {
                    logStr.append("${headers.name(i)}: ${headers.value(i)}\n")
                }

                when {
                    !logBody || !HttpHeaders.hasBody(response) ->
                        logStr.append("<-- END HTTP\n")
                    bodyEncoded(response.headers()) ->
                        logStr.append("--< END HTTP (encoded body omitted")
                    else -> {
                        val source = responseBody.source()
                        source.request(Long.MAX_VALUE)
                        val buffer = Buffer()

                        var charset = UTF8
                        val contentType = responseBody.contentType()
                        if (contentType != null) {
                            charset = contentType.charset(UTF8)
                        }

                        if (!isPlaintext(buffer)) {
                            logStr.append("\n")
                            logStr.append("<-- END HTTP (binary ${buffer.size()}-byte body omitted\n")
                            logger.log(logStr.toString())
                            return response
                        }

                        if (responseBody.contentLength() != 0L) {
                            logStr.append("\n")
                            val json = buffer.clone().readString(charset)
                            val jsonFormat = json.jsonFormat()
                            logStr.append("$json\n${
                            if (jsonFormat.length > 200)
                                "${jsonFormat.substring(0, 100)}\n\n The Json String was too long...\n\n ${
                                jsonFormat.substring(jsonFormat.length - 100)}\n"
                            else
                                "$jsonFormat\n\n"}")
                        }
                        logStr.append("<-- END HTTP (${buffer.size()}-byte body)\n")
                    }
                }
            }
        }
        logger.log(logStr.toString())
        return response
    }

    /**
     * 检测是否包含可读文本
     *
     * @return 是否包含
     */
    private fun isPlaintext(buffer: Buffer) =
            try {
                var plaintext = true
                val prefix = Buffer()
                val byteCount = if (buffer.size() < 64) buffer.size() else 64
                buffer.copyTo(prefix, 0, byteCount)
                for (i in 0..15) {
                    if (prefix.exhausted()) {
                        break
                    }
                    val codePoint = prefix.readUtf8CodePoint()
                    if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                        plaintext = false
                    }
                }
                plaintext
            } catch (e: EOFException) {
                false // Truncated UTF-8 sequence.
            }


    private fun bodyEncoded(headers: Headers): Boolean {
        val contentEncoding = headers.get("Content-Encoding")
        return contentEncoding != null && !contentEncoding.equals("identity", ignoreCase = true)
    }
}

/**
 * 日志打印接口
 */
interface InterceptorLogger {

    /**
     * 打印日志
     *
     * @param msg 日志文本
     */
    fun log(msg: String)

    companion object {
        /** 默认日志打印 */
        val DEFAULT: InterceptorLogger = object : InterceptorLogger {
            override fun log(msg: String) {
                Platform.get().log(Platform.INFO, msg, null)
            }
        }
    }

}

/**
 * 枚举类，日志打印范围
 */
enum class Level {
    /**
     * 不打印日志
     */
    NONE,
    /**
     * 打印请求和响应行
     */
    BASIC,
    /**
     * 打印请求和响应行以及他们各自的头
     */
    HEADERS,
    /**
     * 打印请求和响应行、他们各自的头以及主体
     */
    BODY
}