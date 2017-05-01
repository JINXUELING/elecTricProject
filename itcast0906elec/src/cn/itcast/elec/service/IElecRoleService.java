package cn.itcast.elec.service;

import java.util.List;

import cn.itcast.elec.domain.ElecUser;
import cn.itcast.elec.domain.ElecUserRole;
import cn.itcast.elec.web.form.XmlObjectBean;

public interface IElecRoleService {
	public static final String SERVICE_NAME = "cn.itcast.elec.service.impl.ElecRoleServiceImpl";

	List<XmlObjectBean> readFunctionXml();

	List<XmlObjectBean> editFunctionXml(ElecUserRole elecUserRole);

	List<ElecUser> findUserList(ElecUserRole elecUserRole);

	void saveRole(ElecUserRole elecUserRole);
	
}
