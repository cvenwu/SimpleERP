package com.pojo;
		/**
		 * 
		 * @author SiVan
		 * @time 2017年4月24日 上午11:42:01
		 * TODO		员工详情
		 */
public class EmpInfo {
	private String Id;
	private String Name;
	private String Card;
	private String Phone;
	private String Hiredate;
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getCard() {
		return Card;
	}
	public void setCard(String card) {
		Card = card;
	}
	public String getPhone() {
		return Phone;
	}
	public void setPhone(String phone) {
		Phone = phone;
	}
	public String getHiredate() {
		return Hiredate;
	}
	public void setHiredate(String hiredate) {
		Hiredate = hiredate;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.Name;
	}
	
}
