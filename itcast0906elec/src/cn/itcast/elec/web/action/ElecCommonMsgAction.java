package cn.itcast.elec.web.action;


import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.elec.domain.ElecCommonMsg;
import cn.itcast.elec.service.IElecCommonMsgService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;


@Controller("elecCommonMsgAction")
@Scope(value="prototype")
@SuppressWarnings("serial")
public class ElecCommonMsgAction extends BaseAction implements ModelDriven<ElecCommonMsg> {

	private ElecCommonMsg elecCommonMsg = new ElecCommonMsg();
	
	public ElecCommonMsg getModel() {
		return elecCommonMsg;
	}
	
	@Resource(name=IElecCommonMsgService.SERVICE_NAME)
	private IElecCommonMsgService elecCommonMsgService;
	
	/**  
	* @Name: home
	* @Description: 跳转到代办事宜的首页面
	* @Author: 刘洋（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2012-09-07 （创建日期）
	* @Parameters: 无
	* @Return: String,跳转到system/actionIndex.jsp
	*/
	public String home(){
		elecCommonMsg = elecCommonMsgService.findCommonMsg();
		//将对象放置到栈顶
		ActionContext.getContext().getValueStack().pop();
		ActionContext.getContext().getValueStack().push(elecCommonMsg);
		return "home";
	}
	
	/**  
	* @Name: save
	* @Description: 保存代办事宜
	* @Author: 刘洋（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2012-09-07 （创建日期）
	* @Parameters: 无
	* @Return: String 使用重定向，重定向到actingIndex.jsp
	*/
	public String save(){
		elecCommonMsgService.saveCommonMsg(elecCommonMsg);
		return "save";
	}
}
