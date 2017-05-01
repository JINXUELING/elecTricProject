package cn.itcast.elec.web.form;

import java.io.File;
import java.io.InputStream;

public class ElecProcessDefinition {

	//流程定义的key
	private String key;
	//流程定义的id
	private String id;
	
	//部署的流程定义文档
	private File upload;
	
	//查看流程图的inputStream
	private InputStream inputStream;
	
	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}
	
}
