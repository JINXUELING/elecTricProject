package cn.itcast.elec.web.action;

import javax.annotation.Resource;

import org.apache.commons.lang.xwork.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.sun.org.apache.xalan.internal.xsltc.runtime.Hashtable;

import cn.itcast.elec.domain.ElecCommonMsg;
import cn.itcast.elec.domain.ElecUser;
import cn.itcast.elec.service.IElecCommonMsgService;
import cn.itcast.elec.service.IElecUserService;
import cn.itcast.elec.service.impl.ElecUserServiceImpl;
import cn.itcast.elec.util.LogonUtils;
import cn.itcast.elec.util.MD5keyBean;

@Controller("elecMenuAction")
@Scope("prototype")
@SuppressWarnings("serial")
public class ElecMenuAction extends BaseAction {
	
	@Resource(name=IElecCommonMsgService.SERVICE_NAME)
	private IElecCommonMsgService elecCommonMsgService;
	@Resource(name=IElecUserService.SERVICE_NAME)
	private IElecUserService elecUserService;
	/**  
	* @Name: home
	* @Description: 处理系统登录
	* @Author: 刘洋（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2012-09-14 （创建日期）
	* @Parameters: 无
	* @Return: String 
	*       如果登录成功，跳转到home.jsp
	*       如果登录不成功，返回到index.jsp
	*/
	public String home(){
		/**验证验证码和记住我
		 * boolean falg:
		 * 		*flag=ture:表示验证码输入有误
		 * 		*flag=false:表示验证码输入错误，返回首页面，提示验证码输入有误
		 * */
		boolean flag = LogonUtils.checkNumber(request);
		if(!flag){
			this.addFieldError("error", "验证码输入有误");
			return "error";
		}
		/**验证用户名和密码 */
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		//以登录名作为查询条件，查询用户信息表 ，获取对应的的ElecUser对象
		ElecUser elecUser = elecUserService.findElecUserByLogonName(name);
		//返回登录页面，显示错误信息
		if(elecUser==null){
			this.addFieldError("error", "您输入的用户名不正确");
			return "error";
		}
		//以密码作为条件进行验证密码的正确性
		if(StringUtils.isBlank(password)){
			this.addFieldError("error", "您输入的密码不能为空");
			return "error";
		}
		//使用MD5对密码进行加密
		MD5keyBean bean = new MD5keyBean();
		String md5Password = bean.getkeyBeanofStr(password);
		//验证密码的正确性
		 if(!elecUser.getLogonPwd().equals(md5Password)){
			 this.addFieldError("error", "您输入的密码不正确");
				return "error";
		 }
		request.getSession().setAttribute("globle_user", elecUser);
		/**使用登录名获取系统中的角色ID和角色名称，存放到HashTable中*/
		java.util.Hashtable <String,String> hashtable = elecUserService.findRoleByLogonName(name);
		if(hashtable == null){
			this.addFieldError("error", "您当前的登录名没有分配角色");
			return "error";
		}
		request.getSession().setAttribute("globle_role", hashtable);
		/**使用登录名获取用户被赋予的权限集合*/
		String popedom = elecUserService.findPopedomByLogonName(name);
		if(StringUtils.isBlank(popedom)){
			this.addFieldError("error","您当前的登录名没有权限");
			return "error";
		}
		request.getSession().setAttribute("globle_pepedom", popedom);
		/**添加记住我功能*/
		LogonUtils.rememberMe(request,response);
		return "home";
	}
	
	public String logout(){
		request.getSession().invalidate();
		return "logout";
	}
	
	public String logouterror(){
		request.getSession().invalidate();
		return "logout";
	}
	
	public String title(){
		return "title";
	}
	
	public String left(){
		return "left";
	}
	
	public String change1(){
		return "change1";
	}
	
	public String loading(){
		return "loading";
	}
	
	public String alermXZ(){
		return "alermXZ";
	}
	
	public String alermJX(){
		return "alermJX";
	}
	
	/**  
	* @Name: alermZD
	* @Description: 显示站点运行情况
	* @Author: 刘洋（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2012-09-07 （创建日期）
	* @Parameters: 无
	* @Return: String 跳转到alermZD.jsp
	*/
	public String alermZD(){
		this.initCommonMsg();
		return "alermZD";
	}
	
	

	/**  
	* @Name: alermSB
	* @Description: 显示设备运行情况
	* @Author: 刘洋（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2012-09-07 （创建日期）
	* @Parameters: 无
	* @Return: String 跳转到alermSB.jsp
	*/
	public String alermSB(){
		this.initCommonMsg();
		return "alermSB";
	}
	
	/**加载代办事宜站点运行情况和设备运行情况信息*/
	private void initCommonMsg() {
		ElecCommonMsg elecCommonMsg = elecCommonMsgService.findCommonMsg();
		//放入到栈顶
		ActionContext.getContext().getValueStack().pop();
		ActionContext.getContext().getValueStack().push(elecCommonMsg);
	}
	
	public String alermYS(){
		return "alermYS";
	}
}
