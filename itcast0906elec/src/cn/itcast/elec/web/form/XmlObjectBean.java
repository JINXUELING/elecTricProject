package cn.itcast.elec.web.form;

public class XmlObjectBean {
	private String code;        //权限code
	private String name;        //权限名称
	private String parentCode;  //父级权限code
	private String parentName;  //父级权限名称
	
	/**
	 * 用来判断页面权限的复选框是否被选中的标识
	 *    如果flag=1:说明当前角色具有该权限，此时页面的复选框被选中
	 *    如果flag=2:说明当前角色不具有该权限，此时页面的复选框不被选中
	 */
	private String flag;
	
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParentCode() {
		return parentCode;
	}
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	
	
}
