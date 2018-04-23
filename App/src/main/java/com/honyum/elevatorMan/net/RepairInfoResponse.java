package com.honyum.elevatorMan.net;

import com.honyum.elevatorMan.data.RepairInfo;
import com.honyum.elevatorMan.net.base.Response;

import java.util.List;

public class RepairInfoResponse extends Response {

	private List<RepairInfo> body;

	public List<RepairInfo> getBody() {
		return body;
	}

	public void setBody(List<RepairInfo> body) {
		this.body = body;
	}

	/**
	 * 根据json生成对象
	 * @param json
	 * @return
	 */
	public static RepairInfoResponse getRepairInfoResponse(String json) {
		return (RepairInfoResponse) parseFromJson(RepairInfoResponse.class, json);
	}
}
