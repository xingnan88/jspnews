<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ page import="dreamtime.dreamnews.*"%>
<%@ include file="Config.jsp"%>
<%
/*****************************************************************
 *  
 *  源文件名:  ReadClass.jsp
 *  功    能： 梦想年华新闻系统 大类显示页面
 *	作者：梦想年华 [DreamTime]
 *	Email:fanwsp@126.com
 *  QQ:122142023 
 * 	CopyRight(c)2005-2006 By DreamTime 
 *
 *****************************************************************
*/
%>
<%
	request.setCharacterEncoding("GBK");	 //设置编码方式为GBK
	ID = request.getParameter("BigClassID");
	iPageType = 1;
%>
<html>
<head>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<meta name="keywords" content="梦想年华新闻系统|新闻发布|国内新闻|时事新闻|国际新闻|地方新闻|娱乐新闻|网络编程|网络管理|fanwsp@126.com">
<LINK  href="css/newscss/newscss.css" rel=stylesheet type=text/css>
<title><%=sNews.IDToTitle(ID,iPageType)%> - <%=DreamNewsTitle%></title>
</head>
<body>
<%@ include file="Top.jsp"%>
<div id="B1"></div>
<table align="center" cellpadding="0" cellspacing="0">
<tr>
<td valign="top" align="center" width="255" height="100%">
<table width="100%" height="100%" style="border:1px solid #959595" bgcolor="#F1F1F1" cellpadding="0" cellspacing="0">
<tr><td valign="top" height="100%" bgcolor="#FFFFFF"> 
<%=LC.ShowHeadPic(ID,false)%>
<div class="lArea">
<div id="Title3"><div id="stFont">专题导航</div></div>
<div id="B2"></div>
<%=LC.ShowLeftNews(false)%>
</div>
</td></tr></table>
</td>
<td valign="top" align="center" width="13"></td>
<td valign="top" align="center" width="480" height="100%"> 
<table width="100%" height="100%" style="border-bottom:1px solid #959595" cellpadding="0" cellspacing="0">
<tr><td valign="top" height="100%">     
<div id="HotNews"><%=LC.ShowHotNews(ID,false)%></div>
<div id="B1"></div>
<div id="rArea">
<div id="Title1"><div id="stFont">今日头条</div></div>
<%=LC.ShowHeadNews(ID,false)%>
<div id="stArea"><div id="sTitle2"><div id="stFont">最近更新</div></div></div>
<%=LC.ShowTopNews(ID,false)%>
<%=LC.ShowClassNews(ID)%>
</div>	
</td></tr></table>
</td>		
</tr>
</table>
<div id="B1"></div>
<%=CopyRight%>
<div id="B2"></div>
</body>
</html>