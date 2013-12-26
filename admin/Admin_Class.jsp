<%@ page contentType="text/html; charset=GBK" language="java" buffer="32kb"%>
<%@ page import="dreamtime.dreamnews.*"%>
<%@ include file="Session.jsp"%>
<% if (Function1.StrToInt((String)session.getAttribute("AdminType"))<2) 
	{
	   response.sendError(403,"��ֹ����");
	   return;
	}
%>
<html>
<head>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link href="css/bodystyle.css" rel="stylesheet" type="text/css">
<script src="js/post.js"></script>
<title>����������</title></head>
<% 
	request.setCharacterEncoding("GBK");	 //���ñ��뷽ʽΪGBK	
	String Action = request.getParameter("Action");
	String IP = request.getRemoteAddr();	//�õ��ͻ���Ip��ַ
	String AdminName = (String)session.getAttribute("AdminName"); 
	AdminClass aClass = new AdminClass();
	Function Fun = new Function();
	String s = "";
	String [][] sArray = aClass.GetAllClass(true,false,null);
	String BigClassID   = request.getParameter("BigClassID");
	String BigClassName = request.getParameter("BigClassName");
	String BigClassInfo = request.getParameter("BigClassInfo");
	String SClassID   = request.getParameter("SClassID");
	String SClassName = request.getParameter("SClassName");
	String SClassInfo = request.getParameter("SClassInfo");
	String strPage = request.getParameter("intPage");
	if (Action == null || Action.equals("")) Action = "List";
	
	//��ȡ����б�
	if (Action.equals("List")) 
	{ 
		String sPage = request.getContextPath() + request.getServletPath();
		String sOK = aClass.ReadClass();
		if (sOK.equals("No"))
		{
			out.println(Fun.OutError("��ȡ����б��������"));
			return;
		}
		else
		{
			out.println(sOK);
		}
	}
	
	
	//������ӵĴ���
	else if (Action.equals("SaveAddBigClass"))
	{
		s = aClass.AddBigClass(BigClassID,BigClassName,BigClassInfo,AdminName,IP);
		if (s.equals("Yes")) 
			out.print("<script>alert('��������ɹ�!');location.href='Admin_Class.jsp?Action=List';</script>");
		else 
		{
			out.print(Fun.OutError(s));
			return;
		}
	}
	
	//�����޸ĵĴ���
	else if (Action.equals("SaveEditBigClass"))
	{	
		String OldBigClassID   = request.getParameter("OldBigClassID");
		String OldBigClassName = request.getParameter("OldBigClassName");
		s = aClass.EditBigClass(BigClassID,BigClassName,BigClassInfo,OldBigClassID,OldBigClassName,AdminName,IP);
		if (s.equals("Yes")) 
			out.print("<script>alert('�޸Ĵ���ɹ�!');location.href='Admin_Class.jsp?Action=List';</script>");
			//response.sendRedirect("Admin_Class.jsp?Action=List");
		else 
		{
			out.print(Fun.OutError(s));
			return;
		}
	}
	
	//ɾ������
	else if (Action.equals("DelBig"))
	{
		//������ʾ��Ϣ
		String sWarn = "�ò�����ɾ���ô��౾���ô����µ�����С���Լ��ô����µ��������ţ��ò��������ɻָ������Ƿ�ȷ��Ҫɾ����";
		sWarn += "�ò���ֻ����һ��ȷ�ϣ���������������";
		if (request.getParameter("OK") != null && request.getParameter("OK").equals("Yes"))
		{
			if (aClass.DelBigClass(BigClassID,AdminName,IP))
				out.print("<script>alert('ɾ������ɹ�!');location.href='Admin_Class.jsp?Action=List';</script>");
			else 
			{
				out.print(Fun.OutError("ɾ����������"));
				return;
			}
		}	
		else out.print(Fun.OutWarn(sWarn));			
	}
	
	//�������С��
	else if (Action.equals("SaveAddSClass"))
	{
		s = aClass.AddSClass(SClassName,SClassInfo,BigClassID,AdminName,IP);
		if (s.equals("Yes")) 
			out.print("<script>alert('����С��ɹ�!');location.href='Admin_Class.jsp?Action=List';</script>");
		else 
		{
			out.print(Fun.OutError(s));
			return;
		}
	}
	
	//�����޸ĵ�С��
	else if (Action.equals("SaveEditSClass"))
	{
		String OldSmallClassName = request.getParameter("OldSmallClassName");
		s = aClass.EditSClass(BigClassID,SClassID,SClassName,SClassInfo,OldSmallClassName,AdminName,IP);
		if (s.equals("Yes")) 
			out.print("<script>alert('�޸�С��ɹ�!');location.href='Admin_Class.jsp?Action=List';</script>");
		else 
		{
			out.print(Fun.OutError(s));
			return;
		}
	}
	
	//ɾ��С��
	else if (Action.equals("DelSmall"))
	{
		//������ʾ��Ϣ
		String sWarn = "�ò�����ɾ����С�౾����С���µ��������ţ��ò��������ɻָ������Ƿ�ȷ��Ҫɾ����";
		sWarn += "�ò���ֻ����һ��ȷ�ϣ���������������";
		if (request.getParameter("OK") != null && request.getParameter("OK").equals("Yes"))
		{
			if (aClass.DelSClass(SClassID,AdminName,IP))
				out.print("<script>alert('ɾ��С��ɹ�!');location.href='Admin_Class.jsp?Action=List';</script>");
			else 
			{
				out.print(Fun.OutError("ɾ����������"));
				return;
			}
		}		
		else out.print(Fun.OutWarn(sWarn));	
	}

	
if (Action.equals("AddBigClass")) { 
%> 
<br><br>
<form action="Admin_Class.jsp" method="POST" name="BigClass" id="BigClass" onSubmit="return CheckBigClass()">
  <table width="90%"  border="1" align="center" cellpadding="2" cellspacing="0">
    <tr background="images/bg.gif">
      <td height="27" colspan="2" background="images/bg.gif">
	  <div align="center"><span class="title"><strong>�������Ŵ���</strong></span></div></td>
    </tr>
    <tr>
      <td width="38%" height="25"><div align="right" class="chinese">��������</div></td>
      <td width="62%"><input name="BigClassID" type="text" class="chinese" id="BigClassID" size="10" maxlength="4" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')">&nbsp;<span class="info1">* �����Ǵ��� 0 ������</span></td>
    </tr>
    <tr>
      <td height="25"><div align="right" class="chinese">������⣺</div></td>
      <td><input name="BigClassName" type="text" class="chinese" id="BigClassName" size="20" maxlength="30">&nbsp;<span class="info1">* ����</span></td>
    </tr>
    <tr>
      <td height="25"><div align="right" class="chinese">����˵����</div></td>
      <td><textarea name="BigClassInfo" cols="20" rows="5" class="chinese" id="BigClassInfo" onKeyDown="BigPressKey();"></textarea>&nbsp;<span class="info1">* ����(�ɰ�Ctrl+Enter)�ύ</span></td>
    </tr>
    <tr>
      <td height="30" colspan="2"><div align="center">
          <input name="Submit2" type="submit" class="button" value="ȷ��">
          <input name="Action" type="hidden" id="Action" value="SaveAddBigClass">
          <input name="Return4" type="button" class="button" id="Return" value="����" onClick="javascript:history.back();">
      </div></td>
    </tr>
  </table>
</form>
<br><br>
<% }
if (Action.equals("EditBigClass")) 
{
	String OldBigClassID = request.getParameter("BigClassID");
	String [] sa = aClass.ReadBigClass(OldBigClassID,true);
%>
<br><br>
<form action="Admin_Class.jsp" method="POST" Name="BigClass" id="BigClass" onSubmit="return CheckBigClass()">
  <table width="90%"  border="1" align="center" cellpadding="2" cellspacing="0">
    <tr background="images/bg.gif">
      <td height="27" colspan="2" background="images/bg.gif">
	  <div align="center"><span class="title"><strong>�޸����Ŵ���</strong></span></div></td>
    </tr>
    <tr>
      <td width="38%" height="25"><div align="right" class="chinese">��������</div></td>
      <td width="62%" valign="middle"><input name="BigClassID" type="text" class="chinese" id="BigClassID" value="<%=OldBigClassID%>" size="10" maxlength="4" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')">
          <input name="OldBigClassID" type="hidden" id="OldBigClassID2" value="<%=OldBigClassID%>">&nbsp;<span class="info1">* �����Ǵ��� 0 ������</span> </td>
    </tr>
    <tr>
      <td height="25"><div align="right" class="chinese">������⣺</div></td>
      <td><input name="BigClassName" type="text" class="chinese" id="BigClassName" value="<%=sa[0]%>" size="20" maxlength="30">
          <input name="OldBigClassName" type="hidden" id="OldBigClassName" value="<%=sa[0]%>">&nbsp;<span class="info1">* ����</span></td>
    </tr>
    <tr>
      <td height="25"><div align="right" class="chinese">����˵����</div></td>
      <td><textarea name="BigClassInfo" cols="20" rows="5" class="chinese" id="BigClassInfo" onKeyDown="BigPressKey();"><%=sa[1]%></textarea>&nbsp;<span class="info1">* ����(�ɰ�Ctrl+Enter)�ύ</span> </td>
    </tr>
    <tr>
      <td height="30" colspan="2"><div align="center">
          <input name="Submit" type="submit" class="button" value="ȷ��">
          <input name="Action" type="hidden" id="Action" value="SaveEditBigClass">
          <input name="Return" type="button" class="button" id="Return" value="����" onClick="javascript:history.back();">
      </div></td>
    </tr>
  </table>
</form>
<br><br>
<% } 
if (Action.equals("AddSClass")) 
{ 
	String BCID = request.getParameter("BigClassID");
	String BCName = aClass.ReadBigClass(BCID,true)[0];
%>
<br>
<form action="Admin_Class.jsp" method="POST" Name="SmallClass" id="SmallClass" onSubmit="return CheckSClass()">
  <table width="90%"  border="1" align="center" cellpadding="2" cellspacing="0">
    <tr background="images/bg.gif">
      <td height="27" colspan="2" background="images/bg.gif">
	  <div align="center"><span class="title"><strong>����С��</strong></span></div></td>
    </tr>
    <tr>
      <td width="38%" height="25"><div align="right" class="chinese">�������ࣺ</div></td>
      <td width="62%">
        <select name="BigClassID" size="1" class="chinese" id="BigClassID">
          <% if (sArray[0][0]==null)
				{
					out.print("<option value=\"\">���޴���</option>");
				}
				else
				{
			 		for(int i=0;i<sArray.length;i++)
					{
						out.print("<option value=\"" + sArray[i][0] + "\"");
						if (sArray[i][0].equals(BCID))  { out.print(" selected"); }
						out.print(">" + sArray[i][1] + "</option>");
					}
				}
			 %>
        </select>&nbsp;<span class="info1">* ��ѡ</span></td>
    </tr>
    <tr>
      <td height="25"><div align="right" class="chinese">С����⣺</div></td>
      <td><input name="SClassName" type="text" class="chinese" id="SClassName" size="20" maxlength="30">&nbsp;<span class="info1">* ����</span></td>
    </tr>
    <tr>
      <td height="25"><div align="right" class="chinese">С��˵����</div></td>
      <td><textarea name="SClassInfo" cols="20" rows="5" class="chinese" id="SClassInfo" onKeyDown="SPressKey();"></textarea>&nbsp;<span class="info1">* ����(�ɰ�Ctrl+Enter)�ύ</span></td>
    </tr>
    <tr>
      <td height="30" colspan="2"><div align="center">
          <input name="Submit3" type="submit" class="button" value="ȷ��">
          <input name="Action" type="hidden" id="Action" value="SaveAddSClass">
          <input name="Return" type="button" class="button" id="Return5" value="����" onClick="javascript:history.back();">
      </div></td>
    </tr>
  </table>
</form>
<br><br>
<% } 
if (Action.equals("EditSClass")) 
{
	String SCID = request.getParameter("SClassID");
	String [] sa = aClass.ReadBigClass(SCID,false);
%>
<br><br>
<form action="Admin_Class.jsp" method="POST"  Name="SmallClass" id="SmallClass" onSubmit="return CheckSClass()">
  <table width="90%"  border="1" align="center" cellpadding="2" cellspacing="0">
    <tr background="images/bg.gif">
      <td height="27" colspan="2" background="images/bg.gif"><div align="center"><span class="title"><strong>�޸�С��</strong></span></div></td>
    </tr>
    <tr>
      <td width="38%" height="25"><div align="right" class="chinese">�������ࣺ</div></td>
      <td width="62%">
        <select name="BigClassID" size="1" class="chinese" id="BigClassID">
          <% if (sArray[0][0]==null)
				{
					out.print("<option value=\"\">���޴���</option>");
				}
				else
				{ 
			 		for(int i=0;i<sArray.length;i++)
					{
						out.print("<option value=\"" + sArray[i][0] + "\"");
						if (sArray[i][0].equals(sa[2])) out.print(" selected");
						out.println(">" + sArray[i][1] + "</option>");
					}
				}
			 %>
        </select>
        <input name="SClassID" type="hidden" id="SClassID" value="<%=SCID%>"><span class="info1">* ��ѡ</span></td>
    </tr>
    <tr>
      <td height="25"><div align="right" class="chinese">С����⣺</div></td>
      <td><input name="SClassName" type="text" class="chinese" id="SClassName" value="<%=sa[0]%>" size="20" maxlength="30">
          <input name="OldSmallClassName" type="hidden" id="OldSmallClassName" value="<%=sa[0]%>"><span class="info1">* ����</span></td>
    </tr>
    <tr>
      <td height="25"><div align="right" class="chinese">С��˵����</div></td>
      <td><textarea name="SClassInfo" cols="20" rows="5" class="chinese" id="SClassInfo" onKeyDown="SPressKey();"><%=sa[1]%></textarea>&nbsp;<span class="info1">* ����(�ɰ�Ctrl+Enter)�ύ</span></td>
    </tr>
    <tr>
      <td height="30" colspan="2"><div align="center">
          <input name="Submit" type="submit" class="button" value="ȷ��">
          <input name="Action" type="hidden" id="Action" value="SaveEditSClass">
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
