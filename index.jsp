<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ page import="dreamtime.dreamnews.*"%>
<%@ include file="Config.jsp"%>
<%
/*****************************************************************
 *  
 *  Դ�ļ���:  ShowNews.jsp
 *  ��    �ܣ� �����껪����ϵͳ ��ҳ
 *	���ߣ������껪 [DreamTime]
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
<meta name="keywords" content="�����껪����ϵͳ|���ŷ���|��������|ʱ������|��������|�ط�����|��������|������|�������|fanwsp@126.com">
<LINK  href="css/newscss/newscss.css" rel=stylesheet type=text/css>
<title><%=DreamNewsTitle%> - �� ҳ</title>
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
<div id="Title3"><div id="stFont">ר�⵼��</div></div>
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
<div id="Title1"><div id="tFont">����ͷ��</div></div>
<%=LC.ShowHeadNews(null,false)%>
<div id="tArea"><div id="Title2"><div id="tFont">�������</div></div></div>
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