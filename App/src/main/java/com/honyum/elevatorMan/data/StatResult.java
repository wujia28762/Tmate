package com.honyum.elevatorMan.data;

public class StatResult implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private Integer total = 0;
	private Integer unDeal= 0;
	private Integer dealing= 0;
	private Integer finsh= 0;
	private Integer cancel= 0;
	private String title = "";
	private double lng = 0.0;
	private double lat= 0.0;

	public StatResult() {
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Integer getUnDeal() {
		return unDeal;
	}
	public void setUnDeal(Integer unDeal) {
		this.unDeal = unDeal;
	}
	public Integer getDealing() {
		return dealing;
	}
	public void setDealing(Integer dealing) {
		this.dealing = dealing;
	}
	public Integer getFinsh() {
		return finsh;
	}
	public void setFinsh(Integer finsh) {
		this.finsh = finsh;
	}
	public Integer getCancel() {
		return cancel;
	}
	public void setCancel(Integer cancel) {
		this.cancel = cancel;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}


    public double getLat() {
        return lat;
    }
	public double setLat(double lat) {
		return this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}
}
