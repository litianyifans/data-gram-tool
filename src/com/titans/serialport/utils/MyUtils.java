package com.titans.serialport.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Queue;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyUtils {

	/**
	 * 获取当前日期 : "yyyy-MM-dd HH:mm:ss"
	 * 
	 * @return "yyyy-MM-dd HH:mm:ss"字符串
	 */
	public static String formatDateStr_ss() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}

	public static String formatDateStr_num() {
		return new SimpleDateFormat("yyyyMMdd").format(new Date());
	}
	public static String formatDateStr() {
		return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	}
	
	
	public static String formatDateStr_param(Date date) {
		return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(date);
	}

	public static String formatDateStr_dataParse(String dstr) {
		if (dstr == null || "".equals(dstr)) {
			return null;
		}
		
		if (dstr.trim().length() != 17) {
			return dstr;
		}
		return dstr.substring(0, 4) + "-" + dstr.substring(4, 6) + "-" + dstr.substring(6, 8) + " " + dstr.substring(8, 10) + ":"
				+ dstr.substring(10, 12) + ":" + dstr.substring(12, 14)+":"+dstr.substring(14, 17);
	}
	public static String formatDateStr_all(String dstr){
		if (dstr == null || "".equals(dstr)) {
			return null;
		}
		return dstr.replaceAll("-", "").replaceAll(":", "").replaceAll(" ", "") ;
	}
	
	private static String sysStr = MyUtils.formatDateStr_ss() ;
	private static int ranNum = 100 ;
	public static String formatDateStr_random(String dateStr){
		if(sysStr.equals(dateStr) ){
			return  dateStr+":"+(++ranNum) ;
		}else{
			ranNum = 100 ;
			sysStr = dateStr ;
			return dateStr+":"+ranNum ;
		}	
	}
	/*
	 * 分割0x10316020 成10 31 60 20 
	 */
	
	public static String toSplitStringHex(String str) {
		if (str == null || "".equals(str)) {
			return null;
		}
    	String result = "" ;
    	str = str.replace("0x", "") ;
    	for(int i = 0 ; i < str.length() ;){
    		result += str.substring(i, i+2)+" " ;
    		i = i +2 ;
    	}
    	return result ;
    }
	
	public static String data_show_format(String text) {
		if (text == null || "".equals(text)) {
			return null;
		}
		String[] texts = text.split(",");
		String name = "";
		String value = "";
		if (texts[0].indexOf("name") >= 0) {
			name = texts[0].split("=")[1];
		} else {
			name = texts[0];
		}
		if (texts[1].indexOf("value") >= 0) {
			value = texts[1].split("=")[1];
		} else {
			value = texts[1];
		}
		
		int len = String_length(name.trim());
		for(int i =0 ; i < 30 -len ; i++){
			name +=" " ;
		}
		//return String.format("%1$"+(30-len)+"s", name)+ "  ===============>  " + value;
		return name +"  ===============>  "+ value ;
	}
	 public static int String_length(String value) {
		  int valueLength = 0;
		  String chinese = "[\u4e00-\u9fa5]";
		  for (int i = 0; i < value.length(); i++) {
		   String temp = value.substring(i, i + 1);
		   if (temp.matches(chinese)) {
		    valueLength += 2;
		   } else {
		    valueLength += 1;
		   }
		  }
		  return valueLength;
	}


	/**
	 * 字符串是否为空
	 * 
	 * 如果这个字符串为null或者trim后为空字符串则返回true，否则返回false。
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		if (str == null || "".equals(str.trim())) return true;
		return false;
	}

	/**
	 * 用来把mac字符串转换为long
	 * 
	 * @param strMac
	 * @return
	 */
	public static long macToLong(String strMac) {
		byte[] mb = new BigInteger(strMac, 16).toByteArray();
		ByteBuffer mD = ByteBuffer.allocate(mb.length);
		mD.put(mb);
		long mac = 0;
		// 如果长度等于8代表没有补0;
		if (mD.array().length == 8) {
			mac = mD.getLong(0);
		} else if (mD.array().length == 9) {
			mac = mD.getLong(1);
		}
		return mac;
	}

	public static byte[] getBytes(Object obj) throws IOException {
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(bout);
		out.writeObject(obj);
		out.flush();
		byte[] bytes = bout.toByteArray();
		bout.close();
		out.close();
		return bytes;
	}

	public static Object getObject(byte[] bytes) throws IOException, ClassNotFoundException {
		ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
		ObjectInputStream oi = new ObjectInputStream(bi);
		Object obj = oi.readObject();
		bi.close();
		oi.close();
		return obj;
	}

	public static ByteBuffer getByteBuffer(Object obj) throws IOException {
		byte[] bytes = MyUtils.getBytes(obj);
		ByteBuffer buff = ByteBuffer.wrap(bytes);
		return buff;
	}

	/**
	 * byte[] 转short 2字节
	 * 
	 * @param bytes
	 * @return
	 */
	public static short bytesToshort(byte[] bytes) {
		return (short) ((bytes[0] & 0xff) | ((bytes[1] << 8) & 0xff00));
	}

	/**
	 * byte 转Int
	 * 
	 * @param b
	 * @return
	 */
	public static int byteToInt(byte b) {
		return (b) & 0xff;
	}

	public static int bytesToInt(byte[] bytes) {
		int addr = bytes[0] & 0xFF;
		addr |= ((bytes[1] << 8) & 0xFF00);
		addr |= ((bytes[2] << 16) & 0xFF0000);
		addr |= ((bytes[3] << 24) & 0xFF000000);
		return addr;
	}

	public static byte[] intToByte(int i) {
		byte[] abyte0 = new byte[4];
		abyte0[0] = (byte) (0xff & i);
		abyte0[1] = (byte) ((0xff00 & i) >> 8);
		abyte0[2] = (byte) ((0xff0000 & i) >> 16);
		abyte0[3] = (byte) ((0xff000000 & i) >> 24);
		return abyte0;
	}

	public static byte[] LongToByte(Long i) {
		byte[] abyte0 = new byte[8];
		abyte0[0] = (byte) (0xff & i);
		abyte0[1] = (byte) ((0xff00 & i) >> 8);
		abyte0[2] = (byte) ((0xff0000 & i) >> 16);
		abyte0[3] = (byte) ((0xff000000 & i) >> 24);
		abyte0[4] = (byte) ((0xff00000000l & i) >> 32);
		abyte0[5] = (byte) ((0xff0000000000l & i) >> 40);
		abyte0[6] = (byte) ((0xff000000000000l & i) >> 48);
		abyte0[7] = (byte) ((0xff00000000000000l & i) >> 56);
		return abyte0;
	}

	/**
	 * 函数名称：shortChange</br>
	 * 功能描述：short 大端转小端
	 * 
	 * @param mshort
	 */
	public static short shortChange(Short mshort) {
		mshort = (short) ((mshort >> 8 & 0xFF) | (mshort << 8 & 0xFF00));
		return mshort;
	}

	/**
	 * 函数名称：intChange</br>
	 * 功能描述：int 大端转小端
	 * 
	 * @param mint
	 */
	public static int intChange(int mint) {
		mint = (int) (((mint) >> 24 & 0xFF) | ((mint) >> 8 & 0xFF00) | ((mint) << 8 & 0xFF0000) | ((mint) << 24 & 0xFF000000));
		return mint;
	}

	/**
	 * 函数名称：intChange</br>
	 * 功能描述：LONG 大端转小端
	 * 
	 * @param mlong
	 */
	public static long longChange(long mlong) {
		mlong = (long) (((mlong) >> 56 & 0xFF) | ((mlong) >> 48 & 0xFF00) | ((mlong) >> 24 & 0xFF0000) | ((mlong) >> 8 & 0xFF000000)
				| ((mlong) << 8 & 0xFF00000000l) | ((mlong) << 24 & 0xFF0000000000l) | ((mlong) << 40 & 0xFF000000000000l)
				| ((mlong) << 56 & 0xFF00000000000000l));
		return mlong;
	}

	/**
	 * 将byte转换为无符号的short类型
	 * 
	 * @param b 需要转换的字节数
	 * @return 转换完成的short
	 */
	public static short byteToUshort(byte b) {
		return (short) (b & 0x00ff);
	}

	/**
	 * 将byte转换为无符号的int类型
	 * 
	 * @param b 需要转换的字节数
	 * @return 转换完成的int
	 */
	public static int byteToUint(byte b) {
		return b & 0x00ff;
	}

	/**
	 * 将byte转换为无符号的long类型
	 * 
	 * @param b 需要转换的字节数
	 * @return 转换完成的long
	 */
	public static long byteToUlong(byte b) {
		return b & 0x00ff;
	}

	/**
	 * 将short转换为无符号的int类型
	 * 
	 * @param s 需要转换的short
	 * @return 转换完成的int
	 */
	public static int shortToUint(short s) {
		return s & 0x00ffff;
	}

	/**
	 * 将short转换为无符号的long类型
	 * 
	 * @param s 需要转换的字节数
	 * @return 转换完成的long
	 */
	public static long shortToUlong(short s) {
		return s & 0x00ffff;
	}

	/**
	 * 将int转换为无符号的long类型
	 * 
	 * @param i 需要转换的字节数
	 * @return 转换完成的long
	 */
	public static long intToUlong(int i) {
		return i & 0x00ffffffff;
	}

	/**
	 * 将short转换成小端序的byte数组
	 * 
	 * @param s 需要转换的short
	 * @return 转换完成的byte数组
	 */
	public static byte[] shortToLittleEndianByteArray(short s) {
		return ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN).putShort(s).array();
	}

	/**
	 * 将int转换成小端序的byte数组
	 * 
	 * @param i 需要转换的int
	 * @return 转换完成的byte数组
	 */
	public static byte[] intToLittleEndianByteArray(int i) {
		return ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(i).array();
	}

	/**
	 * 将long转换成小端序的byte数组
	 * 
	 * @param l 需要转换的long
	 * @return 转换完成的byte数组
	 */
	public static byte[] longToLittleEndianByteArray(long l) {
		return ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN).putLong(l).array();
	}

	/**
	 * 将short转换成大端序的byte数组
	 * 
	 * @param s 需要转换的short
	 * @return 转换完成的byte数组
	 */
	public static byte[] shortToBigEndianByteArray(short s) {
		return ByteBuffer.allocate(2).order(ByteOrder.BIG_ENDIAN).putShort(s).array();
	}

	/**
	 * 将int转换成大端序的byte数组
	 * 
	 * @param i 需要转换的int
	 * @return 转换完成的byte数组
	 */
	public static byte[] intToBigEndianByteArray(int i) {
		return ByteBuffer.allocate(2).order(ByteOrder.BIG_ENDIAN).putInt(i).array();
	}

	/**
	 * 将long转换成大端序的byte数组
	 * 
	 * @param l 需要转换的long
	 * @return 转换完成的byte数组
	 */
	public static byte[] longToBigEndianByteArray(long l) {
		return ByteBuffer.allocate(2).order(ByteOrder.BIG_ENDIAN).putLong(l).array();
	}

	/**
	 * 将short转换为16进制字符串
	 * 
	 * @param s 需要转换的short
	 * @param isLittleEndian 是否是小端序（true为小端序false为大端序）
	 * @return 转换后的字符串
	 */
	public static String shortToHexString(short s, boolean isLittleEndian) {
		byte byteArray[] = null;
		if (isLittleEndian) {
			byteArray = shortToLittleEndianByteArray(s);
		} else {
			byteArray = shortToBigEndianByteArray(s);
		}
		return byteArrayToHexString(byteArray);
	}

	/**
	 * 将int转换为16进制字符串
	 * 
	 * @param i 需要转换的int
	 * @param isLittleEndian 是否是小端序（true为小端序false为大端序）
	 * @return 转换后的字符串
	 */
	public static String intToHexString(int i, boolean isLittleEndian) {
		byte byteArray[] = null;
		if (isLittleEndian) {
			byteArray = intToLittleEndianByteArray(i);
		} else {
			byteArray = intToBigEndianByteArray(i);
		}
		return byteArrayToHexString(byteArray);
	}

	/**
	 * 将long转换为16进制字符串
	 * 
	 * @param l 需要转换的long
	 * @param isLittleEndian 是否是小端序（true为小端序false为大端序）
	 * @return 转换后的字符串
	 */
	public static String longToHexString(long l, boolean isLittleEndian) {
		byte byteArray[] = null;
		if (isLittleEndian) {
			byteArray = longToLittleEndianByteArray(l);
		} else {
			byteArray = longToBigEndianByteArray(l);
		}
		return byteArrayToHexString(byteArray);
	}
	/**
	 * 将字节数组转换成16进制字符串
	 * 
	 * @param array 需要转换的字符串
	 * @param toPrint 是否为了打印输出，如果为true则会每4自己添加一个空格
	 * @return 转换完成的字符串
	 */
	// public static String byteArrayToHexString(byte[] array, boolean toPrint) {
	// if (array == null) {
	// return "null";
	// }
	// StringBuffer sb = new StringBuffer();
	//
	// for (int i = 0; i < array.length; i++) {
	// sb.append(byteToHex(array[i]));
	// if (toPrint && (i + 1) % 4 == 0) {
	// sb.append(" ");
	// }
	// }
	// return sb.toString();
	// }

	/**
	 * 字节数组转换成String，指定长度转换长度
	 *
	 * @param arrBytes
	 * @param count 转换长度
	 * @param blank 要不要空格（每个byte字节，最是否用一个“ ”隔开）
	 * @return "" | arrBytes换成的字符串（不存在null）
	 */
	public static String byteArray2HexString(byte[] arrBytes, int count, boolean blank) {
		String ret = "";
		if (arrBytes == null || arrBytes.length < 1) return ret;
		if (count > arrBytes.length) count = arrBytes.length;
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < count; i++) {
			ret = Integer.toHexString(arrBytes[i] & 0xFF).toUpperCase();
			if (ret.length() == 1) builder.append("0").append(ret);
			else
				builder.append(ret);
			if (blank) builder.append(" ");
		}
		return builder.toString();
	}

	/**
	 * 将16进制字符串转换为byte[]
	 * 
	 * @param str
	 * @return
	 */
	public static byte[] toBytes(String str) {
		if (str == null || str.trim().equals("")) {
			return new byte[0];
		}
		byte[] bytes = new byte[str.length() / 2];
		for (int i = 0; i < str.length() / 2; i++) {
			String subStr = str.substring(i * 2, i * 2 + 2);
			bytes[i] = (byte) Integer.parseInt(subStr, 16);
		}
		return bytes;
	}

	/**
	 * 将指定字符串src，以每两个字符分割转换为16进制形式 如："2B44EFD9" --> byte[]{0x2B, 0x44, 0xEF, 0xD9}
	 * 
	 * @param src String
	 * @return null | byte[]
	 */
	public static byte[] HexString2Bytes(String src) {
		// String strTemp = "";
		if (src == null || "".equals(src)) return null;
		StringBuilder builder = new StringBuilder();
		for (char c : src.trim().toCharArray()) {
			/* 去除中间的空格 */
			if (c != ' ') {
				builder.append(c);
			}
		}
		src = builder.toString();
		byte[] ret = new byte[src.length() / 2];
		byte[] tmp = src.getBytes();
		for (int i = 0; i < src.length() / 2; i++) {
			ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
		}
		return ret;
	}

	/**
	 * 将两个ASCII字符合成一个字节； 如："EF"--> 0xEF
	 * 
	 * @param src0 byte
	 * @param src1 byte
	 * @return byte
	 */
	public static byte uniteBytes(byte src0, byte src1) {
		byte _b0 = Byte.decode("0x" + new String(new byte[] { src0 })).byteValue();
		_b0 = (byte) (_b0 << 4);
		byte _b1 = Byte.decode("0x" + new String(new byte[] { src1 })).byteValue();
		byte ret = (byte) (_b0 ^ _b1);
		return ret;
	}

	/**
	 * 将字节数组转换成16进制字符串
	 * 
	 * @param array 需要转换的字符串(字节间没有分隔符)
	 * @return 转换完成的字符串
	 */
	public static String byteArrayToHexString(byte[] array) {
		return byteArray2HexString(array, Integer.MAX_VALUE, false);
	}

	/**
	 * 将字节数组转换成long类型
	 * 
	 * @param bytes 字节数据
	 * @return long类型
	 */
	public static long byteArrayToLong(byte[] bytes) {
		return ((((long) bytes[0] & 0xff) << 24) | (((long) bytes[1] & 0xff) << 16) | (((long) bytes[2] & 0xff) << 8)
				| (((long) bytes[3] & 0xff) << 0));
	}

	/**
	 * 合并数组
	 * 
	 * @param firstArray 第一个数组
	 * @param secondArray 第二个数组
	 * @return 合并后的数组
	 */
	public static byte[] concat(byte[] firstArray, byte[] secondArray) {
		if (firstArray == null || secondArray == null) {
			if (firstArray != null) return firstArray;
			if (secondArray != null) return secondArray;
			return null;
		}
		byte[] bytes = new byte[firstArray.length + secondArray.length];
		System.arraycopy(firstArray, 0, bytes, 0, firstArray.length);
		System.arraycopy(secondArray, 0, bytes, firstArray.length, secondArray.length);
		return bytes;
	}

	
	/**
	 * 16进制码转ASCII
	 *
	 * @param hex
	 * @return
	 */
	public static String hex2Str(String hex) {
		StringBuilder sb = new StringBuilder();
		StringBuilder temp = new StringBuilder();
		// 49204c6f7665204a617661 split into two characters 49, 20, 4c...
		for (int i = 0; i < hex.length() - 1; i += 2) {
			// grab the hex in pairs
			String output = hex.substring(i, (i + 2));
			// convert hex to decimal
			int decimal = Integer.parseInt(output, 16);
			// convert the decimal to character
			sb.append((char) decimal);
			temp.append(decimal);
		}
		return sb.toString();
	}

	/**
	 * 16进制转2进制
	 *
	 * @param hex
	 * @return
	 */
	public static String hexStringToByte(String hex) {
		if (hex == null || "".equals(hex)) {
			return null;
		}
		int len = hex.length() * 4;
		int i = Integer.parseInt(hex, 16);
		String str2 = Integer.toBinaryString(i);
		int rlen = str2.length();
		for (int k = 0; k < len - rlen; k++) {
			str2 = "0" + str2;
		}
		return str2;
	}

	public static int binaryToTen(String binarySource) {
		BigInteger bi = new BigInteger(binarySource, 2); // 转换为BigInteger类型
		// return Integer.parseInt(bi.toString()); //转换成十进制
		return Integer.parseInt(bi.toString()); // 转换成十进制
	}

	public static String regText(String text, String reg) {
		Pattern r = Pattern.compile(reg);
		Matcher m = r.matcher(text);
		if (m.find()) {
			return m.group();
		} else {
			return null;
		}
	}
	
	public static Queue<String> regAllText(Queue<String> queue,String text, String reg){
		Pattern r = Pattern.compile(reg);
		Matcher m = r.matcher(text);
		while(m.find()){
			queue.add(m.group()) ;
		}
		return queue ;
	}
	
	public static String hexToTen(String hexStr) {
		return new BigInteger(hexStr, 16).toString(10);
	}

	public static String toCP56Time2aFormat(String[] dateStr) {
		if (dateStr.length != 7) {
			return null;
		}
		String mis = MyUtils.hexToTen(dateStr[1] + dateStr[0]);
		String mm = MyUtils.hexToTen(MyUtils.hexStringToByte(dateStr[2]).substring(2, 8));
		String HH = MyUtils.hexToTen(MyUtils.hexStringToByte(dateStr[3]).substring(1, 8));
		String dd = MyUtils.hexToTen(MyUtils.hexStringToByte(dateStr[4]).substring(3, 8));
		String MM = MyUtils.hexToTen(MyUtils.hexStringToByte(dateStr[5]).substring(4, 8));
		String yyyy = Integer.parseInt(MyUtils.hexToTen(MyUtils.hexStringToByte(dateStr[6]).substring(1, 8))) - 2000 + "";
		return yyyy + "年" + MM + "" + dd + " " + HH + ":" + mm + ":" + mis;
	}

	public static List<String> formatBL(List<String> results, String dateStr) {
		if (dateStr.length() != 384) {
			return null;
		}
		for (int i = 0; i < 48; i++) {
			String value = MyUtils.hexToTen(dateStr.substring((i) * 4, (i + 1) * 4));
			if (i % 2 == 0) {
				String pre = i / 2 < 10 ? "0" + i / 2 : (i / 2) + "";
				String cpre = pre + ":00~" + pre + ":30";
				results.add("name=" + cpre + ",value=" + Integer.parseInt(value) * 0.00001);
			}
			if (i % 2 == 1) {
				String pre = i / 2 < 10 ? "0" + i / 2 : (i / 2) + "";
				String nex = (i + 1) / 2 < 10 ? "0" + (i + 1) / 2 : ((i + 1) / 2) + "";
				String cpre = pre + ":30~" + nex + ":00";
				results.add("name=" + cpre + ",value=" + Integer.parseInt(value) * 0.00001);
			}
		}
		return results;
	}
}
