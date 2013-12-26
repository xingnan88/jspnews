<%@ page contentType="text/html; charset=GBK" language="java" buffer="32kb"%>
<%@ page import="dreamtime.dreamnews.*"%>
<%@ include file="Session.jsp"%>
<% Function Fun = new Function(); %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<style type="text/css">
BODY {
	BACKGROUND:#799AE1; MARGIN: 0px; FONT: 9pt 宋体;
	SCROLLBAR-HIGHLIGHT-COLOR: ;
	SCROLLBAR-ARROW-COLOR:#215dc6;
	SCROLLBAR-TRACK-COLOR:#d6dff7;
	SCROLLBAR-BASE-COLOR:#AEC6F0;
}
TD { FONT: 12px 宋体}
A { FONT: 12px 宋体; COLOR: #000000; TEXT-DECORATION: none }
A:hover { COLOR: #428eff; TEXT-DECORATION: underline}

.sec_menu 
{
	BORDER-RIGHT: white 1px solid; BACKGROUND: #d6dff7; 
	OVERFLOW: hidden; BORDER-LEFT: white 1px solid; BORDER-BOTTOM: white 1px solid 
}

.menu_title SPAN { FONT-WEIGHT: bold; LEFT: 8px; COLOR: #215dc6; POSITION: relative; TOP: 2px }

.menu_title2 SPAN { FONT-WEIGHT: bold; LEFT: 8px; COLOR: #428eff; POSITION: relative; TOP: 2px }
</style>
</head>

<table cellspacing="0" cellpadding="0" width="158" align="center">
<tr><td valign="bottom" height="42"><img height="38" src="images/title.gif" width="158" border="0"></td></tr>
<tr><td class="menu_title" onmouseover="this.className='menu_title2';" onmouseout="this.className='menu_title';" background="images/title_bg_quit.gif" height="25">
<span><a target="rightFrame" title="返回管理首页" href="Index_Face.html"><b><font color="215DC6">管理首页</font></b></a>|<a target="_parent" title="安全退出管理系统" href="Logout.jsp"><b><font color="215DC6">安全退出</font></b></a></span>
<tr>
  <td class="menu_title2" onmouseover="this.className='menu_title2';" onmouseout="this.className='menu_title';" background="images/title_bg_quit.gif" height="25">
  <span class="menu_title"><b><font color="215DC6">登录用户：<%=session.getAttribute("AdminName")%></font></b></span> <br>
<tr><td>&nbsp;<br><br></td></tr>
</table>
<% if (Fun.StrToInt((String)session.getAttribute("AdminType"))>1) { %>
<table cellspacing="0" cellpadding="0" width="158" align="center">
<tr>
  <td class="menu_title" id="menuTitle1" onmouseover="this.className='menu_title2';" onclick="showsubmenu(1)" onmouseout="this.className='menu_title';" style="cursor:hand" background="images/title_bg_show.gif" height="25"><span>系统管理</span></td>
</tr>
<tr><td id="submenu1" style="DISPLAY: none">
	<div class="sec_menu" style="WIDTH: 158px">
	<table cellspacing="0" cellpadding="0" width="135" align="center">
		<tr>
		  <td height="22"><a href="Admin_Info.jsp" title="网站基本信息设置" target="rightFrame">系统基本信息</a></td>
		</tr>
		<tr>
		  <td height="22"><a href="Admin_Config.jsp" title="系统参数设置" target="rightFrame">系统参数设置</a></td>
		</tr>
		<tr>
		  <td height="22"><a href="Admin_Log.jsp" target="rightFrame">系统日志管理</a></td>
		  </tr>
	</table>
	</div>
	<div style="WIDTH: 158px">
	<table cellspacing="0" cellpadding="0" width="135" align="center">
		<tr><td height="22"></td></tr>
	</table></div>
</td></tr>
</table>
<% }
  if (Fun.StrToInt((String)session.getAttribute("AdminType"))>1) { %>
<table cellspacing="0" cellpadding="0" width="158" align="center">
<tr>
  <td class="menu_title" id="menuTitle2" onmouseover="this.className='menu_title2';" onclick="showsubmenu(2)" onmouseout="this.className='menu_title';" style="cursor:hand" background="images/title_bg_show.gif" height="25"><span>版面管理</span></td>
</tr>
<tr><td id="submenu2" style="DISPLAY: none">
	<div class="sec_menu" style="WIDTH: 158px">
	<table cellspacing="0" cellpadding="0" width="135" align="center">
		<tr>
		  <td height="22"><a href="Admin_Class.jsp" title="管理新闻类别" target="rightFrame">类别管理</a></td>
		</tr>
		<tr>
		  <td height="22"><a href="Admin_Special.jsp" target="rightFrame">专题管理</a></td>
		</tr>
	</table>
	</div>
	<div style="WIDTH: 158px">
	<table cellspacing="0" cellpadding="0" width="135" align="center">
		<tr><td height="22"></td></tr>
	</table></div>
</td></tr>
</table>
<% } %>
<table cellspacing="0" cellpadding="0" width="158" align="center">
<tr><td class="menu_title" id="menuTitle3" onmouseover="this.className='menu_title2';" onclick="showsubmenu(3)" onmouseout="this.className='menu_title';" style="cursor:hand" background="images/title_bg_show.gif" height="25"><span>新闻管理</span></td>
</tr>
<tr><td id="submenu3" style="DISPLAY: none">
	<div class="sec_menu" style="WIDTH: 158px">
	<table cellspacing="0" cellpadding="0" width="135" align="center">
		<tr>
		  <td height="22"><a href="AddNews.jsp" title="添加新闻" target="rightFrame">添加新闻</a></td>
		</tr>
		<tr>
		  <td height="22"><a href="ListNews.jsp" title="修改新闻" target="rightFrame">修改新闻</a></td>
		</tr>
	</table>
	</div>
	<div style="WIDTH: 158px">
	<table cellspacing="0" cellpadding="0" width="135" align="center">
		<tr><td height="22"></td></tr>
	</table></div>
</td></tr>
</table>
<% if (Fun.StrToInt((String)session.getAttribute("AdminType"))==3) { %>
<table cellspacing="0" cellpadding="0" width="158" align="center">
<tr>
  <td class="menu_title" id="menuTitle4" onmouseover="this.className='menu_title2';" onclick="showsubmenu(4)" onmouseout="this.className='menu_title';" style="cursor:hand" background="images/title_bg_show.gif" height="25"><span>用户管理 </span></td>
</tr>
<tr><td id="submenu4" style="DISPLAY: none">
	<div class="sec_menu" style="WIDTH: 158px">
	<table cellspacing="0" cellpadding="0" width="135" align="center">
		<tr>
		  <td height="22"><a href="Admin_User.jsp?Action=Add" target="rightFrame">添加用户</a></td>
		</tr>
		<tr>
		  <td height="22"><a href="Admin_User.jsp?Action=List" title="修改新闻" target="rightFrame">修改用户</a></td>
		</tr>
		
	</table>
	</div>
	<div style="WIDTH: 158px">
	<table cellspacing="0" cellpadding="0" width="135" align="center">
		<tr><td height="22"></td></tr>
	</table></div>
</td></tr>
</table>
<% } %>
<table cellspacing="0" cellpadding="0" width="158" align="center">
  <tr>
    <td class="menu_title" id="menuTitle5" onmouseover="this.className='menu_title2';" onclick="showsubmenu(5)" onmouseout="this.className='menu_title';" style="cursor:hand" background="images/title_bg_show.gif" height="25"><span>个人信息</span></td>
  </tr>
  <tr>
    <td id="submenu5" style="DISPLAY: none">
      <div class="sec_menu" style="WIDTH: 158px">
        <table cellspacing="0" cellpadding="0" width="135" align="center">
          <tr>
            <td height="22"><a href="Person.jsp?Action=View" target="rightFrame">浏览信息</a></td>
          </tr>
          <tr>
            <td height="22"><a href="Person.jsp?Action=Edit" target="rightFrame">修改资料</a></td>
          </tr>
          <tr>
            <td height="22"><a href="Person.jsp?Action=Mod1" target="rightFrame">修改密码</a><a href="Person.jsp?Action=Mod2" target="rightFrame"></a></td>
          </tr>
        </table>
      </div>
      <div style="WIDTH: 158px">
        <table cellspacing="0" cellpadding="0" width="135" align="center">
          <tr>
            <td height="22"></td>
          </tr>
        </table>
    </div></td>
  </tr>
</table>
<table cellspacing="0" cellpadding="0" width="158" align="center">
  <tr>
    <td class="menu_title" id="menuTitle6" onmouseover="this.className='menu_title2';" onclick="showsubmenu(6)" onmouseout="this.className='menu_title';" style="cursor:hand" background="images/title_bg_show.gif" height="25"><span>帮助系统</span></td>
  </tr>
  <tr>
    <td id="submenu6" style="DISPLAY: none">
      <div class="sec_menu" style="WIDTH: 158px">
        <table cellspacing="0" cellpadding="0" width="135" align="center">
          <tr>
            <td height="22"><a href="help/index.html" target="rightFrame">安装说明</a></td>
          </tr>
          <tr>
            <td height="22"><a href="help/intro.html" target="rightFrame">功能简介</a></td>
          </tr>
          <tr>
            <td height="22"><a href="help/copyright.html" target="rightFrame">版权说明</a></td>
          </tr>
        </table>
      </div>
      <div style="WIDTH: 158px">
        <table cellspacing="0" cellpadding="0" width="135" align="center">
          <tr>
            <td height="22"></td>
          </tr>
        </table>
    </div></td>
  </tr>
</table>
<br>
<br>
<table cellspacing="0" cellpadding="0" width="158" align="center">
<tr>
  <td class="menu_title" id="menuTitle7" onmouseover="this.className='menu_title2';" onmouseout="this.className='menu_title';" background="images/admin_left_9.gif" height="25">
<span>DreamNews信息</span> </td>
</tr><tr><td><div class="sec_menu" style="WIDTH: 158px"><table cellspacing="0" cellpadding="0" width="135" align="center">
<tr><td height="22"><br>    
版本：DreamNews 1.0 <br>
    <br>版权所有：<br>
    梦想年华[<a target="_blank" href="http://"><font color="000000">DreamTime</font></a>]<br>
<br></td></tr></table>
</div></td></tr></table></tr></tbody></table>
</div>

<table cellspacing="0" cellpadding="0" width="158" align="center">
<tr><td>&nbsp;<br><br></td></td></tr>
</table>

<script>
function showsubmenu(sid)
{
	whichEl = eval("submenu" + sid);
	menuTitle = eval("menuTitle" + sid);
	if (whichEl.style.display == "none")
	{
	eval("submenu" + sid + ".style.display=\"\";");
	menuTitle.background="images/title_bg_hide.gif";
	}
	else
	{
	eval("submenu" + sid + ".style.display=\"none\";");
	menuTitle.background="images/title_bg_show.gif";
	}
}
</script>