package cn.itcast.elec.domain;

import java.io.File;
import java.io.InputStream;
import java.util.Date;

@SuppressWarnings("serial")
public class ElecApplication implements java.io.Serializable {
	
	private Long applicationID;                  //主键ID
	private Long applicationTemplateID;          //申请模板表的主键
	private String title;						 //上传的文件标题
	private String path;						 //上传的文件的存储路径
	private String applicationUserID;			 //申请人ID
	private String applicationLogonName;		 //申请人登录名
	private String applicationUserName;			 //申请人姓名
	private Date applyTime;                      //申请日期
	private String status;                       //当前审批状态
	
	public Long getApplicationID() {
		return applicationID;
	}
	public void setApplicationID(Long applicationID) {
		this.applicationID = applicationID;
	}
	public Long getApplicationTemplateID() {
		return applicationTemplateID;
	}
	public void setApplicationTemplateID(Long applicationTemplateID) {
		this.applicationTemplateID = applicationTemplateID;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getApplicationUserID() {
		return applicationUserID;
	}
	public void setApplicationUserID(String applicationUserID) {
		this.applicationUserID = applicationUserID;
	}
	public String getApplicationLogonName() {
		return applicationLogonName;
	}
	public void setApplicationLogonName(String applicationLogonName) {
		this.applicationLogonName = applicationLogonName;
	}
	public String getApplicationUserName() {
		return applicationUserName;
	}
	public void setApplicationUserName(String applicationUserName) {
		this.applicationUserName = applicationUserName;
	}
	public Date getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	/****************************添加非持久化的属性*******************************************/
	public static String APP_RUNNING = "1";  //审核中
	public static String APP_REJECT = "2";   //审核不通过
	public static String APP_PASS = "3";     //审核通过
	
	//定义流程定义的key
	private String processDefinitionKey;
	//在页面上显示申请时间
	private String time;
	//定义任务ID
	private String taskID;
	
	//添加审核的相关信息
	private Long approveID;          //主键ID
	private String comment;			 //审批意见
	private boolean approval;        //审批结果，是否通过（同意或者不同意）
	private String approveUserID;	 //审批人ID
	private String approveUserName;  //审批人姓名
	private Date approveTime;        //审批日期
	
	//指定连线名称
	private String outcome;
	
	//上传的附件
	private File upload;
	//用于下载文件
	private InputStream inputStream;

	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	public File getUpload() {
		return upload;
	}
	public void setUpload(File upload) {
		this.upload = upload;
	}
	public String getProcessDefinitionKey() {
		return processDefinitionKey;
	}
	public void setProcessDefinitionKey(String processDefinitionKey) {
		this.processDefinitionKey = processDefinitionKey;
	}
	public String getTaskID() {
		return taskID;
	}
	public void setTaskID(String taskID) {
		this.taskID = taskID;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public Long getApproveID() {
		return approveID;
	}
	public void setApproveID(Long approveID) {
		this.approveID = approveID;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public boolean isApproval() {
		return approval;
	}
	public void setApproval(boolean approval) {
		this.approval = approval;
	}
	public String getApproveUserID() {
		return approveUserID;
	}
	public void setApproveUserID(String approveUserID) {
		this.approveUserID = approveUserID;
	}
	public String getApproveUserName() {
		return approveUserName;
	}
	public void setApproveUserName(String approveUserName) {
		this.approveUserName = approveUserName;
	}
	public Date getApproveTime() {
		return approveTime;
	}
	public void setApproveTime(Date approveTime) {
		this.approveTime = approveTime;
	}
	public String getOutcome() {
		return outcome;
	}
	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}
	
	
	/****************************添加非持久化的属性*******************************************/
}
