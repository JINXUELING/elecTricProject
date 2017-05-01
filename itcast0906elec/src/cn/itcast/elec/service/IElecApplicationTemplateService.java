package cn.itcast.elec.service;

import java.io.InputStream;
import java.util.List;

import cn.itcast.elec.domain.ElecApplicationTemplate;

public interface IElecApplicationTemplateService {
	public static final String SERVICE_NAME = "cn.itcast.elec.dao.impl.ElecApplicationTemplateServiceImpl";

	List<ElecApplicationTemplate> findApplicationTemplateList();

	void saveApplicationTemplate(ElecApplicationTemplate elecApplicationTemplate);

	ElecApplicationTemplate findApplicationTemplateByID(
			ElecApplicationTemplate elecApplicationTemplate);

	void deleteApplicationTemplateByID(
			ElecApplicationTemplate elecApplicationTemplate);

	InputStream findInputStreamByPath(ElecApplicationTemplate elecApplicationTemplate);

	void updateApplicationTemplate(
			ElecApplicationTemplate elecApplicationTemplate);
}
