package cn.itcast.elec.dao;

import java.util.LinkedHashMap;
import java.util.List;

import cn.itcast.elec.domain.ElecUser;

public interface IElecUserDao extends ICommonDao<ElecUser>{
	public static final String SERVICE_NAME = "cn.itcast.elec.dao.impl.ElecUserDaoImpl";

	List<Object[]> findRoleByLogonName(String name);

	List<Object> findPopedomByLogonName(String name);

	List<Object[]> findCharDataSet();

	
}
