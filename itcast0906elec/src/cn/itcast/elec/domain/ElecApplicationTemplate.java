package cn.itcast.elec.domain;

import java.io.File;
import java.io.InputStream;


@SuppressWarnings("serial")
public class ElecApplicationTemplate implements java.io.Serializable {
	private Long id;                     //主键ID
	private String name;                 //名称
	private String processDefinitionKey; //流程定义的key
	private String path;                 //上传的模板文件的存储位置
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProcessDefinitionKey() {
		return processDefinitionKey;
	}
	public void setProcessDefinitionKey(String processDefinitionKey) {
		this.processDefinitionKey = processDefinitionKey;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	
	
	/************************非持久化对象属性********************************************/
	//用于上传附件的File
	private File upload;
	
	//用于文件下载InputStream
	private InputStream inputStream;

	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	public File getUpload() {
		return upload;
	}
	public void setUpload(File upload) {
		this.upload = upload;
	}
	
	
	/************************非持久化对象属性********************************************/
	
}
