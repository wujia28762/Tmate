package com.honyum.elevatorMan.net;

import com.honyum.elevatorMan.data.ToDoInfo;
import com.honyum.elevatorMan.net.base.Response;

import java.util.List;

public class ToDoResponse extends Response {

	public List<ToDoInfo> getBody() {
		return body;
	}

	public void setBody(List<ToDoInfo> body) {
		this.body = body;
	}

	private List<ToDoInfo> body;


	/**
	 * 根据json生成对象
	 * @param json
	 * @return
	 */
	public static ToDoResponse getToDoInfoResponse(String json) {
		return (ToDoResponse) parseFromJson(ToDoResponse.class, json);
	}
}
