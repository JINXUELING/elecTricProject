package cn.itcast.elec.web.action;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.elec.domain.ElecSystemDDL;
import cn.itcast.elec.service.IElecSystemDDLService;
import cn.itcast.elec.service.impl.ElecSystemDDLServiceImpl;

import com.opensymphony.xwork2.ModelDriven;


@Controller("elecSystemDDLAction")
@Scope(value="prototype")
@SuppressWarnings("serial")
public class ElecSystemDDLAction extends BaseAction implements ModelDriven<ElecSystemDDL> {

	private ElecSystemDDL elecSystemDDL = new ElecSystemDDL();
	
	public ElecSystemDDL getModel() {
		return elecSystemDDL;
	}
	
	@Resource(name=IElecSystemDDLService.SERVICE_NAME)
	private IElecSystemDDLService elecSystemDDLService;
	
	/**  
	* @Name: home
	* @Description: 跳转到数据字典的首页面
	* @Author: 刘洋（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2012-09-10 （创建日期）
	* @Parameters: 无
	* @Return: String,跳转到system/dictionaryIndex.jsp
	*/
	public String home(){
		List<ElecSystemDDL> list = elecSystemDDLService.findKeywordList();
		request.setAttribute("systemList", list);
		return "home";
	}
	
	/**  
	* @Name: edit
	* @Description: 选择数据类型列表跳转到对应的编辑页面
	* @Version: V1.00 （版本号）
	* @Create Date: 2012-09-10 （创建日期）
	* @Parameters: 无
	* @Return: String,跳转到system/dictionaryEdit.jsp
	*/
	public String edit(){
		List<ElecSystemDDL> list = elecSystemDDLService.findSystemDDLList(elecSystemDDL);
		request.setAttribute("systemList", list);
		return "edit";
	}
	/**  
	 * @Name: edit
	 * @Description: 选择数据类型列表跳转到对应的编辑页面
	 * @Version: V1.00 （版本号）
	 * @Create Date: 2012-09-10 （创建日期）
	 * @Parameters: 无
	 * @Return: String,跳转到system/dictionaryEdit.jsp
	 */
	public String save(){
		elecSystemDDLService.saveSystemDDL(elecSystemDDL);
		return "edit";
	}
	
}
