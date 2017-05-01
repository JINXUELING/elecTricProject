<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<HTML>
<HEAD>
<title>角色权限管理</title>
<LINK href="${pageContext.request.contextPath }/css/Style.css"
	type="text/css" rel="stylesheet">
<script language="javascript"
	src="${pageContext.request.contextPath }/script/function.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/script/pub.js"></script>
<script language="javascript">
		  
		 function saveRole(){
		 
           document.Form2.roleid.value=document.Form1.roleid.value;
		   document.Form2.action="system/elecRoleAction_save.do";
		   document.Form2.submit();
		}
       
       function selectRole(){
          
          if(document.Form1.roleid.value=="0"){
          
             document.Form1.action="system/elecRoleAction_home.do";
             document.Form1.submit();            
          }else{
        	  /**
        	   使用ajax实现form1和form2表单的异步操作
        	   *选择form1中的角色类型，向服务器传递form1的表单做为参数
        	    将返回结果放置到另一个页面roleEdit。jsp中
        	    将roleEdit。jsp中的内容放置到roleIndex。jsp的form2中
        	  */
            Pub.submitActionWithForm('Form2','system/elecRoleAction_edit.do','Form1');
          }
       }
	   function checkAllOper(oper){
          var selectoper = document.getElementsByName("selectoper");
          for(var i=0;i<selectoper.length;i++){
          	selectoper[i].checked = oper.checked;
          }
       }
	   function checkAllUser(user){
          var selectuser = document.getElementsByName("selectuser");
          for(var i=0;i<selectuser.length;i++){
          	selectuser[i].checked = user.checked;
          }
       }
		</script>
</HEAD>

<body>
	<Form name="Form1" id="Form1" method="post" style="margin:0px;">
		<table cellSpacing="1" cellPadding="0" width="90%" align="center"
			bgColor="#f5fafe" border="0">
			<TBODY>
				<tr>
					<td class="ta_01" colspan=2 align="center"
						background="${pageContext.request.contextPath }/images/b-info.gif">
						<font face="宋体" size="2"><strong>角色管理</strong>
					</font></td>
				</tr>
				<tr>
					<td class="ta_01" colspan=2 align="right" width="100%" height=10>
					</td>
				</tr>
				<tr>
					<td class="ta_01" align="right" width="35%">角色类型&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td class="ta_01" align="left" width="65%"><s:select
							list="#request.roleList" name="roleid" listKey="ddlCode"
							listValue="ddlName" headerKey="0" headerValue="请选择" cssClass="bg"
							cssStyle="width:180px" onchange="selectRole()">
						</s:select></td>
				</tr>

				<tr>
					<td class="ta_01" align="right" colspan=2 align="right"
						width="100%" height=10></td>
				</tr>

			</TBODY>
		</table>
	</Form>

	<Form name="Form2" id="Form2" method="post" style="margin:0px;">

		<table cellSpacing="1" cellPadding="0" width="90%" align="center"
			bgColor="#f5fafe" border="0">
			<tr>
				<td>
					<fieldset style="width:100%; border : 1px solid #73C8F9;text-align:left;COLOR:#023726;FONT-SIZE: 12px;">
						<legend align="left"> 权限分配 </legend>
						<table cellSpacing="0" cellPadding="0" width="90%" align="center" bgColor="#f5fafe" border="0">
							<tr>
								<td class="ta_01" colspan=2 align="left" width="100%">
									<s:if test="#request.xmlList!=null && #request.xmlList.size()>0">
									      <!-- 设置根节点为空 -->
										<s:set name="parentCode" scope="request" value="%{''}"></s:set>
										  <!-- 迭代元素组合(XMLObjectBean) -->
										<s:iterator value="#request.xmlList" var="xml">
										      <!-- 如果是当前节点父节点 -->
											<s:if test="%{#request.parentCode == #xml.parentCode}">
												<input type="checkbox" name="selectoper" value="<s:property value="%{#xml.code}"/>">
												    <!-- 节点名称 -->
												<s:property value="%{#xml.name}" />
											</s:if>
											        <!-- 如果为当前节点的下一个节点或者第一个节点 -->
											<s:else>
											        <!-- 给父节点向下赋值,实现循环 -->
												<s:set name="parentCode" scope="request" value="%{#xml.parentCode}"></s:set>
												<br>
												    <!-- 调整对其格式，给固定长度为8个空格 -->
												<s:iterator begin="0" end="%{8-#xml.parentName.length()}" step="1">
                                                    &nbsp;&nbsp;&nbsp;
                                                </s:iterator>
                                                
												<s:property value="%{#xml.parentName}" />：
												<input type="checkbox" name="selectoper" value="<s:property value="%{#xml.code}"/>">
												    <!-- 节点名称 -->
												<s:property value="%{#xml.name}" />
											</s:else>
										</s:iterator>
									</s:if>
								</td>
							</tr>
						</table>
					</fieldset>
				</td>
			</tr>
		</table>
	</Form>
</body>
</HTML>
