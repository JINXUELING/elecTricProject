package cn.itcast.elec.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.elec.dao.IElecRolePopedomDao;
import cn.itcast.elec.dao.IElecSystemDDLDao;
import cn.itcast.elec.dao.IElecUserDao;
import cn.itcast.elec.domain.ElecUser;
import cn.itcast.elec.service.IElecUserService;
import cn.itcast.elec.util.MD5keyBean;
import cn.itcast.elec.util.PageInfo;

@Service(IElecUserService.SERVICE_NAME)
@Transactional(readOnly = true)
public class ElecUserServiceImpl implements IElecUserService {
	@Resource(name = IElecUserDao.SERVICE_NAME)
	private IElecUserDao elecUserDao;
	@Resource(name = IElecSystemDDLDao.SERVICE_NAME)
	private IElecSystemDDLDao elecSystemDDLDao;
	
	public List<ElecUser> findElecUserList(ElecUser elecUser) {
		String userName = elecUser.getUserName();
		// 组织查询条件
		String condition = "";
		List<String> paramsList = new ArrayList<String>();
		if (StringUtils.isNotBlank(userName)) {
			condition += " and o.userName like ?";
			paramsList.add("%" + userName + "%");
		}
		Object[] params = paramsList.toArray();
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("o.onDutyDate", "asc");
//		List<ElecUser> list = elecUserDao.findCollectionByConditionNoPage(
//				condition, params, orderby);
		/**添加分页逻辑开始 */ 
		 HttpServletRequest request = ServletActionContext.getRequest();
		 PageInfo pageInfo = new PageInfo(request);//处理分页
		 List<ElecUser> list = elecUserDao.findCollectionByConditionWithPage(condition, params, orderby, pageInfo);
		 request.setAttribute("page", pageInfo.getPageBean());
		/**  end */
		// 将数据字典项进行转换，才能显示到页面上
		this.userListConverntSystemDDL(list);
		return list;
	}
	
	//值转换，将数据字典项进行转换成页面显示的值
	private void userListConverntSystemDDL(List<ElecUser> list) {
		if(list != null && list.size() > 0){
			for(int i = 0; i < list.size(); i++){
				ElecUser elecUser = list.get(i);
				//性别
				elecUser
						.setSexID(elecUser.getSexID() != null && !elecUser.getSexID().equals("")? elecSystemDDLDao
							.findDdlNameByCondition("性别", elecUser.getSexID()): "");
				//是否在职
				elecUser
						.setIsDuty(elecUser.getIsDuty()!=null && !elecUser.getIsDuty().equals("")? elecSystemDDLDao.findDdlNameByCondition("是否在职", elecUser.getIsDuty()):"");
			}
		}
	}
	
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void saveElecUser(ElecUser elecUser) {
		
		String userID = elecUser.getUserID();
		this.elecuserLogonPwdByMd5(elecUser);
		if(userID!=null&&!"".equals(userID)){
			elecUserDao.update(elecUser);
		}else{
			elecUserDao.save(elecUser);
		}
	}
	
	/**将ElecUser中的logonPwd字段使用MD5进行加密
	 * @param elecUser
	 */
	private void elecuserLogonPwdByMd5(ElecUser elecUser) {
		String logonPwd = elecUser.getLogonPwd();
		String md5LogonPwd = "";
		//获取判断是否修改密码的标识
		String md5flag = elecUser.getMd5flag();
		//添加默认密码
		if (logonPwd == null || "".equals(logonPwd)) {
			logonPwd = "000000";
		}
		//此时标识没有修改密码，此时不需要进行密码加密
		if(md5flag!=null && md5flag.equals("1")){
			md5LogonPwd = logonPwd;
		}
		//否则都需要进行密码加密
		else{
			MD5keyBean bean = new MD5keyBean();
			md5LogonPwd = bean.getkeyBeanofStr(logonPwd);
		}
		elecUser.setLogonPwd(md5LogonPwd);
	}
	
	public ElecUser findElecUserByID(ElecUser elecUser) {
		elecUser = elecUserDao.findObjectByID(elecUser.getUserID());
		return elecUser;
	}
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void deleteElecUserByID(ElecUser elecUser) {
		elecUserDao.deleteObjectByIDs(elecUser.getUserID());
	}
	public String checkUser(String logonName) {
		String condition = " and o.logonName=?";
		Object[] params = {logonName};
		List<ElecUser> list = elecUserDao.findCollectionByConditionNoPage(condition, params, null);
		String flag = "";
		if(list != null && list.size() > 0){
			flag = "1";
		}else{
			flag = "2";
		}
		return flag;
	}
	/**  
	* @Name: findElecUserByLogonName
	* @Description: 使用登录名作为条件，获取ElecUser对象，作为登录的验证
	* @Author: 刘洋（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2012-09-14 （创建日期）
	* @Parameters: String 登录名
	* @Return: ElecUser：存放用户信息
	*/
	public ElecUser findElecUserByLogonName(String name) {
		String condition = " and o.logonName=?";
		Object[] params = {name};
		ElecUser elecUser = null;
		List<ElecUser> list = elecUserDao.findCollectionByConditionNoPage(condition, params, null);
		if(list!=null&&list.size()>0){
			elecUser = list.get(0);
		}
		return elecUser;
	}
	/**  
	* @Name: findRoleByLogonName
	* @Description: 使用登录名作为条件，获取Role Hash表。
	* @Author: 晋学领（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2012-09-14 （创建日期）
	* @Parameters: String 登录名
	* @Return: Hashtable：存放用户角色信息
	*/
	public Hashtable<String, String> findRoleByLogonName(String name) {
		java.util.Hashtable<String, String> hashtable = null;
		List<Object[]> list = elecUserDao.findRoleByLogonName(name);
		if(list!=null&&list.size()>0){
			hashtable = new java.util.Hashtable<String, String>();
			for(int i = 0; i < list.size(); i++){
				Object[] objects = list.get(i);
				hashtable.put(objects[0].toString(), objects[1].toString());
			}
		}
		return hashtable;
	}
	/**  
	* @Name: findPopedomByLogonName
	* @Description: 使用登录名作为条件，获取popedom权限字符串。
	* @Author: 晋学领（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2012-09-14 （创建日期）
	* @Parameters: String 登录名
	* @Return: String pepedom：存放用户权限信息
	*/
	public String findPopedomByLogonName(String name) {
		List<Object> list = elecUserDao.findPopedomByLogonName(name);
		StringBuffer buffer = null;
		if(list!=null && list.size()>0){
			buffer = new StringBuffer("");
			for(int i=0;i<list.size();i++){
				Object object = list.get(i);
				buffer.append(object.toString());
			}
		}
		if(buffer==null){
			return null;
		}
		return buffer.toString();
	}
	/**构造excel的标题集合*/
	public ArrayList<String> excelFildName() {
		ArrayList<String> fieldName = new ArrayList<String>();
		fieldName.add("登录名");
		fieldName.add("用户姓名");
		fieldName.add("性别");
		fieldName.add("联系电话");
		fieldName.add("是否在职");
		return fieldName;
	}

	public ArrayList<ArrayList<String>> excelFildData(ElecUser elecUser) {
		String userName = elecUser.getUserName();
		/**中文格式转码*/
		//方式一
		/*try {
			userName = new String(userName.getBytes(userName),"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}*/
		//方式二
		try {
			userName = URLDecoder.decode(userName, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		// 组织查询条件
		String condition = "";
		List<String> paramsList = new ArrayList<String>();
		if (StringUtils.isNotBlank(userName)) {
			condition += " and o.userName like ?";
			paramsList.add("%" + userName + "%");
		}
		Object[] params = paramsList.toArray();
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("o.onDutyDate", "asc");
		List<ElecUser> list = elecUserDao.findCollectionByConditionNoPage(condition, params, orderby);
		// 将数据字典项进行转换，才能显示到页面上
		this.userListConverntSystemDDL(list);
		//将list集合转换成导出的格式
		ArrayList<ArrayList<String>> fieldData = new ArrayList<ArrayList<String>>();
		for(int i=0;list!=null && i<list.size();i++){
			ArrayList<String> field = new ArrayList<String>();
			ElecUser user = list.get(i);
			field.add(user.getLogonName());
			field.add(user.getUserName());
			field.add(user.getSexID());
			field.add(user.getContactTel());
			field.add(user.getIsDuty());
			fieldData.add(field);
		}
		return fieldData;
	}
	
	/**导入excel文件中的数据记录*/
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void excelImportData(ArrayList<String[]> arraylist) {
		MD5keyBean bean = new MD5keyBean();
		for(int i=0;arraylist!=null && i<arraylist.size();i++){
			ElecUser elecUser = new ElecUser();
			String[] str = arraylist.get(i);
			elecUser.setLogonName(str[0].toString());
			elecUser.setLogonPwd(bean.getkeyBeanofStr(str[1].toString()));//密码 加密
			elecUser.setUserName(str[2].toString());//如果是真实项目需要进行字典转换
			elecUser.setSexID(str[3].toString());//如果是真实项目需要进行字典转换
			elecUser.setJctID(str[4].toString());//如果是真实项目需要进行字典转换
			elecUser.setAddress(str[5].toString());//如果是真实项目需要进行字典转换
			elecUser.setIsDuty(str[6].toString());//如果是真实项目需要进行字典转换
			
			elecUserDao.save(elecUser);
		}
	}

	public List<Object[]> findCharDataSet() {
		List<Object[]>  list = elecUserDao.findCharDataSet();
		return list;
	}
}
