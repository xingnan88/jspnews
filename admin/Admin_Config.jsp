<%@ page contentType="text/html; charset=GBK" language="java" buffer="32kb"%>
<%@ page import="dreamtime.dreamnews.*"%>
<%@ include file="Session.jsp"%>
<% if (Function1.StrToInt((String)session.getAttribute("AdminType"))<2) 
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
</head>

<%
	request.setCharacterEncoding("GBK");	 //设置编码方式为GBK	
	Config con = new Config();
	Function Fun = new Function();
	String Action = request.getParameter("Action");
	String [] s = con.ReadConfig(true);
	if (Action != null && Action.equals("OK"))
	{
		for(int i=0;i<22;i++)
		{
			s[i] = request.getParameter("Text"+(i+1));
		}
		String IP = request.getRemoteAddr();							//得到客户端Ip地址
		String AdminName = (String)session.getAttribute("AdminName"); 
		String sOK = con.SaveConfig(s,AdminName,IP,true);
		if (sOK.equals("Yes")) 
			out.print("<script>alert('修改系统参数成功!');location.href='Admin_Config.jsp';</script>");
		else 
		{
			for(int i=0;i<22;i++)
			{
				out.println(s[i]);
			}
			out.print(Fun.OutError(sOK));
			return;
		}
	}

%>
<body>
<br><br>
<form method="POST" action="Admin_Config.jsp" name="form1">
  <table width="90%"  border="1" align="center" cellpadding="2" cellspacing="1" style="border-collapse:collapse">
    <tr background="images/bg.gif">
      <td height="27" colspan="2" background="images/bg.gif"><div align="center" class="title"><strong>系统参数：设置</strong></div></td>
    </tr>
    <tr bgcolor="#d6dff7">
      <td height="40" colspan="2" class="title"><div align="center"><br>
          <strong>后台管理参数：设置</strong> </div></td>
    </tr>
    <tr bgcolor="#d6dff7" class="chinese">
      <td width="42%" height="25" class="title"><div align="right" class="chinese">用户管理每页显示记录数：</div></td>
      <td width="58%" class="chinese"><input name="Text1" type="text" class="chinese" value="<%=s[0]%>" size="10" maxlength="3"> &nbsp;<span class="info1">参数：为(1-999)之间的整数：</span></td>
    </tr>
    <tr bgcolor="#d6dff7" class="chinese">
      <td height="25" class="chinese"><div align="right">日志管理每页显示记录数：</div></td>
      <td class="chinese"><input name="Text2" type="text" class="chinese" value="<%=s[1]%>" size="10" maxlength="3"></td>
    </tr>
    <tr bgcolor="#d6dff7" class="chinese">
      <td height="25" class="chinese"><div align="right">新闻管理每页显示记录数：</div></td>
      <td class="chinese"><input name="Text3" type="text" class="chinese" value="<%=s[2]%>" size="10" maxlength="3"></td>
    </tr>
    <tr bgcolor="#d6dff7" class="chinese">
      <td height="40" colspan="2" class="chinese"><div align="center"><br><span class="title"><strong>前台显示参数：设置</strong></span><br></div></td>
    </tr>
    <tr bgcolor="#d6dff7" class="chinese">
      <td height="30" colspan="2" class="chinese"><div align="center" class="info1"><strong>首页设置</strong></div></td>
    </tr>
    <tr bgcolor="#d6dff7" class="chinese">
      <td height="25" class="chinese"><div align="right">首页滚动新闻条数：</div></td>
      <td class="chinese"><input name="Text4" type="text" class="chinese" value="<%=s[3]%>" size="10" maxlength="3">&nbsp;</td>
    </tr>
    <tr bgcolor="#d6dff7" class="chinese">
      <td height="25" class="chinese"><div align="right">首页头条新闻条数：</div></td>
      <td class="chinese"><input name="Text5" type="text" class="chinese" value="<%=s[4]%>" size="10" maxlength="3">&nbsp;</td>
    </tr>
    <tr bgcolor="#d6dff7" class="chinese">
      <td height="25" class="chinese"><div align="right">首页最新文字新闻条数：</div></td>
      <td class="chinese"><input name="Text6" type="text" class="chinese" value="<%=s[5]%>" size="10" maxlength="3">&nbsp;<span class="info1">请设置为5的倍数：</span></td>
    </tr>
    <tr bgcolor="#d6dff7" class="chinese">
      <td height="25" class="chinese"><div align="right">首页最新图片新闻条数：</div></td>
      <td class="chinese"><input name="Text7" type="text" class="chinese" value="<%=s[6]%>" size="10" maxlength="3">&nbsp;<span class="info1">请设置为3的倍数：</span>&nbsp;</td>
    </tr>
    <tr bgcolor="#d6dff7" class="chinese">
      <td height="25" class="chinese"><div align="right">首页调用大类文字新闻条数：</div></td>
      <td class="chinese"><input name="Text8" type="text" class="chinese" value="<%=s[7]%>" size="10" maxlength="3">&nbsp;<span class="info1">请设置为5的倍数：</span></td>
    </tr>
    <tr bgcolor="#d6dff7" class="chinese">
      <td height="25" class="chinese"><div align="right">首页调用大类图片新闻条数：</div></td>
      <td class="chinese"><input name="Text9" type="text" class="chinese" value="<%=s[8]%>" size="10" maxlength="3">&nbsp;<span class="info1">请设置为3的倍数：</span></td>
    </tr>
    <tr bgcolor="#d6dff7" class="chinese">
      <td height="25" class="chinese"><div align="right">首页显示的专题数：</div></td>
      <td class="chinese"><input name="Text10" type="text" class="chinese" value="<%=s[9]%>" size="10" maxlength="3"></td>
    </tr>
    <tr bgcolor="#d6dff7" class="chinese">
      <td height="25" class="chinese"><div align="right">首页显示每专题的新闻数：</div></td>
      <td class="chinese"><input name="Text11" type="text" class="chinese" value="<%=s[10]%>" size="10" maxlength="3"></td>
    </tr>
    <tr bgcolor="#d6dff7" class="chinese">
      <td height="30" colspan="2" class="chinese"><div align="center" class="info1"><strong>一级分类设置</strong></div></td>
    </tr>
    <tr bgcolor="#d6dff7" class="chinese">
      <td height="25" class="chinese"><div align="right">大类滚动新闻条数：</div></td>
      <td class="chinese"><input name="Text12" type="text" class="chinese" value="<%=s[11]%>" size="10" maxlength="3"></td>
    </tr>
    <tr bgcolor="#d6dff7" class="chinese">
      <td height="25" class="chinese"><div align="right">大类头条新闻数：</div></td>
      <td class="chinese"><input name="Text13" type="text" class="chinese" value="<%=s[12]%>" size="10" maxlength="3"></td>
    </tr>
    <tr bgcolor="#d6dff7" class="chinese">
      <td height="25" class="chinese"><div align="right">大类最新新闻条数：</div></td>
      <td class="chinese"><input name="Text14" type="text" class="chinese" value="<%=s[13]%>" size="10" maxlength="3"> &nbsp;<span class="info1">请设置为5的倍数：</span></td>
    </tr>
    <tr bgcolor="#d6dff7" class="chinese">
      <td height="25" class="chinese"><div align="right">大类最新新闻图片条数：</div></td>
      <td class="chinese"><input name="Text15" type="text" class="chinese" value="<%=s[14]%>" size="10" maxlength="3">&nbsp;<span class="info1">请设置为3的倍数：</span></td>
    </tr>
    <tr bgcolor="#d6dff7" class="chinese">
      <td height="25" class="chinese"><div align="right">大类读取新闻小类显示新闻的条数：</div></td>
      <td class="chinese"><input name="Text16" type="text" class="chinese" value="<%=s[15]%>" size="10" maxlength="3">&nbsp;<span class="info1">请设置为5的倍数：</span></td>
    </tr>
    <tr bgcolor="#d6dff7" class="chinese">
      <td height="25" class="chinese"><div align="right">大类显示小类图片新闻数：</div></td>
      <td class="chinese"><input name="Text17" type="text" class="chinese" value="<%=s[16]%>" size="10" maxlength="3">&nbsp;<span class="info1">请设置为3的倍数：</span></td>
    </tr>
    <tr bgcolor="#d6dff7" class="chinese">
      <td height="25" class="chinese"><div align="right">大类显示的专题数：</div></td>
      <td class="chinese"><input name="Text18" type="text" class="chinese" value="<%=s[17]%>" size="10" maxlength="3"></td>
    </tr>
    <tr bgcolor="#d6dff7" class="chinese">
      <td height="25" class="chinese"><div align="right">大类专题显示新闻条数：</div></td>
      <td class="chinese"><input name="Text19" type="text" class="chinese" value="<%=s[18]%>" size="10" maxlength="3"></td>
    </tr>
    <tr bgcolor="#d6dff7" class="chinese">
      <td height="30" colspan="2" class="chinese"><div align="center" class="info1"><strong>其它设置</strong></div></td>
    </tr>
    <tr bgcolor="#d6dff7" class="chinese">
      <td height="25" class="chinese"><div align="right">专题列表每页显示的专题数：</div></td>
      <td class="chinese"><input name="Text20" type="text" class="chinese" id="Text20" value="<%=s[19]%>" size="10" maxlength="3"></td>
    </tr>
    <tr bgcolor="#d6dff7" class="chinese">
      <td height="25" class="chinese"><div align="right">小类每页显示的新闻数：</div></td>
      <td class="chinese"><input name="Text21" type="text" class="chinese" value="<%=s[20]%>" size="10" maxlength="3"></td>
    </tr>
    <tr bgcolor="#d6dff7" class="chinese">
      <td height="25" class="chinese"><div align="right">新闻搜索每页显示的新闻数：</div></td>
      <td class="chinese"><input name="Text22" type="text" class="chinese" id="Text22" value="<%=s[21]%>" size="10" maxlength="3"></td>
    </tr>
    <tr bgcolor="#d6dff7" class="chinese">
      <td height="25" colspan="2" class="chinese"><div align="right"></div></td>
    </tr>
    <tr bgcolor="#d6dff7">
      <td height="30" colspan="2" valign="middle"><div align="center">
          <input name="Submit" type="submit" class="button" value="提 交">
          <input name="Action" type="hidden" id="Action" value="OK"> &nbsp;
          <input name="Return" type="button" class="button" value="返 回" onClick="location.href='Index_Face.html';">
          <br>
      </div></td>
    </tr>
  </table>
</form>
<br><br>
</body>

</html>
