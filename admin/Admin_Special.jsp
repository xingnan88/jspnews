<%@ page contentType="text/html; charset=GBK" language="java" buffer="32kb"%>
<%@ page import="dreamtime.dreamnews.*"%>
<%@ include file="Session.jsp"%>
<% if (Function1.StrToInt((String)session.getAttribute("AdminType"))<2) 
	{
	   response.sendError(403,"禁止访问 您没有这个权限");
	   return;
	}
%>
<html>
<head>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link href="css/bodystyle.css" rel="stylesheet" type="text/css">
<script src="js/post.js"></script>
<title>新闻专题管理</title></head>
<% 
	request.setCharacterEncoding("GBK");	 //设置编码方式为GBK	
	String Action = request.getParameter("Action");
	String IP = request.getRemoteAddr();	//得到客户端Ip地址
	String AdminName = (String)session.getAttribute("AdminName"); 
	AdminClass aClass = new AdminClass();
	Function Fun = new Function();
	String s = "";
	String SpecialID   = request.getParameter("SpecialID");
	String SpecialName   = request.getParameter("SpecialName");
	String SpecialInfo = request.getParameter("SpecialInfo");
	if (Action == null || Action.equals("")) Action = "List";
	
	//读取类别列表
	if (Action.equals("List")) 
	{ 
		String sOK = aClass.ReadSpecial();
		if (sOK.equals("No"))
		{
			out.println(Fun.OutError("读取专题列表操作出错！"));
			return;
		}
		else
		{
			out.println(sOK);
		}
	}
	
	//保存添加专题
	else if (Action.equals("SaveAdd"))
	{
		s = aClass.AddSpecial(SpecialName,SpecialInfo,AdminName,IP);
		if (s.equals("Yes")) 
			out.print("<script>alert('添加专题成功!');location.href='Admin_Special.jsp?Action=List';</script>");
		else 
		{
			out.print(Fun.OutError(s));
			return;
		}
	}
	
	//保存修改的专题
	else if (Action.equals("SaveEdit"))
	{
		String OldSpecialName = request.getParameter("OldSpecialName");
		s = aClass.EditSpecial(SpecialName,SpecialInfo,AdminName,IP,SpecialID,OldSpecialName);
		if (s.equals("Yes")) 
			out.print("<script>alert('修改专题成功!');location.href='Admin_Special.jsp?Action=List';</script>");
		else 
		{
			out.print(Fun.OutError(s));
			return;
		}
	}
	
	//删除小类
	else if (Action.equals("Del"))
	{
		//给出提示信息
		String sWarn = "该操作将删除该专题本身、以及该专题下的所有新闻，该操作将不可恢复，你是否确定要删除？";
		sWarn += "该操作只有这一次确认，请您谨慎操作！";
		if (request.getParameter("OK") != null && request.getParameter("OK").equals("Yes"))
		{
			if (aClass.DelSpecial(SpecialID,AdminName,IP))
				out.print("<script>alert('删除专题成功!');location.href='Admin_Special.jsp?Action=List';</script>");
			else 
			{
				out.print(Fun.OutError("删除操作出错！"));
				return;
			}
		}		
		else out.print(Fun.OutWarn(sWarn));	
	}

	
if (Action.equals("Add")) { 
%> 
<br><br>
<form action="Admin_Special.jsp" method="POST" Name="Special" id="Special" onSubmit="return CheckSpecial()">
  <table width="90%"  border="1" align="center" cellpadding="2" cellspacing="0">
    <tr background="images/bg.gif">
      <td height="27" colspan="2" background="images/bg.gif">
	  <div align="center"><span class="title"><strong>增加专题</strong></span></div></td>
    </tr>
    <tr>
      <td width="38%" height="25"><div align="right" class="chinese">专题名称：</div></td>
      <td width="62%"><input name="SpecialName" type="text" class="chinese" id="SpecialName" size="20" maxlength="30">&nbsp;<span class="info1">* 必填</span></td>
    </tr>
    <tr>
      <td height="25"><div align="right" class="chinese">专题说明：</div></td>
      <td><textarea name="SpecialInfo" cols="20" rows="5" class="chinese" id="SpecialInfo" onKeyDown="SpPressKey();"></textarea>&nbsp;<span class="info1">* 必填(可按Ctrl+Enter)提交</span></td>
    </tr>
    <tr>
      <td height="30" colspan="2"><div align="center">
          <input name="Submit3" type="submit" class="button" value="确定">
          <input name="Action" type="hidden" id="Action" value="SaveAdd">
          <input name="Return" type="button" class="button" id="Return5" value="返回" onClick="javascript:history.back();">
      </div></td>
    </tr>
  </table>
</form>
<br><br>
<% } 
if (Action.equals("Edit")) 
{
	String [] sa = aClass.ReadSpecial(SpecialID);
%>
<br><br>
<form action="Admin_Special.jsp" method="POST"  Name="Special" id="Special" onSubmit="return CheckSpecial()">
  <table width="90%"  border="1" align="center" cellpadding="2" cellspacing="0">
    <tr background="images/bg.gif">
      <td height="27" colspan="2" background="images/bg.gif"><div align="center"><span class="title"><strong>修改专题</strong></span></div></td>
    </tr>
    <tr>
      <td width="38%" height="25"><div align="right" class="chinese">专题名称：</div></td>
      <td width="62%"><input name="SpecialName" type="text" class="chinese" id="SpecialName" value="<%=sa[0]%>" size="20" maxlength="30">
          <input name="OldSpecialName" type="hidden" id="OldSpecialName" value="<%=sa[0]%>"><span class="info1">* 必填</span></td>
    </tr>
    <tr>
      <td height="25"><div align="right" class="chinese">专题说明：</div></td>
      <td><textarea name="SpecialInfo" cols="20" rows="5" class="chinese" id="SpecialInfo" onKeyDown="SpPressKey();"><%=sa[1]%></textarea>&nbsp;<span class="info1">* 必填(可按Ctrl+Enter)提交</span></td>
    </tr>
    <tr>
      <td height="30" colspan="2"><div align="center">
          <input name="Submit" type="submit" class="button" value="确定">
          <input name="Action" type="hidden" id="Action" value="SaveEdit">
		  <input name="SpecialID" type="hidden" id="SpecialID" value="<%=SpecialID%>">
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
