package com.titans.serialport.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class TestSQLite{
	   public static void main(String[] args) throws Exception{
		   test1();
	    /* try{
	     //连接SQLite的JDBC
	     Class.forName("org.sqlite.JDBC");   
	     //建立一个数据库名zieckey.db的连接，如果不存在就在当前目录下创建之
	     Connection conn = DriverManager.getConnection("jdbc:sqlite:zieckey.db");  
	     Statement stat = conn.createStatement();
	     stat.executeUpdate( "create table tbl1(name varchar(20), salary int);" );//创建一个表，两列
	     stat.executeUpdate( "insert into tbl1 values('ZhangSan',8000);" ); //插入数据
	     stat.executeUpdate( "insert into tbl1 values('LiSi',7800);" );
	     stat.executeUpdate( "insert into tbl1 values('WangWu',5800);" );
	     stat.executeUpdate( "insert into tbl1 values('ZhaoLiu',9100);" ); 
	     ResultSet rs = stat.executeQuery("select * from tbl1;"); //查询数据 
	     while (rs.next()) { //将查询到的数据打印出来
	       System.out.print("name = " + rs.getString("name") + " "); //列属性一
	       System.out.println("salary = " + rs.getString("salary")); //列属性二
	     }
	     rs.close();
	     conn.close(); //结束数据库的连接 
	     }
	     catch( Exception e )
	     {
	     e.printStackTrace ( );
	     }*/
	  }
	  
	  public static void test1() throws Exception{
		  //连接SQLite的JDBC
		     Class.forName("org.sqlite.JDBC");   
		     //建立一个数据库名zieckey.db的连接，如果不存在就在当前目录下创建之
		     Connection conn = DriverManager.getConnection("jdbc:sqlite:20190723082543.db");
		     Statement stat = conn.createStatement();
		     ResultSet rs = stat.executeQuery("select * from COM4;"); //查询数据 
		     while (rs.next()) { //将查询到的数据打印出来
				   
				   System.out.print("colDate = " + rs.getString("colDate") + " "); //列属性一
				   System.out.println("colDate = " + rs.getString("ColPacket")); //列属性二
				}
	  }
	   
}



