<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ page import="dreamtime.dreamnews.*"%>
<%
/*****************************************************************
 *  
 *  源文件名:  Top.jsp
 *  功    能： 梦想年华新闻系统 顶部导航信息
 *	作者：梦想年华 [DreamTime]
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
		<span>新闻搜索：</span><input name="Key" type="text" size="30" maxlength="20" class="sText"> 
       	<input type="hidden" name="Action" value="Search">
		<input type="hidden" name="KeyType" value="0">
		<input type="hidden" name="NewsType" value="0">
		<input type="submit" name="Submit" value="搜索" class="Button"> 
		<input type="button" name="hSearch" value="高级搜索" class="Button" onClick="location.href='Search.jsp'">  
   	</form>
   </div></td>      
</tr>      
</table>