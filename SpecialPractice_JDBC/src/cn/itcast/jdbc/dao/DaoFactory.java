package cn.itcast.jdbc.dao;
//单例模式
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DaoFactory {
	//类初始化时，先加载静态变量
	private static UserDao userDao = null;
	private static DaoFactory instance = new DaoFactory();
			
	private DaoFactory(){
		
		try {
			Properties prop = new Properties();
			InputStream inStream = DaoFactory.class.getClassLoader()
					.getResourceAsStream("daoconfig.properties");
			prop.load(inStream);
			String userDaoclass = prop.getProperty("userDaoClass");
			System.out.println("1:"+userDaoclass);
			Class clazz = Class.forName(userDaoclass);
			System.out.println("2:"+clazz);
			userDao = (UserDao) clazz.newInstance(); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static DaoFactory getInstance(){
		return instance;
	}
	
	public UserDao getUserDao(){
		return userDao;
	}
}
