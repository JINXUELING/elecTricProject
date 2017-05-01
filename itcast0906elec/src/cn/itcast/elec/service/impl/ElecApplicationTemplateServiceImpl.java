package cn.itcast.elec.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.elec.dao.IElecApplicationTemplateDao;
import cn.itcast.elec.domain.ElecApplicationTemplate;
import cn.itcast.elec.service.IElecApplicationTemplateService;
import cn.itcast.elec.util.UploadUtils;

@Service(IElecApplicationTemplateService.SERVICE_NAME)
@Transactional(readOnly=true)

public class ElecApplicationTemplateServiceImpl implements IElecApplicationTemplateService {
	
	@Resource(name=IElecApplicationTemplateDao.SERVICE_NAME)
	private IElecApplicationTemplateDao elecApplicationTemplateDao;
	
	/**获取申请模板列表*/
	public List<ElecApplicationTemplate> findApplicationTemplateList() {
		List<ElecApplicationTemplate> list = elecApplicationTemplateDao.findCollectionByConditionNoPage("", null, null);
		return list;
	}
	/**保存申请模板列表*/
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void saveApplicationTemplate(
			ElecApplicationTemplate elecApplicationTemplate) {
		//在UploadUtils类中完成文件上传，并返回对应的path
		File upload = elecApplicationTemplate.getUpload();
		String path = UploadUtils.uploadReturnPath(upload);
		//保存数据库
		elecApplicationTemplate.setPath(path);
		//执行保存
		elecApplicationTemplateDao.save(elecApplicationTemplate);
	}
	/**修改申请模板列表*/
	public ElecApplicationTemplate findApplicationTemplateByID(
			ElecApplicationTemplate elecApplicationTemplate) {
		ElecApplicationTemplate elecApplicationTemplateNew = elecApplicationTemplateDao.findObjectByID(elecApplicationTemplate.getId());
		
		return elecApplicationTemplateNew;
	}
	/**删除申请模板*/
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void deleteApplicationTemplateByID(
			ElecApplicationTemplate elecApplicationTemplate) {
		long ID = elecApplicationTemplate.getId();
		ElecApplicationTemplate elecApplicationTemplateNew = elecApplicationTemplateDao.findObjectByID(ID);
		String path = elecApplicationTemplateNew.getPath();
		File file = new File(path);
		if(file.exists()){
			file.delete();
		}
		elecApplicationTemplateDao.deleteObjectByIDs(ID);
	}
	/**下载申请模板*/
	public InputStream findInputStreamByPath(
			ElecApplicationTemplate elecApplicationTemplate) {
		
		long ID = elecApplicationTemplate.getId();
		ElecApplicationTemplate elecApplicationTemplateNew = elecApplicationTemplateDao.findObjectByID(ID);
		InputStream inputStream = null;
		//根据ID找到对应的申请模板对象，获取模板对象的文件路径和文件名，返回文件的输入流。
		String path = elecApplicationTemplateNew.getPath();
		String filename = elecApplicationTemplateNew.getName();
		try {
			filename = new String(filename.getBytes("GBK"),"iso8859-1");
			ServletActionContext.getRequest().setAttribute("fileName", filename);
			File file = new File(path);
			inputStream = new FileInputStream(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return inputStream;
	}
	/**更新申请模板*/
	public void updateApplicationTemplate(
			ElecApplicationTemplate elecApplicationTemplate) {
		//已上传文件的路径
		String oldPath = elecApplicationTemplate.getPath();
		//使用upload判断是否上传了新的模板文件
		File upload = elecApplicationTemplate.getUpload();
		if(upload!=null){
			//删除旧的的文件
			File oldFile = new File(oldPath);
			if(oldFile.exists())
			oldFile.delete();
			//上传新的文件
			String path = UploadUtils.uploadReturnPath(upload);
			//在保存的对象中添加新的路径path
			elecApplicationTemplate.setPath(path);
		}
		else{
			
		}
		elecApplicationTemplateDao.update(elecApplicationTemplate);
	}
}
