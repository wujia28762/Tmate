package com.honyum.elevatorMan.net;

import com.honyum.elevatorMan.data.MaintenanceContenInfo;
import com.honyum.elevatorMan.net.base.Response;

import java.util.List;

public class MaintenanceContentInfoResponse extends Response {

	private List<MaintenanceContenInfo> body;

	public List<MaintenanceContenInfo> getBody() {
		return body;
	}

	public void setBody(List<MaintenanceContenInfo> body) {
		this.body = body;
	}

	/**
	 * 根据json生成对象
	 * @param json
	 * @return
	 */
	public static MaintenanceContentInfoResponse getRepairInfoResponse(String json) {
		return (MaintenanceContentInfoResponse) parseFromJson(MaintenanceContentInfoResponse.class, json);
	}
}
