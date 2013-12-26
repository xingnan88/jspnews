<%@ page contentType="text/html; charset=GBK" language="java" buffer="32kb"%>
<%@ page import="dreamtime.dreamnews.*"%>
<%@ include file="Session.jsp"%>
<link href="css/bodystyle.css" rel="stylesheet" type="text/css">
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<%
	AdminClass aClass = new AdminClass();
	News News1 = new News();
	Function Fun = new Function();
	String IP = request.getRemoteAddr();		//得到客户端Ip地址
	String AdminName = (String)session.getAttribute("AdminName"); 
	String NewsID = request.getParameter("NewsID");
	String sWarn = "你是否确定要删除该新闻？该操作不可恢复，请您谨慎操作！";
	String OK = request.getParameter("OK");
	if (OK != null && OK.equals("Yes"))
	{
		if(News1.DelNews(NewsID,AdminName,IP))
			out.print("<script>alert('删除新闻成功!');location.href='ListNews.jsp';</script>");
		else 
		{
			out.print(Fun.OutError("删除新闻操作出错，靖勿传递非法参数！"));
			return;
		}
	}
	else out.print(Fun.OutWarn(sWarn));
%>
