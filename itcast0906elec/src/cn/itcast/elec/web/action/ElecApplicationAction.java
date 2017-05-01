package cn.itcast.elec.web.action;


import java.io.InputStream;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.elec.domain.ElecApplication;
import cn.itcast.elec.domain.ElecApplicationTemplate;
import cn.itcast.elec.domain.ElecApproveInfo;
import cn.itcast.elec.domain.ElecSystemDDL;
import cn.itcast.elec.service.IElecApplicationService;
import cn.itcast.elec.service.IElecApplicationTemplateService;
import cn.itcast.elec.service.IElecSystemDDLService;

import com.opensymphony.xwork2.ModelDriven;


@Controller("elecApplicationAction")
@Scope(value="prototype")
@SuppressWarnings("serial")
public class ElecApplicationAction extends BaseAction implements ModelDriven<ElecApplication> {

	private ElecApplication elecApplication = new ElecApplication();
	
	public ElecApplication getModel() {
		return elecApplication;
	}
	
	@Resource(name=IElecApplicationService.SERVICE_NAME)
	private IElecApplicationService elecApplicationService;
	
	@Resource(name=IElecApplicationTemplateService.SERVICE_NAME)
	private IElecApplicationTemplateService elecApplicationTemplateService;
	
	@Resource(name=IElecSystemDDLService.SERVICE_NAME)
	
	private IElecSystemDDLService elecSystemDDLService;
	/**跳转到起草申请的首页面（列表）*/
	public String templateHome(){
		List<ElecApplicationTemplate> list = elecApplicationTemplateService.findApplicationTemplateList();
		request.setAttribute("templateList", list);
		return "templateHome";
	}
	
	/**跳转到对应模板下载和提交页面,完成模板流程的提交*/
	public String flowSubmitApplication(){
		return "flowSubmitApplication";
	}
	
	/**保存提交的申请文档，并开启工作流*/
	public String saveApplication(){
		elecApplicationService.saveApplication(elecApplication);
		return "saveApplication";
	}
	
	/**下载申请文档模板*/
	public String download(){
		InputStream inputStream = elecApplicationService.findInputStreamByPath(elecApplication);
		//放置到模型驱动中
		elecApplication.setInputStream(inputStream);
		return "download";
	}
	/**我的申请页面、展示了申请*/
	public String myApplicationHome(){
		//初始化页面的申请模板列表
		List<ElecApplicationTemplate> temList = elecApplicationTemplateService.findApplicationTemplateList();
		request.setAttribute("templateList", temList);
		//初始化页面的审核状态列表
		ElecSystemDDL elecSystemDDL = new ElecSystemDDL();
		elecSystemDDL.setKeyword("审核状态");
		List<ElecSystemDDL> sysList = elecSystemDDLService.findSystemDDLList(elecSystemDDL);
		request.setAttribute("systemList", sysList);
		//获取申请信息表的相关信息（根据当前人进行查询）
		List<ElecApplication> appList = elecApplicationService.findApplicationList(elecApplication);
		request.setAttribute("applicationList", appList);
		return "myApplicationHome";
	}
	/**查看申请的流程记录*/
	public String flowApprovedHistory(){
		List<ElecApproveInfo> list = elecApplicationService.findApproveInfoListByApplicationID(elecApplication);
		request.setAttribute("approveList", list);
		return "flowApprovedHistory";
	}
	/**申请*/
	public String approve(){
		elecApplicationService.approveInfo(elecApplication);
		return "approve";
	}
	/**待我审批*/
	public String myTaskHome(){
		List<ElecApplication> list = elecApplicationService.findApplicationVariable();
		request.setAttribute("applicationList",list);
		return "myTaskHome";
	}
	/**审批处理*/
	public String flowApprove(){
		Collection<String> collection = elecApplicationService.getOutComeTransition(elecApplication);
		request.setAttribute("collection", collection);
		return "flowApprove";
	}
}
