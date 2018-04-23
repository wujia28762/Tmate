package com.honyum.elevatorMan.net;

import com.honyum.elevatorMan.data.WorkOrderInfo;
import com.honyum.elevatorMan.net.base.Response;

import java.util.List;

public class WorkOrderInfoResponse extends Response {

	private List<WorkOrderInfo> body;

	public List<WorkOrderInfo> getBody() {
		return body;
	}

	public void setBody(List<WorkOrderInfo> body) {
		this.body = body;
	}

	/**
	 * 根据json生成对象
	 * @param json
	 * @return
	 */
	public static WorkOrderInfoResponse getRepairInfoResponse(String json) {
		return (WorkOrderInfoResponse) parseFromJson(WorkOrderInfoResponse.class, json);
	}
}
