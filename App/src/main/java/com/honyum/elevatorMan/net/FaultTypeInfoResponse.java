package com.honyum.elevatorMan.net;

import com.honyum.elevatorMan.data.FaultTypeInfo;
import com.honyum.elevatorMan.net.base.Response;

import java.util.List;

public class FaultTypeInfoResponse extends Response {

	public List<FaultTypeInfo> getBody() {
		return body;
	}

	public void setBody(List<FaultTypeInfo> body) {
		this.body = body;
	}

	private List<FaultTypeInfo> body;

	/**
	 * 根据json生成对象
	 * @param json
	 * @return
	 */
	public static FaultTypeInfoResponse getContratInfoResponse(String json) {
		return (FaultTypeInfoResponse) parseFromJson(FaultTypeInfoResponse.class, json);
	}
}
