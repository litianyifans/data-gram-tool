package com.titans.serialport.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.titans.serialport.bean.ELCommand;
import com.titans.serialport.bean.ELData;
import com.titans.serialport.costant.SysParamConst;

public class MessageElianUtil {
	private static String protocol = "[{\"key\":\"01\",\"name\":\"版本更新请求上行\",\"type\":\"00\",\"datas\":[{\"name\":\"版本号\",\"group\":\"01\",\"len\":20,\"format\":\"ASCII\",\"start\":1,\"optionFlag\":false,\"options\":null}]},{\"key\":\"02\",\"name\":\"版本更新信息下行\",\"type\":\"00\",\"datas\":[{\"name\":\"是否有更新\",\"group\":\"01\",\"len\":1,\"format\":\"BIN\",\"start\":1,\"optionFlag\":true,\"options\":{\"0\":\"无更新\",\"1\":\"有更新\"}},{\"name\":\"版本号\",\"group\":\"01\",\"len\":20,\"format\":\"ASCII\",\"start\":2,\"optionFlag\":false,\"options\":null},{\"name\":\"账户名\",\"group\":\"01\",\"len\":32,\"format\":\"ASCII\",\"start\":22,\"optionFlag\":false,\"options\":null},{\"name\":\"密码\",\"group\":\"01\",\"len\":32,\"format\":\"ASCII\",\"start\":54,\"optionFlag\":false,\"options\":null},{\"name\":\"更新包URL\",\"group\":\"01\",\"len\":200,\"format\":\"ASCII\",\"start\":86,\"optionFlag\":false,\"options\":null}]},{\"key\":\"03\",\"name\":\"系统对时请求上行\",\"type\":\"00\",\"datas\":[{\"name\":\"电桩时间\",\"group\":\"01\",\"len\":7,\"format\":\"CP56Time2a\",\"start\":1,\"optionFlag\":false,\"options\":null}]},{\"key\":\"04\",\"name\":\"系统对时信息下行\",\"type\":\"00\",\"datas\":[{\"name\":\"电桩时间\",\"group\":\"01\",\"len\":7,\"format\":\"CP56Time2a\",\"start\":1,\"optionFlag\":false,\"options\":null},{\"name\":\"平台时间\",\"group\":\"01\",\"len\":7,\"format\":\"CP56Time2a\",\"start\":8,\"optionFlag\":false,\"options\":null}]},{\"key\":\"05\",\"name\":\"心跳信息上行\",\"type\":\"00\",\"datas\":[{\"name\":\"电桩物理状态\",\"group\":\"01\",\"len\":1,\"format\":\"BIN\",\"start\":1,\"optionFlag\":true,\"options\":{\"1\":\"空闲中\",\"2\":\"充电中\",\"3\":\"故障中\",\"4\":\"维护中\",\"5\":\"满电占用中\",\"6\":\"未满电占用中\"}},{\"name\":\"充电枪状态\",\"group\":\"01\",\"len\":1,\"format\":\"BIN\",\"start\":2,\"optionFlag\":true,\"options\":{\"1\":\"未连接\",\"2\":\"完全连接\"}},{\"name\":\"车位占用状态\",\"group\":\"01\",\"len\":1,\"format\":\"BIN\",\"start\":3,\"optionFlag\":true,\"options\":{\"1\":\"未占用\",\"2\":\"已占用\",\"3\":\"不可信\"}},{\"name\":\"保留字段\",\"group\":\"01\",\"len\":1,\"format\":\"BIN\",\"start\":4,\"optionFlag\":false,\"options\":null},{\"name\":\"错误代码\",\"group\":\"01\",\"len\":1,\"format\":\"BIN\",\"start\":5,\"optionFlag\":true,\"options\":{\"0\":\"无故障\",\"1\":\"急停故障\",\"2\":\"电表故障\",\"3\":\"接触器故障\",\"4\":\"读卡器故障\",\"5\":\"内部过温故障\",\"6\":\"连接器故障\",\"7\":\"绝缘故障\",\"8\":\"充电机过温\",\"9\":\"AC输入过压\",\"10\":\"AC输入欠压\",\"11\":\"DC输出过压\",\"12\":\"DC输出欠压\",\"13\":\"DC输出过流\",\"14\":\"DC输出短路\",\"15\":\"充电模块故障\",\"16\":\"火灾告警\",\"17\":\"地锁故障\",\"18\":\"充电机柜门异常\",\"19\":\"执行通讯超时\",\"20\":\"模块启动失败\",\"21\":\"直流接触器故障\",\"22\":\"放电接触器故障\",\"23\":\"电子锁故障\",\"24\":\"绝缘检测故障\",\"25\":\"电池反接故障\",\"26\":\"控制导引故障\",\"27\":\"充电桩过温\",\"28\":\"充电枪过温\",\"29\":\"BMS通讯异常\",\"30\":\"充电桩柜门异常\",\"31\":\"桩内部通讯异常\",\"32\":\"风扇故障\",\"255\":\"其它故障\"}},{\"name\":\"电度表计数\",\"group\":\"01\",\"len\":4,\"format\":\"BIN\",\"start\":6,\"optionFlag\":false,\"options\":{\"power\":\"0.01\"}},{\"name\":\"地锁状态\",\"group\":\"01\",\"len\":1,\"format\":\"BIN\",\"start\":10,\"optionFlag\":true,\"options\":{\"0\":\"无地锁\",\"1\":\"开锁态\",\"2\":\"闭锁态\",\"3\":\"合法中间态\",\"4\":\"非法中间态\",\"5\":\"故障\",\"6\":\"地锁正在运动\"}},{\"name\":\"枪是否归位\",\"group\":\"01\",\"len\":1,\"format\":\"BIN\",\"start\":11,\"optionFlag\":true,\"options\":{\"0\":\"未归位\",\"1\":\"归位\"}}]},{\"key\":\"06\",\"name\":\"心跳信息下行\",\"type\":\"00\",\"datas\":[{\"name\":\"电桩逻辑状态\",\"group\":\"01\",\"len\":1,\"format\":\"BIN\",\"start\":1,\"optionFlag\":true,\"options\":{\"1\":\"空状态\",\"2\":\"空闲中\",\"3\":\"锁定中\"}}]},{\"key\":\"07\",\"name\":\"即时预约请求上行\",\"type\":\"00\",\"datas\":[{\"name\":\"登入方式\",\"group\":\"01\",\"len\":1,\"format\":\"BIN\",\"start\":1,\"optionFlag\":true,\"options\":{\"1\":\"二维码\",\"2\":\"手机号\",\"3\":\"刷卡\",\"4\":\"Vin码\"}},{\"name\":\"手机号/卡号/Vin码\",\"group\":\"01\",\"len\":17,\"format\":\"ASCII\",\"start\":2,\"optionFlag\":false,\"options\":null},{\"name\":\"充电时长(分钟)\",\"group\":\"01\",\"len\":2,\"format\":\"BIN\",\"start\":19,\"optionFlag\":false,\"options\":null},{\"name\":\"预约开始时间\",\"group\":\"01\",\"len\":7,\"format\":\"CP56Time2a\",\"start\":21,\"optionFlag\":false,\"options\":null},{\"name\":\"预约方式\",\"group\":\"01\",\"len\":1,\"format\":\"BIN\",\"start\":28,\"optionFlag\":true,\"options\":{\"1\":\"单枪预约\",\"2\":\"组合预约\"}},{\"name\":\"预约序号\",\"group\":\"01\",\"len\":2,\"format\":\"ASCII\",\"start\":29,\"optionFlag\":false,\"options\":null},{\"name\":\"预约枪口号\",\"group\":\"01\",\"len\":2,\"format\":\"BIN\",\"start\":31,\"optionFlag\":false,\"options\":null}]},{\"key\":\"08\",\"name\":\"即时预约响应下行\",\"type\":\"00\",\"datas\":[{\"name\":\"验证是否通过\",\"group\":\"01\",\"len\":1,\"format\":\"BIN\",\"start\":1,\"optionFlag\":true,\"options\":{\"1\":\"验证通过\",\"2\":\"验证失败\"}},{\"name\":\"交易流水号\",\"group\":\"01\",\"len\":32,\"format\":\"ASCII\",\"start\":2,\"optionFlag\":false,\"options\":null},{\"name\":\"预约开始时间\",\"group\":\"01\",\"len\":7,\"format\":\"CP56Time2a\",\"start\":34,\"optionFlag\":false,\"options\":null},{\"name\":\"预约时长\",\"group\":\"01\",\"len\":2,\"format\":\"BIN\",\"start\":41,\"optionFlag\":false,\"options\":null},{\"name\":\"错误代码\",\"group\":\"01\",\"len\":1,\"format\":\"BIN\",\"start\":43,\"optionFlag\":true,\"options\":{\"01\":\"余额不足\",\"02\":\"账号正在使用中\",\"03\":\"账号锁定\",\"04\":\"没有该用户\",\"05\":\"其它\"}}]},{\"key\":\"09\",\"name\":\"交易账单提交上行\",\"type\":\"00\",\"datas\":[{\"name\":\"交易流水号\",\"group\":\"01\",\"len\":32,\"format\":\"ASCII\",\"start\":1,\"optionFlag\":false,\"options\":null},{\"name\":\"到达时间\",\"group\":\"01\",\"len\":7,\"format\":\"CP56Time2a\",\"start\":33,\"optionFlag\":false,\"options\":null},{\"name\":\"充电开始时间\",\"group\":\"01\",\"len\":7,\"format\":\"CP56Time2a\",\"start\":40,\"optionFlag\":false,\"options\":null},{\"name\":\"充电时长(分钟)\",\"group\":\"01\",\"len\":2,\"format\":\"BIN\",\"start\":47,\"optionFlag\":false,\"options\":null},{\"name\":\"占桩时长(分钟)\",\"group\":\"01\",\"len\":2,\"format\":\"BIN\",\"start\":49,\"optionFlag\":false,\"options\":null},{\"name\":\"占桩费用(元)\",\"group\":\"01\",\"len\":4,\"format\":\"BIN\",\"start\":51,\"optionFlag\":false,\"options\":null},{\"name\":\"离桩时间(分钟)\",\"group\":\"01\",\"len\":7,\"format\":\"CP56Time2a\",\"start\":55,\"optionFlag\":false,\"options\":null},{\"name\":\"阶段数量\",\"group\":\"01\",\"len\":1,\"format\":\"BIN\",\"start\":62,\"optionFlag\":false,\"options\":null},{\"name\":\"电量\",\"group\":\"03\",\"len\":2,\"format\":\"BIN\",\"start\":63,\"optionFlag\":false,\"options\":null},{\"name\":\"充电费用\",\"group\":\"01\",\"len\":4,\"format\":\"BIN\",\"start\":0,\"optionFlag\":false,\"options\":{\"power\":\"0.01\"}},{\"name\":\"服务费用\",\"group\":\"01\",\"len\":4,\"format\":\"BIN\",\"start\":0,\"optionFlag\":false,\"options\":{\"power\":\"0.01\"}}]},{\"key\":\"10\",\"name\":\"交易账单确认下行\",\"type\":\"00\",\"datas\":[{\"name\":\"交易流水号\",\"group\":\"01\",\"len\":32,\"format\":\"ASCII\",\"start\":1,\"optionFlag\":false,\"options\":null}]},{\"key\":\"11\",\"name\":\"提前结束充电请求上行\",\"type\":\"00\",\"datas\":[{\"name\":\"登入方式\",\"group\":\"01\",\"len\":1,\"format\":\"BIN\",\"start\":1,\"optionFlag\":true,\"options\":{\"1\":\"二维码\",\"2\":\"手机号\",\"3\":\"刷卡\",\"4\":\"Vin码\"}},{\"name\":\"手机号/卡号/Vin码\",\"group\":\"01\",\"len\":17,\"format\":\"ASCII\",\"start\":2,\"optionFlag\":false,\"options\":null},{\"name\":\"密码\",\"group\":\"01\",\"len\":32,\"format\":\"String\",\"start\":19,\"optionFlag\":false,\"options\":null}]},{\"key\":\"12\",\"name\":\"提前结束充电响应下行\",\"type\":\"00\",\"datas\":[{\"name\":\"登入方式\",\"group\":\"01\",\"len\":1,\"format\":\"BIN\",\"start\":1,\"optionFlag\":true,\"options\":{\"1\":\"二维码\",\"2\":\"手机号\",\"3\":\"刷卡\",\"4\":\"Vin码\"}},{\"name\":\"手机号/卡号/Vin码\",\"group\":\"01\",\"len\":17,\"format\":\"ASCII\",\"start\":2,\"optionFlag\":false,\"options\":null},{\"name\":\"验证结果\",\"group\":\"01\",\"len\":1,\"format\":\"BIN\",\"start\":19,\"optionFlag\":true,\"options\":{\"1\":\"成功\",\"2\":\"失败\"}}]},{\"key\":\"13\",\"name\":\"提前结束充电响应反馈上行\",\"type\":\"00\",\"datas\":[{\"name\":\"登入方式\",\"group\":\"01\",\"len\":1,\"format\":\"BIN\",\"start\":1,\"optionFlag\":true,\"options\":{\"1\":\"二维码\",\"2\":\"手机号\",\"3\":\"刷卡\",\"4\":\"Vin码\"}},{\"name\":\"手机号/卡号/Vin码\",\"group\":\"01\",\"len\":17,\"format\":\"ASCII\",\"start\":2,\"optionFlag\":false,\"options\":null}]},{\"key\":\"14\",\"name\":\"登入验证请求上行\",\"type\":\"00\",\"datas\":[{\"name\":\"登入方式\",\"group\":\"01\",\"len\":1,\"format\":\"BIN\",\"start\":1,\"optionFlag\":true,\"options\":{\"1\":\"二维码\",\"2\":\"手机号\",\"3\":\"刷卡\",\"4\":\"Vin码\"}},{\"name\":\"手机号/卡号/Vin码\",\"group\":\"01\",\"len\":17,\"format\":\"ASCII\",\"start\":2,\"optionFlag\":false,\"options\":null},{\"name\":\"密码\",\"group\":\"01\",\"len\":32,\"format\":\"String\",\"start\":19,\"optionFlag\":false,\"options\":null}]},{\"key\":\"15\",\"name\":\"登入验证响应下行\",\"type\":\"00\",\"datas\":[{\"name\":\"登入方式\",\"group\":\"01\",\"len\":1,\"format\":\"BIN\",\"start\":1,\"optionFlag\":true,\"options\":{\"1\":\"二维码\",\"2\":\"手机号\",\"3\":\"刷卡\",\"4\":\"Vin码\"}},{\"name\":\"手机号/卡号/Vin码\",\"group\":\"01\",\"len\":17,\"format\":\"ASCII\",\"start\":2,\"optionFlag\":false,\"options\":null},{\"name\":\"密码\",\"group\":\"01\",\"len\":32,\"format\":\"String\",\"start\":19,\"optionFlag\":false,\"options\":null},{\"name\":\"占桩时间(分钟)\",\"group\":\"01\",\"len\":2,\"format\":\"BIN\",\"start\":51,\"optionFlag\":false,\"options\":null},{\"name\":\"占桩费率版本号\",\"group\":\"01\",\"len\":4,\"format\":\"BIN\",\"start\":53,\"optionFlag\":false,\"options\":null},{\"name\":\"占桩开始时间\",\"group\":\"02\",\"len\":3,\"format\":\"BIN\",\"start\":57,\"optionFlag\":false,\"options\":null},{\"name\":\"占桩结束时间\",\"group\":\"02\",\"len\":3,\"format\":\"BIN\",\"start\":60,\"optionFlag\":false,\"options\":null},{\"name\":\"占桩费率\",\"group\":\"02\",\"len\":4,\"format\":\"BIN\",\"start\":63,\"optionFlag\":false,\"options\":null},{\"name\":\"服务费率版本号\",\"group\":\"01\",\"len\":4,\"format\":\"BIN\",\"start\":177,\"optionFlag\":false,\"options\":null},{\"name\":\"服务费率数据\",\"group\":\"01\",\"len\":192,\"format\":\"BIN\",\"start\":181,\"optionFlag\":false,\"options\":{\"fl\":\"1\"}},{\"name\":\"电价费率版本号\",\"group\":\"01\",\"len\":4,\"format\":\"BIN\",\"start\":373,\"optionFlag\":false,\"options\":null},{\"name\":\"电价费率数据\",\"group\":\"01\",\"len\":192,\"format\":\"BIN\",\"start\":377,\"optionFlag\":false,\"options\":{\"fl\":\"2\"}},{\"name\":\"当前最大可用充电时长\",\"group\":\"01\",\"len\":2,\"format\":\"BIN\",\"start\":569,\"optionFlag\":false,\"options\":null},{\"name\":\"预约状态\",\"group\":\"01\",\"len\":1,\"format\":\"BIN\",\"start\":571,\"optionFlag\":true,\"options\":{\"0\":\"无预约\",\"1\":\"已预约其他桩\",\"2\":\"已预约当前桩\"}},{\"name\":\"交易流水号\",\"group\":\"01\",\"len\":32,\"format\":\"ASCII\",\"start\":572,\"optionFlag\":false,\"options\":null},{\"name\":\"预约开始时间\",\"group\":\"01\",\"len\":7,\"format\":\"CP56Time2a\",\"start\":604,\"optionFlag\":false,\"options\":null},{\"name\":\"预约时长\",\"group\":\"01\",\"len\":2,\"format\":\"BIN\",\"start\":611,\"optionFlag\":false,\"options\":null},{\"name\":\"错误代码\",\"group\":\"01\",\"len\":1,\"format\":\"BIN\",\"start\":613,\"optionFlag\":true,\"options\":{\"31\":\"非此充电桩\",\"32\":\"尚有账单未结清\",\"33\":\"此射频卡无订单\",\"01\":\"密码输入错误\",\"02\":\"用户不存在\",\"03\":\"账户冻结\",\"04\":\"卡挂失\",\"05\":\"其他\",\"06\":\"预留\"}},{\"name\":\"预约时长\",\"group\":\"01\",\"len\":4,\"format\":\"BIN\",\"start\":614,\"optionFlag\":false,\"options\":null}]},{\"key\":\"16\",\"name\":\"登入验证响应反馈上行\",\"type\":\"00\",\"datas\":[{\"name\":\"登入方式\",\"group\":\"01\",\"len\":1,\"format\":\"BIN\",\"start\":1,\"optionFlag\":true,\"options\":{\"1\":\"二维码\",\"2\":\"手机号\",\"3\":\"刷卡\",\"4\":\"Vin码\"}},{\"name\":\"手机号/卡号/Vin码\",\"group\":\"01\",\"len\":17,\"format\":\"ASCII\",\"start\":2,\"optionFlag\":false,\"options\":null},{\"name\":\"密码\",\"group\":\"01\",\"len\":32,\"format\":\"String\",\"start\":19,\"optionFlag\":false,\"options\":null}]},{\"key\":\"17\",\"name\":\"查询用户资料请求上行\",\"type\":\"00\",\"datas\":[{\"name\":\"登入方式\",\"group\":\"01\",\"len\":1,\"format\":\"BIN\",\"start\":1,\"optionFlag\":true,\"options\":{\"1\":\"二维码\",\"2\":\"手机号\",\"3\":\"刷卡\",\"4\":\"Vin码\"}},{\"name\":\"手机号/卡号/Vin码\",\"group\":\"01\",\"len\":17,\"format\":\"ASCII\",\"start\":2,\"optionFlag\":false,\"options\":null},{\"name\":\"密码\",\"group\":\"01\",\"len\":32,\"format\":\"String\",\"start\":19,\"optionFlag\":false,\"options\":null}]},{\"key\":\"18\",\"name\":\"查询用户资料信息下行\",\"type\":\"00\",\"datas\":[{\"name\":\"登入方式\",\"group\":\"01\",\"len\":1,\"format\":\"BIN\",\"start\":1,\"optionFlag\":true,\"options\":{\"1\":\"二维码\",\"2\":\"手机号\",\"3\":\"刷卡\",\"4\":\"Vin码\"}},{\"name\":\"手机号/卡号/Vin码\",\"group\":\"01\",\"len\":17,\"format\":\"ASCII\",\"start\":2,\"optionFlag\":false,\"options\":null},{\"name\":\"密码\",\"group\":\"01\",\"len\":1,\"format\":\"BIN\",\"start\":19,\"optionFlag\":true,\"options\":{\"0\":\"失败\",\"1\":\"成功\"}},{\"name\":\"余额\",\"group\":\"01\",\"len\":4,\"format\":\"BIN\",\"start\":20,\"optionFlag\":false,\"options\":null}]},{\"key\":\"19\",\"name\":\"登出通知上行\",\"type\":\"00\",\"datas\":[{\"name\":\"登入方式\",\"group\":\"01\",\"len\":1,\"format\":\"BIN\",\"start\":1,\"optionFlag\":true,\"options\":{\"1\":\"二维码\",\"2\":\"手机号\",\"3\":\"刷卡\",\"4\":\"Vin码\"}},{\"name\":\"手机号/卡号/Vin码\",\"group\":\"01\",\"len\":17,\"format\":\"ASCII\",\"start\":2,\"optionFlag\":false,\"options\":null},{\"name\":\"登出原因\",\"group\":\"01\",\"len\":1,\"format\":\"String\",\"start\":19,\"optionFlag\":true,\"options\":{\"1\":\"超时登出\",\"2\":\"主动登出\"}}]},{\"key\":\"20\",\"name\":\"开始充电响应下行\",\"type\":\"00\",\"datas\":[{\"name\":\"登入方式\",\"group\":\"01\",\"len\":1,\"format\":\"BIN\",\"start\":1,\"optionFlag\":true,\"options\":{\"1\":\"二维码\",\"2\":\"手机号\",\"3\":\"刷卡\",\"4\":\"Vin码\"}},{\"name\":\"手机号/卡号/Vin码\",\"group\":\"01\",\"len\":17,\"format\":\"ASCII\",\"start\":2,\"optionFlag\":false,\"options\":null},{\"name\":\"密码\",\"group\":\"01\",\"len\":32,\"format\":\"String\",\"start\":19,\"optionFlag\":false,\"options\":null}]},{\"key\":\"21\",\"name\":\"开始充电响应反馈上行\",\"type\":\"00\",\"datas\":[{\"name\":\"登入方式\",\"group\":\"01\",\"len\":1,\"format\":\"BIN\",\"start\":1,\"optionFlag\":true,\"options\":{\"1\":\"二维码\",\"2\":\"手机号\",\"3\":\"刷卡\",\"4\":\"Vin码\"}},{\"name\":\"手机号/卡号/Vin码\",\"group\":\"01\",\"len\":17,\"format\":\"ASCII\",\"start\":2,\"optionFlag\":false,\"options\":null},{\"name\":\"密码\",\"group\":\"01\",\"len\":32,\"format\":\"String\",\"start\":19,\"optionFlag\":false,\"options\":null}]},{\"key\":\"22\",\"name\":\"桩状态变化报告上行\",\"type\":\"00\",\"datas\":[{\"name\":\"电桩物理状态\",\"group\":\"01\",\"len\":1,\"format\":\"BIN\",\"start\":1,\"optionFlag\":true,\"options\":{\"1\":\"空闲中\",\"2\":\"充电中\",\"3\":\"故障中\",\"4\":\"维护中\",\"5\":\"满电占用中\",\"6\":\"未满电占用中\"}},{\"name\":\"充电枪状态\",\"group\":\"01\",\"len\":1,\"format\":\"BIN\",\"start\":2,\"optionFlag\":true,\"options\":{\"1\":\"未连接\",\"2\":\"完全连接\"}},{\"name\":\"车位占用状态\",\"group\":\"01\",\"len\":1,\"format\":\"BIN\",\"start\":3,\"optionFlag\":true,\"options\":{\"1\":\"未占用\",\"2\":\"已占用\",\"3\":\"不可信\"}},{\"name\":\"故障信息个数\",\"group\":\"01\",\"len\":1,\"format\":\"BIN\",\"start\":3,\"optionFlag\":false,\"options\":null},{\"name\":\"故障信息块\",\"group\":\"01\",\"len\":0,\"format\":\"BIN\",\"start\":999,\"optionFlag\":false,\"options\":null},{\"name\":\"停止充电原因\",\"group\":\"01\",\"len\":1,\"format\":\"BIN\",\"start\":0,\"optionFlag\":false,\"options\":{\"bit\":\"88\"}},{\"name\":\"错误代码\",\"group\":\"01\",\"len\":1,\"format\":\"BIN\",\"start\":0,\"optionFlag\":false,\"options\":{\"0\":\"无故障\",\"1\":\"急停故障\",\"2\":\"电表故障\",\"3\":\"接触器故障\",\"4\":\"读卡器故障\",\"5\":\"内部过温故障\",\"6\":\"连接器故障\",\"7\":\"绝缘故障\",\"8\":\"充电机过温\",\"9\":\"AC输入过压\",\"10\":\"AC输入欠压\",\"11\":\"DC输出过压\",\"12\":\"DC输出欠压\",\"13\":\"DC输出过流\",\"14\":\"DC输出短路\",\"15\":\"充电模块故障\",\"16\":\"火灾告警\",\"17\":\"地锁故障\",\"18\":\"充电机柜门异常\",\"19\":\"执行通讯超时\",\"20\":\"模块启动失败\",\"21\":\"直流接触器故障\",\"22\":\"放电接触器故障\",\"23\":\"电子锁故障\",\"24\":\"绝缘检测故障\",\"25\":\"电池反接故障\",\"26\":\"控制导引故障\",\"27\":\"充电桩过温\",\"28\":\"充电枪过温\",\"29\":\"BMS通讯异常\",\"30\":\"充电桩柜门异常\",\"31\":\"桩内部通讯异常\",\"32\":\"风扇故障\",\"255\":\"其它故障\"}},{\"name\":\"桩状态变化时间\",\"group\":\"01\",\"len\":7,\"format\":\"CP56Time2a\",\"start\":0,\"optionFlag\":false,\"options\":null},{\"name\":\"地锁状态\",\"group\":\"01\",\"len\":1,\"format\":\"BIN\",\"start\":0,\"optionFlag\":false,\"options\":{\"0\":\"无地锁\",\"1\":\"开锁态\",\"2\":\"闭锁态\",\"3\":\"合法中间态\",\"4\":\"非法中间态\",\"5\":\"故障\",\"6\":\"地锁正在运动\"}},{\"name\":\"枪是否归位\",\"group\":\"01\",\"len\":1,\"format\":\"BIN\",\"start\":0,\"optionFlag\":false,\"options\":{\"0\":\"未归位\",\"1\":\"归位\"}}]},{\"key\":\"23\",\"name\":\"桩状态变化报告反馈下行\",\"type\":\"00\",\"datas\":[{\"name\":\"确认接收\",\"group\":\"01\",\"len\":1,\"format\":\"BIN\",\"start\":1,\"optionFlag\":true,\"options\":{\"1\":\"成功\",\"2\":\"失败\"}},{\"name\":\"电桩逻辑状态\",\"group\":\"01\",\"len\":1,\"format\":\"BIN\",\"start\":2,\"optionFlag\":true,\"options\":{\"1\":\"空状态\",\"2\":\"空闲中\",\"3\":\"锁定中\"}}]},{\"key\":\"24\",\"name\":\"开始充电请求上行\",\"type\":\"00\",\"datas\":[{\"name\":\"登入方式\",\"group\":\"01\",\"len\":1,\"format\":\"BIN\",\"start\":1,\"optionFlag\":true,\"options\":{\"1\":\"二维码\",\"2\":\"手机号\",\"3\":\"刷卡\",\"4\":\"Vin码\"}},{\"name\":\"手机号/卡号/Vin码\",\"group\":\"01\",\"len\":17,\"format\":\"ASCII\",\"start\":2,\"optionFlag\":false,\"options\":null},{\"name\":\"交易流水号\",\"group\":\"01\",\"len\":32,\"format\":\"ASCII\",\"start\":19,\"optionFlag\":false,\"options\":null}]},{\"key\":\"25\",\"name\":\"车辆基础信息\",\"type\":\"00\",\"datas\":[{\"name\":\"动力蓄电池类型\",\"group\":\"01\",\"len\":1,\"format\":\"BIN\",\"start\":1,\"optionFlag\":true,\"options\":{\"1\":\"铅酸电池\",\"2\":\"镍氢电池\",\"3\":\"磷酸铁锂电池\",\"4\":\"锰酸锂电池\",\"5\":\"钴酸锂电池\",\"6\":\"三元材料电池\",\"7\":\"三元材料电池\",\"8\":\"三元材料电池\",\"255\":\"其他电池\"}},{\"name\":\"动力蓄电池组额定容量\",\"group\":\"01\",\"len\":2,\"format\":\"BIN\",\"start\":2,\"optionFlag\":false,\"options\":{\"power\":\"0.1\"}},{\"name\":\"动力蓄电池额定总电压\",\"group\":\"01\",\"len\":4,\"format\":\"BIN\",\"start\":4,\"optionFlag\":false,\"options\":{\"power\":\"0.01\"}},{\"name\":\"充电前动力蓄电池总电压\",\"group\":\"01\",\"len\":4,\"format\":\"BIN\",\"start\":8,\"optionFlag\":false,\"options\":{\"power\":\"0.01\"}},{\"name\":\"电池生产厂商名\",\"group\":\"01\",\"len\":4,\"format\":\"ASCII\",\"start\":12,\"optionFlag\":false,\"options\":null},{\"name\":\"电池组序号\",\"group\":\"01\",\"len\":4,\"format\":\"BIN\",\"start\":16,\"optionFlag\":false,\"options\":null},{\"name\":\"电池组生产日期\",\"group\":\"01\",\"len\":3,\"format\":\"BIN\",\"start\":20,\"optionFlag\":false,\"options\":null},{\"name\":\"电池组充电次数\",\"group\":\"01\",\"len\":3,\"format\":\"BIN\",\"start\":23,\"optionFlag\":false,\"options\":null},{\"name\":\"电池组产权标识\",\"group\":\"01\",\"len\":1,\"format\":\"BIN\",\"start\":26,\"optionFlag\":true,\"options\":{\"0\":\"租赁\",\"1\":\"自有\"}},{\"name\":\"预留\",\"group\":\"01\",\"len\":1,\"format\":\"BIN\",\"start\":27,\"optionFlag\":false,\"options\":null},{\"name\":\"车辆识别码\",\"group\":\"01\",\"len\":17,\"format\":\"ASCII\",\"start\":28,\"optionFlag\":false,\"options\":null}]},{\"key\":\"26\",\"name\":\"电池充电总信息\",\"type\":\"00\",\"datas\":[{\"name\":\"电压需求\",\"group\":\"01\",\"len\":4,\"format\":\"BIN\",\"start\":1,\"optionFlag\":false,\"options\":{\"power\":\"0.01\"}},{\"name\":\"电流需求\",\"group\":\"01\",\"len\":4,\"format\":\"BIN\",\"start\":5,\"optionFlag\":false,\"options\":{\"power\":\"0.01\"}},{\"name\":\"充电模式\",\"group\":\"01\",\"len\":1,\"format\":\"BIN\",\"start\":9,\"optionFlag\":true,\"options\":{\"1\":\"恒压充电\",\"2\":\"恒流充电\"}},{\"name\":\"充电电压测量值\",\"group\":\"01\",\"len\":4,\"format\":\"BIN\",\"start\":10,\"optionFlag\":false,\"options\":{\"power\":\"0.01\"}},{\"name\":\"充电电流测量值\",\"group\":\"01\",\"len\":4,\"format\":\"BIN\",\"start\":14,\"optionFlag\":false,\"options\":{\"power\":\"0.01\"}},{\"name\":\"当前SOC\",\"group\":\"01\",\"len\":1,\"format\":\"BIN\",\"start\":18,\"optionFlag\":false,\"options\":null},{\"name\":\"估算剩余充电时间\",\"group\":\"01\",\"len\":2,\"format\":\"BIN\",\"start\":19,\"optionFlag\":false,\"options\":null},{\"name\":\"最高单体动力蓄电池电池电压\",\"group\":\"01\",\"len\":4,\"format\":\"BIN\",\"start\":21,\"optionFlag\":false,\"options\":{\"power\":\"0.01\"}},{\"name\":\"最高单体动力蓄电池电压所在编号\",\"group\":\"01\",\"len\":1,\"format\":\"BIN\",\"start\":25,\"optionFlag\":false,\"options\":null},{\"name\":\"最低单体动力蓄电池电池电压\",\"group\":\"01\",\"len\":2,\"format\":\"BIN\",\"start\":26,\"optionFlag\":false,\"options\":null},{\"name\":\"最低单体动力蓄电池电压所在编号\",\"group\":\"01\",\"len\":1,\"format\":\"BIN\",\"start\":28,\"optionFlag\":false,\"options\":null},{\"name\":\"最高动力蓄电池温度\",\"group\":\"01\",\"len\":1,\"format\":\"BIN\",\"start\":29,\"optionFlag\":false,\"options\":null},{\"name\":\"最高温度检测点编号\",\"group\":\"01\",\"len\":1,\"format\":\"BIN\",\"start\":30,\"optionFlag\":false,\"options\":null},{\"name\":\"最低动力蓄电池温度\",\"group\":\"01\",\"len\":1,\"format\":\"BIN\",\"start\":31,\"optionFlag\":false,\"options\":null},{\"name\":\"电池状态1\",\"group\":\"01\",\"len\":1,\"format\":\"BIN\",\"start\":32,\"optionFlag\":false,\"options\":{\"bit\":\"89\"}},{\"name\":\"电池状态2\",\"group\":\"01\",\"len\":1,\"format\":\"BIN\",\"start\":33,\"optionFlag\":false,\"options\":{\"bit\":\"90\"}}]},{\"key\":\"27\",\"name\":\"动力蓄电池充电参数\",\"type\":\"00\",\"datas\":[{\"name\":\"单体动力蓄电池最高允许充电电压\",\"group\":\"01\",\"len\":4,\"format\":\"BIN\",\"start\":1,\"optionFlag\":false,\"options\":{\"power\":\"0.01\"}},{\"name\":\"最高允许充电电流\",\"group\":\"01\",\"len\":4,\"format\":\"BIN\",\"start\":5,\"optionFlag\":false,\"options\":{\"power\":\"0.01\"}},{\"name\":\"动力蓄电池标称总能量\",\"group\":\"01\",\"len\":4,\"format\":\"BIN\",\"start\":9,\"optionFlag\":false,\"options\":{\"power\":\"0.1\"}},{\"name\":\"最高允许充电总电压\",\"group\":\"01\",\"len\":4,\"format\":\"BIN\",\"start\":13,\"optionFlag\":false,\"options\":{\"power\":\"0.01\"}},{\"name\":\"最高允许温度\",\"group\":\"01\",\"len\":1,\"format\":\"BIN\",\"start\":14,\"optionFlag\":false,\"options\":{\"power\":\"0.01\"}},{\"name\":\"整车动力蓄电池荷电状态SOC\",\"group\":\"01\",\"len\":2,\"format\":\"BIN\",\"start\":15,\"optionFlag\":false,\"options\":{\"power\":\"0.01\"}},{\"name\":\"整车动力蓄电池当前电池电压\",\"group\":\"01\",\"len\":4,\"format\":\"BIN\",\"start\":17,\"optionFlag\":false,\"options\":{\"power\":\"0.01\"}}]},{\"key\":\"28\",\"name\":\"单体蓄电池电压信息\",\"type\":\"00\",\"datas\":[{\"name\":\"当前单体蓄电池总单体数\",\"group\":\"01\",\"len\":1,\"format\":\"BIN\",\"start\":1,\"optionFlag\":false,\"options\":null},{\"name\":\"单体蓄电池电压\",\"group\":\"03\",\"len\":2,\"format\":\"BIN\",\"start\":2,\"optionFlag\":false,\"options\":{\"power\":\"0.01\"}}]},{\"key\":\"29\",\"name\":\"电池温度采样点信息\",\"type\":\"00\",\"datas\":[{\"name\":\"当前动力蓄电池温度点采样数\",\"group\":\"01\",\"len\":1,\"format\":\"BIN\",\"start\":1,\"optionFlag\":false,\"options\":null},{\"name\":\"单体蓄电池电压\",\"group\":\"03\",\"len\":0,\"format\":\"BIN\",\"start\":999,\"optionFlag\":false,\"options\":{\"power\":\"0.01\"}}]},{\"key\":\"30\",\"name\":\"充电机基础信息\",\"type\":\"00\",\"datas\":[{\"name\":\"充电机输出电压\",\"group\":\"01\",\"len\":4,\"format\":\"BIN\",\"start\":1,\"optionFlag\":false,\"options\":{\"power\":\"0.01\"}},{\"name\":\"充电机输出电流\",\"group\":\"01\",\"len\":4,\"format\":\"BIN\",\"start\":5,\"optionFlag\":false,\"options\":{\"power\":\"0.01\"}},{\"name\":\"充电模式\",\"group\":\"01\",\"len\":1,\"format\":\"BIN\",\"start\":9,\"optionFlag\":true,\"options\":{\"1\":\"恒压充电\",\"2\":\"恒流充电\"}},{\"name\":\"充电控制方式\",\"group\":\"01\",\"len\":3,\"format\":\"T-BIT\",\"start\":81,\"optionFlag\":true,\"options\":{\"0\":\"充电桩控制\",\"1\":\"本地手动控制\",\"2\":\"后台监控制\",\"3\":\"其它\",\"name\":\"充电机当前状态\"}},{\"name\":\"在线\",\"group\":\"01\",\"len\":1,\"format\":\"T-BIT\",\"start\":84,\"optionFlag\":true,\"options\":{\"0\":\"在线\",\"1\":\"离线\",\"name\":\"充电机当前状态\"}},{\"name\":\"状态\",\"group\":\"01\",\"len\":3,\"format\":\"T-BIT\",\"start\":85,\"optionFlag\":true,\"options\":{\"1\":\"故障\",\"2\":\"故障\",\"3\":\"充电\",\"name\":\"充电机当前状态\"}}]},{\"key\":\"31\",\"name\":\"充电模块总信息\",\"type\":\"00\",\"datas\":[{\"name\":\"充电模块在线模块总数量\",\"group\":\"01\",\"len\":1,\"format\":\"BIN\",\"start\":1,\"optionFlag\":false,\"options\":null},{\"name\":\"充电模块工作模块数量\",\"group\":\"01\",\"len\":1,\"format\":\"BIN\",\"start\":2,\"optionFlag\":false,\"options\":null},{\"name\":\"充电模块最高输出电压\",\"group\":\"01\",\"len\":4,\"format\":\"BIN\",\"start\":3,\"optionFlag\":false,\"options\":{\"power\":\"0.01\"}},{\"name\":\"充电模块最低输出电压\",\"group\":\"01\",\"len\":4,\"format\":\"BIN\",\"start\":7,\"optionFlag\":false,\"options\":{\"power\":\"0.01\"}},{\"name\":\"充电模块最高输出电流\",\"group\":\"01\",\"len\":4,\"format\":\"BIN\",\"start\":11,\"optionFlag\":false,\"options\":{\"power\":\"0.01\"}},{\"name\":\"充电模块最低输出电流\",\"group\":\"01\",\"len\":4,\"format\":\"BIN\",\"start\":15,\"optionFlag\":false,\"options\":{\"power\":\"0.01\"}}]},{\"key\":\"32\",\"name\":\"充电模块详细信息\",\"type\":\"00\",\"datas\":[{\"name\":\"充电模块工作模块数量\",\"group\":\"01\",\"len\":1,\"format\":\"BIN\",\"start\":1,\"optionFlag\":false,\"options\":null},{\"name\":\"模块1序号\",\"group\":\"03\",\"len\":1,\"format\":\"BIN\",\"start\":2,\"optionFlag\":false,\"options\":null},{\"name\":\"充电模块输出电压\",\"group\":\"03\",\"len\":4,\"format\":\"BIN\",\"start\":3,\"optionFlag\":false,\"options\":{\"power\":\"0.01\"}},{\"name\":\"充电模块输出电流\",\"group\":\"03\",\"len\":4,\"format\":\"BIN\",\"start\":7,\"optionFlag\":false,\"options\":{\"power\":\"0.01\"}}]},{\"key\":\"35\",\"name\":\"BMS 停止充电\",\"type\":\"00\",\"datas\":[{\"name\":\"达到需求SOC目标值\",\"group\":\"01\",\"len\":2,\"format\":\"BIT\",\"start\":1,\"optionFlag\":true,\"options\":{\"10\":\"不可信\",\"00\":\"未达到\",\"01\":\"达到\",\"name\":\"BMS中止充电原因\"}},{\"name\":\"达到总电压设定值\",\"group\":\"01\",\"len\":2,\"format\":\"BIT\",\"start\":3,\"optionFlag\":true,\"options\":{\"10\":\"不可信\",\"00\":\"未达到\",\"01\":\"达到\",\"name\":\"BMS中止充电原因\"}},{\"name\":\"达到单体电压设定值\",\"group\":\"01\",\"len\":2,\"format\":\"BIT\",\"start\":5,\"optionFlag\":true,\"options\":{\"10\":\"不可信\",\"00\":\"未达到\",\"01\":\"达到\",\"name\":\"BMS中止充电原因\"}},{\"name\":\"充电机主动停止\",\"group\":\"01\",\"len\":2,\"format\":\"BIT\",\"start\":7,\"optionFlag\":true,\"options\":{\"10\":\"不可信\",\"00\":\"未达到\",\"01\":\"充电机中止\",\"name\":\"BMS中止充电原因\"}},{\"name\":\"绝缘故障\",\"group\":\"01\",\"len\":2,\"format\":\"BIT\",\"start\":9,\"optionFlag\":true,\"options\":{\"10\":\"不可信\",\"00\":\"正常，\",\"01\":\"故障\",\"name\":\"BMS中止充电故障原因\"}},{\"name\":\"输出连接器过温\",\"group\":\"01\",\"len\":2,\"format\":\"BIT\",\"start\":11,\"optionFlag\":true,\"options\":{\"10\":\"不可信\",\"00\":\"正常，\",\"01\":\"故障\",\"name\":\"BMS中止充电故障原因\"}},{\"name\":\"BMS元件\",\"group\":\"01\",\"len\":2,\"format\":\"BIT\",\"start\":13,\"optionFlag\":true,\"options\":{\"10\":\"不可信\",\"00\":\"正常，\",\"01\":\"故障\",\"name\":\"BMS中止充电故障原因\"}},{\"name\":\"充电连接器\",\"group\":\"01\",\"len\":2,\"format\":\"BIT\",\"start\":15,\"optionFlag\":true,\"options\":{\"10\":\"不可信\",\"00\":\"正常，\",\"01\":\"故障\",\"name\":\"BMS中止充电故障原因\"}},{\"name\":\"电池组过温\",\"group\":\"01\",\"len\":2,\"format\":\"BIT\",\"start\":17,\"optionFlag\":true,\"options\":{\"10\":\"不可信\",\"00\":\"正常，\",\"01\":\"故障\",\"name\":\"BMS中止充电故障原因\"}},{\"name\":\"高压继电器故障\",\"group\":\"01\",\"len\":2,\"format\":\"BIT\",\"start\":19,\"optionFlag\":true,\"options\":{\"10\":\"不可信\",\"00\":\"正常，\",\"01\":\"故障\",\"name\":\"BMS中止充电故障原因\"}},{\"name\":\"检测点电压检测故障\",\"group\":\"01\",\"len\":2,\"format\":\"BIT\",\"start\":21,\"optionFlag\":true,\"options\":{\"10\":\"不可信\",\"00\":\"正常，\",\"01\":\"故障\",\"name\":\"BMS中止充电故障原因\"}},{\"name\":\"其他故障\",\"group\":\"01\",\"len\":2,\"format\":\"BIT\",\"start\":23,\"optionFlag\":true,\"options\":{\"10\":\"不可信\",\"00\":\"正常，\",\"01\":\"故障\",\"name\":\"BMS中止充电故障原因\"}},{\"name\":\"电流过大\",\"group\":\"01\",\"len\":2,\"format\":\"BIT\",\"start\":25,\"optionFlag\":true,\"options\":{\"10\":\"不可信\",\"00\":\"正常，\",\"01\":\"过大\",\"name\":\"BMS中止充电错误原因\"}},{\"name\":\"电压异常\",\"group\":\"01\",\"len\":2,\"format\":\"BIT\",\"start\":27,\"optionFlag\":true,\"options\":{\"10\":\"不可信\",\"00\":\"正常，\",\"01\":\"过大\",\"name\":\"BMS中止充电错误原因\"}},{\"name\":\"时间\",\"group\":\"01\",\"len\":7,\"format\":\"CP56Time2a\",\"start\":5,\"optionFlag\":false,\"options\":null}]},{\"key\":\"36\",\"name\":\"充电机停止充电\",\"type\":\"00\",\"datas\":[{\"name\":\"达到充电机设定条件中止\",\"group\":\"01\",\"len\":2,\"format\":\"BIT\",\"start\":1,\"optionFlag\":true,\"options\":{\"10\":\"不可信\",\"00\":\"未达到\",\"01\":\"达到\",\"name\":\"BMS中止充电原因\"}},{\"name\":\"人工中止\",\"group\":\"01\",\"len\":2,\"format\":\"BIT\",\"start\":3,\"optionFlag\":true,\"options\":{\"10\":\"不可信\",\"00\":\"未达到\",\"01\":\"人工中止\",\"name\":\"BMS中止充电原因\"}},{\"name\":\"故障中止\",\"group\":\"01\",\"len\":2,\"format\":\"BIT\",\"start\":5,\"optionFlag\":true,\"options\":{\"10\":\"不可信\",\"00\":\"未达到\",\"01\":\"故障中止\",\"name\":\"BMS中止充电原因\"}},{\"name\":\"BMS主动中止\",\"group\":\"01\",\"len\":2,\"format\":\"BIT\",\"start\":7,\"optionFlag\":true,\"options\":{\"10\":\"不可信\",\"00\":\"未达到\",\"01\":\"BMS主动中止\",\"name\":\"BMS中止充电原因\"}},{\"name\":\"充电机过温故障\",\"group\":\"01\",\"len\":2,\"format\":\"BIT\",\"start\":9,\"optionFlag\":true,\"options\":{\"10\":\"不可信\",\"00\":\"正常，\",\"01\":\"充电机过温故障\",\"name\":\"BMS中止充电故障原因\"}},{\"name\":\"充电连接器故障\",\"group\":\"01\",\"len\":2,\"format\":\"BIT\",\"start\":11,\"optionFlag\":true,\"options\":{\"10\":\"不可信\",\"00\":\"正常，\",\"01\":\"充电连接器故障\",\"name\":\"BMS中止充电故障原因\"}},{\"name\":\"充电机内部过温故障\",\"group\":\"01\",\"len\":2,\"format\":\"BIT\",\"start\":13,\"optionFlag\":true,\"options\":{\"10\":\"不可信\",\"00\":\"正常，\",\"01\":\"充电机内部过温故障\",\"name\":\"BMS中止充电故障原因\"}},{\"name\":\"所需电量不能传送\",\"group\":\"01\",\"len\":2,\"format\":\"BIT\",\"start\":15,\"optionFlag\":true,\"options\":{\"10\":\"不可信\",\"00\":\"正常，\",\"01\":\"电量不能传送\",\"name\":\"BMS中止充电故障原因\"}},{\"name\":\"充电机急停故障\",\"group\":\"01\",\"len\":2,\"format\":\"BIT\",\"start\":17,\"optionFlag\":true,\"options\":{\"10\":\"不可信\",\"00\":\"正常，\",\"01\":\"急停\",\"name\":\"BMS中止充电故障原因\"}},{\"name\":\"其他故障\",\"group\":\"01\",\"len\":2,\"format\":\"BIT\",\"start\":19,\"optionFlag\":true,\"options\":{\"10\":\"不可信\",\"00\":\"正常，\",\"01\":\"故障\",\"name\":\"BMS中止充电故障原因\"}},{\"name\":\"电流不匹配\",\"group\":\"01\",\"len\":2,\"format\":\"BIT\",\"start\":25,\"optionFlag\":true,\"options\":{\"10\":\"不可信\",\"00\":\"正常，\",\"01\":\"电流不匹配\",\"name\":\"BMS中止充电错误原因\"}},{\"name\":\"电压异常\",\"group\":\"01\",\"len\":2,\"format\":\"BIT\",\"start\":27,\"optionFlag\":true,\"options\":{\"10\":\"不可信\",\"00\":\"正常，\",\"01\":\"电压异常\",\"name\":\"BMS中止充电错误原因\"}},{\"name\":\"时间\",\"group\":\"01\",\"len\":7,\"format\":\"CP56Time2a\",\"start\":5,\"optionFlag\":false,\"options\":null}]},{\"key\":\"37\",\"name\":\"地锁控制授权上行\",\"type\":\"00\",\"datas\":[{\"name\":\"登入方式\",\"group\":\"01\",\"len\":1,\"format\":\"BIN\",\"start\":1,\"optionFlag\":true,\"options\":{\"1\":\"二维码\",\"2\":\"手机号\",\"3\":\"刷卡\",\"4\":\"Vin码\"}},{\"name\":\"手机号/卡号/Vin码\",\"group\":\"01\",\"len\":17,\"format\":\"ASCII\",\"start\":2,\"optionFlag\":false,\"options\":null},{\"name\":\"密码\",\"group\":\"01\",\"len\":32,\"format\":\"String\",\"start\":19,\"optionFlag\":false,\"options\":null},{\"name\":\"交易流水号\",\"group\":\"01\",\"len\":32,\"format\":\"ASCII\",\"start\":51,\"optionFlag\":false,\"options\":null},{\"name\":\"操作\",\"group\":\"01\",\"len\":1,\"format\":\"BIN\",\"start\":83,\"optionFlag\":false,\"options\":{\"0\":\"升地锁\",\"1\":\"降地锁\"}},{\"name\":\"操作时间\",\"group\":\"01\",\"len\":7,\"format\":\"CP56Time2a\",\"start\":84,\"optionFlag\":false,\"options\":null}]},{\"key\":\"38\",\"name\":\"地锁控制授权下行\",\"type\":\"00\",\"datas\":[{\"name\":\"登入方式\",\"group\":\"01\",\"len\":1,\"format\":\"BIN\",\"start\":1,\"optionFlag\":true,\"options\":{\"1\":\"二维码\",\"2\":\"手机号\",\"3\":\"刷卡\",\"4\":\"Vin码\"}},{\"name\":\"手机号/卡号/Vin码\",\"group\":\"01\",\"len\":17,\"format\":\"ASCII\",\"start\":2,\"optionFlag\":false,\"options\":null},{\"name\":\"操作\",\"group\":\"01\",\"len\":1,\"format\":\"BIN\",\"start\":19,\"optionFlag\":false,\"options\":{\"0\":\"升地锁\",\"1\":\"降地锁\"}},{\"name\":\"验证结果\",\"group\":\"01\",\"len\":1,\"format\":\"BIN\",\"start\":20,\"optionFlag\":true,\"options\":{\"1\":\"成功\",\"2\":\"失败\"}},{\"name\":\"错误代码\",\"group\":\"01\",\"len\":1,\"format\":\"BIN\",\"start\":21,\"optionFlag\":true,\"options\":{\"01\":\"地锁状态不匹配\",\"02\":\"桩充电中\",\"03\":\"用户无订单\",\"04\":\"非此充电桩\"}},{\"name\":\"交易流水号\",\"group\":\"01\",\"len\":32,\"format\":\"ASCII\",\"start\":22,\"optionFlag\":false,\"options\":null}]},{\"key\":\"39\",\"name\":\"地锁控制授权下行反馈上行\",\"type\":\"00\",\"datas\":[{\"name\":\"交易流水号\",\"group\":\"01\",\"len\":32,\"format\":\"ASCII\",\"start\":1,\"optionFlag\":false,\"options\":null}]},{\"key\":\"88\",\"name\":\"充电桩中止充电原因\",\"type\":\"00\",\"datas\":[{\"name\":\"达到所需求的SOC目标值\",\"group\":\"01\",\"len\":1,\"format\":\"BIT\",\"start\":1,\"optionFlag\":true,\"options\":{\"0\":\"未达到所需SOC目标值\",\"1\":\"达到所需SOC\"}},{\"name\":\"达到总电压的设定值\",\"group\":\"01\",\"len\":1,\"format\":\"BIT\",\"start\":2,\"optionFlag\":true,\"options\":{\"0\":\"未达到总电压设定值\",\"1\":\"达到总电压设定值\"}},{\"name\":\"达到单体电压设定值\",\"group\":\"01\",\"len\":1,\"format\":\"BIT\",\"start\":3,\"optionFlag\":true,\"options\":{\"0\":\"未达到单体电压设定值\",\"1\":\"未达到单体电压设定值\"}},{\"name\":\"BMS停止充电\",\"group\":\"01\",\"len\":1,\"format\":\"BIT\",\"start\":4,\"optionFlag\":true,\"options\":{\"0\":\"bms允许充电\",\"1\":\"bms停止充电\"}},{\"name\":\"达到电量设定值\",\"group\":\"01\",\"len\":1,\"format\":\"BIT\",\"start\":5,\"optionFlag\":true,\"options\":{\"0\":\"未达到电量设定值\",\"1\":\"达到电量设定值\"}},{\"name\":\"达到时间设定值\",\"group\":\"01\",\"len\":1,\"format\":\"BIT\",\"start\":6,\"optionFlag\":true,\"options\":{\"0\":\"未达到时间设定值\",\"1\":\"达到时间设定值\"}},{\"name\":\"达到金额设定值\",\"group\":\"01\",\"len\":1,\"format\":\"BIT\",\"start\":7,\"optionFlag\":true,\"options\":{\"0\":\"未达到金额设定值\",\"1\":\"达到金额设定值\"}},{\"name\":\"停止充电标识位\",\"group\":\"01\",\"len\":1,\"format\":\"BIT\",\"start\":8,\"optionFlag\":true,\"options\":{\"0\":\"不启用该标识位\",\"1\":\"启用该标识位\"}}]},{\"key\":\"89\",\"name\":\"电池status1\",\"type\":\"00\",\"datas\":[{\"name\":\"单体动力蓄电池电压过高/过低状态\",\"group\":\"01\",\"len\":2,\"format\":\"BIT\",\"start\":1,\"optionFlag\":true,\"options\":{\"10\":\"过低\",\"00\":\"正常\",\"01\":\"过高\"}},{\"name\":\"动力蓄电池SOC过高/过低状态\",\"group\":\"01\",\"len\":2,\"format\":\"BIT\",\"start\":3,\"optionFlag\":true,\"options\":{\"10\":\"过低\",\"00\":\"正常\",\"01\":\"过高\"}},{\"name\":\"动力蓄电池充电过电流状态\",\"group\":\"01\",\"len\":2,\"format\":\"BIT\",\"start\":5,\"optionFlag\":true,\"options\":{\"10\":\"不可信状态\",\"00\":\"正常\",\"01\":\"过流\"}},{\"name\":\"动力蓄电池温度过高状态\",\"group\":\"01\",\"len\":2,\"format\":\"BIT\",\"start\":7,\"optionFlag\":true,\"options\":{\"10\":\"不可信状态\",\"00\":\"正常\",\"01\":\"过高\"}}]},{\"key\":\"90\",\"name\":\"电池status2\",\"type\":\"00\",\"datas\":[{\"name\":\"单体动力蓄电池绝缘状态\",\"group\":\"01\",\"len\":2,\"format\":\"BIT\",\"start\":1,\"optionFlag\":true,\"options\":{\"10\":\"不可信状态\",\"00\":\"正常\",\"01\":\"不正常\"}},{\"name\":\"动力蓄电池组输出连接器状态\",\"group\":\"01\",\"len\":2,\"format\":\"BIT\",\"start\":3,\"optionFlag\":true,\"options\":{\"10\":\"不可信状态\",\"00\":\"正常\",\"01\":\"不正常\"}},{\"name\":\"充电允许状态\",\"group\":\"01\",\"len\":2,\"format\":\"BIT\",\"start\":5,\"optionFlag\":true,\"options\":{\"00\":\"禁止\",\"01\":\"允许\"}}]}]";
	public static HashMap<String, ELCommand> commands = new HashMap<String, ELCommand>();
	static{
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		List<ELCommand> cms = null;
		try {
			cms = mapper.readValue(protocol, new TypeReference<List<ELCommand>>() {
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (ELCommand com : cms) {
			commands.put(Integer.parseInt(com.getKey())+"", com);
		}
	}
	
	public static List<String> comParase(String text) {
		//text ="FE 20 00 02 05 00 00 A1 02 32 30 33 31 39 30 33 31 39 30 30 30 30 30 30 30 30 30 30 31 00 04 06 03 78 16"; 
		//text ="FE 3F 00 02 05 00 00 BF 07 32 30 33 31 39 30 33 31 39 30 30 30 30 30 30 30 30 30 30 31 00 02 0A 30 30 30 30 30 30 30 30 30 30 30 30 30 30 30 30 30 30 30 30 30 30 30 30 30 33 38 32 33 30 32 35 78 16";
		//text ="FE 21 00 02 05 00 00 AE 07 32 30 33 31 39 30 33 31 39 30 30 30 30 30 30 30 30 30 30 31 00 02 17 01 01 67 16";
		//text = "FE 33 00 02 06 00 00 04 00 30 30 30 30 30 30 30 30 30 30 30 30 30 30 31 30 30 30 30 30 01 01 01 56 32 2E 36 2E 30 2E 32 0A 0A 00 00 00 00 00 00 00 00 00 00 B3 16";
	    //text="FE 89 02 02 06 19 4C 29 00 30 34 34 30 34 30 30 30 30 30 30 32 30 37 38 30 30 30 30 31 01 01 0F 02 31 37 35 31 31 31 31 31 31 31 31 00 00 00 00 00 00 39 36 45 37 39 32 31 38 39 36 35 45 42 37 32 43 39 32 41 35 34 39 44 44 35 41 33 33 30 31 31 32 02 00 31 8C AA 58 00 00 00 18 00 00 50 C3 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 42 8C AA 58 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 20 4E 00 00 1F 8C AA 58 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 A0 86 01 00 01 F0 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 20 4E 00 00 3F 16";
		if (text == null || "".equals(text)) {
			return null;
		}
		String checkFlag =  MyUtils.regText(text,SysParamConst.PROTOCOL_EL) ;
		if(checkFlag == null){
			return null ;
		}
		List<String> results = new ArrayList<String>();
		String[] messages = text.split("\\s");
		if ("FE".equals(messages[0])) {
			results.add("报文头,FE");
		}
		//长度2
		String len = messages[1]+messages[2] ;
		results.add("长度,"+MyUtils.hexToTen(len)) ;
		//版本号  主版本+次版本
		results.add("主版本,"+MyUtils.hexToTen(messages[3])) ;
		results.add("次版本,"+MyUtils.hexToTen(messages[4])) ;
		//平台发送序号
		String pserial = messages[5]+messages[6] ;
		results.add("平台发送序号,"+MyUtils.hexToTen(pserial)) ;
		//电桩发送序号
		String sserial = messages[7]+messages[8] ;
		results.add("电桩发送序号,"+MyUtils.hexToTen(sserial)) ;	
		//充电桩编号
		String sno = "" ;
		for(int i = 9 ; i < 29 ;i++ ){
			sno += messages[i] ;
		}
		results.add("城市编号,"+sno.substring(0,10));
		results.add("加盟商编号,"+sno.substring(10,20));
		results.add("电站编号,"+sno.substring(20,30));
		results.add("电桩编号,"+sno.substring(30,40));
		
		//桩类型
		results.add("桩类型,"+MyUtils.hexToTen(messages[29]));
		//枪口编号
		results.add("枪口编号,"+MyUtils.hexToTen(messages[30]));
		//针类型
		String pointerType = messages[31] ;
		//数据项
		String[] dataMessages = new String[messages.length-34] ;
		for(int i = 0 ; i < messages.length-34 ; i++ ){
			dataMessages[i] = messages[i+32] ;
		}
		//crc
		String crc =  messages[ messages.length-2] ;
		
		//结束符号
		String end =  messages[ messages.length-1] ;
		
		ELCommand  elCommand = commands.get(MyUtils.hexToTen(pointerType)) ;
		if(elCommand != null){
			results.add("报文帧," + elCommand.getName());
			int repeatNum =  0 ;
			int start = 0 ;
			boolean isEndRepate = false ; 
			List<ELData> rDatas = new ArrayList() ;
			List<ELData> cDatas = new ArrayList() ;
			for (int i = 0; i < elCommand.getDatas().size(); i++) {
				ELData obj = elCommand.getDatas().get(i);
				if(Integer.parseInt(obj.getGroup()) == 1){  //各阶段占桩费率
					if(!rDatas.isEmpty()){
						for(int k = 1 ; k< repeatNum+1 ;k++){
							for(ELData temp:rDatas){
								ELData nData = new ELData(temp.getName()+k, temp.getGroup(), 
										temp.getLen(), temp.getFormat(), start, temp.getOptionFlag(),
										temp.getOptions()); 
								start += temp.getLen() ;
								cDatas.add(nData) ;
							}
						}
						rDatas.clear();
					}
					obj.setStart(cDatas.size() == 0 ? obj.getStart():cDatas.get(cDatas.size()-1).getStart()+cDatas.get(cDatas.size()-1).getLen());
					cDatas.add(obj);
					rDatas.clear();
				}else if(Integer.parseInt(obj.getGroup()) == 2){
					ELData lastData =  cDatas.get(cDatas.size()-1) ;
					start = lastData.getStart()+lastData.getLen() ;
					rDatas.add(obj);
					repeatNum = 12 ;
				}else if(Integer.parseInt(obj.getGroup()) == 3){
					ELData lastData =  cDatas.get(cDatas.size()-1) ;
					start = lastData.getStart()+lastData.getLen() ;
					rDatas.add(obj);
					repeatNum = Integer.parseInt(MyUtils.hexToTen(messages[lastData.getStart()-1])) ;
				}
			}
			for (ELData temp:cDatas) {
				selectMethod(results,temp,dataMessages);
			}
		}
	
		results.add("name=crc校验位,value=" + crc);
		results.add("name=结束符号,value=" + end);
        return results ;
	}
	public static List<String> selectMethod(List<String> results, ELData obj, String[] messages ){
		switch (obj.getFormat()) {
		case "BIN":
			return binFormat(results,obj,messages) ;
		case "ASCII":
			return asciFormat(results,obj,messages) ;
		case "CP56Time2a":
			return cpTimeFormat(results,obj,messages) ;
		case "String":
			return strFormat(results,obj,messages) ;
		case "BIT":
			return bitFormat(results,obj,messages,false) ;	
		case "T-BIT":
			return bitFormat(results,obj,messages,true) ;	
		default :
			return null;
		}
	}
	
	
	public static List<String> binFormat(List<String> list, ELData obj, String[] messages) {
		int len = obj.getLen() ;
		
		int start = obj.getStart() ;
		String value = "" ;
		String hexStr = "" ;
		if(len == 1){
			value =  MyUtils.hexToTen(messages[start-1]) ;
		}else{
			for(int k = 0 ; k < len ; k++ ){
				hexStr += messages[start-1+k];
			}
			value =  MyUtils.hexToTen(hexStr);
		}
		if (obj.getOptionFlag()) {
			Map<String, String> options = obj.getOptions();
			list.add("name=" + obj.getName() + ",value=" + options.get(value));
		}else{
			Map<String, String> options = obj.getOptions();
			if(options!=null){
				if(options.containsKey("power")){
					String power = options.get("power");
					long  p = Long.parseLong(power) ;
					list.add("name=" + obj.getName() + ",value=" + Long.parseLong(value)*p);
				}
				if(options.containsKey("fl")){
					String fl = options.get("fl");
					if("1".equals(fl)){
						list.add("name=服务费率,value=" );
					}
					if("2".equals(fl)){
						list.add("name=电价费率,value=" );
					}
					list = MyUtils.formatBL(list,hexStr) ;
				}
				if(options.containsKey("bit")){
					value = messages[start-1] ;
					String strBit = MyUtils.hexStringToByte(value) ;
					String key =options.get("bit") ;
					ELCommand  bitCom  = commands.get(key) ;
					if(bitCom != null){
						list.add("name=" + bitCom.getName() + ",value=" );
						for (int i = 0; i < bitCom.getDatas().size(); i++) {
							ELData bitObj = bitCom.getDatas().get(i);
							String bitValue = strBit.substring(8-len,9-start);
							list.add("name=" + bitObj.getName() + ",value=" +bitObj.getOptions().get(bitValue));
						}	
					}	
				}
			}else{
				list.add("name=" + obj.getName() + ",value=" + value);
				
			}
		}
	    return list ;
	}
	

	public static List<String> asciFormat(List<String> list, ELData obj, String[] messages) {
		int len = obj.getLen() ;
		int start = obj.getStart() ;

		String value = "";
		String hexStr = "";
		for(int i = 0 ; i < len ; i++){
			hexStr += messages[i+start-1] ;
		}
		value = MyUtils.hex2Str(hexStr);
		list.add("name=" + obj.getName() + ",value=" + value);
		return list;
	}
	public static List<String> strFormat(List<String> list, ELData obj, String[] messages) {
		int len = obj.getLen() ;
		int start = obj.getStart() ;

		String value = "";
		String hexStr = "";
		for(int i = 0 ; i < len ; i++){
			hexStr = messages[i+start-1]+hexStr ;
		}
		list.add("name=" + obj.getName() + ",value=" + hexStr);
		return list;
	}
	public static List<String> cpTimeFormat(List<String> list, ELData obj, String[] messages) {
		int len = obj.getLen() ;
		int start = obj.getStart() ;

		String[] params = new String[len] ;
		for(int i = 0 ; i < len ; i++){
			params[i]= messages[i+start-1] ;
		}
		list.add("name=" + obj.getName() + ",value=" + MyUtils.toCP56Time2aFormat(params));
		return list;
	}
	public static List<String> bitFormat(List<String> list, ELData obj, String[] messages ,boolean tran) {
		int len = obj.getLen() ;
		int start = obj.getStart() ;
		int startBin = (int)(start/8)+1 ;
		int startBit = (int)(start%8) ;
		if(startBit==1){
			list.add("name=" +  obj.getOptions().get("name") + ",value=" );
		}
		String hex  = messages[startBin-1] ;
		String bstr = MyUtils.hexStringToByte(hex) ;
		String value = bstr.substring(8-len,9-startBit);
		if(tran){
			value = MyUtils.binaryToTen(value)+"";
		}
	
		if (obj.getOptionFlag()) {
			Map<String, String> options = obj.getOptions();
			if(options!=null){
				list.add("name=" + obj.getName() + ",value=" +options.get(value));
			}
		
		}
		return list;
	}
}
