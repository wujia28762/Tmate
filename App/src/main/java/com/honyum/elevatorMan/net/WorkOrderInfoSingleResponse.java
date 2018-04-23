package com.honyum.elevatorMan.net;

import com.honyum.elevatorMan.data.WorkOrderInfo;
import com.honyum.elevatorMan.net.base.Response;

import java.util.List;

public class WorkOrderInfoSingleResponse extends Response {

	private WorkOrderInfo body;

	public WorkOrderInfo getBody() {
		return body;
	}

	public void setBody(WorkOrderInfo body) {
		this.body = body;
	}

	/**
	 * 根据json生成对象
	 * @param json
	 * @return
	 */
	public static WorkOrderInfoSingleResponse getRepairInfoResponse(String json) {
		return (WorkOrderInfoSingleResponse) parseFromJson(WorkOrderInfoSingleResponse.class, json);
	}
}
