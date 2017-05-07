package com.pojo;

public class Customer {

	private String CustomerId;
	private String CustomerZid;
	private String CustomerName;
	private String CustomerPhone;
	private String CustomerWeChat;
	private String CustomerAddress;
	private String CustomerZoneName;
	public String getCustomerId() {
		return CustomerId;
	}
	public String getCustomerZoneName() {
		return CustomerZoneName;
	}
	public void setCustomerZoneName(String customerZoneName) {
		CustomerZoneName = customerZoneName;
	}
	public void setCustomerId(String customerId) {
		CustomerId = customerId;
	}
	public String getCustomerZid() {
		return CustomerZid;
	}
	public void setCustomerZid(String customerZid) {
		CustomerZid = customerZid;
	}
	public String getCustomerName() {
		return CustomerName;
	}
	public void setCustomerName(String customerName) {
		CustomerName = customerName;
	}
	public String getCustomerPhone() {
		return CustomerPhone;
	}
	public void setCustomerPhone(String customerPhone) {
		CustomerPhone = customerPhone;
	}
	public String getCustomerWeChat() {
		return CustomerWeChat;
	}
	public void setCustomerWeChat(String customerWeChat) {
		CustomerWeChat = customerWeChat;
	}
	public String getCustomerAddress() {
		return CustomerAddress;
	}
	public void setCustomerAddress(String customerAddress) {
		CustomerAddress = customerAddress;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.CustomerName;
	}
}
