<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ page import="dreamtime.dreamnews.*"%>
<%
/*****************************************************************
 *  
 *  Դ�ļ���:  Top.jsp
 *  ��    �ܣ� �����껪����ϵͳ ����������Ϣ
 *	���ߣ������껪 [DreamTime]
 *	Email:fanwsp@126.com
 *  QQ:122142023 
 * 	CopyRight(c)2005-2006 By DreamTime 
 *
 *****************************************************************
*/
%>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<LINK  href="css/newscss/newscss.css" rel=stylesheet type=text/css>
<table width="750" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td><img src="pic/top.gif" width="750" height="80" align="middle"></td>
  </tr>
  <tr><td height="20" background="pic/bg.gif"><%=LC.TopClass()%></td></tr>
</table>
<table width="750" height="20" align="center"  style="border:1px solid #296CBC;" bgcolor="#EFF4F7" cellpadding="0" cellspacing="0">      
<tr>
  <td width="300"><div id="TopTitle"><span><%=sNews.IDToTitle(ID,iPageType)%></span></div></td>      
  <td width="450"><div id="Search">
	<form name="SearchForm" method="post" action="Search.jsp" class="sForm">
		<span>����������</span><input name="Key" type="text" size="30" maxlength="20" class="sText"> 
       	<input type="hidden" name="Action" value="Search">
		<input type="hidden" name="KeyType" value="0">
		<input type="hidden" name="NewsType" value="0">
		<input type="submit" name="Submit" value="����" class="Button"> 
		<input type="button" name="hSearch" value="�߼�����" class="Button" onClick="location.href='Search.jsp'">  
   	</form>
   </div></td>      
</tr>      
</table>