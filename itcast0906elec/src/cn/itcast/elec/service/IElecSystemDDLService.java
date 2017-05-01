package cn.itcast.elec.service;

import java.util.List;

import cn.itcast.elec.domain.ElecSystemDDL;



public interface IElecSystemDDLService {
	public static final String SERVICE_NAME = "cn.itcast.elec.service.impl.ElecSystemDDLServiceImpl";

	List<ElecSystemDDL> findKeywordList();

	List<ElecSystemDDL> findSystemDDLList(ElecSystemDDL elecSystemDDL);

	void saveSystemDDL(ElecSystemDDL elecSystemDDL);
}
