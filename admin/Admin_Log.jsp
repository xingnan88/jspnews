<%@ page contentType="text/html; charset=GBK" language="java" buffer="32kb"%>
<%@ page import="dreamtime.dreamnews.*"%>
<%@ include file="Session.jsp"%>
<% if (Function1.StrToInt((String)session.getAttribute("AdminType"))!=3) 
	{
	   response.sendError(403,"��ֹ���� ��û�����Ȩ��");
	   return;
	}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link href="css/bodystyle.css" rel="stylesheet" type="text/css">
<script src="js/post.js"></script>
<title>���������б�</title></head>
<body>
<%	
	request.setCharacterEncoding("GBK");	 			//���ñ��뷽ʽΪGBK
	Log log = new Log();
	Function Fun = new Function();
	String Action = request.getParameter("Action");
	String IP = request.getRemoteAddr();				//�õ��ͻ���Ip��ַ
	String AdminName = (String)session.getAttribute("AdminName"); 
	String strPage = request.getParameter("intPage");
	String sPage = request.getContextPath() + request.getServletPath() + "?";
	String sOK = log.ReadLog(sPage,strPage);
	if (Action == null || Action.equals("")) Action = "List";
	if (Action.equals("Del"))
	{
		//������ʾ��Ϣ
		String sWarn = "�Ƿ�ȷ��Ҫɾ��������־��";
		if (request.getParameter("OK") != null && request.getParameter("OK").equals("Yes"))
		{
			String LogID = request.getParameter("LogID");
			if (log.DelLog(LogID,AdminName,IP,false))
				out.print("<script>alert('ɾ����־�ɹ�!');location.href='Admin_Log.jsp';</script>");
			else 
			{
				out.print(Fun.OutError("ɾ����־��������"));
				return;
			}
		}	
		else out.print(Fun.OutWarn(sWarn));	
	}
	else if (Action.equals("DelAll"))
	{
		//������ʾ��Ϣ
		String sWarn = "�Ƿ�ȷ��Ҫɾ��������־���ò��������ɻָ��������������!";
		if (request.getParameter("OK") != null && request.getParameter("OK").equals("Yes"))
		{
			if (log.DelLog(null,AdminName,IP,true))
				out.print("<script>alert('ɾ��������־�ɹ�!');location.href='Admin_Log.jsp';</script>");
			else 
			{
				out.print(Fun.OutError("ɾ��������־��������"));
				return;
			}
		}	
		else out.print(Fun.OutWarn(sWarn));	
	}
	
	else if (sOK.equals("No"))
	{
		out.println(Fun.OutError("��ȡ��־�б��������"));
	}
	else
	{
		out.println(sOK);
	}
%>
<br><br>
</body>
</html>