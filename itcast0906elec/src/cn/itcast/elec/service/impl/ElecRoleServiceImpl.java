package cn.itcast.elec.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.elec.dao.IElecRolePopedomDao;
import cn.itcast.elec.dao.IElecUserRoleDao;
import cn.itcast.elec.domain.ElecRolePopedom;
import cn.itcast.elec.domain.ElecUser;
import cn.itcast.elec.domain.ElecUserRole;
import cn.itcast.elec.service.IElecRoleService;
import cn.itcast.elec.web.form.XmlObjectBean;

@Service(IElecRoleService.SERVICE_NAME)
@Transactional(readOnly=true)
public class ElecRoleServiceImpl implements IElecRoleService {
	@Resource(name=IElecUserRoleDao.SERVICE_NAME)
	private IElecUserRoleDao elecUserRoleDao;
	
	@Resource(name=IElecRolePopedomDao.SERVICE_NAME)
	private IElecRolePopedomDao elecRolePopedomDao;
	/**  
	* @Name: readFunctionXml
	* @Description: 读取Function.xml的所有的功能权限
	* @Author: 晋学领（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2012-09-13 （创建日期）
	* @Parameters: 无
	* @Return: List<XmlObjectBean> 权限集合
	*/
	public List<XmlObjectBean> readFunctionXml() {
		//找到类路径下Function文件
		ServletContext servletcontext = ServletActionContext.getServletContext();
		String path = servletcontext.getRealPath("\\WEB-INF\\classes\\Function.xml");
		List<XmlObjectBean> xmlList = new ArrayList<XmlObjectBean>();
		try{
			//使用dom4j解析xml文件
			SAXReader reader = new SAXReader();
			Document document = reader.read(new File(path));
			//得到根节点
			Element element = document.getRootElement();
			//按照XML文件的Function进行循环，获取每条数据
			for(Iterator<Element> ite=element.elementIterator("Function");ite.hasNext();){
				Element elementObject = ite.next();
				XmlObjectBean objectBean = new XmlObjectBean();
				//获取对应的4个节点并设置到实体bean中（code，name，parentcode，parentname）
				objectBean.setCode(elementObject.elementText("FunctionCode"));
				objectBean.setName(elementObject.elementText("FunctionName"));
				objectBean.setParentCode(elementObject.elementText("ParentCode"));
				objectBean.setParentName(elementObject.elementText("ParentName"));
				xmlList.add(objectBean);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return xmlList;
	}
	public List<XmlObjectBean> editFunctionXml(ElecUserRole elecUserRole) {
		//从Function.xml获取系统中所有的权限
		List<XmlObjectBean> xmlList = this.readFunctionXml();
		//使用传递的角色id，查询角色和权限关联表，获取该角色下对应的权限集合
		String roleid = elecUserRole.getRoleid();
		ElecRolePopedom elecRolePopedom = elecRolePopedomDao.findObjectByID(roleid);
		//获取权限集合（存储形式：abcdefghij）
		String popedomcode = "";
		if(elecRolePopedom!=null){
			popedomcode = elecRolePopedom.getPopedomcode();
		}
		for(int i=0;xmlList!=null && i<xmlList.size();i++){
			XmlObjectBean objectBean = xmlList.get(i);
			if(popedomcode.contains(objectBean.getCode())){
				objectBean.setFlag("1");
			}
			else{
				objectBean.setFlag("2");
			}
		}
		return xmlList;
		
	}
	public List<ElecUser> findUserList(ElecUserRole elecUserRole) {
		String roleid = elecUserRole.getRoleid();
		List<Object[]> list = elecUserRoleDao.findUserListByRoleID(roleid);
		List<ElecUser> userList = this.objectListToUserList(list);
		return userList;
	}

	/**值转换*/
	private List<ElecUser> objectListToUserList(List<Object[]> list) {
		List<ElecUser> userList = new ArrayList<ElecUser>();
		for(int i=0;list!=null && i<list.size();i++){
			Object [] objects = list.get(i);
			ElecUser elecUser = new ElecUser();
			elecUser.setFlag(objects[0].toString());
			elecUser.setUserID(objects[1].toString());
			elecUser.setLogonName(objects[2].toString());
			elecUser.setUserName(objects[3].toString());
			userList.add(elecUser);
		}
		return userList;
	}
	/**  
	* @Name: saveRole
	* @Description: 保存用户与角色关联关系，保存角色与权限关联关系
	* @Author: 刘洋（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2012-09-13 （创建日期）
	* @Parameters: ElecUserRole elecUserRole：角色id，权限code集合，用户ID集合
	* @Return: 无
	*/
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void saveRole(ElecUserRole elecUserRole) {
		//角色ID
		String roleid = elecUserRole.getRoleid();
		//权限code集合
		String [] popedoms = elecUserRole.getSelectoper();
		//用户ID集合
		String [] userIDs = elecUserRole.getSelectuser();
		//保存角色与权限关联关系
		this.saveRolePopedom(roleid,popedoms);
		//保存用户与角色关联关系
		this.saveUserRole(roleid,userIDs);
	}

	/**
	 * 保存角色与权限的关联表
	 * @param roleid  角色ID
	 * @param popedoms 权限code集合
	 */
	private void saveRolePopedom(String roleid, String[] popedoms) {
		//code的集合拼装成String的字符串
		String popedom = "";
		if(popedoms!=null){
			for(int i=0;i<popedoms.length;i++){
				popedom += popedoms[i];
			}
		}
		//使用roleid作为条件查询角色权限关联表，获取到ElecRolePopedom对象
		ElecRolePopedom elecRolePopedom = elecRolePopedomDao.findObjectByID(roleid);
		//说明在数据库中存在当前roid的记录，此时执行update操作
		if(elecRolePopedom!=null){
			elecRolePopedom.setPopedomcode(popedom);//更新权限的code
			//elecRolePopedomDao.update(elecRolePopedom);
		}
		//说明在数据库中不存在当前roid的记录，此时执行save操作
		else{
			elecRolePopedom = new ElecRolePopedom();
			elecRolePopedom.setRoleID(roleid);//保存角色ID
			elecRolePopedom.setPopedomcode(popedom);//更新权限的code
			//elecRolePopedomDao.save(elecRolePopedom);
		}
		elecRolePopedomDao.saveOrUpdate(elecRolePopedom);
	}
	
	/**
	 * 保存用户与角色的关联表
	 * @param roleid  角色ID
	 * @param userIDs 用户ID集合
	 */
	private void saveUserRole(String roleid, String[] userIDs) {
		//使用roleid作为条件查询用户角色关联表，获取到对应List集合
		String condition = " and o.roleID = ?";
		Object [] params = {roleid};
		List<ElecUserRole> list = elecUserRoleDao.findCollectionByConditionNoPage(condition, params, null);
		//使用返回的List集合执行delete语句
		elecUserRoleDao.deleteObjectByConllection(list);
		//执行保存
		if(userIDs!=null){
			for(int i=0;i<userIDs.length;i++){
				String userID = userIDs[i];
				//组织ElecUserRole对象
				ElecUserRole elecUserRole = new ElecUserRole();
				elecUserRole.setRoleID(roleid);
				elecUserRole.setUserID(userID);
				elecUserRoleDao.save(elecUserRole);
			}
		}
	}
}
