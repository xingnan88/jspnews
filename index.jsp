<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ page import="dreamtime.dreamnews.*"%>
<%@ include file="Config.jsp"%>
<%
/*****************************************************************
 *  
 *  源文件名:  ShowNews.jsp
 *  功    能： 梦想年华新闻系统 首页
 *	作者：梦想年华 [DreamTime]
 *	Email:fanwsp@126.com
 *  QQ:122142023 
 * 	CopyRight(c)2005-2006 By DreamTime 
 *
 *****************************************************************
*/
%>
<html>
<head>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<meta name="keywords" content="梦想年华新闻系统|新闻发布|国内新闻|时事新闻|国际新闻|地方新闻|娱乐新闻|网络编程|网络管理|fanwsp@126.com">
<LINK  href="css/newscss/newscss.css" rel=stylesheet type=text/css>
<title><%=DreamNewsTitle%> - 首 页</title>
</head>
<body>
<%@ include file="Top.jsp"%>
<div id="B1"></div>
<table align="center" cellpadding="0" cellspacing="0">
<tr>
<td valign="top" align="center" width="255" height="100%">
<table width="100%" height="100%" style="border:1px solid #959595; margin:0; padding:0;" bgcolor="#F1F1F1" cellpadding="0" cellspacing="0">
<tr><td valign="top" height="100%"> 
<%=LC.ShowHeadPic(null,false)%>
<div class="lArea">
<div id="Title3"><div id="stFont">专题导航</div></div>
<div id="B2"></div>
<%=LC.ShowLeftNews(true)%>
</div>
</td></tr></table>
</td>
<td valign="top" align="center" width="13"></td>
<td valign="top" align="center" width="480" height="100%"> 
<table width="100%" height="100%" style="border-bottom:1px solid #959595" cellpadding="0" cellspacing="0">
<tr><td valign="top" height="100%">     
<div id="HotNews"><%=LC.ShowHotNews(null,true)%></div>
<div id="B1"></div>
<div id="rArea">
<div id="Title1"><div id="tFont">今日头条</div></div>
<%=LC.ShowHeadNews(null,false)%>
<div id="tArea"><div id="Title2"><div id="tFont">最近更新</div></div></div>
<%=LC.ShowTopNews(null,false)%>
<%=LC.ShowClassNews(null)%>
</div>	
</td></tr></table>
</td>		
</tr>
</table>
<div id="B2"></div>
<%=CopyRight%>
<div id="B2"></div>
</body>
</html>