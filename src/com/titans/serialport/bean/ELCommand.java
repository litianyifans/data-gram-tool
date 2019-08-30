package com.titans.serialport.bean;

import java.io.Serializable;
import java.util.List;

public class ELCommand implements Serializable{

	
	private static final long serialVersionUID = 2824036409247989289L;
	private String key;
	private String name;
	private List<ELData> datas;
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
	public List<ELData> getDatas() {
		return datas;
	}
	public void setDatas(List<ELData> datas) {
		this.datas = datas;
	}
	
	
}
