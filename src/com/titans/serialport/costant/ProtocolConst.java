package com.titans.serialport.costant;

import java.util.HashMap;

import com.titans.serialport.bean.Message;

public class ProtocolConst {
	public static HashMap<String,String> levelMap = new HashMap<String,String>();
	public static HashMap<String,Message> descMap = new HashMap<String,Message>();
	public static HashMap<String,String> addrMap = new HashMap<String,String>();
	
	

	static{
		//优先权
		levelMap.put("10", "4");
		levelMap.put("18", "6");
		
		//报文描述
		//设置帧
		descMap.put("01", new Message(3,"BIN","充电启动帧"));
		descMap.put("02", new Message(1,"BIN","启动应答帧"));
		descMap.put("03", new Message(1,"BIN","充电停止帧"));
		descMap.put("04", new Message(1,"BIN","停止应答帧"));
		descMap.put("05", new Message(7,"CP56time2a","下发对时帧"));
		descMap.put("06", new Message(7,"CP56time2a","对时应答帧"));
		descMap.put("07", new Message(7,"ASCII","校验版本"));
		descMap.put("08", new Message(7,"ASCII","版本确认"));
		descMap.put("09", new Message(8,"BCD","下发充电机编号信息"));
		descMap.put("0A", new Message(1,"BCD","充电机编号信息确认"));
		descMap.put("0B", new Message(4,"BIN","控制帧"));
		descMap.put("0C", new Message(1,"BIN","控制反馈帧"));
		descMap.put("0D", new Message(1,"","备用"));
		descMap.put("0E", new Message(1,"","备用"));
		descMap.put("0F", new Message(4,"BIN","模块信息下发"));
		descMap.put("10", new Message(1,"BIN","模块信息反馈"));
		//状态帧
	    descMap.put("1C", new Message(1,"BIN","启动充电完成帧"));
		descMap.put("1D", new Message(1,"BIN","启动充电完成应答帧"));
		descMap.put("1E", new Message(2,"BIN","停止充电完成帧"));
		descMap.put("1F", new Message(1,"BIN","停止充电完成应答帧"));
		
		//中转帧
	    descMap.put("21", new Message(4,"BIN","监控单元状态帧"));
		descMap.put("22", new Message(6,"BIN","BMS需求帧"));
		descMap.put("23", new Message(6,"BIN","充电机启停控制帧"));
		
		
		
		//地址分配
		addrMap.put("A0", "计费控制单元") ;
		addrMap.put("60", "监控单元1") ;
		addrMap.put("61", "监控单元2") ;
		addrMap.put("62", "监控单元3") ;
		addrMap.put("63", "监控单元4") ;
		addrMap.put("64", "监控单元5") ;
		addrMap.put("65", "监控单元6") ;
		addrMap.put("66", "监控单元7") ;
		addrMap.put("67", "监控单元8") ;
		addrMap.put("68", "监控单元9") ;
		addrMap.put("69", "监控单元10") ;
		addrMap.put("20", "执行单元1") ;
		addrMap.put("21", "执行单元2") ;
		addrMap.put("22", "执行单元3") ;
		addrMap.put("23", "执行单元4") ;
		addrMap.put("24", "执行单元5") ;
		addrMap.put("25", "执行单元6") ;
		addrMap.put("26", "执行单元7") ;
		addrMap.put("27", "执行单元8") ;
		addrMap.put("28", "执行单元9") ;
		addrMap.put("29", "执行单元10") ;
		addrMap.put("E0", "负荷控制单元") ;
		
        
	}
}
