package cn.itcast.elec.web.form;

import java.util.Date;

@SuppressWarnings("serial")
public class ElecApplicationVariable implements java.io.Serializable {
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
	
	
}
