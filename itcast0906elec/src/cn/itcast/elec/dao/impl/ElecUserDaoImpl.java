package cn.itcast.elec.dao.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.security.auth.callback.Callback;

import org.apache.struts2.views.freemarker.tags.CallbackWriter;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import cn.itcast.elec.dao.IElecUserDao;
import cn.itcast.elec.domain.ElecUser;

@Repository(IElecUserDao.SERVICE_NAME)
public class ElecUserDaoImpl extends CommonDaoImpl<ElecUser>implements IElecUserDao {
	/**  
	* @Name: findRoleByLogonName
	* @Description: 使用登录名作为条件，获取Role Hash表。
	* @Author: 晋学领（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2012-09-14 （创建日期）
	* @Parameters: String 登录名
	* @Return: List<Object[]> ：存放用户角色信息
	*/
	public List<Object[]> findRoleByLogonName(final String name) {
		final String sql = " select b.ddlCode,b.ddlName from elec_user_role a" +
				" left outer join elec_systemddl b on a.roleID = b.ddlCode and b.keyword='角色类型'"+
				" join elec_user c on a.userID = c.userID"+
				" and c.logonName=?"+
				" where c.isDuty = '1'";
		List<Object[]> list = this.getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createSQLQuery(sql);
				query.setParameter(0, name);
				return query.list();
			}
		});
		return list;
	}
	/**  
	* @Name: findPopedomByLogonName
	* @Description: 使用登录名作为条件，该用户名所对应的权限集合。
	* @Author: 晋学领（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2012-09-14 （创建日期）
	* @Parameters: String 登录名
	* @Return: String  ：存放用户权限信息
	*/
	public List<Object> findPopedomByLogonName(final String name) {
		final String sql = "SELECT a.popedomcode FROM elec_role_popedom a " +
				 " LEFT OUTER JOIN elec_user_role b ON a.roleID = b.roleID " +
				 " JOIN elec_user c ON b.userID = c.userID " +
				 " AND c.logonName = ?" +
				 " WHERE c.isDuty = '1'";
		
		List<Object> list = this.getHibernateTemplate().execute(new HibernateCallback(){

			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createSQLQuery(sql);
				query.setParameter(0, name);
				return query.list();
			}});
		
		return list;
	}
	/**  
	* @Name: findCharDataSet
	* @Description: 获取人员统计的信息。
	* @Author: 晋学领（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2012-09-14 （创建日期）
	* @Parameters: 无
	* @Return: List<Object>  ：存放用户信息
	*/
	public List<Object[]> findCharDataSet() {
		final String sql = " select b.keyword,b.ddlName,count(a.jctID) from elec_user a " +
				" left outer join elec_systemddl b on a.jctID = b.ddlCode " +
				" and b.keyword = '所属单位' " +
				" where a.isduty = '1' " +
				" group by a.jctID";
		List<Object[]> List = this.getHibernateTemplate().executeFind(new HibernateCallback<List<Object[]>>() {

			public List<Object[]> doInHibernate(Session session) throws HibernateException,
					SQLException {
				Query query = session.createSQLQuery(sql);
				return query.list();
			}
		});
		return List;
	}
}
