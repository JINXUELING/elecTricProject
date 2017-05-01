package cn.itcast.elec.dao;

import cn.itcast.elec.domain.ElecCommonMsg;

public interface IElecCommonMsgDao extends ICommonDao<ElecCommonMsg> {
	public static final String SERVICE_NAME = "cn.itcast.elec.dao.impl.ElecCommonMsgDaoImpl";
}
