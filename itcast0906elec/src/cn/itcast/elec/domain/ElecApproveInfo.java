package cn.itcast.elec.domain;

import java.util.Date;

@SuppressWarnings("serial")
public class ElecApproveInfo implements java.io.Serializable {
	
	private Long approveID;          //主键ID
	private Long applicationID;	 //申请信息表的主键
	private String comment;			 //审批意见
	private boolean approval;        //审批结果，是否通过（同意或者不同意）
	private String approveUserID;	 //审批人ID
	private String approveUserName;  //审批人姓名
	private Date approveTime;        //审批日期
	
	public Long getApproveID() {
		return approveID;
	}
	public void setApproveID(Long approveID) {
		this.approveID = approveID;
	}
	public Long getApplicationID() {
		return applicationID;
	}
	public void setApplicationID(Long applicationID) {
		this.applicationID = applicationID;
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
	
	
	
}
