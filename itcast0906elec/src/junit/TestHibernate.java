package junit;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import cn.itcast.elec.domain.ElecText;

public class TestHibernate {
	@Test
	public void testSave(){
		Configuration configuration = new Configuration();
		//加载类路径的hibernate.cfg.xml
		configuration.configure();
		//调用sessionFactory
		SessionFactory sf = configuration.buildSessionFactory();
		//打开session
		Session s = sf.openSession();
		//开启事务
		Transaction tr = s.beginTransaction();
		//保存ElecText
		ElecText elecText = new ElecText();
		elecText.setTextName("测试Hibernate名称");
		elecText.setTextDate(new Date());
		elecText.setTextRemark("测试Hibernate备注");
		s.save(elecText);
		//事务提交
		tr.commit();
		//关闭session
		s.close();
	}
}
