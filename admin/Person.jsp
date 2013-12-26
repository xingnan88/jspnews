<%@ page contentType="text/html; charset=GBK" language="java" buffer="32kb"%>
<%@ page import="dreamtime.dreamnews.*"%>
<%@ include file="Session.jsp"%>
<html>
<head>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link href="css/bodystyle.css" rel="stylesheet" type="text/css">
<script language="javascript" src="js/post.js"></script>
<title>�û�����</title>
</head>
<body> 
  <%
  	request.setCharacterEncoding("GBK");	 			//���ñ��뷽ʽΪGBK
	User user = new User();
	Function Fun = new Function();
	String Action = request.getParameter("Action");
	String IP = request.getRemoteAddr();				//�õ��ͻ���Ip��ַ
	String AdminName = (String)session.getAttribute("AdminName");
	String  AdminID = (String)session.getAttribute("AdminID");
	String sOK = "";
	if (Action == null || Action.equals("")) Action = "View";
	
	/************************* �鿴��ǰ�������û���Ϣ  ***********************/
	if (Action.equals("View"))
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
	
	/********************** �޸��û����ϵı�  *********************************/
	else if (Action.equals("Edit"))
	{
		sOK = user.EditUser(AdminID,false);
		if(!sOK.equals("No"))
			out.print(sOK);
		else 
		{
			out.println(Fun.OutError("��ȡ������Ա��Ϣ����!"));
			return;
		}
	}
	
	
	/********************** �޸��û����� *********************************/
	else if (Action.equals("SaveEdit"))
	{
		String [] s = new String [13];
		String Year = request.getParameter("Year");
		String Month = request.getParameter("Month");
		String Day = request.getParameter("Day");
		String UserBirthday = Year + "-" + Month + "-" + Day;
		s[0] = AdminID;
		s[1] = null;
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
		
		sOK = user.SaveEdit(s,AdminName,IP,false);
		if (sOK.equals("Yes")) 
			 out.print("<script>alert('�޸ĸ������ϳɹ�!');location.href='Person.jsp';</script>");
		else 
		{
			out.print(Fun.OutError(sOK));
			return;
		}
	}
	
	/********************** �޸����뱣�� *****************************/
	else if (Action.equals("SaveEditPwd"))
	{
		String [] s = new String [5];
		String AdminPwd2 = request.getParameter("AdminPwd2");
		s[0] = request.getParameter("OldPwd");
		s[1] = request.getParameter("AdminPwd");
		s[2] = AdminName;
		s[3] = IP;
		s[4] = AdminID;
		if(AdminPwd2.equals(s[1]))
		{
			sOK = user.SaveEditPwd(s);
			if (sOK.equals("Yes")) 
				 out.print("<script>alert('�޸�����ɹ�!');location.href='Person.jsp';</script>");
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
	
%>
<% if (Action.equals("Mod1")) { %>
<br><br> 
<form action="Person.jsp" method="POST" name="User"  onSubmit="return CheckModPwd()">
<table width="90%"  border="1" align="center" cellpadding="2" cellspacing="0">
          <tr background="images/bg.gif">
            <td height="27" colspan="2" background="images/bg.gif"><div align="center" class="title"><strong>�޸�����</strong></div></td>
          </tr>
          <tr>
            <td width="43%" height="25"><div align="right" class="chinese">������ԭ���룺</div></td>
            <td width="57%"><input name="OldPwd" type="password" class="chinese" id="OldPwd" size="20" maxlength="16"> &nbsp;</td>
          </tr>
          <tr>
            <td height="25"><div align="right" class="chinese">�����������룺</div></td>
            <td><input name="AdminPwd" type="password" class="chinese" id="AdminPwd" size="20" maxlength="16">&nbsp;</td>
          </tr>
          <tr>
            <td height="25"><div align="right" class="chinese">��ȷ�������룺</div></td>
            <td>
              <input name="AdminPwd2" type="password" class="chinese" id="AdminPwd2" size="20" maxlength="16"></td>
          </tr>
          <tr>
            <td height="30" colspan="2"><div align="center">
                <input name="Submit" type="submit" class="button" value="ȷ��">
                <input name="Action" type="hidden" id="Action" value="SaveEditPwd">
                <input name="Return" type="button" class="button" id="Return" value="����" onClick="location.href='Person.jsp';">
            </div></td>
          </tr>
  </table>
</form>
<br><br>
<% } %>
</body>
</html>
