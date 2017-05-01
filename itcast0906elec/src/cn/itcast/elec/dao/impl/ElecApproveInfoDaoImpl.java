package cn.itcast.elec.dao.impl;

import org.springframework.stereotype.Repository;

import cn.itcast.elec.dao.IElecApproveInfoDao;
import cn.itcast.elec.domain.ElecApproveInfo;


@Repository(IElecApproveInfoDao.SERVICE_NAME)
public class ElecApproveInfoDaoImpl extends CommonDaoImpl<ElecApproveInfo> implements IElecApproveInfoDao {

}
