
/***************************************************
 *  
 *  源文件名:  User.java
 *  功    能： 梦想年华新闻系统 - 用户管理类
 *	作者：梦想年华 [DreamTime]
 *	Email:fanwsp@126.com
 *  QQ:122142023 
 * 	CopyRight(c)2005-2006 by DreamTime 
 *
 ****************************************************
*/

package dreamtime.dreamnews;			//指定类所在的包

import java.sql.*;
import dreamtime.dreamnews.Function;
import dreamtime.dreamnews.DBConnection;
import dreamtime.dreamnews.MD5;

public class User
{

    DBConnection DBConn = new DBConnection();
    Function Fun = new Function(); 
    MD5 md5 = new MD5();
	public String AdminName,AdminPwd,UserName,UserSex,UserTel;
	public String UserQQ,UserEmail,UserAddress,UserZip,UserInfo;
	public String AddTime,LastLoginTime,LastLoginIP;
	public String sAdminType,UserBirthday;
	public int AdminType,BirthYear,BirthMonth,BirthDay;
	public int NewsNum,LoginNum;
	public String [] sType = new String[4];
	
    
    
    public User()
    {
    	sType[1] = "普通管理员";
    	sType[2] = "系统管理员";
    	sType[3] = "超级管理员";  
    }
    
    
    public boolean ReadUser(int AdminID)
    {
    try{	
    	Connection Conn = DBConn.getConn();
    	Statement stmt = Conn.createStatement(1004,1007);
    	ResultSet rs = null;
    	String Sql = "select * from Admin where AdminID=" + AdminID;
    	rs = stmt.executeQuery(Sql);
    	rs.next();
    	AdminName = rs.getString("AdminName");
    	AdminType = rs.getInt("AdminType");
    	UserName = rs.getString("UserName");
    	UserSex = rs.getString("UserSex");
    	UserBirthday = rs.getString("UserBirthday");
    	UserTel = rs.getString("UserTel");
    	UserQQ = rs.getString("UserQQ");
    	UserEmail = rs.getString("UserEmail");
    	UserAddress = rs.getString("UserAddress");
    	UserZip = rs.getString("UserZip");
    	UserInfo = rs.getString("UserInfo");
    	AddTime = rs.getString("AddTime");
    	LastLoginTime = rs.getString("LastLoginTime");
    	LastLoginIP = rs.getString("LastLoginIP");
    	NewsNum = rs.getInt("NewsNum");
    	LoginNum = rs.getInt("LoginNum");
    	
    	String [] s = UserBirthday.split("-");
    	BirthYear = Fun.StrToInt(s[0]);
    	BirthMonth = Fun.StrToInt(s[1]);
    	BirthDay = Fun.StrToInt(s[2]);
    	rs.close();
    	stmt.close();
    	Conn.close();
    	return true;
    	}catch(Exception e){ return false;}
    }
    
    
    
   /***********************************************************
	* 函数名：UserList
	* 作  用：管理员用户列表
	* 参  数：sPage,页面地址; strPage,第几页
	* 返回值：字符串
	***********************************************************/
    public String UserList(String sPage,String strPage)
    {
    	try
    	{
	    	Connection Conn = DBConn.getConn();
	    	Statement stmt = Conn.createStatement(1004,1007);
	    	ResultSet rs = null;
	    	String sSql = null;
	    	StringBuffer sb = new StringBuffer();
			int i;
			int intPage = 1; 			
			int intPageSize = 10;
			if (Fun.ReadConfig() && Fun.AdminUserListNum>0) intPageSize = Fun.AdminUserListNum;
	    	sSql = "select * from Admin order by AdminID desc";
	    	rs = stmt.executeQuery(sSql);	    	
	    	 
	    	sb.append("<br><br><table width=\"90%\"  border=\"1\" align=\"center\" cellpadding=\"2\" cellspacing=\"\">\r\n");
			sb.append("<tr background=\"images/bg.gif\">\r\n");
			sb.append("<td height=\"27\" colspan=\"7\" background=\"images/bg.gif\"><div align=\"center\" class=\"title\">");
			sb.append("<strong>所有管理员用户列表</strong></div></td>\r\n");
			sb.append("</tr>\r\n");
			sb.append("<tr height=\"25\" class=\"chinese\">\r\n");
			sb.append("<td width=\"5%\"><div align=\"center\"><b>ID</b></div></td>\r\n");
			sb.append("<td width=\"12%\"><div align=\"center\"><b>用户名</b></div></td>\r\n");
			sb.append("<td width=\"13%\"><div align=\"center\"><b>用户权限</b></div></td>\r\n");
			sb.append("<td width=\"12%\"><div align=\"center\"><b>真实姓名</div></td>\r\n");
			sb.append("<td width=\"26%\"><div align=\"center\"><b>添加日期</b></div></td>\r\n");
			sb.append("<td width=\"8%\"><div align=\"center\"><b>新闻</b></div></td>\r\n");
			sb.append("<td width=\"24%\">\r\n");
			sb.append("<div align=\"center\">操作 </div></td>\r\n");
			sb.append("</tr>\r\n");
		
			//如果表中没有任何记录，刚给出提示信息
			if (!rs.next())
			{ 
				sb.append("<tr height=\"25\" bgcolor=\"#d6dff7\"  class=\"info1\"><td colspan=\"7\">\r\n");
				sb.append("<div align=\"center\">暂时没有管理用户!</div></td></tr>\r\n");
			}
			else 
			{
				//取得待显示页码 
				intPage = Fun.StrToInt(strPage);
				sPage = Fun.CheckReplace(sPage);
				if (intPage==0) intPage=1;
				
				
				//将记录指针定位到待显示页的第一条记录上 
				rs.absolute((intPage-1) * intPageSize+1);
				i = 0; 
				while(i < intPageSize && !rs.isAfterLast())
				{
					int AdminID = rs.getInt("AdminID");
					AdminType = rs.getInt("AdminType");
					sAdminType = sType[AdminType];
					sb.append("<tr height=\"25\" bgcolor=\"#d6dff7\" class=\"chinese\">\r\n");
					sb.append("<td><div align=\"center\" class=\"chinese\">" + AdminID + "</div></td>\r\n");
					sb.append("<td><div align=\"center\" class=\"chinese\">" + rs.getString("AdminName") + "</div></td>\r\n");
					sb.append("<td><div align=\"center\" class=\"chinese\">" + sAdminType + "</div></td>\r\n");
					sb.append("<td><div align=\"center\" class=\"chinese\">" + rs.getString("UserName") + "</div></td>\r\n");
					sb.append("<td><div align=\"center\" class=\"chinese\">" + rs.getString("AddTime") + "</div></td>\r\n");
					sb.append("<td><div align=\"center\" class=\"chinese\">" + rs.getInt("NewsNum") + "</div></td>\r\n");
					sb.append("<td><div align=\"center\" class=\"chinese\">[<a href=\"Admin_User.jsp?Action=View&AdminID=" + AdminID + "\">浏览</a>]");
					sb.append("[<a href=\"Admin_User.jsp?Action=Edit&AdminID=" + AdminID + "\">修改</a>]");
					sb.append("[<a href=\"Admin_User.jsp?Action=Del&AdminID="+ AdminID + "\">删除</a>]</div></td>\r\n");
					sb.append("</tr>\r\n");
					rs.next(); 
					i++;
				}
				sb.append("</table>\r\n");
				sb.append(Fun.Page(sPage,rs,intPage,intPageSize));
				sb.append("<br><br>");
			}
			rs.close();
    		stmt.close();
    		Conn.close();
			return sb.toString();
		}catch(Exception e)
		{
			//e.printStackTrace();
			return "No";
			//return e.getMessage().toString(); 
		}
    }
    
   
   /*********************************************************
	* 函数名：AddUser
	* 作  用：添加管理员表单
	* 参  数：无
	* 返回值：字符串
	***********************************************************/    
    public String AddUser()
    {
	try{	
		StringBuffer sb = new StringBuffer();
      	sb.append("<br><br><form  name=\"User\" method=\"POST\" action=\"Admin_User.jsp\" onSubmit=\"return CheckUser();\">\r\n");
		sb.append("<table width=\"90%\"  border=\"1\" align=\"center\" cellpadding=\"2\" cellspacing=\"0\">\r\n");
		sb.append("<tr background=\"images/bg.gif\">\r\n");
		sb.append("<td height=\"25\" colspan=\"2\" class=\"chinese\" background=\"images/bg.gif\"><div align=\"center\"><span class=\"title\">");
		sb.append("<strong>添加新的管理用户</strong></span><span class=\"info1\">  (打 * 为必填项)</span></div></td>\r\n");
		sb.append("</tr>\r\n");
		sb.append("<tr bgcolor=\"#d6dff7\">\r\n");
		sb.append("<td width=\"35%\" height=\"25\" class=\"chinese\"><div align=\"right\">用户名：</div></td>\r\n");
		sb.append("<td width=\"65%\"><input name=\"AdminName\" type=\"text\" class=\"chinese\" id=\"AdminName\" size=\"20\" maxlength=\"16\">");
		sb.append("<span class=\"info1\"> * </span></td></tr>\r\n");
		sb.append("<tr bgcolor=\"#d6dff7\">\r\n");
		sb.append("<td height=\"25\" class=\"chinese\"><div align=\"right\">密码：</div></td>\r\n");
		sb.append("<td><input name=\"AdminPwd\" type=\"password\" class=\"chinese\" id=\"AdminPwd\" size=\"20\" maxlength=\"16\">");
		sb.append("<span class=\"info1\"> * (4-16位)</span></td></tr>\r\n");
		sb.append("<tr bgcolor=\"#d6dff7\">\r\n");
		sb.append("<td height=\"25\" class=\"chinese\"><div align=\"right\">确认密码：</div></td>\r\n");
		sb.append("<td><input name=\"AdminPwd2\" type=\"password\" class=\"chinese\" id=\"AdminPwd2\" size=\"20\" maxlength=\"16\">");
		sb.append("<span class=\"info1\"> * </span></td></tr>\r\n");
		sb.append("<tr bgcolor=\"#d6dff7\">\r\n");
		sb.append("<td height=\"25\" class=\"chinese\"><div align=\"right\">权限设置：</div></td>\r\n");
		sb.append("<td><select name=\"AdminType\" class=\"chinese\" id=\"AdminType\">\r\n");
		sb.append("<option value=\"1\" selected>普通管理员</option>\r\n");
		sb.append("<option value=\"2\">系统管理员</option>\r\n");
		sb.append("<option value=\"3\">超级管理员</option>\r\n");
		sb.append("</select></td>\r\n");
		sb.append("</tr>\r\n");
		sb.append("<tr bgcolor=\"#d6dff7\">\r\n");
		sb.append("<td height=\"25\" class=\"chinese\"><div align=\"right\">真实姓名：</div></td>\r\n");
		sb.append("<td><input name=\"UserName\" type=\"text\" class=\"chinese\" id=\"UserName\" size=\"20\" maxlength=\"16\">");
		sb.append("<span class=\"info1\"> * </span></td></tr>\r\n");
		sb.append("<tr bgcolor=\"#d6dff7\">\r\n");
		sb.append("<td height=\"25\" class=\"chinese\"><div align=\"right\">性别：</div></td>\r\n");
		sb.append("<td class=\"chinese\"><input name=\"UserSex\" type=\"radio\" value=\"男\" checked>男\r\n");
		sb.append("<input name=\"UserSex\" type=\"radio\" value=\"女\">女</td>\r\n");
		sb.append("</tr>\r\n");
		sb.append("<tr bgcolor=\"#d6dff7\">\r\n");
		sb.append("<td height=\"25\" class=\"chinese\"><div align=\"right\">出生日期：</div></td>\r\n");
		sb.append("<td class=\"chinese\"><select name=\"Year\" class=\"chinese\" id=\"Year\">\r\n");
		for(int Year=1900;Year<=2000;Year++)
		{
			sb.append("<option value=\"" + Year + "\">" + Year + "</option>");
		}
		
		sb.append("\r\n");
		sb.append("</select>年\r\n");
		sb.append("<select name=\"Month\" class=\"chinese\" id=\"Month\">\r\n");
			
		for(int Month=1;Month<=12;Month++)
		{
			sb.append("<option value=\"" + Month + "\">" + Month + "</option>");
		}
		
		sb.append("\r\n");
		sb.append("</select>月\r\n");
		sb.append("<select name=\"Day\" class=\"chinese\" id=\"Day\">\r\n");
		
		for(int Day=1;Day<=31;Day++)
		{
			sb.append("<option value=\"" + Day + "\">" + Day + "</option>");
		}
		
		sb.append("\r\n");
		sb.append("</select>日</td>\r\n");
		sb.append("</tr>\r\n");
		sb.append("<tr bgcolor=\"#d6dff7\">\r\n");
		sb.append("<td height=\"25\" class=\"chinese\"><div align=\"right\">电子邮件：</div></td>\r\n");
		sb.append("<td><input name=\"UserEmail\" type=\"text\" class=\"chinese\" id=\"UserEmail\" size=\"20\" maxlength=\"50\">");
		sb.append("<span class=\"info1\"> * </span></td></tr>\r\n");
		sb.append("<tr bgcolor=\"#d6dff7\">\r\n");
		sb.append("<td height=\"25\" class=\"chinese\"><div align=\"right\">联系电话：</div></td>\r\n");
		sb.append("<td><input name=\"UserTel\" type=\"text\" class=\"chinese\" id=\"UserTel\" size=\"20\" maxlength=\"11\"></td>\r\n");
		sb.append("</tr>\r\n");
		sb.append("<tr bgcolor=\"#d6dff7\">\r\n");
		sb.append("<td height=\"25\" class=\"chinese\"><div align=\"right\">QQ：</div></td>\r\n");
		sb.append("<td><input name=\"UserQQ\" type=\"text\" class=\"chinese\" id=\"UserQQ\" size=\"20\" maxlength=\"10\"></td>\r\n");
		sb.append("</tr>\r\n");
		sb.append("<tr bgcolor=\"#d6dff7\">\r\n");
		sb.append("<td height=\"25\" class=\"chinese\"><div align=\"right\">通讯地址：</div></td>\r\n");
		sb.append("<td><input name=\"UserAddress\" type=\"text\" class=\"chinese\" id=\"UserAddress\" size=\"40\" maxlength=\"80\"></td>\r\n");
		sb.append("</tr>\r\n");
		sb.append("<tr bgcolor=\"#d6dff7\">\r\n");
		sb.append("<td height=\"25\" class=\"chinese\"><div align=\"right\">邮编：</div></td>\r\n");
		sb.append("<td><input name=\"UserZip\" type=\"text\" class=\"chinese\" id=\"UserZip\" size=\"20\" maxlength=\"6\"></td>\r\n");
		sb.append("</tr>\r\n");
		sb.append("<tr bgcolor=\"#d6dff7\">\r\n");
		sb.append("<td height=\"25\" class=\"chinese\"><div align=\"right\">个人简介：</div></td>\r\n");
		sb.append("<td><textarea name=\"UserInfo\" cols=\"40\" rows=\"4\" class=\"chinese\" id=\"UserInfo\"></textarea></td>\r\n");
		sb.append("</tr>\r\n");
		sb.append("<tr>\r\n");
		sb.append("<td height=\"25\" colspan=\"2\"><div align=\"center\">\r\n");
		sb.append("<input name=\"Submit\" type=\"submit\" class=\"button\" value=\" 确 定 \">&nbsp;\r\n");
		sb.append("<input name=\"Action\" type=\"hidden\" id=\"Action\" value=\"SaveAdd\">");
		sb.append("<input name=\"Reset\" type=\"reset\" class=\"button\" id=\"Reset\" value=\" 重 填 \">\r\n");
		sb.append("<input name=\"Return\" type=\"button\" class=\"button\" id=\"Return\"");
		sb.append("onClick=\"javascript:location.href='Admin_User.jsp'\" value=\"返回用户列表\">\r\n");
		sb.append("</div></td>\r\n");
		sb.append("</tr>\r\n");
		sb.append("</table>\r\n");
		sb.append("</form><br><br>\r\n");
		return sb.toString();
		}catch(Exception e){
			////e.printStackTrace();
			//System.out.print(e.getMessage());
    		return "No";
    	}	
    }
    
    
   /*********************************************************
	* 函数名：EditUser
	* 作  用：编辑管理员信息
	* 参  数：s,字符串型，管理员ID
	*         b, 布尔型 是否允许修改密码
	* 返回值：字符串
	***********************************************************/
    public String EditUser(String s,boolean b)
    {
    try{
    	
		String sError = "";
		boolean OK = true;
		String sAction = "Person.jsp";
		String sModType = "修改个人资料";
		String sCheck = "onSubmit=\"return CheckPerson();\"";
		
		if(b) 
		{
			sAction = "Admin_User.jsp";
			sModType = "修改管理员用户资料";
			sCheck = "onSubmit=\"return CheckEditUser();\"";
		}
		int AdminID = Fun.StrToInt(Fun.CheckReplace(s));
		if (AdminID==0)
		{
			OK = false;
			sError = "No";
		}
		
		if(OK)
		{
			if(!ReadUser(AdminID))
			{
				OK = false;
				sError = "No";
			}
		}
		if(OK)
		{	  	
	    	StringBuffer sb = new StringBuffer();		
			sb.append("<br><br>\r\n");
			sb.append("<form name=\"User\" method=\"POST\" action=\"");
			sb.append(sAction);
			sb.append("\" " + sCheck + ">\r\n");
			sb.append("<table width=\"90%\"  border=\"1\" align=\"center\" cellpadding=\"2\" cellspacing=\"0\">\r\n");
			sb.append("<tr background=\"images/bg.gif\">\r\n");
			sb.append("<td height=\"25\" colspan=\"2\" class=\"chinese\" background=\"images/bg.gif\"><div align=\"center\"><span class=\"title\">");
			sb.append("<strong>" + sModType + "</strong></span><span class=\"info1\">  (打 * 为必填项)</span></div></td>\r\n");
			sb.append("</tr>\r\n");
			sb.append("<tr bgcolor=\"#d6dff7\">\r\n");
			sb.append("<td width=\"34%\" height=\"25\" class=\"chinese\"><div align=\"right\">用户名：</div></td>\r\n");
			sb.append("<td width=\"66%\"><input name=\"AdminName\" readonly type=\"text\" class=\"chinese\" id=\"AdminName\" value=\"");
			sb.append(AdminName);
			sb.append("\" size=\"20\" maxlength=\"16\"><input name=\"AdminID\" type=\"hidden\" id=\"AdminID\" value=\"");
			sb.append(AdminID);
			sb.append("\"></td></tr>\r\n");
			if(b)
			{	
				sb.append("<tr bgcolor=\"#d6dff7\">\r\n");
				sb.append("<td height=\"25\" class=\"chinese\"><div align=\"right\">密码：</div></td>\r\n");
				sb.append("<td><input name=\"AdminPwd\" type=\"password\" class=\"chinese\" id=\"AdminPwd\" value=\"");
				//sb.append(AdminPwd);
				sb.append("\" size=\"20\" maxlength=\"16\"><span class=\"info1\">  (留空则表示不修改密码)</span></td>\r\n");
				sb.append("</tr>\r\n");
				sb.append("<tr bgcolor=\"#d6dff7\">\r\n");
				sb.append("<td height=\"25\" class=\"chinese\"><div align=\"right\">确认密码：</div></td>\r\n");
				sb.append("<td class=\"chinese\"><input name=\"AdminPwd2\" type=\"password\" class=\"chinese\" id=\"AdminPwd\" value=\"");
				//sb.append(AdminPwd2);
				sb.append("\" size=\"20\" maxlength=\"16\"> </td>\r\n");
				sb.append("</tr>\r\n");
				sb.append("<tr bgcolor=\"#d6dff7\">\r\n");
				sb.append("<td height=\"25\" class=\"chinese\"><div align=\"right\">权限设置：</div></td>\r\n");
				sb.append("<td><select name=\"AdminType\" class=\"chinese\" id=\"AdminType\">\r\n");
				sb.append("  <option value=\"1\" ");
				sb.append(((Integer.toString(AdminType).equals("1"))?"SELECTED":""));
				sb.append(">普通管理员</option>\r\n");
				sb.append("  <option value=\"2\" ");
				sb.append(((Integer.toString(AdminType).equals("2"))?"SELECTED":""));
				sb.append(">系统管理员</option>\r\n");
				sb.append("  <option value=\"3\" ");
				sb.append(((Integer.toString(AdminType).equals("3"))?"SELECTED":""));
				sb.append(">超级管理员</option>\r\n");
				sb.append("</select></td>\r\n");
				sb.append("</tr>\r\n");
			}
			sb.append("<tr bgcolor=\"#d6dff7\">\r\n");
			sb.append("<td height=\"25\" class=\"chinese\"><div align=\"right\">真实姓名：</div></td>\r\n");
			sb.append("<td><input name=\"UserName\" type=\"text\" class=\"chinese\" value=\"");
			sb.append(UserName);
			sb.append("\"  size=\"20\" maxlength=\"16\"></td>\r\n");
			sb.append("</tr>\r\n");
			sb.append("<tr bgcolor=\"#d6dff7\">\r\n");
			sb.append("<td height=\"25\" class=\"chinese\"><div align=\"right\">性别：</div></td>\r\n");
			sb.append("<td class=\"chinese\"><input ");
			sb.append((UserSex.equals("男"))?"CHECKED":"");
			sb.append(" name=\"UserSex\" type=\"radio\" value=\"男\" checked>\r\n");
			sb.append("男\r\n");
			sb.append("<input ");
			sb.append((UserSex.equals("女"))?"CHECKED":"");
			sb.append(" name=\"UserSex\" type=\"radio\" value=\"女\">\r\n");
			sb.append("女</td>\r\n");
			sb.append("</tr>\r\n");
			sb.append("<tr bgcolor=\"#d6dff7\">\r\n");
			sb.append("<td height=\"25\" class=\"chinese\"><div align=\"right\">出生日期：</div></td>\r\n");
			sb.append("<td class=\"chinese\"><select name=\"Year\" class=\"chinese\" id=\"Year\">\r\n");
			for(int Year=1900;Year<=2000;Year++)
			{
				String sSelect = (Year==BirthYear)?"SELECTED":"";
				sb.append("<option value=\"" + Year + "\"" + sSelect + ">" + Year + "</option>");
			}
	
			sb.append("\r\n");
			sb.append("</select>年\r\n");
			sb.append("<select name=\"Month\" class=\"chinese\" id=\"Month\">\r\n");
			
			for(int Month=1;Month<=12;Month++)
			{
				String sSelect = (Month==BirthMonth)?"SELECTED":"";
				sb.append("<option value=\"" + Month + "\"" + sSelect + ">" + Month + "</option>");
			}
	
			sb.append("\r\n");
			sb.append("</select>月\r\n");
			sb.append("<select name=\"Day\" class=\"chinese\" id=\"Day\">\r\n");
			
			for(int Day=1;Day<=31;Day++)
			{
				String sSelect = (Day==BirthDay)?"SELECTED":"";
				sb.append("<option value=\"" + Day + "\"" + sSelect + ">" + Day + "</option>");
			}
	
			sb.append("\r\n");
			sb.append("</select>日</td>\r\n");
			sb.append("</tr>\r\n");
			sb.append("<tr bgcolor=\"#d6dff7\">\r\n");
			sb.append("<td height=\"25\" class=\"chinese\"><div align=\"right\">电子邮件：</div></td>\r\n");
			sb.append("<td><input name=\"UserEmail\" type=\"text\" class=\"chinese\" id=\"UserEmail2\" value=\"");
			sb.append(UserEmail);
			sb.append("\" size=\"20\" maxlength=\"50\"></td>\r\n");
			sb.append("</tr>\r\n");
			sb.append("<tr bgcolor=\"#d6dff7\">\r\n");
			sb.append("<td height=\"25\" class=\"chinese\"><div align=\"right\">联系电话：</div></td>\r\n");
			sb.append("<td><input name=\"UserTel\" type=\"text\" class=\"chinese\" id=\"UserTel2\" value=\"");
			sb.append(UserTel);
			sb.append("\" size=\"20\" maxlength=\"11\"></td>\r\n");
			sb.append("</tr>\r\n");
			sb.append("<tr bgcolor=\"#d6dff7\">\r\n");
			sb.append("<td height=\"25\" class=\"chinese\"><div align=\"right\">QQ：</div></td>\r\n");
			sb.append("<td><input name=\"UserQQ\" type=\"text\" class=\"chinese\" id=\"UserQQ2\" value=\"");
			sb.append(UserQQ);
			sb.append("\" size=\"20\" maxlength=\"10\"></td>\r\n");
			sb.append("</tr>\r\n");
			sb.append("<tr bgcolor=\"#d6dff7\">\r\n");
			sb.append("<td height=\"25\" class=\"chinese\"><div align=\"right\">通讯地址：</div></td>\r\n");
			sb.append("<td><input name=\"UserAddress\" type=\"text\" class=\"chinese\" id=\"UserAddress2\" value=\"");
			sb.append(UserAddress);
			sb.append("\" size=\"20\" maxlength=\"50\"></td>\r\n");
			sb.append("</tr>\r\n");
			sb.append("<tr bgcolor=\"#d6dff7\">\r\n");
			sb.append("<td height=\"25\" class=\"chinese\"><div align=\"right\">邮编：</div></td>\r\n");
			sb.append("<td><input name=\"UserZip\" type=\"text\" class=\"chinese\" id=\"UserZip2\" value=\"");
			sb.append(UserZip);
			sb.append("\" size=\"20\" maxlength=\"6\"></td>\r\n");
			sb.append("</tr>\r\n");
			sb.append("<tr bgcolor=\"#d6dff7\">\r\n");
			sb.append("<td height=\"25\" class=\"chinese\"><div align=\"right\">个人简介：</div></td>\r\n");
			sb.append("<td><textarea name=\"UserInfo\" cols=\"40\" rows=\"4\" class=\"chinese\" id=\"textarea\">");
			sb.append(UserInfo);
			sb.append("</textarea></td>\r\n");
			sb.append("</tr>\r\n");
			sb.append("<tr>\r\n");
			sb.append("<td height=\"30\" colspan=\"2\"><div align=\"center\">\r\n");
			sb.append("<input name=\"Submit\" type=\"submit\" class=\"button\" value=\" 确 定 \">&nbsp;\r\n");
			sb.append("<input name=\"Action\" type=\"hidden\" id=\"Action\" value=\"SaveEdit\">\r\n");
			sb.append("<input name=\"Return\" type=\"button\" class=\"button\" id=\"Return\"");
			sb.append("onClick=\"javascript:history.go(-1)\" value=\"取消返回\">\r\n");
			sb.append("</div></td>\r\n");
			sb.append("</tr>\r\n");
			sb.append("</table>\r\n");
			sb.append("</form><br><br>\r\n");
			return sb.toString();
		}else return sError;
		
		}catch(Exception e){
			////e.printStackTrace();
			//System.out.print(e.getMessage());
    		return "No";
    	}	
		
	}
	
	
   /*********************************************************
	* 函数名：ViewUser
	* 作  用：查看管理员详细信息
	* 参  数：字符串 s 管理员ID
	* 返回值：字符串
	***********************************************************/
	public String ViewUser(String s)
    {
    try{	
		StringBuffer sb = new StringBuffer();
		String sError = "";
		boolean OK = true;
		int AdminID = Fun.StrToInt(Fun.CheckReplace(s));
		if (AdminID==0)
		{
			OK = false;
			//sError = "非法的参数传递";
			sError = "No";
			
		}
		
		if(OK)
		{
			if(!ReadUser(AdminID))
			{
				OK = false;
				//sError = "数据库操作出错，请重试!";
				sError = "No";
			}
		}
		if(OK)
		{	
			sAdminType = sType[AdminType];
			sb.append("<br><br>\r\n");
			sb.append("<table width=\"90%\" border=\"1\" align=\"center\" cellpadding=\"4\" cellspacing=\"0\">\r\n");
			sb.append("<tr background=\"images/bg.gif\">\r\n");
			sb.append("<td class=\"chinese\" colspan=\"4\" background=\"images/bg.gif\"><div align=\"center\"><span class=\"title\">");
			sb.append("<strong>浏览管理用户详细信息</strong></span></div></td>\r\n");
			sb.append("</tr>\r\n");
			sb.append("<tr bgcolor=\"#d6dff7\">\r\n");
			sb.append("<td bgcolor=\"#d6dff7\" class=\"chinese\"><div align=\"right\">用户ID：</div></td>\r\n");
			sb.append("<td class=\"chinese\">&nbsp;");
			sb.append(AdminID);
			sb.append("</td>\r\n");
			sb.append("<td bgcolor=\"#d6dff7\" class=\"chinese\"><div align=\"right\">添加时间：</div></td>\r\n");
			sb.append("<td class=\"chinese\">&nbsp;");
			sb.append(AddTime);
			sb.append("</td>\r\n");
			sb.append("</tr>\r\n");
			sb.append("<tr bgcolor=\"#d6dff7\">\r\n");
			sb.append("<td width=\"18%\" class=\"chinese\"><div align=\"right\">用户名： </div></td>\r\n");
			sb.append("<td width=\"30%\" class=\"chinese\">&nbsp;");
			sb.append(AdminName);
			sb.append("</td>\r\n");
			sb.append("<td width=\"22%\" class=\"chinese\"><div align=\"right\">登录次数：</div></td>\r\n");
			sb.append("<td width=\"30%\"><span class=\"chinese\">&nbsp;");
			sb.append(LoginNum);
			sb.append(" </span></td>\r\n");
			sb.append("</tr>\r\n");
			sb.append("<tr bgcolor=\"#d6dff7\">\r\n");
			sb.append("<td class=\"chinese\"><div align=\"right\">管理员权限： </div></td>\r\n");
			sb.append("<td class=\"chinese\">&nbsp;");
			sb.append(sAdminType);
			sb.append("</td>\r\n");
			sb.append("<td class=\"chinese\"><div align=\"right\">最后登录的时间：</div></td>\r\n");
			sb.append("<td class=\"chinese\"><span class=\"chinese\">&nbsp;");
			sb.append(LastLoginTime);
			sb.append(" </span></td>\r\n");
			sb.append("</tr>\r\n");
			sb.append("<tr bgcolor=\"#d6dff7\">\r\n");
			sb.append("<td class=\"chinese\"><div align=\"right\">性别： </div></td>\r\n");
			sb.append("<td class=\"chinese\">&nbsp;");
			sb.append(UserSex);
			sb.append(" </td>\r\n");
			sb.append("<td class=\"chinese\"><div align=\"right\">最后登录的IP：</div></td>\r\n");
			sb.append("<td class=\"chinese\">&nbsp;");
			sb.append(LastLoginIP);
			sb.append(" </td>\r\n");
			sb.append("</tr>\r\n");
			sb.append("<tr bgcolor=\"#d6dff7\">\r\n");
			sb.append("<td class=\"chinese\"><div align=\"right\">出生日期： </div></td>\r\n");
			sb.append("<td class=\"chinese\">&nbsp;");
			sb.append(UserBirthday);
			sb.append(" </td>\r\n");
			sb.append("<td class=\"chinese\"><div align=\"right\">发表的新闻数：</div></td>\r\n");
			sb.append("<td class=\"chinese\">&nbsp;");
			sb.append(NewsNum);
			sb.append(" </td>\r\n");
			sb.append("</tr>\r\n");
			sb.append("<tr bgcolor=\"#d6dff7\">\r\n");
			sb.append("<td class=\"chinese\"><div align=\"right\">电子邮件： </div></td>\r\n");
			sb.append("<td><span class=\"chinese\">&nbsp;");
			sb.append(UserEmail);
			sb.append(" </span></td>\r\n");
			sb.append("<td colspan=\"2\"><div align=\"center\" class=\"chinese\">管理员其它信息</div></td>\r\n");
			sb.append("</tr>\r\n");
			sb.append("<tr bgcolor=\"#d6dff7\">\r\n");
			sb.append("<td class=\"chinese\"><div align=\"right\">联系电话： </div></td>\r\n");
			sb.append("<td><span class=\"chinese\">&nbsp;");
			sb.append(UserTel);
			sb.append(" </span></td>\r\n");
			sb.append("<td colspan=\"2\" rowspan=\"4\" valign=\"top\"><div align=\"right\"></div>      \r\n");
			sb.append("<div align=\"left\"><span class=\"chinese\">");
			sb.append(UserInfo);
			sb.append("</span></di></td>\r\n");
			sb.append("</tr>\r\n");
			sb.append("<tr bgcolor=\"#d6dff7\">\r\n");
			sb.append("<td class=\"chinese\"><div align=\"right\">QQ： </div></td>\r\n");
			sb.append("<td><span class=\"chinese\">&nbsp;");
			sb.append(UserQQ);
			sb.append(" </span></td>\r\n");
			sb.append("</tr>\r\n");
			sb.append("<tr bgcolor=\"#d6dff7\">\r\n");
			sb.append("<td class=\"chinese\"><div align=\"right\">通讯地址： </div></td>\r\n");
			sb.append("<td><span class=\"chinese\">&nbsp;");
			sb.append(UserAddress);
			sb.append(" </span></td>\r\n");
			sb.append("</tr>\r\n");
			sb.append("<tr bgcolor=\"#d6dff7\">\r\n");
			sb.append("<td class=\"chinese\"><div align=\"right\">邮编： </div></td>\r\n");
			sb.append("<td><span class=\"chinese\">&nbsp;");
			sb.append(UserZip);
			sb.append("</span></td>\r\n");
			sb.append("</tr>\r\n");
			sb.append("<tr bgcolor=\"#d6dff7\">\r\n");
			sb.append("<td colspan=\"4\" class=\"chinese\"><div align=\"center\"><a href=\"#\"");
			sb.append("onClick=\"javascript:history.go(-1)\"><strong>返回</strong></a></div></td>\r\n");
			sb.append("</tr>\r\n");
			sb.append("</table>\r\n");
			sb.append("<br><br>\r\n");
			
			return sb.toString();
		}else return sError;
    	}catch(Exception e){return "No";}
    }
	
	
	
	
	/*********************************************************
	* 函数名:SaveAdd()
	* 作  用:添加用户
	* 参  数:s,字符串数组，添加用户的各项参数
	*        s1:管理员;s2:IP地址
	* 返回值:字符串型。返回操作结果的信息
	***********************************************************/
	public String SaveAdd(String [] s,String s1,String s2)
    {
		String NowTime = (new java.util.Date()).toLocaleString();
		String [] sLog = new String[5];
		sLog[0] = s1;
		sLog[2] = NowTime;
		sLog[3] = s2; 
	    try{
	    	boolean OK = true;
	    	String sError = "";
	    	Connection Conn = DBConn.getConn();
	    	Statement stmt = Conn.createStatement(1004,1007);
	    	ResultSet rs = null;
	    	for(int i=0;i<s.length;i++)
	    	{
	    		s[i] = Fun.getStrCN(Fun.CheckReplace(s[i]));
	    	}
	    	String [] sa1 = new String [4];
	    	String [] sa2 = new String [4];
	    	sa1[0] = s[0];
	    	sa1[1] = s[1];
	    	sa1[2] = s[4];
	    	sa1[3] = s[7];
	    	sa2[0] = "管理员用户名";
	    	sa2[1] = "管理员密码";
	    	sa2[2] = "管理员真实姓名";
	    	sa2[3] = "电子邮件";
	    	int iAdminType = Fun.StrToInt(s[2]);
	    	if (iAdminType == 0) iAdminType = 1;
	    	
	    	String sOK = Fun.CheckDate(sa1,sa2);
	    	if (!sOK.equals("Yes"))
	    	{
	    		OK = false;
	    		sError = sOK; 
	    	}
	
	    	String sql1 = "select * from Admin where AdminName='" + s[0] + "'";
	    	ResultSet rs1 = stmt.executeQuery(sql1);
	    	if (rs1.next()) 
	    	{
	    		OK = false;
	    		sError = "该用户名已经存在，请重新输入!";
	    	}
	    	
	    	if(OK)
	    	{
		    	s[1] = md5.getMD5ofStr(md5.getMD5ofStr(s[1]));
				String sql = "insert into Admin (AdminName,AdminPwd,AdminType,AddTime,UserName,UserSex,";
				sql += "UserBirthday,UserEmail,UserQQ,UserTel,UserAddress,UserZip,UserInfo) values (";
				sql += "'" + s[0] + "',";
				sql += "'" + s[1] + "',";
				sql += iAdminType + ",";
				sql += "'" + s[3] + "',";
				sql += "'" + s[4] + "',";
				sql += "'" + s[5] + "',";
				sql += "'" + s[6] + "',";
				sql += "'" + s[7] + "',";
				sql += "'" + s[8] + "',";
				sql += "'" + s[9] + "',";
				sql += "'" + s[10] + "',";
				sql += "'" + s[11] + "',";
				sql += "'" + s[12] + "')";
				//out.println(sql);
				stmt.executeUpdate(sql);
				stmt.close();
				Conn.close();
				sLog[1] = "添加新的管理员用户["+s[0]+"]";
				sLog[4] = "Yes";
				Fun.AddLog(sLog);
	    		return "Yes";
		    }
		    else return sError;

    	}catch(Exception e){
    		sLog[1] = "添加新的管理员用户["+s[0]+"]操作失败";
    		sLog[4] = "No";
			Fun.AddLog(sLog);
    		////e.printStackTrace();
    		//System.out.print(e.getMessage());
    		//System.out.print(sql);
    		return "添加用户操作出错，请核对你的输入!";
    	}
	
	}
	
	
	
	/*********************************************************
	* 函数名:SaveEdit()
	* 作  用:修改用户资料，
	* 参  数:s,字符串数组，修改用户的各项参数
	*         s1:管理员;s2:IP地址,
	*		  b:布尔型，是否超级管理员
	* 返回值:字符串型。返回操作结果的信息
	***********************************************************/
	
	public String SaveEdit(String [] s,String s1,String s2,boolean b)
    {
    	
    	String NowTime = (new java.util.Date()).toLocaleString();
		String [] sLog = new String[5];
		sLog[0] = s1;
		sLog[2] = NowTime;
		sLog[3] = s2; 
		sLog[1] = "修改ID为 [" + s[0] + " ] 的管理员用户资料";
		
    	try{
    		
    		boolean OK = true;
	    	String sError = "";
    		
    		for(int i=0;i<s.length;i++)
	    	{
	    		s[i] = Fun.getStrCN(Fun.CheckReplace(s[i]));
	    	}
	    	
	    	String [] sa1 = new String [3];
	    	String [] sa2 = new String [3];
	    	sa1[0] = s[0];
	    	sa1[1] = s[4];
	    	sa1[2] = s[7];
	    	sa2[0] = "管理员用户名";
	    	sa2[1] = "管理员真实姓名";
	    	sa2[2] = "电子邮件";
	    	
	    	String sOK = Fun.CheckDate(sa1,sa2);
	    	if (!sOK.equals("Yes"))
	    	{
	    		OK = false;
	    		sError = sOK; 
	    	}
			
			if(OK)
			{
		    	Connection Conn = DBConn.getConn();
		    	Statement stmt = Conn.createStatement(1004,1007);
		    	ResultSet rs = null;
		    	int iAdminType = Fun.StrToInt(s[2]);
	    		if (iAdminType == 0) iAdminType = 1;
	    		String sql = "update Admin set ";
	    		sql += "AddTime='" + s[3] + "',";
	    		sql += "UserName='" + s[4] + "',";
	    		sql += "UserSex='" + s[5] + "',";
	    		sql += "UserBirthday='" + s[6] + "',";
	    		sql += "UserEmail='" + s[7] + "',";
	    		sql += "UserQQ='" + s[8] + "',";
	    		sql += "UserTel='" + s[9] + "',";
	    		sql += "UserAddress='" + s[10] + "',";
	    		sql += "UserZip='" + s[11] + "',";
	    		sql += "UserInfo='" + s[12] + "'";
	    		String sql1 = " where AdminID=" + s[0];
		    	if(b)
			    {
			    	sql += ",AdminType=" + iAdminType;
			    	if (s[1]!=null && !s[1].equals("")) 
			    	{
			    		s[1] = md5.getMD5ofStr(md5.getMD5ofStr(s[1]));	
			    		sql += ",AdminPwd='" + s[1] + "'";
			    		sLog[1] += "和密码!";
			    	}
			    }
		    	sql += sql1;
				stmt.executeUpdate(sql);
				stmt.close();
				Conn.close();
				sLog[4] = "Yes";
				Fun.AddLog(sLog);
		    	return "Yes";
		    } return sError;
    	}catch(Exception e){
    		sLog[4] = "No";
			Fun.AddLog(sLog);
    		////e.printStackTrace();
    		//System.out.print(e.getMessage());
    		//System.out.print(sql);
    		return "修改用户操作出错，请核对你的输入!";
    	}
    }
	
	
	
	/*********************************************************
	* 函数名:SaveEditPwd()
	* 作  用:修改用户密码
	* 参  数: s[0],旧密码s[1]，新密码，s[2]:管理员;
	*		  s[3]:IP地址,s[4],管理员ID
	* 返回值:字符串型。返回操作结果的信息
	***********************************************************/
	
	public String SaveEditPwd(String [] s)
    {
    	
    	String NowTime = (new java.util.Date()).toLocaleString();
		String [] sLog = new String[5];
		sLog[0] = s[2];
		sLog[2] = NowTime;
		sLog[3] = s[3];
		String sql=null;
    	try{	
    		boolean OK = true;
	    	String sError = "";
    		
    		for(int i=0;i<s.length;i++)
	    	{
	    		s[i] = Fun.getStrCN(Fun.CheckReplace(s[i]));
	    	}
	    	
	    	String [] sa1 = new String [1];
	    	String [] sa2 = new String [1];
	    	sa1[0] = s[0];
	    	sa2[0] = "旧密码";
	    	int AdminID = Fun.StrToInt(s[4]);
	    	
	    	String sOK = Fun.CheckDate(sa1,sa2);
	    	if (!sOK.equals("Yes"))
	    	{
	    		OK = false;
	    		sError = sOK; 
	    	}
	    	
			if (OK && AdminID==0)
	    	{
	    		OK = false;
	    		sError = "错误的参数传递";
	    	}
			
			if(OK)
			{
		    	Connection Conn = DBConn.getConn();
		    	Statement stmt = Conn.createStatement(1004,1007);
		    	ResultSet rs = null;
		    	ResultSet rs1 = null;
		    	s[0] = md5.getMD5ofStr(md5.getMD5ofStr(s[0]));
		    	s[1] = md5.getMD5ofStr(md5.getMD5ofStr(s[1]));
		    	String sql1 = "select AdminPwd from Admin where AdminID="+AdminID;
		    	rs1 = stmt.executeQuery(sql1);
		    	rs1.next();
		    	if(s[0].equals(rs1.getString("AdminPwd")))
		    	{
		    		sql = "update Admin set ";
		    		sql += "AdminPwd='" + s[1] + "'";
		    		sql += " where AdminID=" + AdminID;
			    	
					stmt.executeUpdate(sql);
					stmt.close();
					Conn.close();
					sLog[1] = "用户[" + s[2] + "]修改密码!"; 
					sLog[4] = "Yes";
					Fun.AddLog(sLog);
			    	return "Yes";
			    }
			    else 
			    {
			    	sLog[1] = "用户 [ " + s[2] + " ] 修改密码失败，原密码错误!"; 
					sLog[4] = "Yes";
					Fun.AddLog(sLog);
			    	return "原密码错误";
			    }
		    } 
		    else return sError;
    	}catch(Exception e){
    		sLog[1] = "用户 [ " + s[2] + " ] 修改密码失败，程序异常!";
    		sLog[4] = "No";
			Fun.AddLog(sLog);
    		//e.printStackTrace();
    		//System.out.print();
    		System.out.print(sql);
    		return "修改密码出错!" + e.getMessage();
    	}
    }
	
	
	
	/*********************************************************
	* 函数名：Del
	* 作  用：删除用户
	* 参  数：s1，用户Id，s1，操作用户；s2,IP
	* 返回值：成功　true ，否则返回　false
	***********************************************************/ 
	public boolean Del(String AdminID,String s1,String s2)
    {
	   
	    String NowTime = (new java.util.Date()).toLocaleString();
		String [] sLog = new String[5];
		sLog[0] = s1;
		sLog[2] = NowTime;
		sLog[3] = s2; 
    	try{
	    	Connection Conn = DBConn.getConn();
	    	Statement stmt = Conn.createStatement();
	    	String sql = "delete from Admin where AdminID=" + Fun.StrToInt(AdminID);
	    	stmt.executeUpdate(sql);
	    	stmt.close();
	    	Conn.close();
	    	sLog[1] = "删除ID为[" + AdminID + "]的管理员用户!"; 
			sLog[4] = "Yes";
			Fun.AddLog(sLog);
	    	return true;
    	}catch(Exception e){
    		////e.printStackTrace();
    		//System.out.print(e.getMessage());
			sLog[1] = "删除ID为[" + AdminID + "]的管理员用户失败!"; 
			sLog[4] = "No";
			Fun.AddLog(sLog);
    		//return "添加用户操作出错，请重试！" + e.getMessage();
    		return false;
    	}
	
	}
	
    //测试
	public static void main(String[] args)
	{	
		User user = new User();
		String [] s = new String[14];
		s[0] = "Admin1";
		s[1] = "admin";
		s[2] = "3";
		s[3] = (new java.util.Date()).toLocaleString();
		s[4] = "znl";
		s[5] = "女";
		s[6] = "1984-12-20";
		s[7] = "fanwsp@126.com";
		s[13] = "27";
		for(int i=8;i<s.length-1;i++)
		{
			s[i] = "暂无";
		}
		System.out.println(user.UserList("Admin_User.jsp","1"));
		//System.out.println(user.AddUser());
		//System.out.println(user.ViewUser("1"));
		//System.out.print(user.SaveEdit(s));
		//System.out.print(s[0]);
		//System.out.print(user.Del("35","http://''","IP"));
	}
    
    
}