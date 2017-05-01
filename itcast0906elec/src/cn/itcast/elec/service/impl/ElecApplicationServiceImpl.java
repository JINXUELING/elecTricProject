package cn.itcast.elec.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.task.Task;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.elec.dao.IElecApplicationDao;
import cn.itcast.elec.dao.IElecApplicationTemplateDao;
import cn.itcast.elec.dao.IElecApproveInfoDao;
import cn.itcast.elec.dao.IElecSystemDDLDao;
import cn.itcast.elec.domain.ElecApplication;
import cn.itcast.elec.domain.ElecApplicationTemplate;
import cn.itcast.elec.domain.ElecApproveInfo;
import cn.itcast.elec.domain.ElecUser;
import cn.itcast.elec.service.IElecApplicationService;
import cn.itcast.elec.util.UploadUtils;
import cn.itcast.elec.web.form.ElecApplicationVariable;


@Service(IElecApplicationService.SERVICE_NAME)
@Transactional(readOnly=true)
public class ElecApplicationServiceImpl implements IElecApplicationService {

	@Resource(name=IElecApplicationDao.SERVICE_NAME)
	private IElecApplicationDao elecApplicationDao;   //申请

	@Resource(name=IElecApproveInfoDao.SERVICE_NAME)
	private IElecApproveInfoDao elecApproveInfoDao;   //审核
	
	@Resource(name=IElecApplicationTemplateDao.SERVICE_NAME)
	private IElecApplicationTemplateDao elecApplicationTemplateDao;   //申请模板
	
	@Resource(name=IElecSystemDDLDao.SERVICE_NAME)
	private IElecSystemDDLDao elecSystemDDLDao;  //数据字典
	
	@Resource(name="processEngine")
	private ProcessEngine processEngine;

	/**  
	* @Name: saveApplication
	* @Description: 提交申请，同时上传附件，同时启动流程实例，同时完成我的个人任务
	* @Author: 刘洋（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2012-09-20 （创建日期）
	* @Parameters: ElecApplication：存放页面的参数值
	* @Return: 无
	*/
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void saveApplication(ElecApplication elecApplication) {
		//先上传附件，然后再保存申请信息
		elecApplication = this.elecApplicationVOToPO(elecApplication);
		elecApplicationDao.save(elecApplication);
		
		//启动流程实例，按照流程定义的key启动，同时设置流程变量，用于对信息的传递，流程变量的ElecApplicationVariable不能再随意修改
		Map<String, ElecApplicationVariable> variables = new HashMap<String, ElecApplicationVariable>();
		//在jpdl.xml中<task assignee="#{application.applicationLogonName}" g="173,106,127,64" name="提交申请">
		ElecApplicationVariable elecApplicationVariable = this.elecApplicationPOToVariable(elecApplication);
		variables.put("application", elecApplicationVariable);
		ProcessInstance pi = processEngine.getExecutionService().startProcessInstanceByKey(elecApplication.getProcessDefinitionKey(),variables);
		//完成我的个人任务
		//查找我的个人任务
		Task task = processEngine.getTaskService()//
		               .createTaskQuery()//
		               .processInstanceId(pi.getId())//按照流程实例ID进行查询，获取唯一的Task对象
		               .uniqueResult();
		processEngine.getTaskService().completeTask(task.getId());//使用任务ID完成任务
	}

	/**将PO对象转换成流程变量存储的值*/
	private ElecApplicationVariable elecApplicationPOToVariable(
			ElecApplication elecApplication) {
		ElecApplicationVariable elecApplicationVariable = new ElecApplicationVariable();
		//用于javabean属性值的拷贝
		try {
			BeanUtils.copyProperties(elecApplicationVariable, elecApplication);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return elecApplicationVariable;
	}

	/**组织PO对象， 用于保存*/
	private ElecApplication elecApplicationVOToPO(
			ElecApplication elecApplication) {
		//申请时间
		elecApplication.setApplyTime(new Date());
		//申请状态(设置为审核中)
		elecApplication.setStatus(ElecApplication.APP_RUNNING);
		//使用session获取当前人信息，作为申请的信息(申请人ID，申请人的登录名，申请人的姓名)
		HttpSession session = ServletActionContext.getRequest().getSession();
		ElecUser elecUser = (ElecUser) session.getAttribute("globle_user");
		elecApplication.setApplicationUserID(elecUser.getUserID());
		elecApplication.setApplicationLogonName(elecUser.getLogonName());
		elecApplication.setApplicationUserName(elecUser.getUserName());
		//申请的标题：申请标题的格式：申请文件模板名称_申请人姓名_申请时间。
		//获取申请模板名称
		ElecApplicationTemplate elecApplicationTemplate = elecApplicationTemplateDao.findObjectByID(elecApplication.getApplicationTemplateID());
		elecApplication.setTitle(elecApplicationTemplate.getName()+"_"+elecUser.getUserName()+"_"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(elecApplication.getApplyTime()));
		
		//设置上传的路径，同时上传文件
		elecApplication.setPath(UploadUtils.uploadReturnPath(elecApplication.getUpload()));
		//设置流程定义的key
		elecApplication.setProcessDefinitionKey(elecApplicationTemplate.getProcessDefinitionKey());
		return elecApplication;
	}

	/**  
	* @Name: findApplicationList
	* @Description: 获取我的申请查询列表，按照对应的条件进行查询
	* @Author: 刘洋（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2012-09-20 （创建日期）
	* @Parameters: ElecApplication：存放页面的查询参数值
	* @Return: List<ElecApplication> 申请信息列表
	*/
	public List<ElecApplication> findApplicationList(
			ElecApplication elecApplication) {
		String condition = "";
		List<Object> paramsList = new ArrayList<Object>();
		//按照申请模板查询
		if(elecApplication.getApplicationTemplateID()!=null){
			condition += " and o.applicationTemplateID = ?";
			paramsList.add(elecApplication.getApplicationTemplateID());
		}
		//按照审核状态查询
		if(StringUtils.isNotBlank(elecApplication.getStatus())){
			condition += " and o.status = ?";
			paramsList.add(elecApplication.getStatus());
		}
		//查询当前登录的人的申请信息
		ElecUser elecUser = (ElecUser) ServletActionContext.getRequest().getSession().getAttribute("globle_user");
		condition += " and o.applicationLogonName = ?";
		paramsList.add(elecUser.getLogonName());
		
		Object [] params = paramsList.toArray();
		//按照申请时间的降序
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("o.applyTime", "desc");
		List<ElecApplication> list = elecApplicationDao.findCollectionByConditionNoPage(condition, params, orderby);
		//数据字典进行转换，将数据项的编号转换成数据项的值
		list = this.systemDDLConvert(list);
		return list;
	}

	/**值转换，在数据字典中将数据项的编号转换成数据项的值*/
	private List<ElecApplication> systemDDLConvert(List<ElecApplication> list) {
		for(int i=0;list!=null && i<list.size();i++){
			ElecApplication elecApplication = list.get(i);
			elecApplication.setStatus(elecSystemDDLDao.findDdlNameByCondition("审核状态", elecApplication.getStatus()));
		}
		return list;
	}

	/**  
	* @Name: findApplicationVariable
	* @Description: 获取申请人的相关信息
	* @Author: 刘洋（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2012-09-20 （创建日期）
	* @Parameters: 无
	* @Return: List<ElecApplicationVariable> 申请人的相关信息列表
	*/
	public List<ElecApplication> findApplicationVariable() {
		//从session中获取当前人的登录名
		ElecUser elecUser = (ElecUser) ServletActionContext.getRequest().getSession().getAttribute("globle_user");
		String logonName = elecUser.getLogonName();
		//以登录名做为条件查询任务列表
		List<Task> list = processEngine.getTaskService()//
				         .createTaskQuery()//
				         .assignee(logonName)//指定办理人
				         .list();
		//从Task对象中获取对应传递流程变量的对象
		List<ElecApplication> applicationList = this.taskListToApplicationList(list);
		return applicationList;
	}

	/**从Task对象中获取对应传递流程变量的对象*/
	private List<ElecApplication> taskListToApplicationList(List<Task> list) {
		List<ElecApplication> applicationList = new ArrayList<ElecApplication>();
		if(list!=null){
			for(Task task:list){
				ElecApplicationVariable elecApplicationVariable = (ElecApplicationVariable) processEngine.getTaskService()//
				                                             .getVariable(task.getId(), "application");
				ElecApplication elecApplication = new ElecApplication();
				try {
					BeanUtils.copyProperties(elecApplication, elecApplicationVariable);
				} catch (Exception e) {
					e.printStackTrace();
				} 
				elecApplication.setTaskID(task.getId());
				//处理日期转换
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String applyTime = simpleDateFormat.format(elecApplication.getApplyTime());
				elecApplication.setTime(applyTime);
				applicationList.add(elecApplication);
			}			
		}
		return applicationList;
	}

	/**  
	* @Name: getOutComeTransition
	* @Description: 使用任务ID获取执行下一步的连线的名称
	* @Author: 刘洋（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2012-09-20 （创建日期）
	* @Parameters: ElecApplication，存放任务ID
	* @Return: Collection<String>：连线的集合名称
	*/
	public Collection<String> getOutComeTransition(
			ElecApplication elecApplication) {
		//任务ID
		String taskID = elecApplication.getTaskID();
		//获取执行连线的名称集合
		Collection<String> collection = processEngine.getTaskService()//
		                           .getOutcomes(taskID);
		return collection;
	}

	/**  
	* @Name: findInputStreamByPath
	* @Description: 获取申请文件的路径，下载该文件
	* @Author: 刘洋（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2012-09-20 （创建日期）
	* @Parameters: ElecApplication，存放申请ID
	* @Return: InputStream ，存放文件的输入流
	*/
	public InputStream findInputStreamByPath(ElecApplication elecApplication) {
		//获取申请ID
		Long id = elecApplication.getApplicationID();
		//使用id获取申请的详细信息
		ElecApplication application = elecApplicationDao.findObjectByID(id);
		InputStream inputStream = null;
		try {
			//获取路径path
			String path = application.getPath();
			//获取的标题用作下载文件的名称
			String filename = application.getTitle();
			//将filename转成iso8859-1的形式，用作在xml
			filename = new String(filename.getBytes("GBK"),"iso8859-1");
			//将filename使用request传递给struts.xml，用来对下载文件的命名
			ServletActionContext.getRequest().setAttribute("fileName", filename);
			//使用路径path找到对应的文件
			File file = new File(path);
			inputStream = new FileInputStream(file);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return inputStream;
	}

	/**  
	* @Name: approveInfo
	* @Description: 审核人对报审人的审批信息处理
	* @Author: 刘洋（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2012-09-20 （创建日期）
	* @Parameters: ElecApplication，存放审核参数
	* @Return: 无
	*/
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void approveInfo(ElecApplication elecApplication) {
		//从页面中获取保存的参数，保存审批信息表
		ElecApproveInfo elecApproveInfo = this.approveVOToPO(elecApplication);
		elecApproveInfoDao.save(elecApproveInfo);
		
		//按照指定的连线，完成当前任务
		String taskID = elecApplication.getTaskID();
		String outcome = elecApplication.getOutcome();
		//以任务ID作为条件获取任务对象，而且获取任务对象一定要放置到完成任务前操作
		Task task = processEngine.getTaskService()//
		                 .getTask(taskID);
		processEngine.getTaskService()//
		                      .completeTask(taskID, outcome);
		
		//判断当前流程是否结束
		/**
		 * 如果pi==null：说明流程已经执行完成，该流程已经结束
		 * 如果pi!=null:说明流程没有执行完成，该流程没有结束
		 */
		ProcessInstance pi = processEngine.getExecutionService().findProcessInstanceById(task.getExecutionId());
		
		//根据申请主键ID获取申请的详细信息，用于对审核状态进行更新
		ElecApplication application = elecApplicationDao.findObjectByID(elecApplication.getApplicationID());
		
		//获取审核人选择的同意或者是不同意的标识
		boolean approval = elecApplication.isApproval();
		/**
		 * 判断当前流程是否结束，如果当前流程结束，说明此时审核人审核的信息是最后一个，
         *      * 修改申请状态为“审核通过”
         */
		if(approval){
			if(pi==null){
				application.setStatus(ElecApplication.APP_PASS);
			}
		}
		/**
		 * * 判断当前流程是否结束，如果当前流程没有结束，说明此时审核人审核的信息不是最后一个人，
              * 强制终止流程
              * 修改审核状态为“审核不通过”
		 */
		else{
			if(pi!=null){
				//强制终止流程
				processEngine.getExecutionService().endProcessInstance(pi.getId(), ProcessInstance.STATE_ENDED);
			}
			// 修改审核状态为“审核不通过”
			application.setStatus(ElecApplication.APP_REJECT);
		}
		//elecApplicationDao.update(application);
	}

	/**值转换，从模型驱动中的值转换成保存审批信息*/
	private ElecApproveInfo approveVOToPO(ElecApplication elecApplication) {
		ElecApproveInfo elecApproveInfo = new ElecApproveInfo();
		
		elecApproveInfo.setApproval(elecApplication.isApproval());//是否同意
		elecApproveInfo.setComment(elecApplication.getComment());//审批意见
		elecApproveInfo.setApplicationID(elecApplication.getApplicationID());//申请ID
		
		ElecUser elecUser = (ElecUser) ServletActionContext.getRequest().getSession().getAttribute("globle_user");
		elecApproveInfo.setApproveUserID(elecUser.getUserID());   //用户ID
		elecApproveInfo.setApproveUserName(elecUser.getUserName()); //用户名
		elecApproveInfo.setApproveTime(new Date());        //审批日期（当前日期）
		return elecApproveInfo;
	}

	/**  
	* @Name: findApproveInfoListByApplicationID
	* @Description: 按申请ID获取对应审核记录信息，返回列表
	* @Author: 刘洋（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2012-09-20 （创建日期）
	* @Parameters: ElecApplication，存放申请ID
	* @Return: List<ElecApproveInfo> 审核信息
	*/
	public List<ElecApproveInfo> findApproveInfoListByApplicationID(
			ElecApplication elecApplication) {
		String condition = "";
		List<Object> paramsList = new ArrayList<Object>();
		if(elecApplication.getApplicationID()!=null){
			condition += " and o.applicationID = ?";
			paramsList.add(elecApplication.getApplicationID());
		}
		Object [] params = paramsList.toArray();
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("o.approveTime", "asc");
		List<ElecApproveInfo> list = elecApproveInfoDao.findCollectionByConditionNoPage(condition, params, orderby);
		return list;
	}
	
	
}
