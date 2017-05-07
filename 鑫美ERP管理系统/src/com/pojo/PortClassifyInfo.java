package com.pojo;
		/**
		 * 
		 * @author SiVan
		 * @time 2017年4月24日 上午11:41:53
		 * TODO	花盆分类
		 */
public class PortClassifyInfo {

	private String ClassifyName;
	private String ClassifyId;

	public String getClassifyName() {
		return ClassifyName;
	}

	public void setClassifyName(String classifyName) {
		ClassifyName = classifyName;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.ClassifyName;
	}

	public String getClassifyId() {
		return ClassifyId;
	}

	public void setClassifyId(String classifyId) {
		ClassifyId = classifyId;
	}
}
