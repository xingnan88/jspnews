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
<script src="js/post.js"></script>
<title>����ר�����</title></head>
<% 
	request.setCharacterEncoding("GBK");	 //���ñ��뷽ʽΪGBK	
	String Action = request.getParameter("Action");
	String IP = request.getRemoteAddr();	//�õ��ͻ���Ip��ַ
	String AdminName = (String)session.getAttribute("AdminName"); 
	AdminClass aClass = new AdminClass();
	Function Fun = new Function();
	String s = "";
	String SpecialID   = request.getParameter("SpecialID");
	String SpecialName   = request.getParameter("SpecialName");
	String SpecialInfo = request.getParameter("SpecialInfo");
	if (Action == null || Action.equals("")) Action = "List";
	
	//��ȡ����б�
	if (Action.equals("List")) 
	{ 
		String sOK = aClass.ReadSpecial();
		if (sOK.equals("No"))
		{
			out.println(Fun.OutError("��ȡר���б��������"));
			return;
		}
		else
		{
			out.println(sOK);
		}
	}
	
	//�������ר��
	else if (Action.equals("SaveAdd"))
	{
		s = aClass.AddSpecial(SpecialName,SpecialInfo,AdminName,IP);
		if (s.equals("Yes")) 
			out.print("<script>alert('���ר��ɹ�!');location.href='Admin_Special.jsp?Action=List';</script>");
		else 
		{
			out.print(Fun.OutError(s));
			return;
		}
	}
	
	//�����޸ĵ�ר��
	else if (Action.equals("SaveEdit"))
	{
		String OldSpecialName = request.getParameter("OldSpecialName");
		s = aClass.EditSpecial(SpecialName,SpecialInfo,AdminName,IP,SpecialID,OldSpecialName);
		if (s.equals("Yes")) 
			out.print("<script>alert('�޸�ר��ɹ�!');location.href='Admin_Special.jsp?Action=List';</script>");
		else 
		{
			out.print(Fun.OutError(s));
			return;
		}
	}
	
	//ɾ��С��
	else if (Action.equals("Del"))
	{
		//������ʾ��Ϣ
		String sWarn = "�ò�����ɾ����ר�Ȿ���Լ���ר���µ��������ţ��ò��������ɻָ������Ƿ�ȷ��Ҫɾ����";
		sWarn += "�ò���ֻ����һ��ȷ�ϣ���������������";
		if (request.getParameter("OK") != null && request.getParameter("OK").equals("Yes"))
		{
			if (aClass.DelSpecial(SpecialID,AdminName,IP))
				out.print("<script>alert('ɾ��ר��ɹ�!');location.href='Admin_Special.jsp?Action=List';</script>");
			else 
			{
				out.print(Fun.OutError("ɾ����������"));
				return;
			}
		}		
		else out.print(Fun.OutWarn(sWarn));	
	}

	
if (Action.equals("Add")) { 
%> 
<br><br>
<form action="Admin_Special.jsp" method="POST" Name="Special" id="Special" onSubmit="return CheckSpecial()">
  <table width="90%"  border="1" align="center" cellpadding="2" cellspacing="0">
    <tr background="images/bg.gif">
      <td height="27" colspan="2" background="images/bg.gif">
	  <div align="center"><span class="title"><strong>����ר��</strong></span></div></td>
    </tr>
    <tr>
      <td width="38%" height="25"><div align="right" class="chinese">ר�����ƣ�</div></td>
      <td width="62%"><input name="SpecialName" type="text" class="chinese" id="SpecialName" size="20" maxlength="30">&nbsp;<span class="info1">* ����</span></td>
    </tr>
    <tr>
      <td height="25"><div align="right" class="chinese">ר��˵����</div></td>
      <td><textarea name="SpecialInfo" cols="20" rows="5" class="chinese" id="SpecialInfo" onKeyDown="SpPressKey();"></textarea>&nbsp;<span class="info1">* ����(�ɰ�Ctrl+Enter)�ύ</span></td>
    </tr>
    <tr>
      <td height="30" colspan="2"><div align="center">
          <input name="Submit3" type="submit" class="button" value="ȷ��">
          <input name="Action" type="hidden" id="Action" value="SaveAdd">
          <input name="Return" type="button" class="button" id="Return5" value="����" onClick="javascript:history.back();">
      </div></td>
    </tr>
  </table>
</form>
<br><br>
<% } 
if (Action.equals("Edit")) 
{
	String [] sa = aClass.ReadSpecial(SpecialID);
%>
<br><br>
<form action="Admin_Special.jsp" method="POST"  Name="Special" id="Special" onSubmit="return CheckSpecial()">
  <table width="90%"  border="1" align="center" cellpadding="2" cellspacing="0">
    <tr background="images/bg.gif">
      <td height="27" colspan="2" background="images/bg.gif"><div align="center"><span class="title"><strong>�޸�ר��</strong></span></div></td>
    </tr>
    <tr>
      <td width="38%" height="25"><div align="right" class="chinese">ר�����ƣ�</div></td>
      <td width="62%"><input name="SpecialName" type="text" class="chinese" id="SpecialName" value="<%=sa[0]%>" size="20" maxlength="30">
          <input name="OldSpecialName" type="hidden" id="OldSpecialName" value="<%=sa[0]%>"><span class="info1">* ����</span></td>
    </tr>
    <tr>
      <td height="25"><div align="right" class="chinese">ר��˵����</div></td>
      <td><textarea name="SpecialInfo" cols="20" rows="5" class="chinese" id="SpecialInfo" onKeyDown="SpPressKey();"><%=sa[1]%></textarea>&nbsp;<span class="info1">* ����(�ɰ�Ctrl+Enter)�ύ</span></td>
    </tr>
    <tr>
      <td height="30" colspan="2"><div align="center">
          <input name="Submit" type="submit" class="button" value="ȷ��">
          <input name="Action" type="hidden" id="Action" value="SaveEdit">
		  <input name="SpecialID" type="hidden" id="SpecialID" value="<%=SpecialID%>">
          <input name="Return" type="button" class="button" id="Return" value="����" onClick="javascript:history.back();">
      </div></td>
    </tr>
  </table>
</form>
<br><br>
<% } %>
<br><br>
</body>
</html>
