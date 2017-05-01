<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<HTML>
	<HEAD>
		<title>系统设置</title>		
		<LINK href="${pageContext.request.contextPath }/css/Style.css" type="text/css" rel="stylesheet">
		<script language="javascript" src="${pageContext.request.contextPath }/script/function.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath }/script/pub.js"></script>
		<script language="javascript">
		
			
			
		function changetype(){
		
		  /**
		  * 如果document.Form1.keyword.value=="jerrynew"：
		         表示没有选择类型列表，此时类型名称和类型名称的文本框是可见的，可以操作类型名称和对应类型名称下数据项的值，
		         操作后点击保存，保存表示新增一种数据类型以及对应数据项值
		    如果document.Form1.keyword.value!="jerrynew"：
		        表示选择类型列表的值，此时数据项的值要通过类型列表中的值进行修改和编辑
		        操作后点击保存，保存表示在原有的数据类型继承上进行修改和编辑
		        
		  */
		  if(document.Form1.keyword.value=="jerrynew"){
		    
		     
		  	 var textStr="<input type=\"text\" name=\"keywordname\" maxlength=\"50\" size=\"24\"> ";
		     document.getElementById("newtypename").innerHTML="类型名称：";
		     document.getElementById("newddlText").innerHTML=textStr;
		     
		     
		     Pub.submitActionWithForm('Form2','system/elecSystemDDLAction_edit.do','Form1');
		    
		  }else{
		    
		    var textStr="";
		    document.getElementById("newtypename").innerHTML="";
		    document.getElementById("newddlText").innerHTML=textStr;
		     
		    /**
		    页面存在2个表单：分别是Form1和Form2
		    Pub.submitActionWithForm:
		         * 传递3个参数
		         * 参数一：表示表单中Form2的名称
		         * 参数二：访问的URL地址
		         * 参数三：表示表单中Form1的名称
		    
		    方法的调用：首先使用ajax技术以表单Form1中的元素作为参数向服务器进行传递
		                然后使用服务器进行响应和处理，获取到对应的数据结 果，返回到dictionaryEdit.jsp页面，将结果在dictionaryEdit.jsp页面进行显示
		                最后将dictionaryEdit.jsp页面放置到dictionaryIndex.jsp的Form2中
		   效果就是：dictionaryIndex.jsp页面表单From1不发生变化，表单Form2发生变化，从而实现异步效果
		   开发如何提取dictionaryEdit.jsp页面：
		       只需要将dictionaryIndex.jsp页面的Form2中的内容提取出来即可
		    */
		    Pub.submitActionWithForm('Form2','system/elecSystemDDLAction_edit.do','Form1');
		  }  
	   }
	   
     function saveDict(){
	      
	      if(document.Form1.keyword.value=="jerrynew"){
	          if(Trim(document.Form1.keywordname.value)==""){
	             alert("请输入类型名称");
	             return false;
	          }
	          
	         var allkeywords= document.Form1.keyword;
	         for(var i=0;i<allkeywords.length;i++){
	    
	            if(allkeywords[i].value==Trim(document.Form1.keywordname.value)){           

	               alert("已存在此类型名称,请重新输入");
	               return false;
	             }
	             
	         }
	         
	          document.Form2.keywordname.value=document.Form1.keywordname.value;
	          document.Form2.typeflag.value="new";
	          
	      }else{
	      
	          document.Form2.keywordname.value=document.Form1.keyword.value;
	          document.Form2.typeflag.value="add";	
	      }
	      var tbl=document.getElementById("dictTbl");
	      for (i=1;i<tbl.rows.length;i++){        
		   	  	var name = tbl.rows[i].cells[1].children.item(0).value;
		   	  	//alert(name);
		   	  	if(Trim(name)==""){
		   	  	    alert("名称不能为空！");
		   	  	    
		   	  	    return false;
	   	  	    }
	   	  }
	   	  for(k=1;k<=tbl.rows.length-2;k++)
		  {
	 	  	for(m=k+1;m<=tbl.rows.length-1;m++)
	 	  	{     
		  	  	var name1 = tbl.rows[k].cells[1].children.item(0).value;
		  	  	var name2 = tbl.rows[m].cells[1].children.item(0).value;
		  	  	if(name1 == name2){
		  	  		alert("名称不能相同！"); 
		  	  		 return false;
		        }	
		    }
		  }
	      document.Form2.action="system/elecSystemDDLAction_save.do";
	      document.Form2.submit();     
	}
	  
  
       
  function removecheck(seqid){
     if( !confirm("你确定删除吗?")) return false;
      
      document.Form2.keywordname.value=document.Form1.keyword.value;
      document.Form2.action="removeDict.do?seqid="+seqid;
      document.Form2.submit();
  }       
  
     
     
     
       

 function insertRows(){ 

  var tempRow=0; 
  var tbl=document.getElementById("dictTbl");
  tempRow=tbl.rows.length;
  //alert(tbl.rows.length);//2 
  var Rows=tbl.rows;//类似数组的Rows 
  var newRow=tbl.insertRow(tbl.rows.length);//插入新的一行 
  //alert("ok");
  var Cells=newRow.cells;//类似数组的Cells 
  for (i=0;i<3;i++)//每行的3列数据 
  { 
     //alert(newRow.rowIndex+"   "+Cells.length);
     var newCell=Rows(newRow.rowIndex).insertCell(Cells.length); //向新插入的行中新增列
     //alert(newRow.rowIndex+"   "+Cells.length);
     newCell.align="center"; 
     switch (i) 
    { 
      case 0 : newCell.innerHTML="<td class=\"ta_01\" align=\"center\"  width=\"15%\">"+tempRow+"</td>";break; 
      case 1 : newCell.innerHTML="<td class=\"ta_01\" align=\"center\"  width=\"60%\"><input name=\"itemname\" type=\"text\" id=\""+tempRow+"\" size=\"45\" maxlength=25></td>";break; 
      case 2 : newCell.innerHTML="<td class=\"ta_01\" align=\"center\"  width=\"25%\"><a href='javascript:delTableRow(\""+tempRow+"\")'><img src=${pageContext.request.contextPath }/images/delete.gif width=15 height=14 border=0 style=CURSOR:hand></a></td>";break;

    } 
  } 
 } 


function delTableRow(rowNum){ 

   var tbl=document.getElementById("dictTbl");
    //alert(tbl.rows.length+"      "+rowNum);
    if (tbl.rows.length >rowNum){ 
      
      tbl.deleteRow(rowNum);//删除指定rowNum的那一行
      //alert(tbl.rows.length+"      "+rowNum);
      for (i=rowNum;i<tbl.rows.length;i++)
       {
         tbl.rows[i].cells[0].innerText=i;
         tbl.rows[i].cells[2].innerHTML="<a href='javascript:delTableRow(\""+i+"\")'><img src=${pageContext.request.contextPath }/images/delete.gif width=15 height=14 border=0 style=CURSOR:hand></a>";      
      }
   }
} 
    
	 </script>
 </HEAD>
		
	<body>
	 <Form name="Form1" id="Form1"  method="post" style="margin:0px;">
		<table cellSpacing="1" cellPadding="0" width="90%" align="center" bgColor="#f5fafe" border="0">
			<TBODY>
				<tr>
					<td class="ta_01" colspan=3 align="center" background="${pageContext.request.contextPath }/images/b-info.gif">
						<font face="宋体" size="2"><strong>数据字典维护</strong></font>
					</td>
				</tr>
				<TR height=10><td colspan=3></td></TR>		
				<tr>
					<td class="ta_01" align="right" width="35%" >类型列表：</td>
					<td class="ta_01" align="left"  width="30%" >
					<s:select list="#request.systemList" name="keyword"
							  listKey="keyword" listValue="keyword"
							  headerKey="jerrynew" headerValue=""
							  cssClass="bg" cssStyle="width:180px" onchange="changetype()">
					</s:select>
					</td>
						
					 <td class="ta_01"  align="right" width="35%" >					 	    
				    </td>	  		
				</tr>
				
				
				
			    <tr>
			       <td class="ta_01" align="right" width="35%" id="newtypename">类型名称：</td>
				   <td class="ta_01"  align="left" width="30%"  height=20 id="newddlText">
				    <input type="text" name="keywordname" maxlength="25" size=24>	
				   </td>
				   <td class="ta_01"  align="right" width="35%" ></td>
				</tr>
				
				
				<TR height=10><td colspan=3 align="right">
				   <input type="button" name="saveitem" value="添加选项" style="font-size:12px; color:black; height=20;width=80" onClick="insertRows()">
				 </td></TR>   
			</TBODY>
		</table>
 </Form>
		
 <Form name="Form2" id="Form2"  method="post" style="margin:0px;">
    <table cellSpacing="1" cellPadding="0" width="90%" align="center" bgColor="#f5fafe" border="0" >
    <tr>
     <td >
	   <table cellspacing="0"   cellpadding="1" rules="all" bordercolor="gray" border="1" id="dictTbl"
		    style="BORDER-RIGHT:gray 1px solid; BORDER-TOP:gray 1px solid; BORDER-LEFT:gray 1px solid; WIDTH:100%; WORD-BREAK:break-all; BORDER-BOTTOM:gray 1px solid; BORDER-COLLAPSE:collapse; BACKGROUND-COLOR:#f5fafe; WORD-WRAP:break-word">
			
		
						
				<tr style="FONT-WEIGHT:bold;FONT-SIZE:12pt;HEIGHT:25px;BACKGROUND-COLOR:#afd1f3">
					<td class="ta_01" align="center"  width="20%" height=22 background="${pageContext.request.contextPath }/images/tablehead.jpg">编号</td>
					<td class="ta_01" align="center"  width="60%" height=22 background="${pageContext.request.contextPath }/images/tablehead.jpg">名称</td>
					<td class="ta_01" align="center"  width="20%" height=22 background="${pageContext.request.contextPath }/images/tablehead.jpg">删除</td>					
				</tr>
			    
			   
			     <tr>
				   <td class="ta_01" align="center"  width="20%">1</td>
				   <td class="ta_01" align="center"  width="60%">
				   <input name="itemname" type="text"  size="45" maxlength="25"></td>
				   <td class="ta_01" align="center"  width="20%"></td>
				</tr>
				
				
	     </table>
	   </td>
	 </tr>
  <tr>
     <td >   
	 </td>
 </tr>
 <TR height=10><td colspan=3></td></TR>
  <tr>
     <td align="center" colspan=3>
       <input type="button" name="saveitem" value="保存" style="font-size:12px; color:black; height=20;width=50" onClick="return saveDict();">
	 </td>
 </tr>
       <input type="hidden" name="keywordname" >
       <input type="hidden" name="typeflag" >
	 
  </table>
  </Form>
  </body>
</HTML>


