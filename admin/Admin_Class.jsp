<%@ page contentType="text/html; charset=GBK" language="java" buffer="32kb"%>
<%@ page import="dreamtime.dreamnews.*"%>
<%@ include file="Session.jsp"%>
<% if (Function1.StrToInt((String)session.getAttribute("AdminType"))<2) 
	{
	   response.sendError(403,"禁止访问");
	   return;
	}
%>
<html>
<head>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link href="css/bodystyle.css" rel="stylesheet" type="text/css">
<script src="js/post.js"></script>
<title>新闻类别管理</title></head>
<% 
	request.setCharacterEncoding("GBK");	 //设置编码方式为GBK	
	String Action = request.getParameter("Action");
	String IP = request.getRemoteAddr();	//得到客户端Ip地址
	String AdminName = (String)session.getAttribute("AdminName"); 
	AdminClass aClass = new AdminClass();
	Function Fun = new Function();
	String s = "";
	String [][] sArray = aClass.GetAllClass(true,false,null);
	String BigClassID   = request.getParameter("BigClassID");
	String BigClassName = request.getParameter("BigClassName");
	String BigClassInfo = request.getParameter("BigClassInfo");
	String SClassID   = request.getParameter("SClassID");
	String SClassName = request.getParameter("SClassName");
	String SClassInfo = request.getParameter("SClassInfo");
	String strPage = request.getParameter("intPage");
	if (Action == null || Action.equals("")) Action = "List";
	
	//读取类别列表
	if (Action.equals("List")) 
	{ 
		String sPage = request.getContextPath() + request.getServletPath();
		String sOK = aClass.ReadClass();
		if (sOK.equals("No"))
		{
			out.println(Fun.OutError("读取类别列表操作出错！"));
			return;
		}
		else
		{
			out.println(sOK);
		}
	}
	
	
	//保存添加的大类
	else if (Action.equals("SaveAddBigClass"))
	{
		s = aClass.AddBigClass(BigClassID,BigClassName,BigClassInfo,AdminName,IP);
		if (s.equals("Yes")) 
			out.print("<script>alert('新增大类成功!');location.href='Admin_Class.jsp?Action=List';</script>");
		else 
		{
			out.print(Fun.OutError(s));
			return;
		}
	}
	
	//保存修改的大类
	else if (Action.equals("SaveEditBigClass"))
	{	
		String OldBigClassID   = request.getParameter("OldBigClassID");
		String OldBigClassName = request.getParameter("OldBigClassName");
		s = aClass.EditBigClass(BigClassID,BigClassName,BigClassInfo,OldBigClassID,OldBigClassName,AdminName,IP);
		if (s.equals("Yes")) 
			out.print("<script>alert('修改大类成功!');location.href='Admin_Class.jsp?Action=List';</script>");
			//response.sendRedirect("Admin_Class.jsp?Action=List");
		else 
		{
			out.print(Fun.OutError(s));
			return;
		}
	}
	
	//删除大类
	else if (Action.equals("DelBig"))
	{
		//给出提示信息
		String sWarn = "该操作将删除该大类本身、该大类下的所有小类以及该大类下的所有新闻，该操作将不可恢复，你是否确定要删除？";
		sWarn += "该操作只有这一次确认，请您谨慎操作！";
		if (request.getParameter("OK") != null && request.getParameter("OK").equals("Yes"))
		{
			if (aClass.DelBigClass(BigClassID,AdminName,IP))
				out.print("<script>alert('删除大类成功!');location.href='Admin_Class.jsp?Action=List';</script>");
			else 
			{
				out.print(Fun.OutError("删除操作出错！"));
				return;
			}
		}	
		else out.print(Fun.OutWarn(sWarn));			
	}
	
	//保存添加小类
	else if (Action.equals("SaveAddSClass"))
	{
		s = aClass.AddSClass(SClassName,SClassInfo,BigClassID,AdminName,IP);
		if (s.equals("Yes")) 
			out.print("<script>alert('新增小类成功!');location.href='Admin_Class.jsp?Action=List';</script>");
		else 
		{
			out.print(Fun.OutError(s));
			return;
		}
	}
	
	//保存修改的小类
	else if (Action.equals("SaveEditSClass"))
	{
		String OldSmallClassName = request.getParameter("OldSmallClassName");
		s = aClass.EditSClass(BigClassID,SClassID,SClassName,SClassInfo,OldSmallClassName,AdminName,IP);
		if (s.equals("Yes")) 
			out.print("<script>alert('修改小类成功!');location.href='Admin_Class.jsp?Action=List';</script>");
		else 
		{
			out.print(Fun.OutError(s));
			return;
		}
	}
	
	//删除小类
	else if (Action.equals("DelSmall"))
	{
		//给出提示信息
		String sWarn = "该操作将删除该小类本身、该小类下的所有新闻，该操作将不可恢复，你是否确定要删除？";
		sWarn += "该操作只有这一次确认，请您谨慎操作！";
		if (request.getParameter("OK") != null && request.getParameter("OK").equals("Yes"))
		{
			if (aClass.DelSClass(SClassID,AdminName,IP))
				out.print("<script>alert('删除小类成功!');location.href='Admin_Class.jsp?Action=List';</script>");
			else 
			{
				out.print(Fun.OutError("删除操作出错！"));
				return;
			}
		}		
		else out.print(Fun.OutWarn(sWarn));	
	}

	
if (Action.equals("AddBigClass")) { 
%> 
<br><br>
<form action="Admin_Class.jsp" method="POST" name="BigClass" id="BigClass" onSubmit="return CheckBigClass()">
  <table width="90%"  border="1" align="center" cellpadding="2" cellspacing="0">
    <tr background="images/bg.gif">
      <td height="27" colspan="2" background="images/bg.gif">
	  <div align="center"><span class="title"><strong>增加新闻大类</strong></span></div></td>
    </tr>
    <tr>
      <td width="38%" height="25"><div align="right" class="chinese">大类排序：</div></td>
      <td width="62%"><input name="BigClassID" type="text" class="chinese" id="BigClassID" size="10" maxlength="4" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')">&nbsp;<span class="info1">* 必须是大于 0 的整数</span></td>
    </tr>
    <tr>
      <td height="25"><div align="right" class="chinese">大类标题：</div></td>
      <td><input name="BigClassName" type="text" class="chinese" id="BigClassName" size="20" maxlength="30">&nbsp;<span class="info1">* 必填</span></td>
    </tr>
    <tr>
      <td height="25"><div align="right" class="chinese">大类说明：</div></td>
      <td><textarea name="BigClassInfo" cols="20" rows="5" class="chinese" id="BigClassInfo" onKeyDown="BigPressKey();"></textarea>&nbsp;<span class="info1">* 必填(可按Ctrl+Enter)提交</span></td>
    </tr>
    <tr>
      <td height="30" colspan="2"><div align="center">
          <input name="Submit2" type="submit" class="button" value="确定">
          <input name="Action" type="hidden" id="Action" value="SaveAddBigClass">
          <input name="Return4" type="button" class="button" id="Return" value="返回" onClick="javascript:history.back();">
      </div></td>
    </tr>
  </table>
</form>
<br><br>
<% }
if (Action.equals("EditBigClass")) 
{
	String OldBigClassID = request.getParameter("BigClassID");
	String [] sa = aClass.ReadBigClass(OldBigClassID,true);
%>
<br><br>
<form action="Admin_Class.jsp" method="POST" Name="BigClass" id="BigClass" onSubmit="return CheckBigClass()">
  <table width="90%"  border="1" align="center" cellpadding="2" cellspacing="0">
    <tr background="images/bg.gif">
      <td height="27" colspan="2" background="images/bg.gif">
	  <div align="center"><span class="title"><strong>修改新闻大类</strong></span></div></td>
    </tr>
    <tr>
      <td width="38%" height="25"><div align="right" class="chinese">大类排序：</div></td>
      <td width="62%" valign="middle"><input name="BigClassID" type="text" class="chinese" id="BigClassID" value="<%=OldBigClassID%>" size="10" maxlength="4" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')">
          <input name="OldBigClassID" type="hidden" id="OldBigClassID2" value="<%=OldBigClassID%>">&nbsp;<span class="info1">* 必须是大于 0 的整数</span> </td>
    </tr>
    <tr>
      <td height="25"><div align="right" class="chinese">大类标题：</div></td>
      <td><input name="BigClassName" type="text" class="chinese" id="BigClassName" value="<%=sa[0]%>" size="20" maxlength="30">
          <input name="OldBigClassName" type="hidden" id="OldBigClassName" value="<%=sa[0]%>">&nbsp;<span class="info1">* 必填</span></td>
    </tr>
    <tr>
      <td height="25"><div align="right" class="chinese">大类说明：</div></td>
      <td><textarea name="BigClassInfo" cols="20" rows="5" class="chinese" id="BigClassInfo" onKeyDown="BigPressKey();"><%=sa[1]%></textarea>&nbsp;<span class="info1">* 必填(可按Ctrl+Enter)提交</span> </td>
    </tr>
    <tr>
      <td height="30" colspan="2"><div align="center">
          <input name="Submit" type="submit" class="button" value="确定">
          <input name="Action" type="hidden" id="Action" value="SaveEditBigClass">
          <input name="Return" type="button" class="button" id="Return" value="返回" onClick="javascript:history.back();">
      </div></td>
    </tr>
  </table>
</form>
<br><br>
<% } 
if (Action.equals("AddSClass")) 
{ 
	String BCID = request.getParameter("BigClassID");
	String BCName = aClass.ReadBigClass(BCID,true)[0];
%>
<br>
<form action="Admin_Class.jsp" method="POST" Name="SmallClass" id="SmallClass" onSubmit="return CheckSClass()">
  <table width="90%"  border="1" align="center" cellpadding="2" cellspacing="0">
    <tr background="images/bg.gif">
      <td height="27" colspan="2" background="images/bg.gif">
	  <div align="center"><span class="title"><strong>增加小类</strong></span></div></td>
    </tr>
    <tr>
      <td width="38%" height="25"><div align="right" class="chinese">所属大类：</div></td>
      <td width="62%">
        <select name="BigClassID" size="1" class="chinese" id="BigClassID">
          <% if (sArray[0][0]==null)
				{
					out.print("<option value=\"\">暂无大类</option>");
				}
				else
				{
			 		for(int i=0;i<sArray.length;i++)
					{
						out.print("<option value=\"" + sArray[i][0] + "\"");
						if (sArray[i][0].equals(BCID))  { out.print(" selected"); }
						out.print(">" + sArray[i][1] + "</option>");
					}
				}
			 %>
        </select>&nbsp;<span class="info1">* 必选</span></td>
    </tr>
    <tr>
      <td height="25"><div align="right" class="chinese">小类标题：</div></td>
      <td><input name="SClassName" type="text" class="chinese" id="SClassName" size="20" maxlength="30">&nbsp;<span class="info1">* 必填</span></td>
    </tr>
    <tr>
      <td height="25"><div align="right" class="chinese">小类说明：</div></td>
      <td><textarea name="SClassInfo" cols="20" rows="5" class="chinese" id="SClassInfo" onKeyDown="SPressKey();"></textarea>&nbsp;<span class="info1">* 必填(可按Ctrl+Enter)提交</span></td>
    </tr>
    <tr>
      <td height="30" colspan="2"><div align="center">
          <input name="Submit3" type="submit" class="button" value="确定">
          <input name="Action" type="hidden" id="Action" value="SaveAddSClass">
          <input name="Return" type="button" class="button" id="Return5" value="返回" onClick="javascript:history.back();">
      </div></td>
    </tr>
  </table>
</form>
<br><br>
<% } 
if (Action.equals("EditSClass")) 
{
	String SCID = request.getParameter("SClassID");
	String [] sa = aClass.ReadBigClass(SCID,false);
%>
<br><br>
<form action="Admin_Class.jsp" method="POST"  Name="SmallClass" id="SmallClass" onSubmit="return CheckSClass()">
  <table width="90%"  border="1" align="center" cellpadding="2" cellspacing="0">
    <tr background="images/bg.gif">
      <td height="27" colspan="2" background="images/bg.gif"><div align="center"><span class="title"><strong>修改小类</strong></span></div></td>
    </tr>
    <tr>
      <td width="38%" height="25"><div align="right" class="chinese">所属大类：</div></td>
      <td width="62%">
        <select name="BigClassID" size="1" class="chinese" id="BigClassID">
          <% if (sArray[0][0]==null)
				{
					out.print("<option value=\"\">暂无大类</option>");
				}
				else
				{ 
			 		for(int i=0;i<sArray.length;i++)
					{
						out.print("<option value=\"" + sArray[i][0] + "\"");
						if (sArray[i][0].equals(sa[2])) out.print(" selected");
						out.println(">" + sArray[i][1] + "</option>");
					}
				}
			 %>
        </select>
        <input name="SClassID" type="hidden" id="SClassID" value="<%=SCID%>"><span class="info1">* 必选</span></td>
    </tr>
    <tr>
      <td height="25"><div align="right" class="chinese">小类标题：</div></td>
      <td><input name="SClassName" type="text" class="chinese" id="SClassName" value="<%=sa[0]%>" size="20" maxlength="30">
          <input name="OldSmallClassName" type="hidden" id="OldSmallClassName" value="<%=sa[0]%>"><span class="info1">* 必填</span></td>
    </tr>
    <tr>
      <td height="25"><div align="right" class="chinese">小类说明：</div></td>
      <td><textarea name="SClassInfo" cols="20" rows="5" class="chinese" id="SClassInfo" onKeyDown="SPressKey();"><%=sa[1]%></textarea>&nbsp;<span class="info1">* 必填(可按Ctrl+Enter)提交</span></td>
    </tr>
    <tr>
      <td height="30" colspan="2"><div align="center">
          <input name="Submit" type="submit" class="button" value="确定">
          <input name="Action" type="hidden" id="Action" value="SaveEditSClass">
          <input name="Return" type="button" class="button" id="Return" value="返回" onClick="javascript:history.back();">
      </div></td>
    </tr>
  </table>
</form>
<br><br>
<% } %>
<br><br>
</body>
</html>
