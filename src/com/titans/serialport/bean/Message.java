package com.titans.serialport.bean;

public class Message {
	// 报文长度
	private int len;
	// 报文编码格式
	private String format;
	// 报文名称
	private String name;
	

	
	
	public Message() {
		super();
	}
	public Message(int len, String format, String name) {
		super();
		this.len = len;
		this.format = format;
		this.name = name;
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
	public String getDesc() {
		return name;
	}
	public void setDesc(String desc) {
		this.name = name;
	}
	
	

}
