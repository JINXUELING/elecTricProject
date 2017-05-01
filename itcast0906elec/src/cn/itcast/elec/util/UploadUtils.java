package cn.itcast.elec.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.apache.struts2.ServletActionContext;

public class UploadUtils {

	/**
	 * 在UploadUtils类中完成文件上传，并返回对应的path
	 * @param upload，需要上传的文件
	 * @return
	 */
	public static String uploadReturnPath(File upload) {
		//查找文件上传的路径upload
		String basePath = ServletActionContext.getServletContext().getRealPath("/upload");
		//在upload文件中添加最新日期
		String datePath = new SimpleDateFormat("/yyyy/MM/dd/").format(new Date());
		//文件的命名
		String filename = UUID.randomUUID().toString();
		String path = basePath + datePath + filename;
		//创建文件夹
		File file = new File(basePath+datePath);
		if(!file.exists()){
			file.mkdirs();
		}
		//文件上传
		File upladFile = new File(path);
		upload.renameTo(upladFile);
		return path;
	}
	
	public static void main(String[] args) {
		File file1 = new File("F:\\dir\\a.txt");
		File file2 = new File("F:\\dir\\dir2\\a.txt");
		boolean flag = file1.renameTo(file2);
		System.out.println("flag:"+flag);
	}
}
