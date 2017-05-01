package junit;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.itcast.elec.domain.ElecText;
import cn.itcast.elec.service.IElecTextService;

public class TestService {
	@Test
	public void testSaveElecText(){
		//加载类路径下的beans.xml
		ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
		//获取spring容器中的bean的id节点
		IElecTextService elecTextService = (IElecTextService) ac.getBean(IElecTextService.SERVICE_NAME);
		//保存
		ElecText elecText = new ElecText();
		elecText.setTextName("测试Service名称");
		elecText.setTextDate(new Date());
		elecText.setTextRemark("测试Service备注");
		elecTextService.saveElecText(elecText);
	}
	
	/**模拟Action层，对底层查询列表方法进行封装*/
	@Test
	public void testFind(){
		//加载类路径下的beans.xml
		ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
		//获取spring容器中的bean的id节点
		IElecTextService elecTextService = (IElecTextService) ac.getBean(IElecTextService.SERVICE_NAME);
		ElecText elecText = new ElecText();
		elecText.setTextName("李");
		elecText.setTextRemark("李");
		List<ElecText> list = elecTextService.findCollectionByConditionNoPage(elecText);
		for(ElecText text:list){
			System.out.println(text.getTextName()+"    "+text.getTextDate()+"    "+text.getTextRemark());
		}
	}
}
