<%@ page contentType="text/html; charset=GBK" language="java" buffer="32kb"%>
<%@ page import="dreamtime.dreamnews.*"%>
<%@ include file="Session.jsp"%>
<% if (Function1.StrToInt((String)session.getAttribute("AdminType"))<2) 
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
</head>
<%
	request.setCharacterEncoding("GBK");	 //���ñ��뷽ʽΪGBK	
	Config con = new Config();
	Function Fun = new Function();
	String Action = request.getParameter("Action");
	String [] s = con.ReadConfig(false);
	if (Action != null && Action.equals("OK"))
	{
		for(int i=0;i<3;i++)
		{
			s[i] = request.getParameter("Text"+(i+1));
		}
		String IP = request.getRemoteAddr();							//�õ��ͻ���Ip��ַ
		String AdminName = (String)session.getAttribute("AdminName"); 
		String sOK = con.SaveConfig(s,AdminName,IP,false);
		if (sOK.equals("Yes")) 
			out.print("<script>alert('�޸Ļ�����Ϣ�ɹ�!');location.href='Admin_Info.jsp';</script>");
		else 
		{
			out.print(Fun.OutError(sOK));
			return;
		}
	}
%>
<body>
<br><br>
<form method="POST" action="Admin_Info.jsp">
  <table width="90%"  border="1" align="center" cellpadding="2" cellspacing="1" style="border-collapse:collapse">
    <tr background="images/bg.gif">
      <td height="27" colspan="2"  background="images/bg.gif"><div align="center" class="title"><strong>��վ������Ϣ����</strong></div></td>
    </tr>
    <tr>
      <td width="40%" class="chinese"><div align="right" class="chinese"><strong>��վ���ƣ�</strong></div></td>
      <td width="60%"><input name="Text1" type="text" class="chinese" id="Text1" value="<%=s[0]%>" size="20" maxlength="30"></td>
    </tr>
    <tr>
      <td><div align="right" class="chinese"><strong><strong>��Ȩ��Ϣ</strong>��</strong></div></td>
      <td height="25"><input name="Text2" type="text" class="chinese" id="Text2" value="<%=s[1]%>" size="20" maxlength="30"></td>
    </tr>
    <tr>
      <td><div align="right" class="chinese"><strong>�����ʼ���</strong></div>
        </div></td>
      <td><input name="Text3" type="text" class="chinese" id="Text3" value="<%=s[2]%>" size="20" maxlength="30"></td>
    </tr>
    <tr>
      <td height="30" colspan="2" valign="middle"><div align="center">
          <input type="submit" name="Submit" value=" �� �� " class="button">
          <input name="Action" type="hidden" id="Action" value="OK">&nbsp;
          <input name="Return" type="button" class="button" id="Return" value=" �� �� " onClick="location.href='Index_Face.html';">
      </div></td>
    </tr>
  </table>
</form>
</body>
</html>
