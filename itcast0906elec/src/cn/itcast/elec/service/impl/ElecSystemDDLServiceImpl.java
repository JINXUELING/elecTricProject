package cn.itcast.elec.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.xwork.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.elec.dao.IElecSystemDDLDao;
import cn.itcast.elec.domain.ElecSystemDDL;
import cn.itcast.elec.service.IElecSystemDDLService;


@Service(IElecSystemDDLService.SERVICE_NAME)
@Transactional(readOnly=true)
public class ElecSystemDDLServiceImpl implements IElecSystemDDLService {

	@Resource(name=IElecSystemDDLDao.SERVICE_NAME)
	private IElecSystemDDLDao elecSystemDDLDao;

	/**  
	* @Name: findKeywordList
	* @Description: 获取去掉重复值的数据类型列表
	* @Author: 刘洋（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2012-09-10 （创建日期）
	* @Parameters: 无
	* @Return: List<ElecSystemDDL>：封装数据类型列表
	*/
	public List<ElecSystemDDL> findKeywordList() {
		List<Object[]> list = elecSystemDDLDao.findKeywordList();
		//将返回的集合封装成ElecSystemDDL
		List<ElecSystemDDL> systemList = this.elecSystemDDLObjectListToPOList(list);
		return systemList;
	}

	/**值转换*/
	private List<ElecSystemDDL> elecSystemDDLObjectListToPOList(
			List<Object[]> list) {
		List<ElecSystemDDL> systemList = new ArrayList<ElecSystemDDL>();
		for(int i=0;list!=null && i<list.size();i++){
			Object object = list.get(i);
			ElecSystemDDL elecSystemDDL = new ElecSystemDDL();
			elecSystemDDL.setKeyword(object.toString());
			systemList.add(elecSystemDDL);
		}
		return systemList;
	}
	/**  
	* @Name: findSystemDDLList
	* @Description: 使用数据类型作为条件，查询数据字段获取结果集信息
	* @Author: 晋学领（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2012-09-10 （创建日期）
	* @Parameters: ElecSystemDDL：存放数据类型
	* @Return: List<ElecSystemDDL>：封装返回结果集
	*/
	public   List<ElecSystemDDL> findSystemDDLList(
			ElecSystemDDL elecSystemDDL) {
		//数据类型
		String keyword = elecSystemDDL.getKeyword();
		//按照数据类型作为条件，获取对应的数据结果。
		List<ElecSystemDDL> list = this.systemDDLListByKeyword(keyword);
		return list;
	}
	/**按照数据类型查找对应的数据结果。
	 * 
	 * @param keyword
	 * @return list
	 */
	private List<ElecSystemDDL> systemDDLListByKeyword(String keyword) {
		String condition = "";
		List<String> paramsList = new ArrayList<String>();
		if(StringUtils.isNotBlank(keyword)){
			condition += " and o.keyword = ?";
			paramsList.add(keyword);
		}
		Object [] params = paramsList.toArray();
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("o.ddlCode", "asc");//按照数据项的编号升序排列
//		List<ElecSystemDDL> list = elecSystemDDLDao.findCollectionByConditionNoPage(condition, params, orderby);
		List<ElecSystemDDL> list = elecSystemDDLDao.findCollectionByConditionNoPageWithCache(condition, params, orderby);
		return list;
	}
	/**  
	* @Name: findSystemDDLList
	* @Description: 保存数据字段
	* @Author: 晋学领（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2012-09-10 （创建日期）
	* @Parameters: ElecSystemDDL：存放保存的值
	* @Return: List<ElecSystemDDL>：无
	*/
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void saveSystemDDL(ElecSystemDDL elecSystemDDL) {
		String keyword = elecSystemDDL.getKeywordname();
		String typeflag = elecSystemDDL.getTypeflag();
		String [] ddlNames = elecSystemDDL.getItemname();
		if(typeflag!=null && typeflag.equals("new")){
			//保存
			this.systemDDLToPoAndSave(keyword,ddlNames);
		}
		//表示在原有的数据类型中进行修改和编辑
		else{
			//按照数据类型组织条件，获取对应的数据库中的值，然后先删除之前数据库中的值，在插入新的值。
			List<ElecSystemDDL> list = this.systemDDLListByKeyword(keyword);
			elecSystemDDLDao.deleteObjectByConllection(list);
			//保存
			this.systemDDLToPoAndSave(keyword, ddlNames);
		}
	}
	/**值替换，并执行保存的操作*/
	private void systemDDLToPoAndSave(String keyword, String[] ddlNames) {
		if(ddlNames!=null){
			for(int i=0;i<ddlNames.length;i++){
				String ddlName = ddlNames[i];
				ElecSystemDDL elecSystemDDL = new ElecSystemDDL();
				elecSystemDDL.setKeyword(keyword);
				elecSystemDDL.setDdlName(ddlName);
				elecSystemDDL.setDdlCode(i+1);
				elecSystemDDLDao.save(elecSystemDDL);
			}
		}
	}
}
