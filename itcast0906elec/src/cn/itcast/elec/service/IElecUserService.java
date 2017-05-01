package cn.itcast.elec.service;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import cn.itcast.elec.domain.ElecUser;

public interface IElecUserService {
	public static final String SERVICE_NAME = "cn.itcast.elec.service.impl.ElecUserServiceImpl";

	List<ElecUser> findElecUserList(ElecUser elecUser);

	void saveElecUser(ElecUser elecUser);

	ElecUser findElecUserByID(ElecUser elecUser);

	void deleteElecUserByID(ElecUser elecUser);

	String checkUser(String logonName);
	
	ElecUser findElecUserByLogonName(String name);

	Hashtable<String, String> findRoleByLogonName(String name);

	String findPopedomByLogonName(String name);

	ArrayList<String> excelFildName();

	ArrayList<ArrayList<String>> excelFildData(ElecUser elecUser);

	void excelImportData(ArrayList<String[]> arraylist);

	List<Object[]> findCharDataSet();
}
