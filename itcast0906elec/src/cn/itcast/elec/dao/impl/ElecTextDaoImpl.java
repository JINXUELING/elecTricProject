package cn.itcast.elec.dao.impl;

import org.springframework.stereotype.Repository;

import cn.itcast.elec.dao.IElecTextDao;
import cn.itcast.elec.domain.ElecText;

/**
 * <bean id="cn.itcast.elec.dao.impl.ElecTextDaoImpl" class="cn.itcast.elec.dao.impl.ElecTextDaoImpl">
   </bean>
 *
 */
@Repository(IElecTextDao.SERVICE_NAME)
public class ElecTextDaoImpl extends CommonDaoImpl<ElecText> implements IElecTextDao {
	
}
