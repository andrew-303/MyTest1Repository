package cn.itcast.jdbc.datasource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;

public class MyDataSource {
	private static String url="jdbc:mysql://localhost:3306/itcast_jdbc";
	private static String user="root";
	private static String password="";
	
	private static int initCount = 5;	//初始化连接数
	private static int maxCount = 10;	//最大连接数
	private static int currentCount = 0; 	//当前连接数
	private LinkedList<Connection> connectionPool = new LinkedList<Connection>();
	
	public MyDataSource() throws Exception{
		for(int i=0;i<initCount;i++){
			this.connectionPool.addLast(this.createConnection());
			this.currentCount++;
		}
	}

	public Connection getConnection() throws Exception{
		//加入同步锁
		synchronized (connectionPool) {
			//当连接池中有连接时，才返回连接
			if(this.connectionPool.size()>0){
				return this.connectionPool.removeFirst();
			}else if(this.currentCount<this.maxCount){//如果当前连接数小于最大连接数，是可以创建连接的
				this.currentCount++;
				return this.createConnection();
			}
			//return null;
			throw new Exception("已没有连接");
		}
		
	}
	public void free(Connection conn){
		this.connectionPool.addLast(conn);
	}
	private Connection createConnection() throws Exception {		
		return DriverManager.getConnection(url,user,password);
	}
}
