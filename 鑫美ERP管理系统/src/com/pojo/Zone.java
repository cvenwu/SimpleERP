package com.pojo;

public class Zone {

	private String zoneId;
	private String zone;
	public String getZoneId() {
		return zoneId;
	}
	public void setZoneId(String zoneId) {
		this.zoneId = zoneId;
	}
	public String getZone() {
		return zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.zone;
	}
}
