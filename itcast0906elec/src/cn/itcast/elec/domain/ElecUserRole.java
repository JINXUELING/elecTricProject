package cn.itcast.elec.domain;


@SuppressWarnings("serial")
public class ElecUserRole implements java.io.Serializable {
	
	private Integer seqID;    //主键ID
	private String userID;    //用户ID
	private String roleID;    //角色ID
	private String remark;    //备注
	
	public Integer getSeqID() {
		return seqID;
	}
	public void setSeqID(Integer seqID) {
		this.seqID = seqID;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getRoleID() {
		return roleID;
	}
	public void setRoleID(String roleID) {
		this.roleID = roleID;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	/************************添加非持久化对象的属性*********************************/
	//选择的角色id
	private String roleid;
	//存放权限code的集合
	private String [] selectoper;
	//存放用户ID的集合
	private String [] selectuser;
	
	public String[] getSelectoper() {
		return selectoper;
	}
	public void setSelectoper(String[] selectoper) {
		this.selectoper = selectoper;
	}
	public String[] getSelectuser() {
		return selectuser;
	}
	public void setSelectuser(String[] selectuser) {
		this.selectuser = selectuser;
	}
	public String getRoleid() {
		return roleid;
	}
	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}
	/************************添加非持久化对象的属性*********************************/
	
	
}
