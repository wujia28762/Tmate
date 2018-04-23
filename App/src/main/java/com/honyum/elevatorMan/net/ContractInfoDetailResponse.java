package com.honyum.elevatorMan.net;

import com.honyum.elevatorMan.data.ContartInfo;
import com.honyum.elevatorMan.data.ContractElevator;
import com.honyum.elevatorMan.data.ContractFile;
import com.honyum.elevatorMan.data.ContractPayment;
import com.honyum.elevatorMan.data.ResultMapInfo;
import com.honyum.elevatorMan.net.base.Response;
import com.honyum.elevatorMan.net.base.ResponseBody;

import java.io.Serializable;
import java.util.List;

public class ContractInfoDetailResponse extends Response implements Serializable{


	private ContractDetailBody body;

	public void setBody(ContractDetailBody body) {
		this.body = body;
	}

	public ContractDetailBody getBody() {
		return body;
	}

	public static class ContractDetailBody extends ResponseBody{
		private ResultMapInfo _process_resultMap;
		private ContartInfo contract;
		private List<ContractElevator> listContractElevator;
		private List<ContractFile> listContractFile;
		private List<ContractPayment> listContractPayment;
		public void setContract(ContartInfo contract) {
			this.contract = contract;
		}

		public ContartInfo getContract() {
			return contract;
		}

		public void setListContractElevator(List<ContractElevator> listContractElevator) {
			this.listContractElevator = listContractElevator;
		}

		public List<ContractElevator> getListContractElevator() {
			return listContractElevator;
		}

		public List<ContractFile> getListContractFile() {
			return listContractFile;
		}

		public void setListContractFile(List<ContractFile> listContractFile) {
			this.listContractFile = listContractFile;
		}

		public List<ContractPayment> getListContractPayment() {
			return listContractPayment;
		}

		public void setListContractPayment(List<ContractPayment> listContractPayment) {
			this.listContractPayment = listContractPayment;
		}

		public ResultMapInfo get_process_resultMap() {
			return _process_resultMap;
		}

		public void set_process_resultMap(ResultMapInfo _process_resultMap) {
			this._process_resultMap = _process_resultMap;
		}
	}


	/**
	 * 根据json生成对象
	 * @param json
	 * @return
	 */
	public static ContractInfoDetailResponse getContratInfoResponse(String json) {
		return (ContractInfoDetailResponse) parseFromJson(ContractInfoDetailResponse.class, json);
	}
}
