<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ page import="dreamtime.dreamnews.*"%>
<%!
	ListClass LC = new ListClass();
	Function Fun = new Function();
	ShowNews sNews = new ShowNews();
	
	/************* 读取配置信息 ***********************/
	public String CopyRight = Fun.OutCopyRight();
	public String DreamNewsTitle = Fun.DreamNewsTitle;
	public String ID = null;
	public int iPageType = 0;
%>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html; charset=GBK">