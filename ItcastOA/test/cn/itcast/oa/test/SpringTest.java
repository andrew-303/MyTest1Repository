package cn.itcast.oa.test;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTest {

	private ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");

	//引入log4j日志的使用
	private Log log = LogFactory.getLog(getClass());
			
	/*@Test
	public void testLog(){
		log.debug("这是一个debug信息");
		log.info("这是一个info信息");
		log.warn("这是一个warn信息");
		log.error("这是一个error信息");
		log.fatal("这是一个fatal信息");
	}*/
	// 测试SessionFactory
	@Test
	public void testSessionFactory() {
		SessionFactory sessionFactory = (SessionFactory) ac.getBean("sessionFactory");
		System.out.println(sessionFactory);
	}

	/*// 测试事务
	@Test
	public void testTransaction() {
		TestService testService = (TestService) ac.getBean("testService");
		testService.saveTwoUsers();
	}*/
}
