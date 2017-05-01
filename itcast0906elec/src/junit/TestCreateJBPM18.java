package junit;

import org.hibernate.SessionFactory;
import org.jbpm.api.ProcessEngine;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestCreateJBPM18 {
	/**生成jbpm的18张表*/
	@Test
	public void testSaveElecText(){
		ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
		//获取spring容器中的bean的id节点
		SessionFactory sf = (SessionFactory) ac.getBean("sessionFactory");
		System.out.println("SessionFactory:"+sf);
	}
	
	/**测试流程引擎*/
	@Test
	public void testProcessEngine(){
		ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
		//获取spring容器中的bean的id节点
		ProcessEngine processEngine = (ProcessEngine) ac.getBean("processEngine");
		System.out.println("processEngine:"+processEngine);
	}
	
}
