package cn.itcast.elec.dao;

import cn.itcast.elec.domain.ElecApplication;

public interface IElecApplicationDao extends ICommonDao<ElecApplication> {
	public static final String SERVICE_NAME = "cn.itcast.elec.dao.impl.ElecApplicationDaoImpl";

}
