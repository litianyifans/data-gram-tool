package com.titans.serialport.bean;

import java.io.Serializable;
import java.util.Map;


public class DTData implements Serializable{
	
	private static final long serialVersionUID = 6029079037293317553L;
	private String name;
	private int len;
	private String index ;
	private int start ;
	private String format;
	private boolean optionflag;
	private int calcDegree;
	private Map<String,String> options;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getLen() {
		return len;
	}
	public void setLen(int len) {
		this.len = len;
	}
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public boolean isOptionflag() {
		return optionflag;
	}
	public void setOptionflag(boolean optionflag) {
		this.optionflag = optionflag;
	}
	public int getCalcDegree() {
		return calcDegree;
	}
	public void setCalcDegree(int calcDegree) {
		this.calcDegree = calcDegree;
	}
	public Map<String, String> getOptions() {
		return options;
	}
	public void setOptions(Map<String, String> options) {
		this.options = options;
	}
	
}
