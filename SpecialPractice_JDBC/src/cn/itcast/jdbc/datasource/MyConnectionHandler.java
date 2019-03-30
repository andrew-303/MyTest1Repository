package cn.itcast.jdbc.datasource;
/**
 * JAVA动态代理及使用该技术完善连接代理
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
		//这句话相当于MyConnection myconn = new MyConnection(realconn,this);
		this.wrapedConnection = (Connection)Proxy.newProxyInstance(this.getClass().getClassLoader(),
				new Class[]{Connection.class},this); //所有的调用都会转发给最后一个this（MyConnectionHandler）,也叫调用处理器，而这个时候会调用下面的invoke方法
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
