package com.honyum.elevatorMan.net.base;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.honyum.elevatorMan.data.Atom;

import java.lang.reflect.Type;

/**
 * Created by Star on 2018/1/3.
 */

public class NewResponse extends Atom {
    private final static Gson gson = new Gson();
    private ResponseHead head;

    public ResponseHead getHead() {
        return head;
    }

    public void setHead(ResponseHead head) {
        this.head = head;
    }

    public static <T extends NewResponse> T getResponse(String json, Class<? extends NewResponse> classType) {
        return (T) parseFromJson(classType, json);
    }

//    public static <T extends NewResponse> T getResponse(String json) {
//        final Type type = new TypeToken<T>(){}.getType();
//        T result = gson.fromJson(json,type);
//        return  result;
//    }
}
