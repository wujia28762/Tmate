package com.honyum.elevatorMan.net;

import com.honyum.elevatorMan.data.ContartInfo;
import com.honyum.elevatorMan.net.base.Response;

import java.util.List;

public class ContractInfoResponse extends Response {

	public List<ContartInfo> getBody() {
		return body;
	}

	public void setBody(List<ContartInfo> body) {
		this.body = body;
	}

	private List<ContartInfo> body;

	/**
	 * 根据json生成对象
	 * @param json
	 * @return
	 */
	public static ContractInfoResponse getContratInfoResponse(String json) {
		return (ContractInfoResponse) parseFromJson(ContractInfoResponse.class, json);
	}
}
