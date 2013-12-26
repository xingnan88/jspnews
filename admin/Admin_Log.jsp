<%@ page contentType="text/html; charset=GBK" language="java" buffer="32kb"%>
<%@ page import="dreamtime.dreamnews.*"%>
<%@ include file="Session.jsp"%>
<% if (Function1.StrToInt((String)session.getAttribute("AdminType"))!=3) 
	{
	   response.sendError(403,"禁止访问 您没有这个权限");
	   return;
	}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link href="css/bodystyle.css" rel="stylesheet" type="text/css">
<script src="js/post.js"></script>
<title>管理新闻列表</title></head>
<body>
<%	
	request.setCharacterEncoding("GBK");	 			//设置编码方式为GBK
	Log log = new Log();
	Function Fun = new Function();
	String Action = request.getParameter("Action");
	String IP = request.getRemoteAddr();				//得到客户端Ip地址
	String AdminName = (String)session.getAttribute("AdminName"); 
	String strPage = request.getParameter("intPage");
	String sPage = request.getContextPath() + request.getServletPath() + "?";
	String sOK = log.ReadLog(sPage,strPage);
	if (Action == null || Action.equals("")) Action = "List";
	if (Action.equals("Del"))
	{
		//给出提示信息
		String sWarn = "是否确定要删除这条日志？";
		if (request.getParameter("OK") != null && request.getParameter("OK").equals("Yes"))
		{
			String LogID = request.getParameter("LogID");
			if (log.DelLog(LogID,AdminName,IP,false))
				out.print("<script>alert('删除日志成功!');location.href='Admin_Log.jsp';</script>");
			else 
			{
				out.print(Fun.OutError("删除日志操作出错！"));
				return;
			}
		}	
		else out.print(Fun.OutWarn(sWarn));	
	}
	else if (Action.equals("DelAll"))
	{
		//给出提示信息
		String sWarn = "是否确定要删除所有日志？该操作将不可恢复，请你谨慎操作!";
		if (request.getParameter("OK") != null && request.getParameter("OK").equals("Yes"))
		{
			if (log.DelLog(null,AdminName,IP,true))
				out.print("<script>alert('删除所有日志成功!');location.href='Admin_Log.jsp';</script>");
			else 
			{
				out.print(Fun.OutError("删除所有日志操作出错！"));
				return;
			}
		}	
		else out.print(Fun.OutWarn(sWarn));	
	}
	
	else if (sOK.equals("No"))
	{
		out.println(Fun.OutError("读取日志列表操作出错！"));
	}
	else
	{
		out.println(sOK);
	}
%>
<br><br>
</body>
</html>