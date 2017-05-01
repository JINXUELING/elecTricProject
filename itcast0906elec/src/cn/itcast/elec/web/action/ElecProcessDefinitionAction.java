package cn.itcast.elec.web.action;

import java.io.InputStream;
import java.util.List;

import javax.annotation.Resource;

import org.jbpm.api.ProcessDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.elec.service.IElecProcessDefinitionService;
import cn.itcast.elec.web.form.ElecProcessDefinition;

import com.opensymphony.xwork2.ModelDriven;


@Controller("elecProcessDefinitionAction")
@Scope(value="prototype")
@SuppressWarnings("serial")
public class ElecProcessDefinitionAction extends BaseAction implements ModelDriven<ElecProcessDefinition> {

	private ElecProcessDefinition elecProcessDefinition = new ElecProcessDefinition();
	
	public ElecProcessDefinition getModel() {
		return elecProcessDefinition;
	}
	
	@Resource(name=IElecProcessDefinitionService.SERVICE_NAME)
	private IElecProcessDefinitionService elecProcessDefinitionService;

	
	/**  
	* @Name: home
	* @Description: 跳转到是审批流程管理的首页面（列表）
	* @Author: 刘洋（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2012-09-18 （创建日期）
	* @Parameters: 无
	* @Return: String,跳转到workflow/processDefinitionList.jsp
	*/
	public String home(){
		List<ProcessDefinition> list = elecProcessDefinitionService.findPDListByLastVersion();
		request.setAttribute("pdList", list);
		return "home";
	}
	
	/**  
	* @Name: add
	* @Description: 跳转到部署流程定义的页面
	* @Author: 刘洋（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2012-09-18 （创建日期）
	* @Parameters: 无
	* @Return: String,跳转到workflow/processDefinitionAdd.jsp
	*/
	public String add(){
		return "add";
	}
	
	/**  
	* @Name: save
	* @Description: 部署流程定义文档（xml和png）
	* @Author: 刘洋（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2012-09-18 （创建日期）
	* @Parameters: 无
	* @Return: String,重定向到workflow/processDefinitionList.jsp
	*/
	public String save(){
		elecProcessDefinitionService.deployeProcessDefinition(elecProcessDefinition);
		return "save";
	}
	
	/**  
	* @Name: delete
	* @Description: 删除对应key下所有版本的流程定义
	* @Author: 刘洋（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2012-09-18 （创建日期）
	* @Parameters: 无
	* @Return: String,重定向到workflow/processDefinitionList.jsp
	*/
	public String delete(){
		elecProcessDefinitionService.deleteProcessDefinitionByKey(elecProcessDefinition);
		return "save";
	}
	
	/**  
	* @Name: downloadProcessImage
	* @Description: 查看流程图
	* @Author: 刘洋（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2012-09-18 （创建日期）
	* @Parameters: 无
	* @Return: String,将图片定向到流的形式在页面中显示
	*/
	public String downloadProcessImage(){
		InputStream inputStream = elecProcessDefinitionService.findImpageInputStreamByID(elecProcessDefinition);	
		//放置到模型驱动中，使用stream的定义在页面中显示图片
		elecProcessDefinition.setInputStream(inputStream);
		return "downloadProcessImage";
	}
}
