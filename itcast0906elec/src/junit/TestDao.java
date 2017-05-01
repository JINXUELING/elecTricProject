package junit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.itcast.elec.dao.IElecTextDao;
import cn.itcast.elec.domain.ElecText;

public class TestDao {
	@Test
	public void testSaveElecText(){
		//加载类路径下的beans.xml
		ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
		//获取spring容器中的bean的id节点
		IElecTextDao elecTextDao = (IElecTextDao) ac.getBean(IElecTextDao.SERVICE_NAME);
		//保存
		ElecText elecText = new ElecText();
		elecText.setTextName("测试DAO名称");
		elecText.setTextDate(new Date());
		elecText.setTextRemark("测试DAO备注");
		elecTextDao.save(elecText);
	}
	
	@Test
	public void testUpdate(){
		//加载类路径下的beans.xml
		ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
		//获取spring容器中的bean的id节点
		IElecTextDao elecTextDao = (IElecTextDao) ac.getBean(IElecTextDao.SERVICE_NAME);
		//更新
		ElecText elecText = new ElecText();
		elecText.setTextID("402881e439998586013999858bb60001");
		elecText.setTextName("李呵呵");
		elecText.setTextDate(new Date());
		elecText.setTextRemark("小李");
		elecTextDao.update(elecText);
	}
	
	@Test
	public void testFindObjectByID(){
		//加载类路径下的beans.xml
		ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
		//获取spring容器中的bean的id节点
		IElecTextDao elecTextDao = (IElecTextDao) ac.getBean(IElecTextDao.SERVICE_NAME);
		Serializable id = "402881e439998586013999858bb60001";
		ElecText elecText = elecTextDao.findObjectByID(id);
		System.out.println(elecText.getTextName()+"       "+elecText.getTextDate()+"        "+elecText.getTextRemark());
	}
	
	@Test
	public void testDeleteObjectByIDs(){
		//加载类路径下的beans.xml
		ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
		//获取spring容器中的bean的id节点
		IElecTextDao elecTextDao = (IElecTextDao) ac.getBean(IElecTextDao.SERVICE_NAME);
		Serializable [] ids = {"402881e43999baef013999baf27c0001","402881e4399a57bd01399a57f2af0001"};
		elecTextDao.deleteObjectByIDs(ids);
	}
	
	@Test
	public void testDeleteObjectByConllection(){
		//加载类路径下的beans.xml
		ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
		//获取spring容器中的bean的id节点
		IElecTextDao elecTextDao = (IElecTextDao) ac.getBean(IElecTextDao.SERVICE_NAME);
		List<ElecText> list = new ArrayList<ElecText>();
		ElecText elecText1 = new ElecText();
		elecText1.setTextID("402881e4399a353b01399a353e590001");
		ElecText elecText2 = new ElecText();
		elecText2.setTextID("402881e4399a388a01399a388e300001");
		ElecText elecText3 = new ElecText();
		elecText3.setTextID("402881e4399a393d01399a3941ff0001");
		list.add(elecText1);
		list.add(elecText2);
		list.add(elecText3);
		elecTextDao.deleteObjectByConllection(list);
	}
	
	
	
}
