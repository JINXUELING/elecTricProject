package cn.itcast.elec.dao;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

import cn.itcast.elec.domain.ElecSystemDDL;
import cn.itcast.elec.domain.ElecUser;
import cn.itcast.elec.util.PageInfo;


public interface ICommonDao<T> {
	void save(T entity);
	void update(T entity);
	void saveOrUpdate(T entity);
	T findObjectByID(Serializable id);
	void deleteObjectByIDs(Serializable... ids);
	void deleteObjectByConllection(List<T> list);
	List<T> findCollectionByConditionNoPage(String condition,
			Object[] params, LinkedHashMap<String, String> orderby);
	List<T> findCollectionByConditionNoPageWithCache(
			String condition, Object[] params,
			LinkedHashMap<String, String> orderby);
	List<T> findCollectionByConditionWithPage(String condition,
			Object[] params, LinkedHashMap<String, String> orderby,PageInfo pageInfo);
}
