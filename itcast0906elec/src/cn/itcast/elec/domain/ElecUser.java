package cn.itcast.elec.domain;

import java.io.File;
import java.util.Date;

public class ElecUser {
	private String userID;      //主键ID
	private String jctID;       //所属单位code
	private String userName;   //用户姓名
	private String logonName;  //登录名
	private String logonPwd;   //密码
	private String sexID;      //性别
	private Date birthday;     //出生日期
	private String address;    //联系地址
	private String contactTel; //联系电话 
	private String email;      //电子邮箱
	private String mobile;     //手机
	private String isDuty;     //是否在职
	private Date onDutyDate;   //入职时间
	private Date offDutyDate;  //离职时间
	private String remark;     //备注
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getJctID() {
		return jctID;
	}
	public void setJctID(String jctID) {
		this.jctID = jctID;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getLogonName() {
		return logonName;
	}
	public void setLogonName(String logonName) {
		this.logonName = logonName;
	}
	public String getLogonPwd() {
		return logonPwd;
	}
	public void setLogonPwd(String logonPwd) {
		this.logonPwd = logonPwd;
	}
	public String getSexID() {
		return sexID;
	}
	public void setSexID(String sexID) {
		this.sexID = sexID;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getContactTel() {
		return contactTel;
	}
	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getIsDuty() {
		return isDuty;
	}
	public void setIsDuty(String isDuty) {
		this.isDuty = isDuty;
	}
	public Date getOnDutyDate() {
		return onDutyDate;
	}
	public void setOnDutyDate(Date onDutyDate) {
		this.onDutyDate = onDutyDate;
	}
	public Date getOffDutyDate() {
		return offDutyDate;
	}
	public void setOffDutyDate(Date offDutyDate) {
		this.offDutyDate = offDutyDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/******************************非持久化对象的属性*************************************/
	/**
	 * 用来判断跳转的是编辑页面还是查看页面
	 *   如果viewflag=1；此时跳转的是查看页面
	 *   如果viewflag=null；此时跳转的是编辑页面
	 */
	private String viewflag;
	/**用来判断当前用户的角色的类型
		 如果是系统管理员或者高级管理员，roleflag为null
		 如果是系统管理员或者高级管理员以下角色类型，roleflag为1
	 */
	private String roleflag;
	
	/**
	 * 判断当前编辑用户操作是否修改了密码的标识
	   	如果md5flag=1：此时表示没有修改密码，此时服务器处理不需要进行密码加密
	   	如果md5flag=null：此时表示修改了密码，此时服务器处理需要进行密码加密（新增的时候md5flag的值都是null，都得进行密码加密）
	 */
	private String md5flag;
	/***
	 *  添加flag，用来判断页面用户的复选框是否被选中的标识
             如果flag=1:说明当前角色具有该用户，此时页面的复选框被选中
             如果flag=2:说明当前角色不具有该用户，此时页面的复选框不被选中
	 */
	private String flag;
	
	/**
	 * 导入文件
	 */
	private File file;
	
	
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getMd5flag() {
		return md5flag;
	}
	public void setMd5flag(String md5flag) {
		this.md5flag = md5flag;
	}
	public String getViewflag() {
		return viewflag;
	}
	public void setViewflag(String viewflag) {
		this.viewflag = viewflag;
	}
	public String getRoleflag() {
		return roleflag;
	}
	public void setRoleflag(String roleflag) {
		this.roleflag = roleflag;
	}
	/******************************************************************************************/
	
}
