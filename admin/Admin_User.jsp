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
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link href="css/bodystyle.css" rel="stylesheet" type="text/css">
<title>用户管理</title>
<script src="js/post.js"></script>
</head>
<body> 
<%
  	request.setCharacterEncoding("GBK");	 			//设置编码方式为GBK
	User user = new User();								
	Function Fun = new Function();
	String Action = request.getParameter("Action");		//得到提交参数
	String IP = request.getRemoteAddr();				//得到客户端Ip地址
	String AdminName = (String)session.getAttribute("AdminName"); 
	String sOK = "";
	String AdminID = request.getParameter("AdminID");
	
	//显示用户列表
	if (Action == null || Action.equals("")) Action = "List";
	
	//添加用户表单
	if (Action.equals("Add")) 
	{
		out.print(user.AddUser()); 
	}
	
	/********************** 修改用户资料的表单  *********************************/
	else if (Action.equals("Edit"))
	{
		sOK = user.EditUser(AdminID,true);
		if(!sOK.equals("No"))
			out.print(sOK);
		else 
		{
			out.println(Fun.OutError("读取管理理员信息出错!"));
			return;
		}
	}
	
	/********************** 查看管理员信息 *********************************/
	else if (Action.equals("View"))
	{
		sOK = user.ViewUser(AdminID);
		if(!sOK.equals("No"))
			out.print(sOK);
		else 
		{
			out.println(Fun.OutError("读取管理理员信息出错!"));
			return;
		}
	}
	
	/********************** 添加用户保存 *********************************/
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
				 out.print("<script>alert('添加管理员用户成功!');location.href='Admin_User.jsp';</script>");
			else 
			{
				out.print(Fun.OutError(sOK));
				return;
			}
		}
		else  
		{
			out.print(Fun.OutError("您两次输入的密码不一样，请重试!"));
			return;
		}
	}
	
	/********************** 修改用户保存 *********************************/
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
				 out.print("<script>alert('修改管理员用户资料成功!');location.href='Admin_User.jsp';</script>");
			else 
			{
				out.print(Fun.OutError(sOK));
				return;
			}
		}
		else  
		{
			out.print(Fun.OutError("您两次输入的密码不一样，请重试!"));
			return;
		}
	}

	/**********************  删除用户 *********************************/
	else if (Action.equals("Del"))
	{
		//给出提示信息
		String sWarn = "是否确定要删除该管理员？";
		if (request.getParameter("OK") != null && request.getParameter("OK").equals("Yes"))
		{
			String s = request.getParameter("AdminID");
			if (user.Del(s,AdminName,IP))
				out.print("<script>alert('删除管理员用户成功!');location.href='Admin_User.jsp';</script>");
			else 
			{
				out.print(Fun.OutError("删除管理员用户操作出错！"));
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
			out.println(Fun.OutError("读取管理员用户列表操作出错！"));
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
