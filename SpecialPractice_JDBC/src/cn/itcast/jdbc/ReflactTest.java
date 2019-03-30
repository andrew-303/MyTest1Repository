package cn.itcast.jdbc;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import cn.itcast.jdbc.domain.User;

/**
 * �����ʹ��
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
		//��������д������Ϊ��Ҫ������ຬ�� �вεĹ��췽������������д
		//������޲εĹ��췽���Ļ�������ֱ����newInstance()������
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
