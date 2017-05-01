package cn.itcast.elec.web.action;

import java.io.InputStream;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.jbpm.api.ProcessDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.elec.domain.ElecApplicationTemplate;
import cn.itcast.elec.domain.ElecCommonMsg;
import cn.itcast.elec.service.IElecApplicationTemplateService;
import cn.itcast.elec.service.IElecProcessDefinitionService;
import cn.itcast.elec.service.impl.ElecApplicationTemplateServiceImpl;

import com.opensymphony.xwork2.ModelDriven;

@SuppressWarnings("serial")
@Controller("elecApplicationTemplateAction")
@Scope(value="prototype")
public class ElecApplicationTemplateAction extends BaseAction implements ModelDriven<ElecApplicationTemplate>{
	
	private ElecApplicationTemplate elecApplicationTemplate = new ElecApplicationTemplate();
	
	public ElecApplicationTemplate getModel() {
		return elecApplicationTemplate;
	}
	
	@Resource(name=IElecApplicationTemplateService.SERVICE_NAME)
	private IElecApplicationTemplateService elecApplicationTemplateServiceImpl; 
	@Resource(name=IElecProcessDefinitionService.SERVICE_NAME)
	private IElecProcessDefinitionService elecProcessDefinitionService; 
	
	
	/**获取申请模板列表*/
	public String home(){
		List<ElecApplicationTemplate> list = elecApplicationTemplateServiceImpl.findApplicationTemplateList();
		request.setAttribute("templateList", list);
		return "home";
	}
	
	/**删除指定申请模板*/
	public String delete(){
		elecApplicationTemplateServiceImpl.deleteApplicationTemplateByID(elecApplicationTemplate);
		return "save";
	}
	
	/**添加申请模板*/
	public String add(){
		//获取最新版本的流程定义列表，在页面中显示
		List<ProcessDefinition> list = elecProcessDefinitionService.findPDListByLastVersion();
		request.setAttribute("pdList", list);
		return "add";
	}
	
	/**保存申请模板*/
	public String save(){
		//获取最新版本的流程定义列表，在页面中显示
		elecApplicationTemplateServiceImpl.saveApplicationTemplate(elecApplicationTemplate);
		return "save";
	}
	
	/**修改申请模板*/
	public String edit(){
		elecApplicationTemplate = elecApplicationTemplateServiceImpl.findApplicationTemplateByID(elecApplicationTemplate);
		//将对象elecApplicationTemplate放置栈顶，用于表单回显
		ServletActionContext.getContext().getValueStack().pop();
		ServletActionContext.getContext().getValueStack().push(elecApplicationTemplate);
		//获取最新版本的流程定义列表
		List<ProcessDefinition> list = elecProcessDefinitionService.findPDListByLastVersion();
		request.setAttribute("pdList", list);
		return "edit";
	}
	/**更新申请模板*/
	public String update(){
		elecApplicationTemplateServiceImpl.updateApplicationTemplate(elecApplicationTemplate);
		return "save";
	}
	
	/**下载申请模板*/
	public String download(){
		InputStream inputStream = elecApplicationTemplateServiceImpl.findInputStreamByPath(elecApplicationTemplate);
		//放置到模型驱动中
		elecApplicationTemplate.setInputStream(inputStream);
		return "download";
	}
}
