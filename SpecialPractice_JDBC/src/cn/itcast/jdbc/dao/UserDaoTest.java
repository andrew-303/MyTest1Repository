package cn.itcast.jdbc.dao;

import java.util.Date;

import cn.itcast.jdbc.dao.refator.UserDaoImpl2;
import cn.itcast.jdbc.domain.User;

public class UserDaoTest {

	public static void main(String[] args) throws Exception {
		UserDao userDao = DaoFactory.getInstance().getUserDao();
		System.out.println("userDao:"+userDao);
		
		User user = new User();
		user.setId(1);
		user.setBirthday(new Date());
		user.setName("wangwu");
		user.setMoney(1000.0f);

		// userDao.addUser(user);
		// System.out.println(user.getId());

		/*  ����UserDaoImpl�е�ģ�����ģʽ��DAO�еĲ�ѯ����
		User u = userDao.findUser(user.getName(), null);
		System.out.println("u.getId()="+u.getId());*/
		
		//42.ʹ�ò���ģʽ��ģ�巽�����ģʽ���иĽ�
		UserDaoImpl2 udi = new UserDaoImpl2();
		String name = udi.findUserName(user.getId());
		System.out.println("u.getName()="+name);
	}
}
