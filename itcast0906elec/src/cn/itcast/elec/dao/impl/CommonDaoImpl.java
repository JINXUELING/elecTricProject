package cn.itcast.elec.dao.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.itcast.elec.dao.ICommonDao;
import cn.itcast.elec.domain.ElecSystemDDL;
import cn.itcast.elec.util.GenericSuperclass;
import cn.itcast.elec.util.PageInfo;

public class CommonDaoImpl<T> extends HibernateDaoSupport implements ICommonDao<T> {
	
	private Class entityClass = GenericSuperclass.getActualTypeClass(this.getClass());
	/**
	 *  <bean id="hibernateTemplate" class="org.springframework.orm.hibernate3.HibernateTemplate">
			<property name="sessionFactory" ref="sessionFactory"></property>
		</bean>
	 */
	@Resource(name="sessionFactory")
	public final void setSessionFactoryDi(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	
	/**保存*/
	public void save(T entity) {
		this.getHibernateTemplate().save(entity);
	}

	/**更新*/
	public void update(T entity) {
		this.getHibernateTemplate().update(entity);
	}

	/**按照主键ID获取对象*/
	public T findObjectByID(Serializable id) {
		return (T)this.getHibernateTemplate().get(entityClass, id);
	}
	
	/**通过ID传递数组的形式删除对象*/
	public void deleteObjectByIDs(Serializable... ids) {
		for(int i=0;ids!=null && i<ids.length;i++){
			Object entity = this.getHibernateTemplate().get(entityClass, ids[i]);
			this.getHibernateTemplate().delete(entity);
		}
	}
	
	/**使用集合的形式删除*/
	public void deleteObjectByConllection(List<T> list) {
		this.getHibernateTemplate().deleteAll(list);
	}

	/**按照查询条件获取对象集合列表数据，（不分页）*/
	public List<T> findCollectionByConditionNoPage(String condition,
			final Object[] params, LinkedHashMap<String, String> orderby) {
		/**
		 *  SELECT * FROM elec_text o WHERE 1=1   #DAO层封装
			AND o.textName LIKE ? #Service层封装
			AND o.textRemark LIKE ? #Service层封装
			ORDER BY o.textDate ASC,o.textName DESC #Service层封装
		 */
		//定义Hql语句
		String hql = "from " + entityClass.getSimpleName() + " o where 1=1";
		String orderHql = this.orderByHql(orderby);
		final String finalHql = hql + condition + orderHql;
		//执行hql语句
		//方法一：
		//List<T> list = this.getHibernateTemplate().find(hql,params);
		//方法二：
		List<T> list = (List<T>) this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(finalHql);
				for(int i=0;params!=null && i<params.length;i++){
					query.setParameter(i, params[i]);
				}
				return query.list();
			}
		});
		return list;
	}

	/**通过传递的排序集合语句（Map），获取对应的排序条件（String）*/
	/**ORDER BY o.textDate ASC,o.textName DESC*/
	private String orderByHql(LinkedHashMap<String, String> orderby) {
		StringBuffer buffer = new StringBuffer("");
		if(orderby != null){
			buffer.append(" order by ");
			for(Map.Entry<String, String> map:orderby.entrySet()){
				buffer.append(map.getKey()+" "+map.getValue()+",");
			}
			buffer.deleteCharAt(buffer.length()-1);//删除最后一个逗号
		}
		return buffer.toString();
	}
	/**保存或者更新权限集合*/
	public void saveOrUpdate(T entity) {
		this.getHibernateTemplate().saveOrUpdate(entity);
	}
	/**使用二级缓存提高系统的检索性能*/
	public List<T> findCollectionByConditionNoPageWithCache(
			String condition, final Object[] params,
			LinkedHashMap<String, String> orderby) {
		//定义Hql语句
				String hql = "from " + entityClass.getSimpleName() + " o where 1=1";
				String orderHql = this.orderByHql(orderby);
				final String finalHql = hql + condition + orderHql;
				//执行hql语句
				//方法一：
				//List<T> list = this.getHibernateTemplate().find(hql,params);
				//方法二：
				List<T> list = (List<T>) this.getHibernateTemplate().execute(new HibernateCallback(){
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query query = session.createQuery(finalHql);
						for(int i=0;params!=null && i<params.length;i++){
							query.setParameter(i, params[i]);
						}
						//启用二级缓存存储数据
						query.setCacheable(true);
						return query.list();
					}
				});
				return list;
	}
	/**处理分页*/
	public List<T> findCollectionByConditionWithPage(String condition,
			final Object[] params, LinkedHashMap<String, String> orderby,final PageInfo pageInfo) {
		/**
		 *  SELECT * FROM elec_text o WHERE 1=1   #DAO层封装
			AND o.textName LIKE ? #Service层封装
			AND o.textRemark LIKE ? #Service层封装
			ORDER BY o.textDate ASC,o.textName DESC #Service层封装
		 */
		//定义Hql语句
		String hql = "from " + entityClass.getSimpleName() + " o where 1=1";
		String orderHql = this.orderByHql(orderby);
		final String finalHql = hql + condition + orderHql;
		List<T> list = (List<T>) this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(finalHql);
				for(int i=0;params!=null && i<params.length;i++){
					query.setParameter(i, params[i]);
				}
				/**添加分页：2012-9-21 begin*/
				pageInfo.setTotalResult(query.list().size());//设置总的记录数
				query.setFirstResult(pageInfo.getBeginResult());//当前记录从第几条开始检索
				query.setMaxResults(pageInfo.getPageSize());//每页显示几条记录
				/**end*/
				return query.list();
			}
		});
		return list;
	}

	
}
