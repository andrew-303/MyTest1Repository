package cn.itcast.jdbc;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import cn.itcast.jdbc.domain.User;

/**
 * 反射的使用
 * @author Administrator
 *
 */
public class ReflactTest {

	public static void main(String[] args) throws Exception, Exception {
		Class clazz = User.class;
		Object object = create(clazz);
		System.out.println(object);
		System.out.println("---------------");		
		invoke1(object);
		System.out.println("---------------");
		
		Fields1(clazz);
	}
	
	static Object create(Class clazz) throws Exception, SecurityException{
		//下面的这个写法是因为，要反射的类含有 有参的构造方法，所以这样写
		//如果是无参的构造方法的话，可以直接用newInstance()来反射
		Constructor cons = clazz.getConstructor(String.class);
		Object obj = cons.newInstance("test name");
		return obj;		
	}
	
	static void invoke1(Object obj){
		Method[] methods = obj.getClass().getDeclaredMethods();
		for(Method m :methods){
			System.out.println(m.getName());
		}
	}
	
	static void Fields1(Class clazz){
		Field[] fs = clazz.getDeclaredFields();
		for(Field f :fs){
			System.out.println(f.getName());
		}
	}
	
}
