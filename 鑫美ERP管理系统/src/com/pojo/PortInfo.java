package com.pojo;
		/**
		 * 
		 * @author SiVan
		 * @time 2017年4月24日 上午11:42:12
		 * TODO	花盆详情
		 */
public class PortInfo {

	private String PortId;
	private String PortClassify;			
	private String PortName;
	private String PortPrice;				//花盆标价
	private String PortMaking;
	private String PortNumber;
	private String PortSupplier;
	private String PortClassifyId;
	private String PortSupplierId;
	
	public String getPortClassifyId() {
		return PortClassifyId;
	}
	public void setPortClassifyId(String portClassifyId) {
		PortClassifyId = portClassifyId;
	}
	public String getPortSupplierId() {
		return PortSupplierId;
	}
	public void setPortSupplierId(String portSupplierId) {
		PortSupplierId = portSupplierId;
	}

	
	
	public String getPortId() {
		return PortId;
	}
	public void setPortId(String portId) {
		PortId = portId;
	}
	public String getPortClassify() {
		return PortClassify;
	}
	public void setPortClassify(String portClassify) {
		PortClassify = portClassify;
	}
	public String getPortSupplier() {
		return PortSupplier;
	}
	public void setPortSupplier(String portSupplier) {
		PortSupplier = portSupplier;
	}
	public String getPortName() {
		return PortName;
	}
	public void setPortName(String portName) {
		PortName = portName;
	}
	public String getPortPrice() {
		return PortPrice;
	}
	public void setPortPrice(String portPrice) {
		PortPrice = portPrice;
	}
	public String getPortMaking() {
		return PortMaking;
	}
	public void setPortMaking(String portMaking) {
		PortMaking = portMaking;
	}
	public String getPortNumber() {
		return PortNumber;
	}
	public void setPortNumber(String PortNumber) {
		this.PortNumber = PortNumber;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.PortId;
	}
				
}
