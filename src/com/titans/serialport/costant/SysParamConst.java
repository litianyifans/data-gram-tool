package com.titans.serialport.costant;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;

public class SysParamConst {
	/*
	 * 正则校验字符串
	 */
	public static final String PROTOCOL_EL = "FE.+16" ;
	public static final String PROTOCOL_EL_NAME = "驿联网后台通信规约V2.5" ;
	public static final String PROTOCOL_NET = "88\\s[1][8|0]\\s[0|1|2|3|4|6|A|a]\\w\\s[A|a|6|2|E|e][0-9]\\s[A|a6|2|E|e][0-9](\\s\\w\\w){8}" ;
	
	
	public static final String TAB1 = "COM" ;
	public static final String TAB2 = "NET" ;
	public static final String TAB3 = "LOC" ;
	public static final String TAB4 = "DBIMPORT" ;
	
	public static final String COMS = "coms" ;
	public static final String BURDS = "burds" ;
	
	public static final String SELECT_COM = "selectCom" ;
	public static final String SELECT_BURD = "selectburd" ;
	public static final String SELECT_PROTOCOL = "selectProtocol" ;
	
	public static final String CONN_BUTTON_STATUS = "conn_bt_status" ;
	public static final String BT_CONN= "连接" ;
	public static final String BT_CLOSE= "断开" ;
	
	public static final String SHOW_PACKET_TEXT = "showPacketText" ;
	public static final String SHOW_OPEN = "showOpen" ;
	public static final String SHOW_CLOSE = "showClose" ;
	public static final String QUERY_PACKET_TEXT = "queryPacketText" ;
	public static final String QUERY_OPEN = "queryOpen" ;
	public static final String QUERY_CLOSE = "queryClose" ;
	
	//查询面板
	public static final String QUERY_PANEL = "queryPanel" ;
	//串口面板常量
	public static final String SERIAL_PORT_PANEL = "serialPortPanel" ;
	public static final String SERIAL_PORT_LABEL = "serialPortLabel" ;
    public static final String BAUDRATE_LABEL = "baudrateLabel" ;
	public static final String BAUDRATE_CHOICE = "baudrateChoice" ;
	public static final String SERIALPORT_CHOICE = "serialPortChoice" ;
	
	//连接按钮
	public static final String CONN_BUTTON = "connButton" ;
	
	//json显示面板
	public static final String PARSE_JSON_VIEW_PANEL = "scrollDataViewJson" ;
	public static final String PARSE_JSON_VIEW = "parseJsonView" ;
	
	//数据列表面板常量
	public static final String SCROLL_DATAVIEW = "scrollDataView" ;
	public static final String DATA_TABLE = "dataTable" ;
	public static final String TABLE_MODEL = "tableModel" ;
	public static final String ROW_DATAS = "rowdatas" ;
	public static final String COLUMNS = "columns" ;
	public static final String TABLE_WIDTH = "tableWidth" ;
	
	
	public static final String NET_PARAM_PANEL = "netParamPanel" ;
	
	
	public static final HashMap<String,String> protocols = new HashMap<String,String>() ;
	/**
	 * 正常的风格
	 */
	public static String STYLE_NORMAL = "normal";
	/**
	 * 字体为红色
	 */
	public static String STYLE_RED = "red";
	
	//协议容器
	static{
		protocols.put(PROTOCOL_EL_NAME, PROTOCOL_EL) ;
	}
	public static List<String> getProtocolList(){
		List protocols = new ArrayList() ;
		protocols.add(PROTOCOL_EL) ;
		//protocols.add(".*") ;
		return protocols ;
	}
	
	public static String getProtocol(String protocolName){
		return protocols.get(protocolName) ;
	}
	
	
	public  static String getNetSql(String dateText , String dataText){
		return "insert into  net  values('" + dateText + "','" + dataText + "');"  ;
	}
	
	public static void setButtonVisable(JButton jbutton,boolean flag){
		if(flag){
			jbutton.setEnabled(false);
			jbutton.setBackground(Color.GRAY);
		}else{
			jbutton.setEnabled(true);
			jbutton.setOpaque(true);
		}
		
	}
	
	
	public static void changeJBox(List<String> itmes,JComboBox<String> jBox){
		for(String item:itmes){
			jBox.addItem(item) ;
		}
	}
	
	public static List<String> initBautrates(){
		List<String> bautrates = new ArrayList<String>() ;
		bautrates.add("9600");
		bautrates.add("19200");
		bautrates.add("38400");
		bautrates.add("57600");
		bautrates.add("115200");
		return bautrates ;
	}
	public static List<String> initProtocolNames(){
		List<String> pNames = new ArrayList<String>() ;
		pNames.add(PROTOCOL_EL_NAME);
		return pNames ;
	}
	
	
	public static JComboBox<String> initStopPostion(JComboBox<String> stopPostion){
		stopPostion.addItem("1");
		stopPostion.addItem("2");
		stopPostion.addItem("3");
		return stopPostion ;
	}
	
	public static JComboBox<String> initDataPostion(JComboBox<String> dataPostion){
		dataPostion.addItem("8");
		dataPostion.addItem("7");
		dataPostion.addItem("6");
		return dataPostion ;
	}
	
	public static JComboBox<String> initcheckPostion(JComboBox<String> checkPostion){
		checkPostion.addItem("无校验");
		checkPostion.addItem("奇校验");
		checkPostion.addItem("偶校验");
		return checkPostion ;
	}
	
}
