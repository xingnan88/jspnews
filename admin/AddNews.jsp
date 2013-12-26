<%@ page contentType="text/html; charset=GBK" language="java" buffer="32kb"%>
<%@ page import="dreamtime.dreamnews.*"%>
<%@ include file="Session.jsp"%>
<html>
<head>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link href="css/bodystyle.css" rel="stylesheet" type="text/css">
<title>�������</title>
<script Language=Javascript src="js/post.js"></script>
<script Language=Javascript src="js/news.js"></script>
</head>
<%
	request.setCharacterEncoding("GBK");	 			//���ñ��뷽ʽΪGBK
	AdminClass aClass = new AdminClass();
	News News1 = new News();
	Function Fun = new Function();
	String [][] sBig = aClass.GetAllClass(true,false,null);
	String [][] sSmall = aClass.GetAllClass(false,false,null);
	String [][] sSpecial = aClass.GetAllClass(true,true,null);
	String AdminName = (String)session.getAttribute("AdminName"); 
	String Action = request.getParameter("Action");
	if (Action!=null && Action.equals("Add"))
	{
		String IP = request.getRemoteAddr();		//�õ��ͻ���Ip��ַ
		String [] s = new String[15];
		s[0] = request.getParameter("NewsTitle");
		s[1] = request.getParameter("NewsContent");
		s[2] = request.getParameter("NewsKey");
		s[3] = request.getParameter("NewsAuthor");
		s[4] = request.getParameter("NewsFrom");
		s[5] = (new java.util.Date()).toLocaleString();
		s[6] = request.getParameter("NewsPicture");
		s[7] = request.getParameter("BigClassID");
		s[8] = request.getParameter("SClassID");
		s[9] = request.getParameter("IsHead");
		s[10] = request.getParameter("HeadPicture");
		s[11] = request.getParameter("IsImg");
		s[12] = request.getParameter("IsHot");
		s[13] = request.getParameter("SpecialID");
		s[14] = request.getParameter("NewsInfo");
		String sNews = News1.AddNews(s,AdminName,IP);
		if (sNews.equals("Yes"))
		{
			out.print("<script>alert('������ųɹ�!');location.href='ListNews.jsp';</script>");
			return;
		}
		else 
		{
			out.print(Fun.OutError(sNews));
			return;
		}
	}
%>
<script language = "JavaScript">
var iCount;
subcat = new Array();
<%
	if(sSmall!=null)
	{
	int iCount = 0;
	for(iCount=0;iCount<sSmall.length;iCount++)
	{
%>
subcat[<%=iCount%>] = new Array("<%=sSmall[iCount][0]%>","<%=sSmall[iCount][1]%>","<%=sSmall[iCount][2]%>");
<% } %>
iCount=<%=iCount%>;
<% } %>
</script>
<body>
<br><br>
<form name="AddNews" method="POST" action="AddNews.jsp" onSubmit="return CheckNews();">
<table width="90%" border="1" align=center cellpadding="1" cellspacing="1">
  <tr background="images/bg.gif">
    <td height="27" colspan="3" background="images/bg.gif">&nbsp;&nbsp;<b class="title">������� <span class="info1">&nbsp;&nbsp;��������Ա [<%=AdminName%>]</span></b></td>
  </tr>
  <tr class="chinese">
    <td width="130" height="25" align="right"><div align="right">�������ࣺ</div></td>
    <td height="25" colspan="2">
		<select name="BigClassID" onChange="ChangeLocation(document.AddNews.BigClassID.options[document.AddNews.BigClassID.selectedIndex].value)" size="1" class="chinese">
		<%  
			if (sBig==null)
			{
				out.println("<option value=\"\" selected>���޴���</option>");
			}
			else
			{
				out.println("<option value=\"\" selected>��ѡ�����</option>");
				for(int i=0;i<sBig.length;i++)
				{
					out.print("<option value=\"" + sBig[i][0] + "\"");
					//if(i==0) out.print(" selected"); 
					out.println(">" + sBig[i][1] + "</option>");
				}
			}
		 %>
		</select>&nbsp;
<select name="SClassID" size="1" class="chinese" id="SClassID">
  <%  
		if(sBig!=null)
		{
			String [][] sSmall1 = aClass.GetAllClass(false,false,sBig[0][0]);
			if (sSmall1==null)
			{
				out.println("<option value=\"\" selected>����С��</option>");
			}
			else
			{
				out.println("<option value=\"\" selected>��ѡ��С��</option>");
				for(int i=0;i<sSmall1.length;i++)
				{
					out.print("<option value=\"" + sSmall1[i][0] + "\"");
					//if(i==0) out.print(" selected"); 
					out.println(">" + sSmall1[i][1] + "</option>");
				}
			}
		}
		else out.println("<option value=\"\" selected>����С��</option>");
	 %>
</select><span class="info1">&nbsp;*��ѡ�����ŵ����</span></td>
  </tr>
  <tr class="chinese">
    <td width="130" height="25" align="right">����ר�⣺</td>
    <td height="25" colspan="2">
	<select name="SpecialID" size="1" class="chinese" id="SpecialID" onChange="ChangeLocation(document.AddNews.BigClassID.options[document.AddNews.BigClassID.selectedIndex].value)">
      <option selected value="">�������κ�ר��</option>
      <%    if (sSpecial==null)
			{
				out.println("<option value=\"\" selected>����ר��</option>");
			}
			else
			{
				for(int i=0;i<sSpecial.length;i++)
				{
					out.println("<option value=\"" + sSpecial[i][0] + "\">" + sSpecial[i][1] + "</option>");
				}
			}
		 %>
        </select></td>
  </tr>
  <tr class="chinese">
    <td width="130" height="25" align="right">���ű��⣺</td>
    <td height="25" colspan="2"><input name="NewsTitle" type="text" class="chinese" id="NewsTitle" size="40" maxlength="60"></td>
  </tr>
  <tr class="chinese">
    <td width="130" height="25" align="right">�����ˣ�</td>
    <td height="25" colspan="2"><input name="NewsAuthor" type="text" class="chinese" id="NewsAuthor" size="40" maxlength="20"></td>
  </tr>
  <tr class="chinese">
    <td width="130" height="25" align="right">���ų�����</td>
    <td height="25" colspan="2"><input name="NewsFrom" type="text" class="chinese" id="NewsFrom" size="40" maxlength="60"></td>
  </tr>
  <tr class="chinese">
    <td width="130" height="25" align="right">���Źؼ��֣�</td>
    <td height="25" colspan="2"><input name="NewsKey" type="text" class="chinese" id="NewsKey" size="40" maxlength="30"></td>
  </tr>
  <tr class="chinese">
    <td width="130" height="25" align="right">�Ƿ�ͼƬ���ţ�</td>
    <td width="90" height="25"><label><input type="radio" name="IsImg" value="Yes" onClick="ShowPicture(true)">��</label>
      <label><input name="IsImg" type="radio" value="No" checked onClick="ShowPicture(false)">��</label></td>
	 <td width="462" height="25">
	 <div id="p" style="display:none">&nbsp;<input name="NewsPicture" type="text" class="chinese" id="NewsPicture" size=25 maxlength="80" style="display:" value="">
	 <input name="UpImg" type="button" class="button" id="UpImg" onClick="getPicture()" value="�ϴ�����ͼƬ>>">&nbsp;<span class="info1">(130*80)</span></div></td>
	</tr>
  <tr class="chinese">
    <td width="130" height="25"><div align="right">�Ƿ�ͷ�����ţ�</div></td>
    <td width="90" height="25"><label><input type="radio" name="IsHead" value="Yes" onClick="ShowHeadPicture(true)">��</label>
      <label><input name="IsHead" type="radio" value="No" checked onClick="ShowHeadPicture(false)">��</label></td>
    <td height="25">
	 <div id="p1" style="display:none">&nbsp;<input name="HeadPicture" type="text" class="chinese" id="HeadPicture" size=25 maxlength="80" style="display:" value="">
     <input name="UpImg" type="button" class="button" id="UpImg" onClick="getHeadPicture()" value="�ϴ�ͷ��ͼƬ>>">&nbsp;<span class="info1">(250*140)</span></div>
	</td>
    </tr>
  <tr class="chinese">
    <td width="130" height="25" align="right">�Ƿ�������ţ�</td>
    <td height="25" colspan="2"><label><input name="IsHot" type="radio" value="Yes">��</label>
      <label><input name="IsHot" type="radio" value="No" checked>��</label></td>
    </tr>
  <tr class="chinese">
    <td height="25" align="right">����ժҪ��</td>
    <td height="25" colspan="2"><textarea name="NewsInfo" cols="40" rows="3" id="NewsInfo"></textarea> 
      <span class="info1">*120������</span></td>
  </tr>
  <tr class="chinese">
    <td width="130" height="25" align="right">�������ݣ�</td>
    <td height="25" colspan="2">&nbsp;</td>
  </tr>
  <tr>
    <td height="25" colspan="3"><div align="center">
      <textarea name="NewsContent" class="chinese" style="display:none"></textarea>
      <iframe ID="DreamEditor" src="../eWebEditor.jsp?id=NewsContent&style=CoolBlue" frameborder="0" scrolling="no" width="620" height="350"></iframe>
    </div></td>
    </tr>
  <tr>
    <td colspan=3><p align="center"><br>
      <input name="B1" type="button" class="button" value="����>>" onClick="location.href='ListNews.jsp'">
      <input name="Action" type="hidden" id="Action" value="Add">&nbsp;
      <input name="B2" type="submit" class="button" id="B2" value="����>>">
      <br>
      <br><br></p></td>
  </tr>
</table>
<br>
<br>
</form>
</body>
</html>
