package cn.itcast.elec.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.xwork.StringUtils;

public class LogonUtils {
	
	/**验证验证码和记住我
	 * boolean falg:
	 * 		*flag=ture:表示验证码输入有误
	 * 		*flag=false:表示验证码输入错误，返回首页面，提示验证码输入有误
	 * */
	public static boolean checkNumber(HttpServletRequest request) {
		//获取页面的验证
		String checkNumber = request.getParameter("checkNumber");
		if(StringUtils.isBlank(checkNumber)){
			return false;
		}
		String checkNumberSession = (String) request.getSession().getAttribute("CHECK_NUMBER_KEY");
		if(StringUtils.isBlank(checkNumberSession)){
			return false;
		}
		return checkNumberSession.equalsIgnoreCase(checkNumber);
	}
	/**添加记住我功能
	 * @param request 
	 * @param response */
	public static void rememberMe(HttpServletRequest request, HttpServletResponse response) {
		//获取登录名和密码
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		//获取两个cookie
		//处理name的编码格式，处理中文
		try {
			name = URLEncoder.encode(name, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Cookie cookieName = new Cookie("name",name);
		Cookie cookiePassword = new Cookie("password",password);
		//设置cookie的有效路径，路径为根目录
		cookieName.setPath(request.getContextPath());
		cookiePassword.setPath(request.getContextPath());
		//从页面中获取复选框中的值,判断复选框是否被选中
		String remeberMe = request.getParameter("remeberMe");
		//说明复选框中，此时要设置cookie的有效时长
		if(remeberMe!=null && remeberMe.equals("yes")){
			cookieName.setMaxAge(7*24*60);//一个星期
			cookiePassword.setMaxAge(7*24*60);//一个星期
		}
		else{
			cookieName.setMaxAge(0);
			cookiePassword.setMaxAge(0);
		}
		//将cookie存放到response中
		response.addCookie(cookieName);
		response.addCookie(cookiePassword);
	}

}
