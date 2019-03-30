package cn.itcast.jdbc.datasource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;

public class MyDataSource2 {
	private static String url="jdbc:mysql://localhost:3306/itcast_jdbc";
	private static String user="root";
	private static String password="";
	
	private static int initCount = 1;	//��ʼ��������
	private static int maxCount = 1;	//���������
	static int currentCount = 0; 	//��ǰ������
	LinkedList<Connection> connectionPool = new LinkedList<Connection>();
	
	public MyDataSource2() throws Exception{
		for(int i=0;i<initCount;i++){
			this.connectionPool.addLast(this.createConnection());
			this.currentCount++;
		}
	}

	public Connection getConnection() throws Exception{
		//����ͬ����
		synchronized (connectionPool) {
			//�����ӳ���������ʱ���ŷ�������
			if(this.connectionPool.size()>0){
				return this.connectionPool.removeFirst();
			}else if(this.currentCount<this.maxCount){//�����ǰ������С��������������ǿ��Դ������ӵ�
				this.currentCount++;
				return this.createConnection();
			}
			//return null;
			throw new Exception("��û������");
		}
		
	}
	public void free(Connection conn){
		this.connectionPool.addLast(conn);
	}
	private Connection createConnection() throws Exception {		
		//return DriverManager.getConnection(url,user,password);
		Connection realconn = DriverManager.getConnection(url,user,password);
		/*MyConnection myconn = new MyConnection(realconn,this);*/
		
		MyConnectionHandler proxy = new MyConnectionHandler(this);
		return proxy.bind(realconn);
	}
}
