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
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link href="css/bodystyle.css" rel="stylesheet" type="text/css">
<title>�û�����</title>
<script src="js/post.js"></script>
</head>
<body> 
<%
  	request.setCharacterEncoding("GBK");	 			//���ñ��뷽ʽΪGBK
	User user = new User();								
	Function Fun = new Function();
	String Action = request.getParameter("Action");		//�õ��ύ����
	String IP = request.getRemoteAddr();				//�õ��ͻ���Ip��ַ
	String AdminName = (String)session.getAttribute("AdminName"); 
	String sOK = "";
	String AdminID = request.getParameter("AdminID");
	
	//��ʾ�û��б�
	if (Action == null || Action.equals("")) Action = "List";
	
	//����û���
	if (Action.equals("Add")) 
	{
		out.print(user.AddUser()); 
	}
	
	/********************** �޸��û����ϵı�  *********************************/
	else if (Action.equals("Edit"))
	{
		sOK = user.EditUser(AdminID,true);
		if(!sOK.equals("No"))
			out.print(sOK);
		else 
		{
			out.println(Fun.OutError("��ȡ������Ա��Ϣ����!"));
			return;
		}
	}
	
	/********************** �鿴����Ա��Ϣ *********************************/
	else if (Action.equals("View"))
	{
		sOK = user.ViewUser(AdminID);
		if(!sOK.equals("No"))
			out.print(sOK);
		else 
		{
			out.println(Fun.OutError("��ȡ������Ա��Ϣ����!"));
			return;
		}
	}
	
	/********************** ����û����� *********************************/
	else if (Action.equals("SaveAdd"))
	{
		String [] s = new String [13];
		String AdminPwd2 = request.getParameter("AdminPwd2");
		String Year = request.getParameter("Year");
		String Month = request.getParameter("Month");
		String Day = request.getParameter("Day");
		String UserBirthday = Year + "-" + Month + "-" + Day;
		s[0] = request.getParameter("AdminName");
		s[1] = request.getParameter("AdminPwd");
		s[2] = request.getParameter("AdminType");
		s[3] = (new java.util.Date()).toLocaleString();
		s[4] = request.getParameter("UserName");
		s[5] = request.getParameter("UserSex");
		s[6] = UserBirthday;
		s[7] = request.getParameter("UserEmail");
		s[8] = request.getParameter("UserQQ");
		s[9] = request.getParameter("UserTel");
		s[10] = request.getParameter("UserAddress");
		s[11] = request.getParameter("UserZip");
		s[12] = request.getParameter("UserInfo");
		if(s[1].equals(AdminPwd2))
		{
			sOK = user.SaveAdd(s,AdminName,IP);
			if (sOK.equals("Yes")) 
				 out.print("<script>alert('��ӹ���Ա�û��ɹ�!');location.href='Admin_User.jsp';</script>");
			else 
			{
				out.print(Fun.OutError(sOK));
				return;
			}
		}
		else  
		{
			out.print(Fun.OutError("��������������벻һ����������!"));
			return;
		}
	}
	
	/********************** �޸��û����� *********************************/
	else if (Action.equals("SaveEdit"))
	{
		String [] s = new String [13];
		String AdminPwd2 = request.getParameter("AdminPwd2");
		String Year = request.getParameter("Year");
		String Month = request.getParameter("Month");
		String Day = request.getParameter("Day");
		String UserBirthday = Year + "-" + Month + "-" + Day;
		s[0] = request.getParameter("AdminID");
		s[1] = request.getParameter("AdminPwd");
		s[2] = request.getParameter("AdminType");
		s[3] = (new java.util.Date()).toLocaleString();
		s[4] = request.getParameter("UserName");
		s[5] = request.getParameter("UserSex");
		s[6] = UserBirthday;
		s[7] = request.getParameter("UserEmail");
		s[8] = request.getParameter("UserQQ");
		s[9] = request.getParameter("UserTel");
		s[10] = request.getParameter("UserAddress");
		s[11] = request.getParameter("UserZip");
		s[12] = request.getParameter("UserInfo");
		if(s[1].equals(AdminPwd2))
		{
			sOK = user.SaveEdit(s,AdminName,IP,true);
			if (sOK.equals("Yes")) 
				 out.print("<script>alert('�޸Ĺ���Ա�û����ϳɹ�!');location.href='Admin_User.jsp';</script>");
			else 
			{
				out.print(Fun.OutError(sOK));
				return;
			}
		}
		else  
		{
			out.print(Fun.OutError("��������������벻һ����������!"));
			return;
		}
	}

	/**********************  ɾ���û� *********************************/
	else if (Action.equals("Del"))
	{
		//������ʾ��Ϣ
		String sWarn = "�Ƿ�ȷ��Ҫɾ���ù���Ա��";
		if (request.getParameter("OK") != null && request.getParameter("OK").equals("Yes"))
		{
			String s = request.getParameter("AdminID");
			if (user.Del(s,AdminName,IP))
				out.print("<script>alert('ɾ������Ա�û��ɹ�!');location.href='Admin_User.jsp';</script>");
			else 
			{
				out.print(Fun.OutError("ɾ������Ա�û���������"));
				return;
			}
		}	
		else out.print(Fun.OutWarn(sWarn));		
	}
	else
	{
		String strPage = request.getParameter("intPage");
		String sPage = request.getContextPath() + request.getServletPath() + "?";
		sOK = user.UserList(sPage,strPage);
		if (sOK.equals("No"))
		{
			out.println(Fun.OutError("��ȡ����Ա�û��б��������"));
			return;
		}
		else
		{
			out.println(sOK);
		}
	}
	/**********************  End *********************************/
	
%>
</body>
</html>
