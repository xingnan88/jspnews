<%@ page contentType="text/html; charset=GBK" language="java" buffer="32kb"%>
<%@ page import="javax.imageio.*"%>
<%@ page import="dreamtime.dreamnews.*"%>
<html>
<head>
<title>管理员登录</title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  
<SCRIPT src="js/post.js"></SCRIPT> 
<link href="css/bodystyle.css" rel="stylesheet" type="text/css">
</head>
<%
	response.setHeader("Pragma","No-cache");
	response.setHeader("Cache-Control","no-cache");
	response.setDateHeader("Expires", 0);
	request.setCharacterEncoding("GBK");						     //设置编码方式为gb2312
	//处理表单，并进行异常处理
	String Action = request.getParameter("Action");
	if(Action!=null && Action.equals("Login"))
	{
		String Page1  = (String)request.getHeader("Referer");		//得到页面地址来源
		String Page2  = request.getRequestURL().toString();			//得到当前页面地址
		String methon = request.getMethod();
		if (methon.equals("POST") && Page2.equals(Page1))
		{
			try{
				Function Fun = new Function();
				Login login = new Login();
				String IP = request.getRemoteAddr();			//得到客户端Ip地址
				String User = request.getParameter("User");     //得到登录用户名
				String Pwd = request.getParameter("Pwd");       //得到登录密码
				String sCode = Fun.CheckReplace(request.getParameter("VerifyCode"));
				if(!sCode.equals(session.getAttribute("VerifyCode")))
				{
					session.setAttribute("error","<li>验证码错误");
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
					out.println("<SCRIPT LANGUAGE='JavaScript'>alert('登录成功!');location.href='Admin_Main.jsp';</SCRIPT>");
					return;
				}
				else 
				{
					session.setAttribute("error","<li>用户名或密码错误");
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
			response.sendError(403,"禁止访问");
			//out.print("出错" + Page1 + ";" + Page2);
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
                <legend align="left" class="title" accesskey="F"><strong>登录窗口</strong></legend>
                <table width="100%" border="0" cellspacing="2" cellpadding="2">
                <tr> 
				  <td width="8%">&nbsp;</td>
                        <td width="17%" class="chinese">用户名：</td>
                  <td width="75%"><input name="User" type="text" class="chinese" id="User" style="font-size: 12px" size="16" maxlength="16">
				  </td>
                </tr>
                <tr> 
				<td width="8%">&nbsp;</td>
                        <td width="17%" class="chinese">密　码：</td>
                  <td><input name="Pwd" type="password" class="chinese" id="Pwd" style="font-size: 12px" size="16" maxlength="16">
                  </td>
                </tr>
			    <tr>
				  <td width="8%">&nbsp;</td>
                        <td class="chinese">验证码：</td>
                        <td><input name="VerifyCode" type="text" class="chinese" id="VerifyCode" style="font-size: 12px" size="6" maxlength="6"> 
                          <span class="info1">&nbsp;<img src="VerifyCode.jsp">
                        (区分大小写)</span></td>
                </tr>
                <tr>
                  <td colspan="3" align="center"><div class="info1">
				  <%=((String)session.getAttribute("error")==null)? "" : (String)session.getAttribute("error")%></div></td>
                </tr>
                <tr> 
                  <td colspan="3" align="center" height="30"><input type="submit" name="submit" value=" 登 录 " class="button"> 
                    <input name="Action" type="hidden" id="Action" value="Login">
                    <input type="reset" name="submit2" value=" 清 除 " class="button"></td></tr>
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
