package com.titans.serialport.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

public class FileUtils {

	private static Logger logger = Logger.getLogger(FileUtils.class);

	public static void exportFactory(JPanel jPanel, JButton jb, String fileName, List<Map<String, String>> dataList,
			String[] headName, String[] colName) {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setDialogTitle("选择一个导出目录:");
		int returnVal = chooser.showOpenDialog(jb);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			String path = chooser.getSelectedFile().getAbsolutePath();
			File f = new File(path + "\\" + fileName + ".csv");
			try {
				f.createNewFile();
				boolean flag = FileUtils.exportCsv(f, dataList, headName, colName);
				if (flag) {
					JOptionPane.showMessageDialog(jPanel, "导出成功", "导出结果", JOptionPane.WARNING_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(jPanel, "导出失败", "导出结果", JOptionPane.WARNING_MESSAGE);
				}
			} catch (IOException e1) {
				logger.error("导出数据失败");
				e1.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * 描述：导出
	 * 
	 * @author mao2080@sina.com
	 * @created 2017年8月26日 下午2:39:13
	 * @since
	 * @param file
	 *            csv文件(路径+文件名)，csv文件不存在会自动创建
	 * @param dataList
	 *            数据（data1,data2,data3...）
	 * @return
	 */
	public static boolean exportCsv(File file, List<Map<String, String>> dataList, String[] headName,
			String[] colName) {
		FileOutputStream out = null;
		OutputStreamWriter osw = null;
		BufferedWriter bfw = null;
		try {
			out = new FileOutputStream(file);
			osw = new OutputStreamWriter(out, "gbk");
			bfw = new BufferedWriter(osw);
			if (dataList != null && !dataList.isEmpty()) {
				bfw.append(headName[0] + "," + headName[1]).append("\r");
				for (Map<String, String> data : dataList) {
					bfw.append(data.get("ColDate") + "," + data.get("ColPacket")).append("\r");
				}
			}
			bfw.flush();
			out.close();
			osw.close();
			bfw.close();
			return true;
		} catch (Exception e) {
			logger.error("导出的数据格式错误");
			return false;
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (osw != null) {
				try {
					osw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (bfw != null) {
				try {
					bfw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 
	 * 描述：导入
	 * 
	 * @author mao2080@sina.com
	 * @created 2017年8月26日 下午2:42:08
	 * @since
	 * @param file
	 *            csv文件(路径+文件名)
	 * @return
	 */
	public static List<String> importCsv(File file) {
		List<String> dataList = new ArrayList<String>();
		BufferedReader br = null;
		DataInputStream in = null;
		try {
			in = new DataInputStream(new FileInputStream(file));
			br = new BufferedReader(new InputStreamReader(in, "gbk"));
			String line = "";
			while ((line = br.readLine()) != null) {
				dataList.add(line);
			}
		} catch (Exception e) {

		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return dataList;
	}

	public static String getFileEncode(String path) {
		String charset = "asci";
		byte[] first3Bytes = new byte[3];
		BufferedInputStream bis = null;
		try {
			boolean checked = false;
			bis = new BufferedInputStream(new FileInputStream(path));
			bis.mark(0);
			int read = bis.read(first3Bytes, 0, 3);
			if (read == -1)
				return charset;
			if (first3Bytes[0] == (byte) 0xFF && first3Bytes[1] == (byte) 0xFE) {
				charset = "Unicode";// UTF-16LE
				checked = true;
			} else if (first3Bytes[0] == (byte) 0xFE && first3Bytes[1] == (byte) 0xFF) {
				charset = "Unicode";// UTF-16BE
				checked = true;
			} else if (first3Bytes[0] == (byte) 0xEF && first3Bytes[1] == (byte) 0xBB
					&& first3Bytes[2] == (byte) 0xBF) {
				charset = "UTF8";
				checked = true;
			}
			bis.reset();
			if (!checked) {
				int len = 0;
				int loc = 0;
				while ((read = bis.read()) != -1) {
					loc++;
					if (read >= 0xF0)
						break;
					if (0x80 <= read && read <= 0xBF) // 单独出现BF以下的，也算是GBK
						break;
					if (0xC0 <= read && read <= 0xDF) {
						read = bis.read();
						if (0x80 <= read && read <= 0xBF)
							// 双字节 (0xC0 - 0xDF) (0x80 - 0xBF),也可能在GB编码内
							continue;
						else
							break;
					} else if (0xE0 <= read && read <= 0xEF) { // 也有可能出错，但是几率较小
						read = bis.read();
						if (0x80 <= read && read <= 0xBF) {
							read = bis.read();
							if (0x80 <= read && read <= 0xBF) {
								charset = "UTF-8";
								break;
							} else
								break;
						} else
							break;
					}
				}
				// TextLogger.getLogger().info(loc + " " +
				// Integer.toHexString(read));
			}
		} catch (Exception e) {
			logger.error("获取文件编码格式错误" + e.getMessage());
			e.printStackTrace();
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException ex) {
				}
			}
		}
		return charset;
	}

	private static String getEncode(int flag1, int flag2, int flag3) {
		String encode = "";
		// txt文件的开头会多出几个字节，分别是FF、FE（Unicode）,
		// FE、FF（Unicode big endian）,EF、BB、BF（UTF-8）
		if (flag1 == 255 && flag2 == 254) {
			encode = "Unicode";
		} else if (flag1 == 254 && flag2 == 255) {
			encode = "UTF-16";
		} else if (flag1 == 239 && flag2 == 187 && flag3 == 191) {
			encode = "UTF8";
		} else {
			encode = "asci";// ASCII码
		}
		return encode;
	}

	/**
	 * 通过路径获取文件的内容，这个方法因为用到了字符串作为载体，为了正确读取文件（不乱码），只能读取文本文件，安全方法！
	 */
	public static List<String> readFile(String path, Connection conn, boolean inDataBaseFlag) {
		List<String> dataList = new ArrayList<String>();
		String data = null;
		// 判断文件是否存在
		File file = new File(path);
		if (!file.exists()) {
			return null;
		}
		// 获取文件编码格式
		String code = getFileEncode(path);
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			// 根据编码格式解析文件
			if ("asci".equals(code)) {
				// 这里采用GBK编码，而不用环境编码格式，因为环境默认编码不等于操作系统编码
				// code = System.getProperty("file.encoding");
				code = "GBK";
			}
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file), code));
			// 读取文件内容
			String line = "";
			br.readLine();
			while ((line = br.readLine()) != null) {
				String[] strs = line.split(",");
				if (inDataBaseFlag) {
					DBUtils.insertData("loc", strs[3], strs[7]);
				} else {
					dataList.add(strs[3] + "," + strs[7]);
				}

			}

		} catch (Exception e) {
			logger.error("读取文件失败" + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if (isr != null) {
					isr.close();
				}
			} catch (IOException e) {
				logger.error("关闭输入失败" + e.getMessage());
				e.printStackTrace();
			}
		}
		return dataList;
	}

	// 复制文件
	public static void copyFile(File sourceFile, File targetFile) {
		// 新建文件输入流并对它进行缓冲
		FileInputStream input;
		try {
			input = new FileInputStream(sourceFile);
			BufferedInputStream inBuff = new BufferedInputStream(input);
			// 新建文件输出流并对它进行缓冲
			FileOutputStream output = new FileOutputStream(targetFile);
			BufferedOutputStream outBuff = new BufferedOutputStream(output);
			// 缓冲数组
			byte[] b = new byte[1024 * 5];
			int len;
			while ((len = inBuff.read(b)) != -1) {
				outBuff.write(b, 0, len);
			}
			// 刷新此缓冲的输出流
			outBuff.flush();
			// 关闭流
			inBuff.close();
			outBuff.close();
			output.close();
			input.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}