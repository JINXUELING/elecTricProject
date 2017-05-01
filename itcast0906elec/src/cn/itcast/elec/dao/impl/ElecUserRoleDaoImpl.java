package cn.itcast.elec.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import cn.itcast.elec.dao.IElecUserRoleDao;
import cn.itcast.elec.domain.ElecUserRole;

@Repository(IElecUserRoleDao.SERVICE_NAME)
public class ElecUserRoleDaoImpl extends CommonDaoImpl<ElecUserRole> implements
		IElecUserRoleDao {

	/**
	 * @Name: findUserListByRoleID
	 * @Description: 使用角色id获取对应的用户集合
	 * @Author: 刘洋（作者）
	 * @Version: V1.00 （版本号）
	 * @Create Date: 2012-09-13 （创建日期）
	 * @Parameters: String roleid：角色id
	 * @Return: List<Object[]> 用户集合
	 */
	public List<Object[]> findUserListByRoleID(final String roleid) {
		final String sql = "SELECT DISTINCT CASE b.roleID WHEN ? THEN '1' ELSE '2' END AS flag,"
				+ " a.userID AS userid,a.logonName AS logonname,a.userName AS username"
				+ " FROM elec_user a"
				+ " LEFT OUTER JOIN elec_user_role b ON a.userID = b.userID"
				+ " AND b.roleID = ?" + " WHERE a.isduty = '1'";

		List<Object[]> list = (List<Object[]>) this.getHibernateTemplate()
				.execute(new HibernateCallback() {

					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query query = session.createSQLQuery(sql).addScalar(
								"flag", Hibernate.STRING).addScalar("userid",
								Hibernate.STRING).addScalar("logonname",
								Hibernate.STRING).addScalar("username",
								Hibernate.STRING);
						query.setParameter(0, roleid);
						query.setParameter(1, roleid);
						return query.list();
					}
				});
		return list;
	}
}
