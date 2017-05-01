package cn.itcast.elec.web.action;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.connection.UserSuppliedConnectionProvider;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.elec.domain.ElecSystemDDL;
import cn.itcast.elec.domain.ElecUser;
import cn.itcast.elec.service.IElecSystemDDLService;
import cn.itcast.elec.service.IElecUserService;
import cn.itcast.elec.util.ChartUtils;
import cn.itcast.elec.util.ExcelFileGenerator;
import cn.itcast.elec.util.GenerateSqlFromExcel;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

@Controller("elecUserAction")
@Scope(value="prototype")
@SuppressWarnings("serial")
public class ElecUserAction extends BaseAction implements ModelDriven<ElecUser>{
	private ElecUser elecUser = new ElecUser();
	
	public ElecUser getModel() {
		return elecUser;
	}
	
	@Resource(name=IElecUserService.SERVICE_NAME)
	private IElecUserService elecUserService;
	@Resource(name=IElecSystemDDLService.SERVICE_NAME)
	private IElecSystemDDLService elecSystemDDLService;
	/**  
	* @Name: home
	* @Description: 跳转到用户管理的首页面（列表）
	* @Author: 晋学领（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2017/4/19 （创建日期）
	* @Parameters: 无
	* @Return: String,跳转到system/userIndex.jsp
	*/
	
	public String home(){
		List<ElecUser> list = elecUserService.findElecUserList(elecUser);
		request.setAttribute("userList", list);
		/**添加分页
		 * 当点击用户管理的时候，initflag的值为null
		 * 当点击查询，下一页等需要return list，initflag的值为1；
		 * */
		String initflag = request.getParameter("initflag");
		if(initflag!=null && initflag.equals("1")){
			return "list";
		}
		/** end*/
		return "home";
	}
	
	/**
	 * 导出当前报表
	 * @return
	 */
	public String exportExcel(){
		/**
		 * ArrayList<String> fieldName:存放的是excel的标题集合
		 * 	fieldName.add("登录名")
		 * 	fieldName.add("用户姓名")
		 */
		ArrayList<String> fieldName = elecUserService.excelFildName();
		/**
		 * ArrayList fieldData:存放的是excel的数据集合
		 */
		ArrayList<ArrayList<String>> fieldData = elecUserService.excelFildData(elecUser);
		ExcelFileGenerator generator = new ExcelFileGenerator(fieldName,fieldData);
		try {
			//用response对象获取输出流
			OutputStream os = response.getOutputStream();
			//重置response对象中的缓冲区,该方法可以不写，当时要保证response中的数据是最新需要导出的数据
			response.reset();
			//由于导出的格式是excel的文件，设置导出文件的响应头
			response.setContentType("application/vnd.ms-excel");
			//生成excel，传递输出流
			generator.expordExcel(os);
			//刷新缓冲区，将缓存中的数据导出excel
			os.flush();
			//关闭
			if(os!=null){
				os.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 导入报表
	 * @return 导入操作页面
	 */
	public String importExcelPage(){
		
		return "importExcel";
	}
	
	/**导入报表数据*/
	public String importData(){
		File formfile = elecUser.getFile();
		GenerateSqlFromExcel generatorsql = new GenerateSqlFromExcel();
		try {
			/**
			 * ArrayList<String[]> list就是从数据文件中读取的记录集合
			 */
			ArrayList<String[]> Arraylist = generatorsql.generateUserSql(formfile);
			//把list中的数据导入到数据库中
			elecUserService.excelImportData(Arraylist);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		return "importExcel";
	}
	
	/**导出柱状统计图的报表*/
	public String charUser(){
		List<Object[]> list = elecUserService.findCharDataSet();
		//生成图片，同时返回图片的名称，将图片放置到request中
		ChartUtils charUtils = new ChartUtils();
		String filename = null;
		try {
			filename = charUtils.generalBarJpeg(list);
		} catch (IOException e) {
			e.printStackTrace();
		}
		request.setAttribute("filename", filename);
		return "charUser";
	}
	
	/**新增用户信息*/
	public String add(){
		initSystemDDL();
		return "add";
	}

	/**
	 * @param elecSystemDDL
	 */
	public void initSystemDDL() {
		ElecSystemDDL elecSystemDDL = new ElecSystemDDL();
		elecSystemDDL.setKeyword("性别");
		List<ElecSystemDDL> sexlist = elecSystemDDLService.findSystemDDLList(elecSystemDDL);
		elecSystemDDL.setKeyword("所属单位");
		List<ElecSystemDDL> jctlist = elecSystemDDLService.findSystemDDLList(elecSystemDDL);
		elecSystemDDL.setKeyword("是否在职");
		List<ElecSystemDDL> isDutyList = elecSystemDDLService.findSystemDDLList(elecSystemDDL);
		//将三个值放到request对象中
		request.setAttribute("sexList", sexlist);
		request.setAttribute("jctList", jctlist);
		request.setAttribute("isDutyList", isDutyList);
	}
	/**  
	* @Name: edit
	* @Description: 跳转到添加页面
	* @Author: 刘洋（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2012-09-11 （创建日期）
	* @Parameters: 无
	* @Return: String,跳转到system/userIndex.jsp
	*/
	public String save(){
		elecUserService.saveElecUser(elecUser);
		String roleflag = elecUser.getRoleflag();
		if(roleflag!=null&roleflag.equals("1")){
			return edit();
		}
		return "save";
	}
	/**  
	* @Name: edit
	* @Description: 跳转到编辑页面
	* @Author: 刘洋（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2012-09-11 （创建日期）
	* @Parameters: 无
	* @Return: String,跳转到system/userEdit.jsp
	*/
	public String edit(){
		//获取viewflag的值，作为一个中间变量
		String viewflag = elecUser.getViewflag();
		String roleflag = elecUser.getRoleflag();
		elecUser = elecUserService.findElecUserByID(elecUser);
		elecUser.setViewflag(viewflag);//存放viewflag的值
		elecUser.setRoleflag(roleflag);//存放roleflag的值
		//将用户信息放置到栈顶用于显示
		ActionContext.getContext().getValueStack().pop();//删除栈顶信息
		ActionContext.getContext().getValueStack().push(elecUser);//将ElecUser对象放置到栈顶
		//初始化数据字典中的项（性别、所属单位、是否在职）
		this.initSystemDDL();
		//ValueStack stack = ActionContext.getContext().getValueStack();
		return "edit";
	}
	/**  
	* @Name: edit
	* @Description: 跳转到添加页面
	* @Author: 刘洋（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2012-09-11 （创建日期）
	* @Parameters: 无
	* @Return: String,跳转到system/userIndex.jsp
	*/
	public String delete(){
		elecUserService.deleteElecUserByID(elecUser);
		return "save";
	}
	public String checkUser(){
		try{
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=utf-8");
			String logonName = elecUser.getLogonName();
			/**
			 * flas作为判断用户名是否出现重复标识
			 * 	如果flag=1：此时表明用户存在
			 * 	如果flag=2：此时表明用户不存在
			 */
			String flag = elecUserService.checkUser(logonName);
			PrintWriter out = response.getWriter();
			out.println(flag);
		}catch(IOException e){
			e.printStackTrace();
		}
		return null;
	}
}
