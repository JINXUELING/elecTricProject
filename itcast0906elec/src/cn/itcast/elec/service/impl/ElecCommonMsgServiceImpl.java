package cn.itcast.elec.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.elec.dao.IElecCommonMsgDao;
import cn.itcast.elec.domain.ElecCommonMsg;
import cn.itcast.elec.service.IElecCommonMsgService;


@Service(IElecCommonMsgService.SERVICE_NAME)
@Transactional(readOnly=true)
public class ElecCommonMsgServiceImpl implements IElecCommonMsgService {

	@Resource(name=IElecCommonMsgDao.SERVICE_NAME)
	private IElecCommonMsgDao elecCommonMsgDao;

	/**  
	* @Name: findCommonMsg
	* @Description: 获取对应代办事宜结果集信息
	* @Author: 刘洋（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2012-09-07 （创建日期）
	* @Parameters: 无
	* @Return: ElecCommonMsg，查询结果
	*/
	public ElecCommonMsg findCommonMsg() {
		List<ElecCommonMsg> list = elecCommonMsgDao.findCollectionByConditionNoPage("", null, null);
		ElecCommonMsg elecCommonMsg = new ElecCommonMsg();
		if(list!=null && list.size()>0){
			elecCommonMsg = list.get(0);
		}
		return elecCommonMsg;
	}

	/**  
	* @Name: saveCommonMsg
	* @Description: 保存代办事宜
	* @Author: 刘洋（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2012-09-07 （创建日期）
	* @Parameters: ElecCommonMsg：存放页面保存的值
	* @Return:无
	*/
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void saveCommonMsg(ElecCommonMsg elecCommonMsg) {
		List<ElecCommonMsg> list = elecCommonMsgDao.findCollectionByConditionNoPage("", null, null);
		//判断list是否为空,
		//如果不为null，说明数据库中存在该记录，执行update操作
		if(list!=null && list.size()>0){
			ElecCommonMsg msg = list.get(0);
			msg.setDevRun(elecCommonMsg.getDevRun());
			msg.setStationRun(elecCommonMsg.getStationRun());
			msg.setCreateDate(new Date());
			elecCommonMsgDao.update(msg);
		}
		//如果为null，说明数据库中不存在该记录，执行save操作
		else{
			elecCommonMsg.setCreateDate(new Date());
			elecCommonMsgDao.save(elecCommonMsg);
		}
	} 
}
