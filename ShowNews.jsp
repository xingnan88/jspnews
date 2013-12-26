<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ page import="dreamtime.dreamnews.*"%>
<%@ include file="Config.jsp"%>
<%
/*****************************************************************
 *  
 *  源文件名:  ShowNews.jsp
 *  功    能： 梦想年华新闻系统 新闻显示页面
 *	作者：梦想年华 [DreamTime]
 *	Email:fanwsp@126.com
 *  QQ:122142023 
 * 	CopyRight(c)2005-2006 By DreamTime 
 *
 *****************************************************************
*/
%>
<%
	request.setCharacterEncoding("GBK");	 	//设置编码方式为GBK
	ID = request.getParameter("NewsID");
	iPageType = 0;
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
<table align="center" cellpadding="0" cellspacing="0" width="750">
<tr>
<td align="center" valign="top">
<%=sNews.ShowNews(ID)%>
</td>
</tr>
</table>
<div id="B1"></div>
<%=CopyRight%>
<div id="B2"></div>
</body>
</html>