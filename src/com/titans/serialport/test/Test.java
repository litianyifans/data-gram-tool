package com.titans.serialport.test;

import java.io.IOException;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.titans.serialport.bean.Command;
import com.titans.serialport.bean.Data;
import com.titans.serialport.ui.ComFrame;
import com.titans.serialport.utils.MessageElianUtil;
import com.titans.serialport.utils.MyUtils;



public class Test {
	
	
	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
/*		String str = "[{\"key\":\"01\",\"name\":\"充电启动帧\",\"datas\":[{\"name\":\"启动模式\",\"len\":1,\"format\":\"BIN\",\"postions\":[1],\"optionFlag\":true,\"options\":{\"00\":\"自动\",\"01\":\"手动\"}},{\"name\":\"充电模式\",\"len\":1,\"format\":\"BIN\",\"postions\":[2],\"optionFlag\":true,\"options\":{\"00\":\"单枪模式\",\"01\":\"双枪模式\",\"02\":\"三枪模式\",\"03\":\"四枪模式\"}},{\"name\":\"主从模式\",\"len\":1,\"format\":\"BIN\",\"postions\":[3],\"optionFlag\":true,\"options\":{\"00\":\"主设备\",\"01\":\"从设备\"}}]},{\"key\":\"02\",\"name\":\"启动应答帧\",\"datas\":[{\"name\":\"成功标识\",\"len\":1,\"format\":\"BIN\",\"postions\":[1],\"optionFlag\":true,\"options\":{\"01\":\"成功\",\"02\":\"枪未连接\",\"03\":\"执行单元故障\",\"04\":\"监控单元故障\",\"05\":\"充电桩不处于空闲状态\"}}]},{\"key\":\"03\",\"name\":\"充电停止帧\",\"datas\":[{\"name\":\"停止充电原因\",\"len\":1,\"format\":\"BIN\",\"postions\":[1],\"optionFlag\":true,\"options\":{\"01\":\"计费控制单元正常停止\",\"02\":\"计费控制单元故障终止\"}}]},{\"key\":\"04\",\"name\":\"停止应答帧\",\"datas\":[{\"name\":\"成功标识\",\"len\":1,\"format\":\"BIN\",\"postions\":[1],\"optionFlag\":true,\"options\":{\"01\":\"成功\",\"02\":\"失败\"}}]},{\"key\":\"05\",\"name\":\"下发对时帧\",\"datas\":[{\"name\":\"时间\",\"len\":1,\"format\":\"CP56time2a\",\"postions\":[1],\"optionFlag\":false,\"options\":null}]},{\"key\":\"06\",\"name\":\"对时应答帧\",\"datas\":[{\"name\":\"确认标识\",\"len\":1,\"format\":\"CP56time2a\",\"postions\":[1],\"optionFlag\":true,\"options\":{\"01\":\"对时确认\",\"02\":\"对时拒绝\"}}]},{\"key\":\"07\",\"name\":\"校验版本\",\"datas\":[{\"name\":\"计费控制单元当前通信协议版本号\",\"len\":7,\"format\":\"ASCII\",\"postions\":[1,2,3,4,5,6,7],\"optionFlag\":false,\"options\":null}]},{\"key\":\"08\",\"name\":\"版本确认\",\"datas\":[{\"name\":\"控制器当前通信版本号\",\"len\":7,\"format\":\"ASCII\",\"postions\":[1,2,3,4,5,6,7],\"optionFlag\":false,\"options\":null}]},{\"key\":\"09\",\"name\":\"下发充电机编号信息\",\"datas\":[{\"name\":\"充电桩（机）编号\",\"len\":8,\"format\":\"BCD\",\"postions\":[1,2,3,4,5,6,7,8],\"optionFlag\":false,\"options\":null}]},{\"key\":\"0A\",\"name\":\"充电机编号信息确认\",\"datas\":[{\"name\":\"成功标识\",\"len\":1,\"format\":\"BIN\",\"postions\":[1],\"optionFlag\":true,\"options\":{\"01\":\"成功\",\"02\":\"失败\"}}]},{\"key\":\"0B\",\"name\":\"控制帧\",\"datas\":[{\"name\":\"电磁锁操作指令\",\"len\":1,\"format\":\"BIN\",\"postions\":[1],\"optionFlag\":true,\"options\":{\"00\":\"无效\",\"01\":\"锁定\",\"02\":\"解锁\"}},{\"name\":\"BMS电源选择指令\",\"len\":1,\"format\":\"BIN\",\"postions\":[2],\"optionFlag\":true,\"options\":{\"00\":\"无效\",\"01\":\"12V\",\"02\":\"24V\"}},{\"name\":\"地锁控制指令\",\"len\":1,\"format\":\"BIN\",\"postions\":[3],\"optionFlag\":true,\"options\":{\"00\":\"无效\",\"01\":\"降下\",\"02\":\"升起\"}},{\"name\":\"充电功率控制\",\"len\":1,\"format\":\"BIN\",\"postions\":[4],\"optionFlag\":false,\"options\":null}]},{\"key\":\"0C\",\"name\":\"控制反馈帧\",\"datas\":[{\"name\":\"执行结果反馈\",\"len\":1,\"format\":\"BIN\",\"postions\":[1],\"optionFlag\":true,\"options\":{\"01\":\"成功\",\"02\":\"失败\"}}]},{\"key\":\"0F\",\"name\":\"模块信息下发\",\"datas\":[{\"name\":\"最高电压\",\"len\":2,\"format\":\"BIN\",\"postions\":[1,2],\"optionFlag\":false,\"options\":null},{\"name\":\"最低电压\",\"len\":2,\"format\":\"BIN\",\"postions\":[3,4],\"optionFlag\":false,\"options\":null},{\"name\":\"最大电流（总）\",\"len\":2,\"format\":\"BIN\",\"postions\":[5,6],\"optionFlag\":false,\"options\":null},{\"name\":\"最小电流\",\"len\":2,\"format\":\"BIN\",\"postions\":[7,8],\"optionFlag\":false,\"options\":null}]},{\"key\":\"10\",\"name\":\"模块信息反馈帧\",\"datas\":[{\"name\":\"模块信息确认\",\"len\":8,\"format\":\"BIN\",\"postions\":[1,2,3,4,5,6,7,8],\"optionFlag\":false,\"options\":null}]},{\"key\":\"1C\",\"name\":\"启动充电完成帧\",\"datas\":[{\"name\":\"启动原因\",\"len\":1,\"format\":\"BIN\",\"postions\":[1],\"optionFlag\":true,\"options\":{\"01\":\"计费单元手动启动\",\"02\":\"绝缘检测启动\",\"03\":\"预充启动\",\"04\":\"BMS重启充电\"}}]},{\"key\":\"1D\",\"name\":\"启动充电完成应答帧\",\"datas\":[{\"name\":\"执行结果反馈\",\"len\":1,\"format\":\"BIN\",\"postions\":[1],\"optionFlag\":true,\"options\":{\"01\":\"成功\",\"02\":\"失败\"}}]},{\"key\":\"1E\",\"name\":\"停止充电完成帧\",\"datas\":[{\"name\":\"停机类型\",\"len\":1,\"format\":\"BIN\",\"postions\":[1],\"optionFlag\":true,\"options\":{\"01\":\"计费单元正常停机\",\"02\":\"计费单元故障停机\",\"03\":\"绝缘检测完成停机\",\"04\":\"BMS暂停充电\",\"05\":\"BMS停止充电\",\"06\":\"控制导引故障\",\"07\":\"充电机错误停机\"}},{\"name\":\"停止原因\",\"len\":1,\"format\":\"BIN\",\"postions\":[2,3],\"optionFlag\":true,\"options\":{\"01\":\"TCU正常停止\",\"02\":\"TCU故障停止\",\"10\":\"达到SOC目标值\",\"11\":\"达到总电压目标值\",\"12\":\"达到单体电压目标值\",\"13\":\"绝缘故障\",\"14\":\"输出连接器过温故障\",\"15\":\"BMS元件故障\",\"16\":\"充电连接器故障\",\"17\":\"电池组温度过高故障\",\"18\":\"高压继电器故障\",\"19\":\"CC2电压检测故障\",\"1A\":\"电流过大（超过需求值）\",\"1B\":\"电压异常\",\"1C\":\"其他故障\",\"1D\":\"CRM(00)超时\",\"1E\":\"CRM(AA)超时\",\"1F\":\"CML超时\",\"20\":\"CRO超时\",\"21\":\"CCS超时\",\"22\":\"CST超时\",\"23\":\"CSD超时\",\"24\":\"-2FH预留\",\"30\":\"充电机急停故障\",\"31\":\"充电机门控故障\",\"32\":\"充电机过温故障\",\"33\":\"充电机烟雾告警\",\"34\":\"充电机电表故障\",\"35\":\"母联接触器故障\",\"36\":\"风扇故障\",\"37\":\"充电模块故障\",\"38\":\"交流输入过压\",\"39\":\"交流输入欠压\",\"3A\":\"直流输出过压\",\"3B\":\"直流输出欠压\",\"3C\":\"直流输出过流\",\"3D\":\"直流输出短路\",\"3E\":\"充电机侧TCU通讯超时\",\"3F\":\"交流进线开关故障\",\"40\":\"避雷器故障\",\"50\":\"充电桩急停故障\",\"51\":\"充电桩门控故障\",\"52\":\"充电桩过温故障\",\"53\":\"充电桩烟雾告警\",\"54\":\"充电桩电表故障\",\"55\":\"直流接触器故障\",\"56\":\"放电接触器故障\",\"57\":\"放电电阻故障\",\"58\":\"绝缘检测故障\",\"59\":\"电池反接故障\",\"5A\":\"控制导引故障\",\"5B\":\"辅助电源故障\",\"5C\":\"充电桩侧TCU通讯超时\",\"5D\":\"充电枪电子锁故障\",\"5E\":\"充电枪过温故障\",\"5F\":\"绝缘检测前电池电压大于10V\",\"60\":\"车辆最大允许电压低于模块最小输出电压\",\"61\":\"绝缘电压调节失败\",\"62\":\"电池电压误差大于5%\",\"63\":\"BRM接收超时\",\"64\":\"BCP接收超时\",\"65\":\"BRO（00H）接收超时\",\"66\":\"BRO（AAH）接收超时\",\"67\":\"BCS接收超时\",\"68\":\"BCL接收超时\",\"69\":\"单体电压过高\",\"6A\":\"单体电压过低\",\"6B\":\"SOC过高\",\"6C\":\"SOC过低\",\"6D\":\"过流\",\"6E\":\"温度过高\",\"6F\":\"绝缘异常\",\"70\":\"连接器异常\",\"71\":\"充电电压大于BMS最高允许电压\",\"72\":\"充电暂停时间超过10分钟\",\"73\":\"充电末端车辆均流时间过长\",\"74\":\"电池电压不在模块输出范围之内\",\"75\":\"绝缘检测前输出电压大于200V\",\"76\":\"充电监控未启动\"}}]},{\"key\":\"1F\",\"name\":\"停止充电完成应答帧\",\"datas\":[{\"name\":\"执行结果反馈\",\"len\":1,\"format\":\"BIN\",\"postions\":[1],\"optionFlag\":true,\"options\":{\"01\":\"成功\",\"02\":\"失败\"}}]},{\"key\":\"21\",\"name\":\"监控单元状态帧\",\"datas\":[{\"name\":\"充电监控状态\",\"len\":1,\"format\":\"BIN\",\"postions\":[1],\"optionFlag\":true,\"options\":{\"55\":\"停机\",\"AA\":\"开机\"}},{\"name\":\"启动方式\",\"len\":1,\"format\":\"BIN\",\"postions\":[2],\"optionFlag\":true,\"options\":{\"00\":\"自动\",\"01\":\"手动\"}},{\"name\":\"充电机主从状态\",\"len\":1,\"format\":\"BIN\",\"postions\":[3],\"optionFlag\":true,\"options\":{\"00\":\"主设备\",\"01\":\"从设备\"}},{\"name\":\"充电机充电模式\",\"len\":1,\"format\":\"BIN\",\"postions\":[4],\"optionFlag\":true,\"options\":{\"00\":\"单枪独立充电\",\"01\":\"单枪组合充电\",\"02\":\"双枪组合充电\"}}]},{\"key\":\"22\",\"name\":\"BMS需求帧\",\"datas\":[{\"name\":\"需求电压(值需要*10)\",\"len\":2,\"format\":\"BIN\",\"postions\":[1,2],\"optionFlag\":false,\"options\":null},{\"name\":\"需求电流\",\"len\":2,\"format\":\"BIN\",\"postions\":[3,4],\"optionFlag\":false,\"options\":null},{\"name\":\"充电模式\",\"len\":1,\"format\":\"BIN\",\"postions\":[5],\"optionFlag\":true,\"options\":{\"01\":\"恒压充电\",\"02\":\"恒流充电\"}},{\"name\":\"电池荷电状态(单位1%)\",\"len\":1,\"format\":\"BIN\",\"postions\":[6],\"optionFlag\":false,\"options\":null}]},{\"key\":\"23\",\"name\":\"充电机启停控制帧\",\"datas\":[{\"name\":\"充电控制\",\"len\":1,\"format\":\"BIN\",\"postions\":[1],\"optionFlag\":true,\"options\":{\"AA\":\"启动充电\",\"55\":\"停止充电\"}},{\"name\":\"启动方式\",\"len\":1,\"format\":\"BIN\",\"postions\":[2],\"optionFlag\":true,\"options\":{\"00\":\"自动\",\"01\":\"手动\"}},{\"name\":\"开关位置\",\"len\":1,\"format\":\"BIN\",\"postions\":[3],\"optionFlag\":true,\"options\":{\"00\":\"无意义\",\"01\":\"A母线\",\"02\":\"B母线\",\"03\":\"C母线\",\"04\":\"D母线\"}},{\"name\":\"启动原因/停止原因\",\"len\":1,\"format\":\"BIN\",\"postions\":[4],\"optionFlag\":true,\"options\":{\"01\":\"计费单元手动启动/计费单元正常停机\",\"02\":\"绝缘检测启动/计费单元故障停机\",\"03\":\"预充启动/绝缘检测完成停机\",\"04\":\"BMS重启充电/BMS暂停充电\",\"05\":\"BMS停止充电\",\"06\":\"控制导引故障\",\"07\":\"充电机错误停机\"}},{\"name\":\"充电模式\",\"len\":1,\"format\":\"BIN\",\"postions\":[5],\"optionFlag\":true,\"options\":{\"00\":\"单枪模式（轮询）\",\"01\":\"双枪模式（两口同充）\",\"02\":\"三枪模式（三口同充）\",\"03\":\"四枪模式（四口同充）\"}},{\"name\":\"充电电压等级\",\"len\":1,\"format\":\"BIN\",\"postions\":[5],\"optionFlag\":true,\"options\":{\"00\":\"0-750V\",\"01\":\"1-500V\"}}]}]";
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		List<Command> commands =  mapper.readValue(str, new TypeReference<List<Command>>(){});
		for(Command com:commands){
			System.out.println(com.getKey());
			System.out.println(com.getName());
			List<Data> datas = com.getDatas() ;
			for(Data data:datas){
				System.out.println("  "+data.getName());
				System.out.println("  "+data.getLen());
			}
			
		}*/
		//String str = new BigInteger("5E", 16).toString(10);
		//System.out.println(MyUtils.hexStr2Byte(str));
		/*Integer num = Integer.parseInt("12", 16);
	    System.out.println(Integer.toBinaryString(num));
	    System.out.println(MyUtils.hexToTen("16"));
	    System.out.println(MyUtils.hexStringToByte("115"));
	    System.out.println(MyUtils.binaryToTen("111"));*/
	    //MyUtils.hexStringToByte("5E1C");
		
		//String str1="88 18 23 20 60 AA 00 01 01 00 01 00 00";
		//String sys = MyUtils.regText(str1,"881[8|0][0|1|2|3|4|6|A][0-9|A-F][A|6|2|E][0-9][A|6|2|E][0-9]");
		//String str = str1.replaceFirst("881[8|0][0|1|2|3|4|6|A][0-9|A-F][A|6|2|E][0-9][A|6|2|E][0-9]", "");
		//String sys  = MyUtils.regText(str1, "88\\s[1][8|0]\\s[0|1|2|3|4|6|A|a]\\w\\s[A|a|6|2|E|e][0-9]\\s[A|a6|2|E|e][0-9](\\s\\w\\w){8}");
		//System.out.println(sys);
		//System.out.println(str);
		//System.out.println(System.currentTimeMillis());
		String dstr = "20190823105757100";
		String text = dstr.substring(0, 4) + "-" + dstr.substring(4, 6) + "-" + dstr.substring(6, 8) + " " + dstr.substring(8, 10) + ":"
		+ dstr.substring(10, 12) + ":" + dstr.substring(12, 14)+":"+dstr.substring(14, 17);
		System.out.println(text) ;
		//text ="FE 20 00 02 05 00 00 A1 02 32 30 33 31 39 30 33 31 39 30 30 30 30 30 30 30 30 30 30 31 00 04 06 03 78 16"; 
		//text ="FE 3F 00 02 05 00 00 BF 07 32 30 33 31 39 30 33 31 39 30 30 30 30 30 30 30 30 30 30 31 00 02 0A 30 30 30 30 30 30 30 30 30 30 30 30 30 30 30 30 30 30 30 30 30 30 30 30 30 33 38 32 33 30 32 35 78 16";
		//text ="FE 21 00 02 05 00 00 AE 07 32 30 33 31 39 30 33 31 39 30 30 30 30 30 30 30 30 30 30 31 00 02 17 01 01 67 16";
		//text = "FE 33 00 02 06 00 00 04 00 30 30 30 30 30 30 30 30 30 30 30 30 30 30 31 30 30 30 30 30 01 01 01 56 32 2E 36 2E 30 2E 32 0A 0A 00 00 00 00 00 00 00 00 00 00 B3 16";
	    //text="FE 89 02 02 06 19 4C 29 00 30 34 34 30 34 30 30 30 30 30 30 32 30 37 38 30 30 30 30 31 01 01 0F 02 31 37 35 31 31 31 31 31 31 31 31 00 00 00 00 00 00 39 36 45 37 39 32 31 38 39 36 35 45 42 37 32 43 39 32 41 35 34 39 44 44 35 41 33 33 30 31 31 32 02 00 31 8C AA 58 00 00 00 18 00 00 50 C3 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 42 8C AA 58 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 1F 8C AA 58 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 01 F0 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 20 4E 00 00 3F 16";
		//MessageElianUtil.comParase(text);

		//System.out.println(toSplitStringHex("0x10316020"));
	}
	

	    public static String toSplitStringHex(String str) {
	    	
	    	String result = "" ;
	    	str = str.replace("0x", "") ;
	    	for(int i = 0 ; i < str.length() ;){
	    		result += str.substring(i, i+2)+" " ;
	    		i = i +2 ;
	    	}
	    	return result ;
	    }
}
