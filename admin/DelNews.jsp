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
	String IP = request.getRemoteAddr();		//�õ��ͻ���Ip��ַ
	String AdminName = (String)session.getAttribute("AdminName"); 
	String NewsID = request.getParameter("NewsID");
	String sWarn = "���Ƿ�ȷ��Ҫɾ�������ţ��ò������ɻָ�����������������";
	String OK = request.getParameter("OK");
	if (OK != null && OK.equals("Yes"))
	{
		if(News1.DelNews(NewsID,AdminName,IP))
			out.print("<script>alert('ɾ�����ųɹ�!');location.href='ListNews.jsp';</script>");
		else 
		{
			out.print(Fun.OutError("ɾ�����Ų����������𴫵ݷǷ�������"));
			return;
		}
	}
	else out.print(Fun.OutWarn(sWarn));
%>
