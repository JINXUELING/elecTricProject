package cn.itcast.elec.dao.impl;


import org.springframework.stereotype.Repository;

import cn.itcast.elec.dao.IElecApplicationTemplateDao;
import cn.itcast.elec.dao.IElecCommonMsgDao;
import cn.itcast.elec.domain.ElecApplicationTemplate;

@Repository(IElecApplicationTemplateDao.SERVICE_NAME)
public class ElecApplicationTemplateDaoImpl extends CommonDaoImpl<ElecApplicationTemplate> implements
		IElecApplicationTemplateDao {
}
