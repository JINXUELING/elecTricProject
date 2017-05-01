package cn.itcast.elec.web.action;


import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.elec.domain.ElecText;
import cn.itcast.elec.service.IElecTextService;

import com.opensymphony.xwork2.ModelDriven;

/**
 * <bean id="elecTextAction" class="cn.itcast.elec.web.action.ElecTextAction">
 * 		<property name="elecTextService" ref="cn.itcast.elec.service.impl.ElecTextServiceImpl"></property>
   </bean>
 *
 */
@Controller("elecTextAction")
@Scope(value="prototype")
@SuppressWarnings("serial")
public class ElecTextAction extends BaseAction implements ModelDriven<ElecText> {

	private ElecText elecText = new ElecText();
	
	@Resource(name=IElecTextService.SERVICE_NAME)
	private IElecTextService elecTextService;
	
	public ElecText getModel() {
		return elecText;
	}

	public String save(){
		elecTextService.saveElecText(elecText);
		System.out.println(request.getParameter("textDate"));
		return "success";
	}
}
