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
	String [] s = con.ReadConfig(true);
	if (Action != null && Action.equals("OK"))
	{
		for(int i=0;i<22;i++)
		{
			s[i] = request.getParameter("Text"+(i+1));
		}
		String IP = request.getRemoteAddr();							//�õ��ͻ���Ip��ַ
		String AdminName = (String)session.getAttribute("AdminName"); 
		String sOK = con.SaveConfig(s,AdminName,IP,true);
		if (sOK.equals("Yes")) 
			out.print("<script>alert('�޸�ϵͳ�����ɹ�!');location.href='Admin_Config.jsp';</script>");
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
      <td height="27" colspan="2" background="images/bg.gif"><div align="center" class="title"><strong>ϵͳ����������</strong></div></td>
    </tr>
    <tr bgcolor="#d6dff7">
      <td height="40" colspan="2" class="title"><div align="center"><br>
          <strong>��̨�������������</strong> </div></td>
    </tr>
    <tr bgcolor="#d6dff7" class="chinese">
      <td width="42%" height="25" class="title"><div align="right" class="chinese">�û�����ÿҳ��ʾ��¼����</div></td>
      <td width="58%" class="chinese"><input name="Text1" type="text" class="chinese" value="<%=s[0]%>" size="10" maxlength="3"> &nbsp;<span class="info1">������Ϊ(1-999)֮���������</span></td>
    </tr>
    <tr bgcolor="#d6dff7" class="chinese">
      <td height="25" class="chinese"><div align="right">��־����ÿҳ��ʾ��¼����</div></td>
      <td class="chinese"><input name="Text2" type="text" class="chinese" value="<%=s[1]%>" size="10" maxlength="3"></td>
    </tr>
    <tr bgcolor="#d6dff7" class="chinese">
      <td height="25" class="chinese"><div align="right">���Ź���ÿҳ��ʾ��¼����</div></td>
      <td class="chinese"><input name="Text3" type="text" class="chinese" value="<%=s[2]%>" size="10" maxlength="3"></td>
    </tr>
    <tr bgcolor="#d6dff7" class="chinese">
      <td height="40" colspan="2" class="chinese"><div align="center"><br><span class="title"><strong>ǰ̨��ʾ����������</strong></span><br></div></td>
    </tr>
    <tr bgcolor="#d6dff7" class="chinese">
      <td height="30" colspan="2" class="chinese"><div align="center" class="info1"><strong>��ҳ����</strong></div></td>
    </tr>
    <tr bgcolor="#d6dff7" class="chinese">
      <td height="25" class="chinese"><div align="right">��ҳ��������������</div></td>
      <td class="chinese"><input name="Text4" type="text" class="chinese" value="<%=s[3]%>" size="10" maxlength="3">&nbsp;</td>
    </tr>
    <tr bgcolor="#d6dff7" class="chinese">
      <td height="25" class="chinese"><div align="right">��ҳͷ������������</div></td>
      <td class="chinese"><input name="Text5" type="text" class="chinese" value="<%=s[4]%>" size="10" maxlength="3">&nbsp;</td>
    </tr>
    <tr bgcolor="#d6dff7" class="chinese">
      <td height="25" class="chinese"><div align="right">��ҳ������������������</div></td>
      <td class="chinese"><input name="Text6" type="text" class="chinese" value="<%=s[5]%>" size="10" maxlength="3">&nbsp;<span class="info1">������Ϊ5�ı�����</span></td>
    </tr>
    <tr bgcolor="#d6dff7" class="chinese">
      <td height="25" class="chinese"><div align="right">��ҳ����ͼƬ����������</div></td>
      <td class="chinese"><input name="Text7" type="text" class="chinese" value="<%=s[6]%>" size="10" maxlength="3">&nbsp;<span class="info1">������Ϊ3�ı�����</span>&nbsp;</td>
    </tr>
    <tr bgcolor="#d6dff7" class="chinese">
      <td height="25" class="chinese"><div align="right">��ҳ���ô�����������������</div></td>
      <td class="chinese"><input name="Text8" type="text" class="chinese" value="<%=s[7]%>" size="10" maxlength="3">&nbsp;<span class="info1">������Ϊ5�ı�����</span></td>
    </tr>
    <tr bgcolor="#d6dff7" class="chinese">
      <td height="25" class="chinese"><div align="right">��ҳ���ô���ͼƬ����������</div></td>
      <td class="chinese"><input name="Text9" type="text" class="chinese" value="<%=s[8]%>" size="10" maxlength="3">&nbsp;<span class="info1">������Ϊ3�ı�����</span></td>
    </tr>
    <tr bgcolor="#d6dff7" class="chinese">
      <td height="25" class="chinese"><div align="right">��ҳ��ʾ��ר������</div></td>
      <td class="chinese"><input name="Text10" type="text" class="chinese" value="<%=s[9]%>" size="10" maxlength="3"></td>
    </tr>
    <tr bgcolor="#d6dff7" class="chinese">
      <td height="25" class="chinese"><div align="right">��ҳ��ʾÿר�����������</div></td>
      <td class="chinese"><input name="Text11" type="text" class="chinese" value="<%=s[10]%>" size="10" maxlength="3"></td>
    </tr>
    <tr bgcolor="#d6dff7" class="chinese">
      <td height="30" colspan="2" class="chinese"><div align="center" class="info1"><strong>һ����������</strong></div></td>
    </tr>
    <tr bgcolor="#d6dff7" class="chinese">
      <td height="25" class="chinese"><div align="right">�����������������</div></td>
      <td class="chinese"><input name="Text12" type="text" class="chinese" value="<%=s[11]%>" size="10" maxlength="3"></td>
    </tr>
    <tr bgcolor="#d6dff7" class="chinese">
      <td height="25" class="chinese"><div align="right">����ͷ����������</div></td>
      <td class="chinese"><input name="Text13" type="text" class="chinese" value="<%=s[12]%>" size="10" maxlength="3"></td>
    </tr>
    <tr bgcolor="#d6dff7" class="chinese">
      <td height="25" class="chinese"><div align="right">������������������</div></td>
      <td class="chinese"><input name="Text14" type="text" class="chinese" value="<%=s[13]%>" size="10" maxlength="3"> &nbsp;<span class="info1">������Ϊ5�ı�����</span></td>
    </tr>
    <tr bgcolor="#d6dff7" class="chinese">
      <td height="25" class="chinese"><div align="right">������������ͼƬ������</div></td>
      <td class="chinese"><input name="Text15" type="text" class="chinese" value="<%=s[14]%>" size="10" maxlength="3">&nbsp;<span class="info1">������Ϊ3�ı�����</span></td>
    </tr>
    <tr bgcolor="#d6dff7" class="chinese">
      <td height="25" class="chinese"><div align="right">�����ȡ����С����ʾ���ŵ�������</div></td>
      <td class="chinese"><input name="Text16" type="text" class="chinese" value="<%=s[15]%>" size="10" maxlength="3">&nbsp;<span class="info1">������Ϊ5�ı�����</span></td>
    </tr>
    <tr bgcolor="#d6dff7" class="chinese">
      <td height="25" class="chinese"><div align="right">������ʾС��ͼƬ��������</div></td>
      <td class="chinese"><input name="Text17" type="text" class="chinese" value="<%=s[16]%>" size="10" maxlength="3">&nbsp;<span class="info1">������Ϊ3�ı�����</span></td>
    </tr>
    <tr bgcolor="#d6dff7" class="chinese">
      <td height="25" class="chinese"><div align="right">������ʾ��ר������</div></td>
      <td class="chinese"><input name="Text18" type="text" class="chinese" value="<%=s[17]%>" size="10" maxlength="3"></td>
    </tr>
    <tr bgcolor="#d6dff7" class="chinese">
      <td height="25" class="chinese"><div align="right">����ר����ʾ����������</div></td>
      <td class="chinese"><input name="Text19" type="text" class="chinese" value="<%=s[18]%>" size="10" maxlength="3"></td>
    </tr>
    <tr bgcolor="#d6dff7" class="chinese">
      <td height="30" colspan="2" class="chinese"><div align="center" class="info1"><strong>��������</strong></div></td>
    </tr>
    <tr bgcolor="#d6dff7" class="chinese">
      <td height="25" class="chinese"><div align="right">ר���б�ÿҳ��ʾ��ר������</div></td>
      <td class="chinese"><input name="Text20" type="text" class="chinese" id="Text20" value="<%=s[19]%>" size="10" maxlength="3"></td>
    </tr>
    <tr bgcolor="#d6dff7" class="chinese">
      <td height="25" class="chinese"><div align="right">С��ÿҳ��ʾ����������</div></td>
      <td class="chinese"><input name="Text21" type="text" class="chinese" value="<%=s[20]%>" size="10" maxlength="3"></td>
    </tr>
    <tr bgcolor="#d6dff7" class="chinese">
      <td height="25" class="chinese"><div align="right">��������ÿҳ��ʾ����������</div></td>
      <td class="chinese"><input name="Text22" type="text" class="chinese" id="Text22" value="<%=s[21]%>" size="10" maxlength="3"></td>
    </tr>
    <tr bgcolor="#d6dff7" class="chinese">
      <td height="25" colspan="2" class="chinese"><div align="right"></div></td>
    </tr>
    <tr bgcolor="#d6dff7">
      <td height="30" colspan="2" valign="middle"><div align="center">
          <input name="Submit" type="submit" class="button" value="�� ��">
          <input name="Action" type="hidden" id="Action" value="OK"> &nbsp;
          <input name="Return" type="button" class="button" value="�� ��" onClick="location.href='Index_Face.html';">
          <br>
      </div></td>
    </tr>
  </table>
</form>
<br><br>
</body>

</html>
