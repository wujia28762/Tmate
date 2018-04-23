package com.honyum.elevatorMan.net;

import com.honyum.elevatorMan.data.ContartInfo;
import com.honyum.elevatorMan.data.SignInfo;
import com.honyum.elevatorMan.net.base.Response;

import java.util.List;

public class SignInfoResponse extends Response {

	public List<SignInfo> getBody() {
		return body;
	}

	public void setBody(List<SignInfo> body) {
		this.body = body;
	}

	private List<SignInfo> body;

	/**
	 * 根据json生成对象
	 * @param json
	 * @return
	 */
	public static SignInfoResponse getSignInfoResponse(String json) {
		return (SignInfoResponse) parseFromJson(SignInfoResponse.class, json);
	}
}
