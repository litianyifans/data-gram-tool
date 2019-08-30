package com.titans.serialport.bean;

import java.io.Serializable;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Data  implements Serializable{
	
	private static final long serialVersionUID = 4921602442571557357L;
	private String name;
	private int len;
	private String group ;
	private String format;
	private int[] postions ;
	@JsonProperty("optionFlag")
	private boolean optionflag;
	private Map<String,String> options;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setLen(int len) {
		this.len = len;
	}

	public int getLen() {
		return len;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getFormat() {
		return format;
	}

	public void setOptionflag(boolean optionflag) {
		this.optionflag = optionflag;
	}

	public boolean getOptionflag() {
		return optionflag;
	}

	public Map<String, String> getOptions() {
		return options;
	}

	public void setOptions(Map<String, String> options) {
		this.options = options;
	}

	public int[] getPostions() {
		return postions;
	}

	public void setPostions(int[] postions) {
		this.postions = postions;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	

	

	
}
