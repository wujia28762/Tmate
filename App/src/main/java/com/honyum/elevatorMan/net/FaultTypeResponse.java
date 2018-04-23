package com.honyum.elevatorMan.net;

import com.honyum.elevatorMan.data.FaultTypeInfo;
import com.honyum.elevatorMan.net.base.Response;

public class FaultTypeResponse extends Response {

	public FaultTypeInfo getBody() {
		return body;
	}

	public void setBody(FaultTypeInfo body) {
		this.body = body;
	}

	private FaultTypeInfo body;

	/**
	 * 根据json生成对象
	 * @param json
	 * @return
	 */
	public static FaultTypeResponse getFaultTypeResponse(String json) {
		return (FaultTypeResponse) parseFromJson(FaultTypeResponse.class, json);
	}
}
