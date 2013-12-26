<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ page import="dreamtime.dreamnews.*"%>
<%@ include file="Config.jsp"%>
<%
/*****************************************************************
 *  
 *  源文件名:  Special.jsp
 *  功    能： 梦想年华新闻系统 专题新闻显示页面
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
	ID = request.getParameter("SpecialID");
	String strPage = request.getParameter("intPage");
	String sPage = request.getContextPath() + request.getServletPath() + "?SClassID=" + ID + "&";
	boolean b = false;
	String sID = ID;
	if(ID==null) 
	{
		iPageType = 5;
		b = true;
		ID="1";
	}
	else iPageType = 4;
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
<tr><td valign="top" height="100%"> 
<%if (sID==null) out.println(LC.ShowHeadPic(null,true));%>
<div class="lArea">
<div id="Title3"><div id="stFont">专题导航</div></div>
<div id="B2"></div>
<%=LC.ShowLeftNews(true)%>
</div>
</td></tr></table>
</td>
<td valign="top" align="center" width="13"></td>
<td valign="top" align="center" width="480" height="100%"> 
<table width="100%" height="100%" cellpadding="0" cellspacing="0" style="border-bottom:1px solid #959595">
<tr><td valign="top" height="100%">     
<div id="HotNews"><%=LC.ShowHotNews(null,true)%></div>
<div id="B1"></div>
<div id="rArea">
<% 	if (sID==null)
	{
		out.println("<div id=\"Title1\"><div id=\"tFont\">今日头条</div></div>");
		out.println(LC.ShowHeadNews(null,true));
		out.println("<div id=\"tArea\"><div id=\"Title2\"><div id=\"tFont\">最近更新</div></div></div>");
		out.println(LC.ShowTopNews(null,true));
	}
	out.println("<div id=\"Title1\"><div id=\"stFont\">" + sNews.IDToTitle(ID,iPageType) + "</div></div>");
	out.println(LC.ShowSpecial(sID,sPage,strPage));
%>
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