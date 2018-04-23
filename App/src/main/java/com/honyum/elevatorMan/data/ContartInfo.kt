package com.honyum.elevatorMan.data

import java.io.Serializable

/**
 * Created by LiYouGui on 2017/12/4.
 */
class ContartInfo(
     val id: String? = null,
     val code: String? = null, //合同编号
     val name: String? = null ,//合同名称
     val sketched: String? = null ,//合同简述
     val startTime: String? = null, //合同生效日期
     val endTime: String? = null, //合同结束日期
     val type: String? = null ,//合同类型：标准合同、框架合同
     val procurementType: String? = null, //采购类型：维保合同、零星工程
     val payMoney: Double? = 0.0, //合同金额
     val expenditureType: String? = null ,//收支类型：1-收入，0-支出
     val businessType: String? = null ,//合同业务类型：大包、清包
     val archivesCode: String? = null, //档案编号
     val archivesLocation: String? = null ,//归档描述：存档位置
     val userId: String? = null ,//签署人ID
     val userName: String? = null, //签署人姓名
     val userTel: String? = null ,//签署人电话
     val userEmail: String? = null ,//签署人邮箱
     val userBranchId: String? = null, //签署人公司ID
     val userBranchName: String? = null, //签署人公司名称
     val branchId: String? = null, //对方公司ID
     val branchName: String? = null, //对方公司名称
     val branchManager: String? = null, //对方公司联系人
     val branchManagerTel: String? = null ,//对方公司联系人电话
     val branchManagerEmail: String? = null, //对方公司联系人邮箱
     val branchTel: String? = null, //对方公司联系电话
     val taxpayerCode: String? = null ,//对方公司纳税人识别号
     val accountNumber: String? = null, //对方公司银行账号
     val bankName: String? = null ,//对方公司开户银行
     val branchAddress: String? = null, //对方公司注册地址
     val isIncrementInvoice: String? = null ,//是否增值税专用发票：是/否
     val mainClause: String? = null, //合同主要条款
     val inFieldNumber: String? = null, //驻场人数
     val isYearlyInspectionMoney: String? = null ,//是否需要年检费：是/否
     val state: String? = null ,//合同状态
     val createTime: String? = null, //创建时间
     val isDelete: String? = null ,//是否被删除：1删除
     val expenditure: String? = null //收支类型名称：收入，支出
): Serializable