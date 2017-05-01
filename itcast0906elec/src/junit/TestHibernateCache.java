package junit;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

public class TestHibernateCache {
	@Test
	public void testCache(){
		Configuration configuration = new Configuration();
		configuration.configure();
		SessionFactory sf = configuration.buildSessionFactory();
		Session s = sf.openSession();
		Transaction tr = s.beginTransaction();
		
		Query query = s.createQuery("from ElecSystemDDL");
		//使用查询缓存
		query.setCacheable(true);
		query.list();//产生select语句
		
		tr.commit();
		s.close();
		////////////////////////////////////////////////////////////////////
		s = sf.openSession();
		tr = s.beginTransaction();
		
		Query query1 = s.createQuery("from ElecSystemDDL");
		//使用查询缓存
		query1.setCacheable(true);
		query1.list();//?
		
		tr.commit();
		s.close();
	}
}
