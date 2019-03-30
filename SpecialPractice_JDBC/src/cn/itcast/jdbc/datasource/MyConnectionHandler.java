package cn.itcast.jdbc.datasource;
/**
 * JAVA��̬����ʹ�øü����������Ӵ���
 */
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;

public class MyConnectionHandler implements InvocationHandler {

	private Connection realConnection;
	private Connection wrapedConnection;
	private MyDataSource2 dataSource;
	
	private int maxUseCount=5;
	private int currentUseCount=0;
	
	MyConnectionHandler(MyDataSource2 dataSource){
		this.dataSource=dataSource;
	}
	
	Connection bind(Connection connection){
		this.realConnection=connection;
		//��仰�൱��MyConnection myconn = new MyConnection(realconn,this);
		this.wrapedConnection = (Connection)Proxy.newProxyInstance(this.getClass().getClassLoader(),
				new Class[]{Connection.class},this); //���еĵ��ö���ת�������һ��this��MyConnectionHandler��,Ҳ�е��ô������������ʱ�����������invoke����
		return wrapedConnection;
	}
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		if("close".equals(method.getName())){
			System.out.println("invoke -- close");
			this.currentUseCount++;
			if(this.currentUseCount<this.maxUseCount){
				this.dataSource.connectionPool.addLast(wrapedConnection);
			}else{
				this.realConnection.close();
				this.dataSource.currentCount--;
			}
		}
		return method.invoke(this.realConnection, args);
	}

}
