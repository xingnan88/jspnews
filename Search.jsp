<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ page import="dreamtime.dreamnews.*"%>
<%@ include file="Config.jsp"%>
<%
/*****************************************************************
 *  
 *  Դ�ļ���:  Search.jsp
 *  ��    �ܣ� �����껪����ϵͳ ��������ҳ��
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
<title>�������� - <%=DreamNewsTitle%></title>
<script language="javascript">
//������ҳ
function MM_jumpMenu(targ,selObj,restore){ 		//v3.0
  eval(targ+".location='"+selObj.options[selObj.selectedIndex].value+"'");
  if (restore) selObj.selectedIndex=0;
}
</script>
</head>
<body>
<%@ include file="Top.jsp"%>
<div id="B1"></div>
<table align="center" cellpadding="0" cellspacing="0">
<tr>
<td valign="top" align="center" width="255" height="100%">
<table width="100%" height="100%" style="border:1px solid #959595" bgcolor="#F1F1F1" cellpadding="0" cellspacing="0">
<tr><td valign="top" height="100%"> 
<div id="Title3"><div id="stFont">�߼�����</div></div>
<div id="HSearch">
  <form name="SearchForm" method="post" action="Search.jsp" class="sForm">
  <span>�������ͣ�</span><select name="KeyType" size="1" id="KeyType" class="sText">
    <option value="0" selected>����</option>
    <option value="1">����</option>
    <option value="2">����</option>
    <option value="3">�ؼ���</option>
    <option value="4">����ʱ��</option>
  </select> 
  <span>�������ʣ�</span><select name="NewsType" size="1" id="NewsType" class="sText">
    <option value="0" selected>��������</option>
    <option value="1">ͷ������</option>
    <option value="2">ͼƬ����</option>
    <option value="3">��ͨ����</option>
  </select> 
  <span>�ؼ��֣�</span><input name="Key" type="text" size="30" maxlength="20" class="sText"> 
	<input type="hidden" name="Action" value="Search">
	<input type="submit" name="Submit" value="����" class="Button"> 
  </form>
</div>
</td></tr></table>
</td>
<td valign="top" align="center" width="13"></td>
<td valign="top" align="center" width="480" height="100%"> 
<table width="100%" height="100%" style="border-bottom:0px solid #959595" cellpadding="0" cellspacing="0">
<tr><td valign="top" height="100%">     
<div id="rArea">
<div id="sTitle1"><div id="stFont">��������</div></div>
<%
	request.setCharacterEncoding("8859_1");	 	//���ñ��뷽ʽ
	String sAction = request.getParameter("Action");
	if(sAction!=null && sAction.equals("Search") && request.getParameter("Key")!=null && request.getParameter("KeyType")!=null && request.getParameter("NewsType")!=null ) 
	{
		String [] s = new String[3];
		s[0] = new String(request.getParameter("KeyType").getBytes("8859_1"));
		s[1] = new String(request.getParameter("NewsType").getBytes("8859_1"));
		s[2] = new String(request.getParameter("Key").getBytes("8859_1"));
		String strPage = request.getParameter("intPage");
		String sPage = request.getContextPath() + request.getServletPath();
		sPage += "?Action=Search&KeyType="+s[0]+"&Newstype="+s[1]+"&Key="+s[2]+"&";
		sPage = response.encodeURL(sPage);
		out.println(sNews.SearchNews(s,sPage,strPage));
		//out.println(s);
	}
	else 
	{
		out.println("<div id=\"News\"><ul>");
		out.println("<li><div id=\"F3\">������ѡ����������!<br><br><br></div></li>");
		out.println("</ul></div>");
	}
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