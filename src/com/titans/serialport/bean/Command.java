package com.titans.serialport.bean;

import java.io.Serializable;
import java.util.List;

public class Command implements Serializable{
	
	private static final long serialVersionUID = 6259163297688923438L;
	private String key;
	private String name;
	private String type;
	private List<Data> datas;

	public void setKey(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public List<Data> getDatas() {
		return datas;
	}

	public void setDatas(List<Data> datas) {
		this.datas = datas;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}
