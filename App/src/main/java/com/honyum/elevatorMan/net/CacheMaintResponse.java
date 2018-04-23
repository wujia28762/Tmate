package com.honyum.elevatorMan.net;

import com.honyum.elevatorMan.data.CacheMaint;
import com.honyum.elevatorMan.net.base.NewResponse;
import com.honyum.elevatorMan.net.base.Response;

import java.util.List;

/**
 * Created by star on 2018/3/14.
 */

public class CacheMaintResponse extends NewResponse{

    private List<CacheMaint> body;


    public List<CacheMaint> getBody() {
        return body;
    }

    public void setBody(List<CacheMaint> body) {
        this.body = body;
    }
}
