package cn.itcast.elec.dao.impl;

import org.springframework.stereotype.Repository;

import cn.itcast.elec.dao.IElecApplicationDao;
import cn.itcast.elec.domain.ElecApplication;


@Repository(IElecApplicationDao.SERVICE_NAME)
public class ElecApplicationDaoImpl extends CommonDaoImpl<ElecApplication> implements IElecApplicationDao {

	
}
