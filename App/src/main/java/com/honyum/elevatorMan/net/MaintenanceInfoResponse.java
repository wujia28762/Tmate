package com.honyum.elevatorMan.net;

import com.honyum.elevatorMan.data.MaintenanceInfo;
import com.honyum.elevatorMan.net.base.Response;

import java.util.List;

public class MaintenanceInfoResponse extends Response {

	private List<MaintenanceInfo> body;

	public List<MaintenanceInfo> getBody() {
		return body;
	}

	public void setBody(List<MaintenanceInfo> body) {
		this.body = body;
	}

	/**
	 * 根据json生成对象
	 * @param json
	 * @return
	 */
	public static MaintenanceInfoResponse getRepairInfoResponse(String json) {
		return (MaintenanceInfoResponse) parseFromJson(MaintenanceInfoResponse.class, json);
	}
}
