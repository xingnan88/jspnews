<%@ page contentType="text/html; charset=GBK" language="java" buffer="32kb"%>
<%@ page import="dreamtime.dreamnews.*"%>
<%@ include file="Session.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link href="css/bodystyle.css" rel="stylesheet" type="text/css">
<script src="js/post.js"></script>
<script src="js/news.js"></script>
<title>���������б�</title></head>
<body>
<%	
	request.setCharacterEncoding("GBK");	 			//���ñ��뷽ʽΪGBK
	News news = new News();
	Function Fun = new Function();
	String strPage = request.getParameter("intPage");
	String sPage = request.getContextPath() + request.getServletPath() + "?";
	String sOK = news.ListNews(sPage,strPage);
	if (sOK.equals("No"))
	{
		out.println(Fun.OutError("��ȡ�����б��������"));
	}
	else
	{
		out.println(sOK);
	}
%>
<br><br>
</body>
</html>