<%@ page contentType="text/html; charset=GBK" language="java" buffer="32kb"%>
<%@ page import="javax.imageio.*"%>
<%@ page import="dreamtime.dreamnews.*"%>
<html>
<head>
<title>����Ա��¼</title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  
<SCRIPT src="js/post.js"></SCRIPT> 
<link href="css/bodystyle.css" rel="stylesheet" type="text/css">
</head>
<%
	response.setHeader("Pragma","No-cache");
	response.setHeader("Cache-Control","no-cache");
	response.setDateHeader("Expires", 0);
	request.setCharacterEncoding("GBK");						     //���ñ��뷽ʽΪgb2312
	//��������������쳣����
	String Action = request.getParameter("Action");
	if(Action!=null && Action.equals("Login"))
	{
		String Page1  = (String)request.getHeader("Referer");		//�õ�ҳ���ַ��Դ
		String Page2  = request.getRequestURL().toString();			//�õ���ǰҳ���ַ
		String methon = request.getMethod();
		if (methon.equals("POST") && Page2.equals(Page1))
		{
			try{
				Function Fun = new Function();
				Login login = new Login();
				String IP = request.getRemoteAddr();			//�õ��ͻ���Ip��ַ
				String User = request.getParameter("User");     //�õ���¼�û���
				String Pwd = request.getParameter("Pwd");       //�õ���¼����
				String sCode = Fun.CheckReplace(request.getParameter("VerifyCode"));
				if(!sCode.equals(session.getAttribute("VerifyCode")))
				{
					session.setAttribute("error","<li>��֤�����");
					response.sendRedirect("index.jsp");
					return;
				}
				if (login.LoginCheck(User,Pwd,IP))
				{
					session.setAttribute("Login","Yes");
					session.setAttribute("AdminID",Integer.toString(login.AdminID));
					session.setAttribute("AdminType",Integer.toString(login.AdminType));
					session.setAttribute("AdminName",User);
					session.setAttribute("error","");
					out.println("<SCRIPT LANGUAGE='JavaScript'>alert('��¼�ɹ�!');location.href='Admin_Main.jsp';</SCRIPT>");
					return;
				}
				else 
				{
					session.setAttribute("error","<li>�û������������");
					response.sendRedirect("index.jsp");
					return;
				}
			}catch(Exception e){
					response.sendRedirect("index.jsp");
					//out.println(e.getMessage());
					session.setAttribute("error","");
					return;
					}
		}
		else
		{
			response.sendError(403,"��ֹ����");
			//out.print("����" + Page1 + ";" + Page2);
			return;
		}
	}
%>
<body>
<form name="form" action="index.jsp" method="POST" onSubmit="return LoginCheck()">
  <p>&nbsp;</p>
  <p>&nbsp;</p>
  <table width="413" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#EEEAD6">
    <tr>
      <td height="29" colspan="3" background="images/topbg.gif">&nbsp;	  </td>
    </tr>
    <tr>
      <td width="3" background="images/link.gif"></td>
      <td>
	  <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td height="75" background="images/topbg.gif">
			<table width="100%" height="75" border="0" cellpadding="0" cellspacing="0">
          <tr>
		    <td width="30%" align="left" valign="bottom"><img src="images/xpbg.gif" width="411" height="74"></td>
		  </tr>
	  </table>
			</td>
          </tr>
          <tr>
            <td>
			<table width="95%" border="0" align="center">
	      <tr>
		    <td>
	  <fieldset>
                <legend align="left" class="title" accesskey="F"><strong>��¼����</strong></legend>
                <table width="100%" border="0" cellspacing="2" cellpadding="2">
                <tr> 
				  <td width="8%">&nbsp;</td>
                        <td width="17%" class="chinese">�û�����</td>
                  <td width="75%"><input name="User" type="text" class="chinese" id="User" style="font-size: 12px" size="16" maxlength="16">
				  </td>
                </tr>
                <tr> 
				<td width="8%">&nbsp;</td>
                        <td width="17%" class="chinese">�ܡ��룺</td>
                  <td><input name="Pwd" type="password" class="chinese" id="Pwd" style="font-size: 12px" size="16" maxlength="16">
                  </td>
                </tr>
			    <tr>
				  <td width="8%">&nbsp;</td>
                        <td class="chinese">��֤�룺</td>
                        <td><input name="VerifyCode" type="text" class="chinese" id="VerifyCode" style="font-size: 12px" size="6" maxlength="6"> 
                          <span class="info1">&nbsp;<img src="VerifyCode.jsp">
                        (���ִ�Сд)</span></td>
                </tr>
                <tr>
                  <td colspan="3" align="center"><div class="info1">
				  <%=((String)session.getAttribute("error")==null)? "" : (String)session.getAttribute("error")%></div></td>
                </tr>
                <tr> 
                  <td colspan="3" align="center" height="30"><input type="submit" name="submit" value=" �� ¼ " class="button"> 
                    <input name="Action" type="hidden" id="Action" value="Login">
                    <input type="reset" name="submit2" value=" �� �� " class="button"></td></tr>
              </table>
	  </fieldset> 
	  &nbsp;</td></tr>
	  </table></td>
          </tr>
        </table></td>
      <td width="3" background="images/link.gif"></td>
    </tr>
	<tr><td height="3" background="images/linkbom.gif" colspan="3"></td></tr>
</table>
  <p>&nbsp;</p>
  <p>&nbsp;</p>
</form>
</body>
</html>
