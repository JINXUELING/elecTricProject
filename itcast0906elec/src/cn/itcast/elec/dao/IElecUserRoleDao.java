package cn.itcast.elec.dao;

import java.util.List;

import cn.itcast.elec.domain.ElecUserRole;

public interface IElecUserRoleDao extends ICommonDao<ElecUserRole> {
	public static final String SERVICE_NAME = "cn.itcast.elec.dao.impl.ElecUserRoleDaoImpl";

	List<Object[]> findUserListByRoleID(String roleid);

}
