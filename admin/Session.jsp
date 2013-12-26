<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<%@ page contentType="text/html; charset=GBK" language="java" buffer="32kb"%>
<%@ page import="dreamtime.dreamnews.*"%>
<%
	Function Function1 = new Function(); 
	boolean isLogin = Function1.StringToBoolean((String)session.getAttribute("Login"));
	if(!isLogin) 
	{
		response.sendError(403,"禁止访问");
		return;
	}
%>