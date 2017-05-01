package cn.itcast.elec.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.elec.dao.IElecTextDao;
import cn.itcast.elec.domain.ElecText;
import cn.itcast.elec.service.IElecTextService;

/**
*
* <bean id="cn.itcast.elec.service.impl.ElecTextServiceImpl" class="cn.itcast.elec.service.impl.ElecTextServiceImpl">
* 		<property name="elecTextDao" ref="cn.itcast.elec.dao.impl.ElecTextDaoImpl"></property>
  </bean>
*
*/
@Service(IElecTextService.SERVICE_NAME)
@Transactional(readOnly=true)
public class ElecTextServiceImpl implements IElecTextService {

	@Resource(name=IElecTextDao.SERVICE_NAME)
	private IElecTextDao elecTextDao; 
	
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void saveElecText(ElecText elecText) {
		elecTextDao.save(elecText);
	}

	/**Service层，对底层查询列表方法进行封装*/
	public List<ElecText> findCollectionByConditionNoPage(ElecText elecText) {
		/**
		 *  SELECT * FROM elec_text o WHERE 1=1   #DAO层封装
			AND o.textName LIKE '%李%' #Service层封装
			AND o.textRemark LIKE '%李%' #Service层封装
			ORDER BY o.textDate ASC,o.textName DESC #Service层封装
		 */
		//组织查询条件
		String condition = "";
		List<String> paramsList = new ArrayList<String>();
		if(StringUtils.isNotBlank(elecText.getTextName())){
			condition += " and o.textName like ?";
			paramsList.add("%"+elecText.getTextName()+"%");
		}
		if(StringUtils.isNotBlank(elecText.getTextRemark())){
			condition += " and o.textRemark like ?";
			paramsList.add("%"+elecText.getTextRemark()+"%");
		}
		//以数组作为查询条件语句中的参数
		Object [] params = paramsList.toArray();
		//组织排序语句
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("o.textDate", "asc");
		orderby.put("o.textName", "desc");
		List<ElecText> list = elecTextDao.findCollectionByConditionNoPage(condition,params,orderby);
		return list;
	}

}
