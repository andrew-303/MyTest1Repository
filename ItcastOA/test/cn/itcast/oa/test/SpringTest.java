package cn.itcast.oa.test;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTest {

	private ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");

	//����log4j��־��ʹ��
	private Log log = LogFactory.getLog(getClass());
			
	/*@Test
	public void testLog(){
		log.debug("����һ��debug��Ϣ");
		log.info("����һ��info��Ϣ");
		log.warn("����һ��warn��Ϣ");
		log.error("����һ��error��Ϣ");
		log.fatal("����һ��fatal��Ϣ");
	}*/
	// ����SessionFactory
	@Test
	public void testSessionFactory() {
		SessionFactory sessionFactory = (SessionFactory) ac.getBean("sessionFactory");
		System.out.println(sessionFactory);
	}

	/*// ��������
	@Test
	public void testTransaction() {
		TestService testService = (TestService) ac.getBean("testService");
		testService.saveTwoUsers();
	}*/
}
