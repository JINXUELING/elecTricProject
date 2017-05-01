package cn.itcast.elec.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import javax.annotation.Resource;

import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessDefinitionQuery;
import org.jbpm.api.ProcessEngine;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.elec.domain.ElecApplicationTemplate;
import cn.itcast.elec.service.IElecProcessDefinitionService;
import cn.itcast.elec.web.form.ElecProcessDefinition;


@Service(IElecProcessDefinitionService.SERVICE_NAME)
@Transactional(readOnly=true)
public class ElecProcessDefinitionServiceImpl implements IElecProcessDefinitionService{
	
	@Resource(name="processEngine")
	private ProcessEngine processEngine;//流程引擎和流程定义都是javaBean
	
	/**获取最新流程定义列表*/
	public List<ProcessDefinition> findPDListByLastVersion(){
		List<ProcessDefinition> list= processEngine.getRepositoryService()
									.createProcessDefinitionQuery()
									.orderAsc(ProcessDefinitionQuery.PROPERTY_VERSION)////将最新版本的流程放置到后面
									.list();
		Map<String,ProcessDefinition> map = new HashMap<String,ProcessDefinition>();
		for(ProcessDefinition pd:list){
			//不同版本的key相同，同一个map中进行流程定义存储，所以可以覆盖旧版本的流程定义
			map.put(pd.getKey(), pd);
		}
		List<ProcessDefinition> pdList = new ArrayList<ProcessDefinition>(map.values());
		return pdList;
	}
	/**部署流程定义*/
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void deployeProcessDefinition(
			ElecProcessDefinition elecProcessDefinition){
		try{
			//获取页面传递的流程定义文件，格式为zip格式
			File upload = elecProcessDefinition.getUpload();
			ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(upload));
			//部署流程定义
			processEngine.getRepositoryService()
						.createDeployment()
						.addResourcesFromZipInputStream(zipInputStream)
						.deploy();
			zipInputStream.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	/**按照key删除所有版本的流程定义*/
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void deleteProcessDefinitionByKey(
			ElecProcessDefinition elecProcessDefinition) {
		//获取页面传递的流程定义的key
		String key = elecProcessDefinition.getKey();
		//处理key的乱码问题
		try{
			key = new String(key.getBytes("iso-8859-1"),"UTF-8");
		}catch(UnsupportedEncodingException e){
			e.printStackTrace();
		}
		//使用key获取所有版本的流程定义
		List<ProcessDefinition> list = processEngine.getRepositoryService()
									.createProcessDefinitionQuery()
									.processDefinitionKey(key)
									.list();
		for(ProcessDefinition pd:list){
			String deploymentId = pd.getDeploymentId();
			processEngine.getRepositoryService()
							.deleteDeployment(deploymentId);
		}
	}
	/**按照id获取对应图片信息，并封装到inputStream中*/
	public InputStream findImpageInputStreamByID(
			ElecProcessDefinition elecProcessDefinition){
		//获取页面传递的流程定义id
		String processDefinitionid = elecProcessDefinition.getId();
		try{
			processDefinitionid = new String(processDefinitionid.getBytes("iso-8859-1"),"UTF-8");
		}catch(UnsupportedEncodingException e){
			e.printStackTrace();
		}
		//通过流程引擎的注册服务获取给定流程定义ID所对应的流程定义。
		ProcessDefinition pd = processEngine.getRepositoryService()
					.createProcessDefinitionQuery()
					.processDefinitionId(processDefinitionid)
					.uniqueResult();
		//根据流程定义，获取流程部署Id和图片名称
		String deploymentId = pd.getDeploymentId();
		String resourceName = pd.getImageResourceName();
		InputStream inputStream = processEngine.getRepositoryService()
								.getResourceAsStream(deploymentId, resourceName);
		return inputStream;
	}
}
