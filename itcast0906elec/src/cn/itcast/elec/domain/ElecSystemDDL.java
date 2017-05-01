package cn.itcast.elec.domain;


@SuppressWarnings("serial")
public class ElecSystemDDL implements java.io.Serializable {

	private Integer seqID;    //主键ID(自增长)
	private String keyword;   //数据类型
	private Integer ddlCode;  //数据项的code
	private String ddlName;   //数据项的value
	
	public Integer getSeqID() {
		return seqID;
	}
	public void setSeqID(Integer seqID) {
		this.seqID = seqID;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public Integer getDdlCode() {
		return ddlCode;
	}
	public void setDdlCode(Integer ddlCode) {
		this.ddlCode = ddlCode;
	}
	public String getDdlName() {
		return ddlName;
	}
	public void setDdlName(String ddlName) {
		this.ddlName = ddlName;
	}
/*******************************页面对应的属性值****************************************/
	//存放类型名称
	private String keywordname;
	/**
	 * typeflag：用来判断保存操作的标识：
      		如果typeflag=new：表示要新增一种数据类型
      		如果typeflag=add：表示在原有的数据类型中进行修改和编辑
	 */
	private String typeflag;
	//用来存储该数据类型下对应的数据项的值,获取的值是一个String类型的数组
	private String [] itemname;

	public String getKeywordname() {
		return keywordname;
	}
	public void setKeywordname(String keywordname) {
		this.keywordname = keywordname;
	}
	public String getTypeflag() {
		return typeflag;
	}
	public void setTypeflag(String typeflag) {
		this.typeflag = typeflag;
	}
	public String[] getItemname() {
		return itemname;
	}
	public void setItemname(String[] itemname) {
		this.itemname = itemname;
	}
/***********************************************************************/
	
	
}
