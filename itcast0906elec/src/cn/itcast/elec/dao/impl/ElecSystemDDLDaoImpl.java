package cn.itcast.elec.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.itcast.elec.dao.IElecSystemDDLDao;
import cn.itcast.elec.domain.ElecSystemDDL;


@Repository(IElecSystemDDLDao.SERVICE_NAME)
public class ElecSystemDDLDaoImpl extends CommonDaoImpl<ElecSystemDDL> implements IElecSystemDDLDao {

	/**  
	* @Name: findKeywordList
	* @Description: 获取去掉重复值的数据类型列表(distinct)
	* @Author: 刘洋（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2012-09-10 （创建日期）
	* @Parameters: 无
	* @Return: List<Object[]>：封装数据类型列表
	*/
	public List<Object[]> findKeywordList() {
		String hql = "select distinct o.keyword from ElecSystemDDL o";
		List<Object[]> list = this.getHibernateTemplate().find(hql);
		return list;
	}
	/**  
	* @Name: findDdlNameByCondition
	* @Description: 使用数据项编号和数据类型获取数据项的值（唯一的值）
	* @Author: 晋学领（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2017-4-19（创建日期）
	* @Parameters: String keyword：数据类型
	*              String ddlCode：数据项编号
	* @Return: String：数据项的值
	*/
	public String findDdlNameByCondition(String keyword, String ddlCode) {
		String hql = "select o.ddlName from ElecSystemDDL o where o.ddlCode="+ddlCode+"and o.keyword = '"+keyword+"'";
		List<Object>list = this.getHibernateTemplate().find(hql);
		String ddlName = "";
		if(list!=null && list.size()>0){
			Object o = list.get(0);
			ddlName = o.toString();
		}
		return ddlName;
	}
	
}
