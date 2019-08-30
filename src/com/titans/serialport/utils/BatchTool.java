package com.titans.serialport.utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.titans.serialport.ui.MainFrame;

public class BatchTool {
	
	 private static Logger logger = Logger.getLogger(BatchTool.class);
	
	/**
	 * @note 执行批量更新操作 由于connection.comit 操作时 如果存在 statement没有close 就会报错
	 *       因此将此方法加上同步 。
	 * */
	public synchronized void exucteUpdate(String[] batchs) {
		Statement ste = null;
		Connection con = null;
		try {
			con = DBUtils.getConnect();
			con.setAutoCommit(false);
			ste = con.createStatement();
			for (String sql : batchs) {
				if (sql != null) {
					ste.addBatch(sql);
				}
			}
			ste.executeBatch();
			ste.close();
			con.commit();// 提交
			logger.debug(MyUtils.formatDateStr()+"-------成功---------");
			System.out.println(MyUtils.formatDateStr()+"-------成功---------");
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(MyUtils.formatDateStr()+"-------执行失败---------"+e.getMessage());
			System.out.println("执行失败:" );
			try {
				con.rollback();// 回滚
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			 try {
                 if (con != null) {
                	 con.close();
                 }
             } catch (SQLException e) {
                 e.printStackTrace();
             }
			if (ste != null) {
				try {
					ste.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}


 
	
		
}
