package com.titans.serialport.bean;

import java.io.Serializable;
import java.util.List;

public class DTCommand implements Serializable{

	
	private static final long serialVersionUID = 2824036409247989289L;
	private String key;
	private String name;
	private List<DTData> dtDatas;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<DTData> getDtDatas() {
		return dtDatas;
	}
	public void setDtDatas(List<DTData> dtDatas) {
		this.dtDatas = dtDatas;
	}
	
}
