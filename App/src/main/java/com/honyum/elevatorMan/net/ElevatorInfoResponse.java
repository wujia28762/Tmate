package com.honyum.elevatorMan.net;

import com.honyum.elevatorMan.data.ElevatorInfo1;
import com.honyum.elevatorMan.net.base.Response;
import com.honyum.elevatorMan.net.base.ResponseHead;

public class ElevatorInfoResponse extends Response {

	private ResponseHead head;

	private ElevatorInfo1 body;

	@Override
	public ResponseHead getHead() {
		return head;
	}

	@Override
	public void setHead(ResponseHead head) {
		this.head = head;
	}

	public ElevatorInfo1 getBody() {
		return body;
	}

	public void setBody(ElevatorInfo1 body) {
		this.body = body;
	}

	/**
	 * 根据json生成对象
	 * @param json
	 * @return
	 */
	public static ElevatorInfoResponse getElevatorInfoResponse(String json) {
		return (ElevatorInfoResponse) parseFromJson(ElevatorInfoResponse.class, json);
	}

}
