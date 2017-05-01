package cn.itcast.elec.service;

import java.io.InputStream;
import java.util.List;

import org.jbpm.api.ProcessDefinition;

import cn.itcast.elec.domain.ElecApplicationTemplate;
import cn.itcast.elec.web.form.ElecProcessDefinition;

public interface IElecProcessDefinitionService {
	public static final String SERVICE_NAME = "cn.itcast.service.impl.ElecProcessDefinitionServiceImpl";

	List<ProcessDefinition> findPDListByLastVersion();

	void deployeProcessDefinition(ElecProcessDefinition elecProcessDefinition);

	void deleteProcessDefinitionByKey(
			ElecProcessDefinition elecProcessDefinition);

	InputStream findImpageInputStreamByID(
			ElecProcessDefinition elecProcessDefinition);

}
