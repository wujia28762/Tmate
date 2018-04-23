package com.honyum.elevatorMan.net;

import com.honyum.elevatorMan.data.ElevatorInfo1;
import com.honyum.elevatorMan.data.WorkerInfo;
import com.honyum.elevatorMan.net.base.Response;
import com.honyum.elevatorMan.net.base.ResponseBody;

import java.io.Serializable;
import java.util.List;

public class ElevatorInfoDetailResponse extends Response implements Serializable{

	private ContractDetailBody body;

	public void setBody(ContractDetailBody body) {
		this.body = body;
	}

	public ContractDetailBody getBody() {
		return body;
	}

	public static class ContractDetailBody extends ResponseBody{

		private ElevatorInfo1 elevator;

		private List<WorkerInfo> list;

		public void setElevator(ElevatorInfo1 elevator) {
			this.elevator = elevator;
		}

		public ElevatorInfo1 getElevator() {
			return elevator;
		}

		public void setList(List<WorkerInfo> list) {
			this.list = list;
		}

		public List<WorkerInfo> getList() {
			return list;
		}
	}


	/**
	 * 根据json生成对象
	 * @param json
	 * @return
	 */
	public static ElevatorInfoDetailResponse getElevatorInfoDetailResponse(String json) {
		return (ElevatorInfoDetailResponse) parseFromJson(ElevatorInfoDetailResponse.class, json);
	}
}
