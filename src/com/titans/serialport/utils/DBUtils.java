package com.titans.serialport.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.druid.pool.DruidDataSource;

public class DBUtils {
	
	private static DruidDataSource dataSource = null;

	
	public static void GetDbConnect(String DatabaseName) {
		try {
			if (dataSource == null) {
				dataSource = new DruidDataSource();
				// 设置连接参数
				dataSource.setUrl("jdbc:sqlite:"+DatabaseName+".db");
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
			e.printStackTrace();
		}
	}
	
	/**
	 * 取得已经构造生成的数据库连接
	 * 
	 * @return 返回数据库连接对象
	 * @throws Exception
	 */
	public static Connection getConnect() {
		Connection con = null;
		try {
			con = dataSource.getConnection();
		} catch (Exception e) {
			 e.printStackTrace();
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
    
	public static Connection getDbImportConnection(String DatabaseName) {
		// 连接SQLite的JDBC
		try {
			Class.forName("org.sqlite.JDBC");
			// 建立一个数据库名zieckey.db的连接，如果不存在就在当前目录下创建之
			Connection conn = DriverManager.getConnection("jdbc:sqlite:" + DatabaseName );
			return conn;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean createTable() {
		Connection conn = null ;
		ResultSet rs  = null ;
		try {
			conn = getConnect();
			Statement stat = conn.createStatement();
			rs = stat.executeQuery("SELECT count(*) cou FROM sqlite_master WHERE type='table' AND name='com'");
			if (rs.next()) {
				String num = rs.getString("cou");
				if (Integer.parseInt(num) == 0) {
					stat.executeUpdate("create table com(ColDate varchar(18) PRIMARY KEY  NOT NULL, ColPacket varchar(500));");
					stat.executeUpdate("create table net(ColDate varchar(18) PRIMARY KEY  NOT NULL, ColPacket varchar(500));");
					stat.executeUpdate("create table loc(ColDate varchar(18) PRIMARY KEY  NOT NULL, ColPacket varchar(500));");
					System.out.println("创建表成功！");
				} else {
					System.out.println("表已经存在,无需创建。");
				}
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			releaseSqlConnection(rs,null,conn);
		}
		return false;
	}

	public static boolean insertData( String tableName, String colDate, String colPacket) {
		Connection conn = null ;
		ResultSet rs  = null ;
		try {
			conn = getConnect();
			Statement stat = conn.createStatement();
			stat.executeUpdate("insert into " + tableName + " values('" + colDate + "','" + colPacket + "');"); // 插入数据
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			releaseSqlConnection(rs,null,conn);
		}
		return false;
	}

	public static List<Map<String, String>> queryDatas(String tableName, String minDate, String maxDate) {
		Connection conn = null ;
		ResultSet rs  = null ;
		try {
			conn = getConnect();
			Statement stat = conn.createStatement();
			rs = stat.executeQuery("select * from " + tableName + " where ColDate < " + maxDate + " and ColDate > " + minDate + ";"); // 查询数据
			List<Map<String, String>> results = new ArrayList<Map<String, String>>();
			while (rs.next()) { // 将查询到的数据打印出来
				Map<String, String> result = new HashMap<String, String>();
				result.put("ColDate", MyUtils.formatDateStr_dataParse(rs.getString("ColDate")));
				result.put("ColPacket", rs.getString("ColPacket"));
				results.add(result);
			}
			return results;
		} catch (SQLException e) {
			e.printStackTrace();
			releaseSqlConnection(rs,null,conn);
		}
		return null;
	}

	public static List<Map<String, String>> queryDatas(Connection conn, String tableName,int pagesize) {
		Statement stat;
		try {
			
			stat = conn.createStatement();
			ResultSet rs = stat.executeQuery("select * from " + tableName + "  limit 10000 offset "+(pagesize-1)*10000 ); // 查询数据
			//ResultSet rs = stat.executeQuery("select * from " + tableName ); // 查询数据
			List<Map<String, String>> results = new ArrayList<Map<String, String>>();
			while (rs.next()) { // 将查询到的数据打印出来
				Map<String, String> result = new HashMap<String, String>();
				result.put("ColDate", MyUtils.formatDateStr_dataParse(rs.getString("ColDate")));
				result.put("ColPacket", rs.getString("ColPacket"));
				results.add(result);
			}
			return results;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String queryDataOfCountNum(Connection conn, String tableName) {
		Statement stat;
		try {
			stat = conn.createStatement();
			ResultSet rs = stat.executeQuery("select count(1) con from " + tableName ); // 查询数据总条数
			List<Map<String, String>> results = new ArrayList<Map<String, String>>();
			while (rs.next()) { // 将查询到的数据打印出来
				int con = rs.getInt("con") ;
				int sum  ;
				if((con/10000)==0){
					sum = 1 ;
				}else{
					if((con%10000)>0){
						sum = (con/10000)+1 ;
					}else{
						sum = (con/10000);
					}
				}
				return sum+"";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void closeConnet(ResultSet rs, Connection conn) {
		try {
			rs.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// 结束数据库的连接
	}
}
