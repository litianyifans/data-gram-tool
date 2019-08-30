package com.titans.serialport.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//引入站点的配置信息
import com.alibaba.druid.pool.DruidDataSource;

/**
 * 数据库连接生成类，返回一个数据库连接对象 构造函数完成数据库驱动的加载和数据的连接 提供数据库连接的取得和数据库的关闭方法
 * 
 * @author yuyu
 *
 */

public class DbConnect {

	private static DruidDataSource dataSource = null;

	/**
	 * 构造函数完成数据库的连接和连接对象的生成
	 * 
	 * @throws Exception
	 */
	public DbConnect() {

	}

	public void GetDbConnect() throws Exception {
		try {
			if (dataSource == null) {
				dataSource = new DruidDataSource();
				// 设置连接参数
				dataSource.setUrl("jdbc:sqlite:20190819.db");
				dataSource.setDriverClassName("org.sqlite.JDBC");
				// 配置初始化大小、最小、最大
				dataSource.setInitialSize(1);
				dataSource.setMinIdle(1);
				dataSource.setMaxActive(20);
				// 连接泄漏监测
				dataSource.setRemoveAbandoned(true);
				dataSource.setRemoveAbandonedTimeout(30);
				// 配置获取连接等待超时的时间
				dataSource.setMaxWait(20000);
				// 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
				dataSource.setTimeBetweenEvictionRunsMillis(20000);
				// 防止过期
				dataSource.setValidationQuery("SELECT 'x'");
				dataSource.setTestWhileIdle(true);
				dataSource.setTestOnBorrow(true);
			}
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 取得已经构造生成的数据库连接
	 * 
	 * @return 返回数据库连接对象
	 * @throws Exception
	 */
	public Connection getConnect() throws Exception {
		Connection con = null;
		try {
			GetDbConnect();
			con = dataSource.getConnection();
		} catch (Exception e) {
			throw e;
		}
		return con;
	}
	   /**
     * 释放数据库连接 connection 到数据库缓存池，并关闭 rSet 和 pStatement 资源
     * @param rSet 数据库处理结果集
     * @param pStatement 数据库操作语句
     * @param connection 数据库连接对象
     */
    public static void releaseSqlConnection(ResultSet rSet, PreparedStatement pStatement, Connection connection) {
        try {
            if (rSet != null) {
                rSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pStatement != null) {
                    pStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (connection != null) {
                        connection.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
	
	public static void main(String[] args) throws Exception {
		DbConnect dbConnect = new DbConnect();
		Connection connection = dbConnect.getConnect();
		String sql = "SELECT count(*) cou FROM sqlite_master WHERE type='table' AND name='com'";
		//PreparedStatement ps = connection.prepareStatement(sql);
		Statement st = connection.createStatement() ;
		ResultSet resultSet = st.executeQuery(sql);
		if (resultSet.next()) {
			System.out.println(resultSet.getString("cou"));
		}
	}

}