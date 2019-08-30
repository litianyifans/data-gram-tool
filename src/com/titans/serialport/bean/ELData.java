package com.titans.serialport.bean;

import java.io.Serializable;
import java.util.Map;

public class ELData  implements Serializable{

	private static final long serialVersionUID = -1668409215354099317L;
	private String name;
	private String group;
	private int len;
	private String format;
	private int start ;
	private boolean optionFlag;
    private Map<String,String> options ;
    
	public ELData() {
		super();
	}
	
	public ELData(String name, String group, int len, String format, int start, boolean optionFlag,
			Map<String, String> options) {
		super();
		this.name = name;
		this.group = group;
		this.len = len;
		this.format = format;
		this.start = start;
		this.optionFlag = optionFlag;
		this.options = options;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public int getLen() {
		return len;
	}
	public void setLen(int len) {
		this.len = len;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	

	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	
	public boolean getOptionFlag() {
		return optionFlag;
	}

	public void setOptionFlag(boolean optionFlag) {
		this.optionFlag = optionFlag;
	}
	public Map<String, String> getOptions() {
		return options;
	}
	public void setOptions(Map<String, String> options) {
		this.options = options;
	}
    
    
}
