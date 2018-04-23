package com.honyum.elevatorMan.data;

import java.io.Serializable;

/**
 * 报警地址信息
 * @author chang
 *
 */
public class CommunityInfo implements Serializable {

	private String address;

	private String id;

	private String lat = "0.0";		//纬度

	private String lng = "0.0";		//经度

	private String name;

	private String propertyUtel;

	private String branchName;

	private String propertyUname;

	private String branchId;

	private String repairManager;

	private String repairTel;

	public void setRepairManager(String repairManager) {
		this.repairManager = repairManager;
	}

	public String getRepairManager() {
		return repairManager;
	}

	public void setRepairTel(String repairTel) {
		this.repairTel = repairTel;
	}

	public String getRepairTel() {
		return repairTel;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setPropertyUname(String propertyUname) {
		this.propertyUname = propertyUname;
	}

	public String getPropertyUname() {
		return propertyUname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPropertyUtel() {
		return propertyUtel;
	}

	public void setPropertyUtel(String propertyUtel) {
		this.propertyUtel = propertyUtel;
	}
}
