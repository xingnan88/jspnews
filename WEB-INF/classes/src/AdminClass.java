
/***************************************************
 *  
 *  源文件名:  AdminClass.java
 *  功    能： 梦想年华新闻系统 - 新闻类别管理类
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

public class AdminClass
{

    DBConnection DBConn = new DBConnection();
    Function Fun = new Function(); 
    
    public AdminClass()
    {
    	
    } 
     
  
   /*********************************************************
	* 函数名：GetAllClass
	* 作  用：读取所有大类信息
	* 参  数：布尔型b：True 表示大类，否则表示小类
	*		   s1，指定大类的ID
	* 返回值：字符串数二维组型。类别的各项信息
	***********************************************************/
    public String[][] GetAllClass(boolean b,boolean b1,String s1)
    {
    	try
    	{	
	    	Connection Conn = DBConn.getConn();
		    Statement stmt = Conn.createStatement(1004,1007);
		    String sql = "";
		    int iMax = 0;
		    int ID = Fun.StrToInt(s1);
			if(!b1)
			{
			    if (b)								//读取大类
			    {
			    	sql = "select * from BigClass";
			    }
			    else							    //读取小类
			    {
			    	sql = "select * from SmallClass";
			    	if (ID>0) 
			    		sql = "select * from SmallClass where BigClassID=" + ID;
			    }
			}else sql = "select * from Special";	//读取专题
			
		    ResultSet rs = stmt.executeQuery(sql); 
		    rs.last();
		    iMax = rs.getRow(); 
		    if(iMax==0) return null;		    
		    else
		    {
		    	String [][] sArray = new String[iMax][3];
			    rs.first();
				int i=0;
				do
				{
		    		sArray[i][0] = Integer.toString(rs.getInt(1));
		    		sArray[i][1] = rs.getString(2);
		    		if(!b) 
		    		{
		    			sArray[i][2] = Integer.toString(rs.getInt(4));
		    		}
		    		i++;
			    }while(rs.next());
				rs.close();
			    stmt.close();
			    Conn.close();
			    return sArray;
		    }
		    
		 }catch(Exception e){
		 	////e.printStackTrace();
		 	return null;
		 }
	}
  
    
    /*********************************************************
	* 函数名：ReadBigClass
	* 作  用：读取类别信息
	* 参  数：s:类别ID,b:为true表示为大类，否则为小类
	* 返回值：字符串数组型。类别的各项信息
	***********************************************************/
    public String[] ReadBigClass(String s,boolean b)
    {
    	try
    	{	
	    	Connection Conn = DBConn.getConn();
		    Statement stmt = Conn.createStatement(1004,1007);
		    ResultSet rs = null;
		    String sql = null;
		    String [] sa = new String [3];
		    if (b) 
		    {
		    	sql = "select * from BigClass where BigClassID=" + Fun.StrToInt(s);
			    rs = stmt.executeQuery(sql);
			    rs.next();
			    sa[0] = rs.getString("BigClassName");
			    sa[1] = rs.getString("BigClassInfo");
			 }
			 else
			 {
			 	sql = "select * from SmallClass where SmallClassID=" + Fun.StrToInt(s);
			 	rs = stmt.executeQuery(sql);
			    rs.next();
			    sa[0] = rs.getString("SmallClassName");
			    sa[1] = rs.getString("SmallClassInfo");
			    sa[2] = rs.getString("BigClassID");	
			 }
		 	rs.close();
		    stmt.close();
		    Conn.close();
		    return sa;
		 }catch(Exception e){
		 	////e.printStackTrace();
		 	return null;
		 }
	}
	
	
	
	/*********************************************************
	* 函数名：ReadClassNews()
	* 作  用：读类别中的新闻数
	* 参  数：s:大类ID,b:为true表示为大类，否则为小类
	*         b1:是否为专题
	* 返回值：整型数组
	***********************************************************/
    public int [] ReadClassNews(int ID,boolean b,boolean b1)
    {
    	try
    	{	
	    	Connection Conn1 = DBConn.getConn();
		    Statement stmt1 = Conn1.createStatement(1004,1007);
		    ResultSet rsNum = null;
		    String sql = null;
		    int [] iArray = new int[2];
			if(!b1)
			{
			    if (b) 
			    {
			    	sql = "select count(*) from News where BigClassID=" + ID;
				    rsNum = stmt1.executeQuery(sql);
				    if(rsNum.next()) iArray[0] = rsNum.getInt(1);
				    else iArray[0]=0;
				    sql = "select count(*) from SmallClass where BigClassID=" + ID;
				    rsNum = stmt1.executeQuery(sql);
				    if(rsNum.next()) iArray[1] = rsNum.getInt(1);
				    else iArray[1]=0;
				 }
				 else
				 {
				 	sql = "select count(*) from News where SmallClassID=" + ID;
				    rsNum = stmt1.executeQuery(sql);
				    if(rsNum.next()) iArray[0] = rsNum.getInt(1);
				    else iArray[0]=0;
				 }
			}
			else
			{
				sql = "select count(*) from News where SpecialID=" + ID;
			    rsNum = stmt1.executeQuery(sql);
			    if(rsNum.next()) iArray[0] = rsNum.getInt(1);
			    else iArray[0]=0;
			}
		 	rsNum.close();
		    stmt1.close();
		    Conn1.close();
		    return iArray;
		 }catch(Exception e){
		 	//e.printStackTrace();
		 	return null;
		 }
	}
    
    
    
    /*********************************************************
	* 函数名：ReadClass
	* 作  用：读取所有类别
	* 参  数：无
	* 返回值：字符串
	***********************************************************/
    public String ReadClass()
    {
    	try
    	{	
	    	Connection Conn = DBConn.getConn();
		    Statement stmt = Conn.createStatement(1004,1007);
		    Statement stmt1 = Conn.createStatement(1004,1007);
		    String sql = "select * from BigClass order by BigClassID";
		    ResultSet rs = stmt.executeQuery(sql);
		    StringBuffer sb = new StringBuffer();
		    
		    sb.append("<br><br><table width=\"90%\"  border=\"1\" align=\"center\" cellpadding=\"2\" cellspacing=\"0\">\r\n");
			sb.append("<tr><td height=\"27\" colspan=\"3\"  background=\"images/bg.gif\">");
			sb.append("<div align=\"center\" class=\"title\"><strong>新闻类别管理</strong></div></td>");
			sb.append("</tr>\r\n");
			sb.append("<tr>\r\n");
			sb.append("<td height=\"25\" colspan=\"4\"><strong>&nbsp;<a href=\"Admin_Class.jsp?Action=AddBigClass\">");
			sb.append("<font color=\"#ff6600\">[增加大类]</font></a></strong></td>\r\n");
			sb.append("</tr>\r\n");
			sb.append("<tr>\r\n");
			sb.append("<td height=\"25\"><div align=\"center\" class=\"chinese\"><b>类别名称</b></div></td>\r\n");
			sb.append("<td><div align=\"center\" class=\"chinese\"><b>添加时间</b></div></td>\r\n");
			sb.append("<td><div align=\"center\" class=\"chinese\"><b>操作</b></div></td>\r\n");
			sb.append("</tr>\r\n");
		
			//如果表中没有任何记录，刚给出提示信息
			if (!rs.next())
			{ 
				sb.append("<tr height=\"25\" bgcolor=\"#d6dff7\"  class=\"info1\"><td colspan=\"3\">\r\n");
				sb.append("<div align=\"center\"><b>暂时没有任何大类!</b></div></td></tr>\r\n");
			}
			else 
			{
				while(!rs.isAfterLast())
				{
					int BigClassID = rs.getInt("BigClassID");
					String BigClassName = rs.getString("BigClassName");
					String BigClassInfo = rs.getString("BigClassInfo");
					int [] iBNum = ReadClassNews(BigClassID,true,false);
					sb.append("<tr class=\"chinese\">\r\n");
					sb.append("<td width=\"40%\" height='22' title=\"");
					sb.append(BigClassInfo);
					sb.append("\"><div align=\"left\">&nbsp;<img src='images/+.gif'><b>");
					sb.append(BigClassName + "</b>&nbsp;[" + BigClassID + "]");
					sb.append("&nbsp;[" + iBNum[0] + "/" + iBNum[1] + "]</div></td>\r\n");
					sb.append("<td width=\"30%\"><div align=\"center\">");
					sb.append(rs.getString("AddTime"));
					sb.append("</div></td>\r\n");
					sb.append("<td width=\"30%\" class=\"chinese\"><div align=\"right\">[<a href=\"Admin_Class.jsp?Action=AddSClass&BigClassID=");
					sb.append(BigClassID);
					sb.append("\">增加子类</a>][<a href=\"Admin_Class.jsp?Action=EditBigClass&BigClassID=");
					sb.append(BigClassID);
					sb.append("\">修改</a>][<a href=\"Admin_Class.jsp?Action=DelBig&BigClassID=");
					sb.append(BigClassID);
					sb.append("\">删除</a>]</div></td>\r\n");
					sb.append("</tr>\r\n");
					
					String sql1 = "select * from SmallClass where BigClassID=" + BigClassID;
			    	ResultSet rs1= stmt1.executeQuery(sql1);
			
					while(rs1.next())
					{
						int SClassID = rs1.getInt("SmallClassID");
						int [] iSNum = ReadClassNews(SClassID,false,false);
						sb.append("<tr bgcolor=\"#d6dff7\">\r\n");
						sb.append("<td class=\"chinese\" height=\"20\" Title=\"" + rs1.getString("SmallClassInfo") + "\"><div align=\"left\" class=\"chines\">");
						sb.append("&nbsp;&nbsp;&nbsp;<img src='images/-.gif'>");
						sb.append(rs1.getString("SmallClassName") + "&nbsp;[" + SClassID + "]&nbsp;[" + iSNum[0] + "]</div></td>\r\n");
						sb.append("<td class=\"chinese\"><div align=\"center\" class=\"chines\">");
						sb.append(rs1.getString("AddTime") + "</div></td>\r\n");
						sb.append("<td><div align=\"right\" class=\"chinese\">[<a href=\"Admin_Class.jsp?Action=EditSClass&SClassID=");
						sb.append(SClassID);
						sb.append("\">修改</a>] [<a href=\"Admin_Class.jsp?Action=DelSmall&SClassID=");
						sb.append(SClassID);
						sb.append("\">删除</a>]</div></td>\r\n");
						sb.append("</tr>\r\n");
					}
					rs.next();	
				}
			}
			
			sb.append("</table>");
			sb.append("<br><br>");
			rs.close();
    		stmt.close();
    		Conn.close();
		    return sb.toString();
		}catch(Exception e)
        {
            //e.printStackTrace();
            //System.out.print(e.getMessage());
            return "No";
        }
    }
    
    
    
    /*********************************************************
	* 函数名：ReadSpecial
	* 作  用：读取专题列表
	* 参  数：无
	* 返回值：字符串
	***********************************************************/
    public String ReadSpecial()
    {
    	try
    	{	
	    	Connection Conn = DBConn.getConn();
		    Statement stmt = Conn.createStatement(1004,1007);
		    String sql = "select * from Special";
		    ResultSet rs = stmt.executeQuery(sql);
		    StringBuffer sb = new StringBuffer();

		    sb.append("<br><br><table width=\"90%\"  border=\"1\" align=\"center\" cellpadding=\"2\" cellspacing=\"0\">\r\n");
			sb.append("<tr><td height=\"27\" colspan=\"3\"  background=\"images/bg.gif\">");
			sb.append("<div align=\"center\" class=\"title\"><strong>专题管理</strong></div></td>");
			sb.append("</tr>\r\n");
			sb.append("<tr>\r\n");
			sb.append("<td height=\"25\" colspan=\"4\"><strong>&nbsp;<a href=\"Admin_Special.jsp?Action=Add\">");
			sb.append("<font color=\"#ff6600\">[增加专题]</font></a></strong></td>\r\n");
			sb.append("</tr>\r\n");
			sb.append("<tr>\r\n");
			sb.append("<td height=\"25\"><div align=\"center\" class=\"chinese\"><b>专题名称 专题ID/新闻数</b></div></td>\r\n");
			sb.append("<td><div align=\"center\" class=\"chinese\"><b>添加时间</b></div></td>\r\n");
			sb.append("<td><div align=\"center\" class=\"chinese\"><b>操作</b></div></td>\r\n");
			sb.append("</tr>\r\n");
			
			//如果表中没有任何记录，刚给出提示信息
			if (!rs.next())
			{ 
				sb.append("<tr height=\"25\" bgcolor=\"#d6dff7\"  class=\"info1\"><td colspan=\"3\">\r\n");
				sb.append("<div align=\"center\"><b>暂时没有任何专题!</b></div></td></tr>\r\n");
			}
			else 
			{
				while(!rs.isAfterLast())
				{
					int SpecialID = rs.getInt("SpecialID");
					String SpecialName = rs.getString("SpecialName");
					String SpecialInfo = rs.getString("SpecialInfo");
					int [] iSpNum = ReadClassNews(SpecialID,false,true);
					sb.append("<tr class=\"chinese\" bgcolor=\"#d6dff7\">\r\n");
					sb.append("<td width=\"50%\" height='22' title=\"");
					sb.append(SpecialInfo);
					sb.append("\"><div align=\"left\">&nbsp;<img src='images/+.gif'><b>");
					sb.append(SpecialName + "</b>&nbsp;[" + SpecialID + "]/");
					sb.append("[" + iSpNum[0] + "]</div></td>\r\n");
					sb.append("<td width=\"30%\"><div align=\"center\">");
					sb.append(rs.getString("SpecialTime"));
					sb.append("</div></td>\r\n");
					sb.append("<td width=\"20%\" class=\"chinese\"><div align=\"right\">");
					sb.append("[<a href=\"Admin_Special.jsp?Action=Edit&SpecialID=");
					sb.append(SpecialID);
					sb.append("\">修改</a>][<a href=\"Admin_Special.jsp?Action=Del&SpecialID=");
					sb.append(SpecialID);
					sb.append("\">删除</a>]</div></td>\r\n");
					sb.append("</tr>\r\n");
					rs.next();
				}
			}
			
			sb.append("</table>");
			sb.append("<br><br>");
			rs.close();
    		stmt.close();
    		Conn.close();
		    return sb.toString();
		}catch(Exception e)
        {
            //e.printStackTrace();
            //System.out.print(e.getMessage());
            return "No";
        }
    }
    
    
    /*********************************************************
	* 函数名：ReadSpecial()
	* 作  用：读取某一专题的信息
	* 参  数：s:专题ID
	* 返回值：字符串数组型。类别的各项信息
	***********************************************************/
    public String[] ReadSpecial(String s)
    {
    	try
    	{	
	    	Connection Conn = DBConn.getConn();
		    Statement stmt = Conn.createStatement(1004,1007);
		    ResultSet rs = null;
		    String sql = null;
		    String [] sa = new String [2];
		    int SpecialID = Fun.StrToInt(s);
		    if(SpecialID>0)
		    {
		    	sql = "select * from Special where SpecialID=" + SpecialID;
			    rs = stmt.executeQuery(sql);
			    rs.next();
			    sa[0] = rs.getString(2);
			    sa[1] = rs.getString(3);
			 	rs.close();
			    stmt.close();
			    Conn.close();
			    return sa;
			 }
			 else return null;
		 }catch(Exception e){
		 	//e.printStackTrace();
		 	return null;
		 }
	}
    
    
    
   /*********************************************************
	* 函数名：AddBigClass
	* 作  用：添加大类
	* 参  数：s1，大类ID；s2，大类标题；
	*　　     s3，大类说明；s4,操作用户，s5，IP地址
	* 返回值：字符串型。返回操作的信息
	***********************************************************/
    public String AddBigClass(String s1,String s2,String s3,String s4,String s5)
    {			
		try
    	{
	    	boolean OK = true;
    		Connection Conn = DBConn.getConn();
	    	Statement stmt = Conn.createStatement(1004,1007);
	    	Statement stmt1 = Conn.createStatement(1004,1007);
	    	ResultSet rs = null;
    		String sError = "";
	    	int BigClassID = Fun.StrToInt(s1);
	    	String BigClassName = Fun.CheckReplace(s2);
	    	String BigClassInfo = Fun.CheckReplace(s3);
	    	String [] sa1 = new String [3];
	    	String [] sa2 = new String [3];
	    	sa1[0] = s1;
	    	sa1[1] = s2;
	    	sa1[2] = s3;
	    	sa2[0] = "大类排序";
	    	sa2[1] = "大类标题";
	    	sa2[2] = "大类说明";
	    	String s = Fun.CheckDate(sa1,sa2);
	    	if (BigClassID==0)
	    	{
	    		OK = false;
	    		sError = "类别排序必须是大于 0 的整数!";
	    	}
	    	if (!s.equals("Yes"))
	    	{
	    		OK = false;
	    		sError = s; 
	    	}	
	    	if(OK)
		    {
		    	String sql1 = "select * from BigClass where BigClassID=" + BigClassID;
		    	String sql2 = "select * from BigClass where BigClassName='" + BigClassName + "'";
		    	ResultSet rs1 = stmt.executeQuery(sql1);
		    	ResultSet rs2 = stmt1.executeQuery(sql2);
		    	if (rs1.next()) 
		    	{
		    		rs1.close();
		    		OK = false;
		    		sError = "<li>该排序数字 [ " + s1 + " ] 已经存在，不能重复!";
		    	}
		    	if (rs2.next())
		    	{
		    		rs2.close();
		    		stmt1.close();
		    		OK = false;
		    		sError += "<li>该大类标题 [ " + s2 + " ] 已经存在，不能重复!";	
		    	}
		    }
	    	if(OK)
	    	{
		    	
		    	String NowTime = (new java.util.Date()).toLocaleString();
		    	String [] sLog = new String[5];
		    	String sql = "insert into BigClass (BigClassID,BigClassName,";
		    	sql += "BigClassInfo,AddTime) values (";
		    	sql += BigClassID + ",";
				sql += "'" + BigClassName + "',";
				sql += "'" + BigClassInfo + "',";
				sql += "'" + NowTime + "')";
		    	stmt.executeUpdate(sql);
		    	sLog[0] = Fun.CheckReplace(s4);
		    	sLog[1] = "添加大类[" + BigClassName + "]";
		    	sLog[2] = NowTime;
		    	sLog[3] = Fun.CheckReplace(s5); 
		    	sLog[4] = "Yes";
		    	Fun.AddLog(sLog);
				stmt.close();
				Conn.close();
				
		    	return "Yes";
		    }
		    else return sError;
	    }catch(Exception e)
        {
            ////e.printStackTrace();
            //ystem.out.print(e.getMessage());
            return "操作失败!";
        }
     }
     
    
   /*********************************************************
	* 函数名：EditBigClass
	* 作  用：修改大类
	* 参  数：s1，大类新ID；s2，大类标题；
	*　　     s3，大类说明；s4，大类原ID；s5，原大类标题；
	*         s6，用户，s7，IP
	* 返回值：字符串型。返回操作的信息
	***********************************************************/ 
    public String EditBigClass(String s1,String s2,String s3,String s4,String s5,String s6,String s7)
    {			
		try
    	{
	    	Connection Conn = DBConn.getConn();
	    	Connection Conn1 = DBConn.getConn();
	    	Statement stmt = Conn.createStatement(1004,1007);
	    	Statement stmt1 = Conn1.createStatement(1004,1007);
	    	Statement stmt2 = Conn1.createStatement(1004,1007);
	    	ResultSet rs1 = null;
	    	ResultSet rs2 = null;
	    	boolean OK=true;
	    	String sError="";
	    	int NewBigClassID = Fun.StrToInt(s1);
	    	int OldBigClassID = Fun.StrToInt(s4);
	    	String NewBigClassName = Fun.CheckReplace(s2);
	    	String OldBigClassName = Fun.CheckReplace(s5);
	    	String BigClassInfo = Fun.CheckReplace(s3);
	    	String [] sa1 = new String [3];
	    	String [] sa2 = new String [3];
	    	sa1[0] = s1;
	    	sa1[1] = s2;
	    	sa1[2] = s3;
	    	sa2[0] = "大类排序";
	    	sa2[1] = "大类标题";
	    	sa2[2] = "大类说明";
	    	String s = Fun.CheckDate(sa1,sa2);
	    	if (NewBigClassID==0)
	    	{
	    		OK = false;
	    		sError = "类别排序必须是大于 0 的整数!";
	    	}
	    	if (!s.equals("Yes"))
	    	{
	    		OK = false;
	    		sError = s; 
	    	}
		    if (OK)
		    {
		    	String sql1 = "select * from BigClass where BigClassID=" + NewBigClassID;
		    	String sql2 = "select * from BigClass where BigClassName='" + NewBigClassName + "'";
		    	rs1 = stmt1.executeQuery(sql1);
		    	rs2 = stmt2.executeQuery(sql2);
		    	rs1.last();
		    	rs2.last(); 
		    	if ((NewBigClassID != OldBigClassID) && rs1.getRow()>0) 
		    	{
		    		rs1.close();
		    		stmt1.close();
		    		sError = "<li>该排序数字 [ " + s1 + " ] 已经存在，不能重复!";
		    		OK = false;
		    	}
		     	if ((!OldBigClassName.equals(NewBigClassName)) && rs2.getRow()>0)
		    	{
		    		rs2.close();
		    		stmt2.close();
		    		sError += "<li>该大类标题 [ " + NewBigClassName + " ] 已经存在，不能重复!";
		    		OK = false;	
		    	}
		    }
	    	if(OK)
	    	{
		    	String sql = "update BigClass set ";
		    	sql += "BigClassID='" + NewBigClassID + "',";
		    	sql += "BigClassName='" + NewBigClassName + "',";
		    	sql += "BigClassInfo='" + BigClassInfo + "'";
		    	sql += " where BigClassID=" + OldBigClassID;
		    	String sql1 = "update SmallClass set BigClassID=" + NewBigClassID + " where BigClassID=" + OldBigClassID;
		    	String sql2 = "update News set BigClassID=" + NewBigClassID + " where BigClassID=" + OldBigClassID;
		    	try 
		    	{
			    	Conn.setAutoCommit(false); 		//更改JDBC默认事务提交方式
			    	stmt.executeUpdate(sql);
			    	stmt.executeUpdate(sql1);
			    	stmt.executeUpdate(sql2);
			    	Conn.commit();					//确认事务
			    	Conn.setAutoCommit(true);		//设为默认
					stmt.close();
					Conn.close();
					String NowTime = (new java.util.Date()).toLocaleString();
			    	String [] sLog = new String[5];
					sLog[0] = Fun.CheckReplace(s6);
			    	sLog[1] = "修改原大类[" + OldBigClassName + "]为[" + NewBigClassName + "]";
			    	sLog[2] = NowTime;
			    	sLog[3] = Fun.CheckReplace(s7); 
			    	sLog[4] = "Yes";
			    	Fun.AddLog(sLog);
			    	return "Yes";
			    }catch (Exception e) {
					Conn.rollback();		//回滚JDBC事务
					//e.printStackTrace();
					Conn.close();
					return "事务操作失败!";
					}
		    }
		    else return sError;
	    }catch(Exception e)
        {
            ////e.printStackTrace();
            //System.out.print(sql);
            return "操作失败!";
        }
     }
     
     
   /*********************************************************
	* 函数名：DelBigClass
	* 作  用：删除大类
	* 参  数：s1，大类新ID，s2，操作用户，s3，IP地址
	* 返回值：字符串型。返回操作的信息
	***********************************************************/ 
    public boolean DelBigClass(String s1,String s2,String s3)
    {			
		
		String NowTime = (new java.util.Date()).toLocaleString();
		String [] sLog = new String[5];
		sLog[0] = s2;
		sLog[2] = NowTime;
		sLog[3] = s3; 
		try
    	{
	    	Connection Conn = DBConn.getConn();
	    	Statement stmt = Conn.createStatement(1004,1007);
	    	boolean OK=true;
	    	int BigClassID = Fun.StrToInt(s1);
	    	String sql1 = "delete from BigClass where BigClassID=" + BigClassID;
	    	String sql2 = "delete from SmallClass where BigClassID=" + BigClassID;
	    	String sql3 = "delete from News where BigClassID=" + BigClassID;	
		    if (BigClassID>0)
	    	{ 
			    try{	
			    	Conn.setAutoCommit(false); 		//更改JDBC默认事务提交方式	
			    	stmt.executeUpdate(sql1);
			    	stmt.executeUpdate(sql2);
			    	stmt.executeUpdate(sql3);
			    	Conn.commit();
			    	Conn.setAutoCommit(true); 
					stmt.close();
					Conn.close();
					sLog[0] = Fun.CheckReplace(s2);
			    	sLog[1] = "删除ID为 [ " + s1 + " ] 的大类";
			    	sLog[2] = NowTime;
			    	sLog[3] = Fun.CheckReplace(s3); 
			    	sLog[4] = "Yes";
			    	Fun.AddLog(sLog);
			    	return true;
			     }catch (Exception e) {
						Conn.rollback();			//回滚JDBC事务
						//e.printStackTrace();
						Conn.close();
						return false;
					}
			}else return false;
	    }catch(SQLException e)
        {
            ////e.printStackTrace();
    		sLog[1] = "删除ID为[" + s1 + "]的大类，操作出错!"; 
	    	sLog[4] = "No";
	    	Fun.AddLog(sLog);
            //System.out.print(sql);
            return false;
        }
     }
 
 	
 	/*********************************************************
	* 函数名：AddSClass
	* 作  用：添加大类
	* 参  数：s2，小类标题；s3，小类说明
	*　　     s4,所属大类，s5,操作用户，s6，IP地址
	* 返回值：字符串型。返回操作的信息
	***********************************************************/
    public String AddSClass(String s2,String s3,String s4,String s5,String s6)
    {			
		try
    	{
	    	boolean OK = true;
    		Connection Conn = DBConn.getConn();
	    	Statement stmt = Conn.createStatement(1004,1007);
	    	Statement stmt1 = Conn.createStatement(1004,1007);
	    	ResultSet rs = null;
    		String sError = "";
    		int BigClassID = Fun.StrToInt(s4);
	    	String SClassName = Fun.CheckReplace(s2);
	    	String SClassInfo = Fun.CheckReplace(s3);
	    	String [] sa1 = new String [2];
	    	String [] sa2 = new String [2];
	    	sa1[0] = s2;
	    	sa1[1] = s3;
	    	sa2[0] = "小类标题";
	    	sa2[1] = "小类说明";
	    	String s = Fun.CheckDate(sa1,sa2);
	    	if (!s.equals("Yes"))
	    	{
	    		OK = false;
	    		sError = s; 
	    	}
	    	if (BigClassID==0)
	    	{
	    		OK = false;
	    		sError = "所属大类参数传递错误，请重试!"; 
	    	}	
	    	if(OK)
		    {
		    	String sql2 = "select * from SmallClass where SmallClassName='" + SClassName + "' and BigClassID=" + BigClassID;
		    	ResultSet rs2 = stmt1.executeQuery(sql2);
		    	if (rs2.next())
		    	{
		    		rs2.close();
		    		stmt1.close();
		    		OK = false;
		    		sError += "<li>该小类标题 [ " + s2 + " ] 已经存在，不能重复!";	
		    	}
		    }
	    	if(OK)
	    	{	
		    	String NowTime = (new java.util.Date()).toLocaleString();
		    	String [] sLog = new String[5];
		    	String sql = "insert into SmallClass (SmallClassName,";
		    	sql += "SmallClassInfo,BigClassID,AddTime) values (";
				sql += "'" + SClassName + "',";
				sql += "'" + SClassInfo + "',";
				sql += BigClassID + ",";
				sql += "'" + NowTime + "')";
		    	stmt.executeUpdate(sql);
		    	sLog[0] = Fun.CheckReplace(s5);
		    	sLog[1] = "添加小类[" + SClassName + "]";
		    	sLog[2] = NowTime;
		    	sLog[3] = Fun.CheckReplace(s6); 
		    	sLog[4] = "Yes";
		    	Fun.AddLog(sLog);
				stmt.close();
				Conn.close();
		    	return "Yes";
		    }
		    else return sError;
	    }catch(SQLException e)
        {
            ////e.printStackTrace();
            //ystem.out.print(e.getMessage());
            return "操作失败!";
        }
     }
 
 
 
 	/*********************************************************
	* 函数名：EditSClass
	* 作  用：修改小类
	* 参  数：s0，所属大类，s1,小类ID;s2，小类标题；
	*　　     s3，小类说明；s4，原小类标题；
	*         s5，用户，s6，IP
	* 返回值：字符串型。返回操作的信息
	***********************************************************/ 
    public String EditSClass(String s0,String s1,String s2,String s3,String s4,String s5,String s6)
    {			
		try
    	{
	    	Connection Conn = DBConn.getConn();
	    	Statement stmt = Conn.createStatement(1004,1007);
	    	Statement stmt1 = Conn.createStatement(1004,1007);
	    	ResultSet rs1 = null;
	    	ResultSet rs2 = null;
	    	boolean OK=true;
	    	String sError="";
	    	int BigClassID = Fun.StrToInt(s0);
	    	int SmallClassID = Fun.StrToInt(s1);
	    	String NewSClassName = Fun.CheckReplace(s2);
	    	String OldSClassName = Fun.CheckReplace(s4);
	    	String SClassInfo = Fun.CheckReplace(s3);
	    	String [] sa1 = new String [3];
	    	String [] sa2 = new String [3];
	    	sa1[0] = s0;
	    	sa1[1] = s2;
	    	sa1[2] = s3;
	    	sa2[0] = "所属大类";
	    	sa2[1] = "小类标题";
	    	sa2[2] = "小类说明";
	    	String s = Fun.CheckDate(sa1,sa2);
	    	if (!s.equals("Yes"))
	    	{
	    		OK = false;
	    		sError = s; 
	    	}
	    	if (SmallClassID==0 || BigClassID==0)
	    	{
	    		OK = false;
	    		sError = "类别参数传递错误，请重试!"; 
	    	}
		    if (OK)
		    {
		    	String sql1 = "select * from SmallClass where SmallClassName='" + NewSClassName + "'";
		    	rs1 = stmt1.executeQuery(sql1);
		    	rs1.last();
		     	if ((!OldSClassName.equals(s2)) && rs1.getRow()>0)
		    	{
		    		rs1.close();
		    		stmt1.close();
		    		sError += "<li>该小类标题 [ " + s2 + " ] 已经存在，不能重复!";
		    		OK = false;	
		    	}
		    }
	    	if(OK)
	    	{
		    	String sql = "update SmallClass set ";
		    	sql += "SmallClassName='" + NewSClassName + "',";
		    	sql += "SmallClassInfo='" + SClassInfo + "',";
		    	sql += "BigClassID=" + BigClassID;
		    	sql += " where SmallClassID=" + SmallClassID;
		 		//Conn.setAutoCommit(false);
		    	stmt.executeUpdate(sql);
		    	//Conn.commit();
		    	//Conn.setAutoCommit(true);
				stmt.close();
				Conn.close();
				String NowTime = (new java.util.Date()).toLocaleString();
		    	String [] sLog = new String[5];
				sLog[0] = Fun.CheckReplace(s5);
		    	sLog[1] = "修改原小类[" + OldSClassName + "]为[" + NewSClassName + "]";
		    	sLog[2] = NowTime;
		    	sLog[3] = Fun.CheckReplace(s6); 
		    	sLog[4] = "Yes";
		    	Fun.AddLog(sLog);
		    	return "Yes";
		    }
		    else return sError;
	    }catch(SQLException e)
        {
            ////e.printStackTrace();
            //System.out.print(sql);
            return "操作失败!";
        }
     }
     
 	
 	
 	/*********************************************************
	* 函数名：DelSClass
	* 作  用：删除小类
	* 参  数：s1，小类ID，s2，操作用户，s3，IP地址
	* 返回值：成功　true,否则false
	***********************************************************/ 
    public boolean DelSClass(String s1,String s2,String s3)
    {			
		String NowTime = (new java.util.Date()).toLocaleString();
		String [] sLog = new String[5];
		sLog[0] = s2;
		sLog[2] = NowTime;
		sLog[3] = s3; 
		try
    	{
	    	Connection Conn = DBConn.getConn();
	    	Statement stmt = Conn.createStatement(1004,1007);
	    	boolean OK=true;
	    	int SClassID = Fun.StrToInt(s1);
	    	String sql1 = "delete from SmallClass where SmallClassID=" + SClassID;
	    	String sql2 = "delete from News where SmallClassID=" + SClassID;	
			if(SClassID>0)
			{   
				try
			    {
			    	//用事物处理，防止程序出错。
			    	Conn.setAutoCommit(false);	//更改默认事物处理
			    	stmt.executeUpdate(sql1);
			    	stmt.executeUpdate(sql2);
			    	Conn.commit();
			    	Conn.setAutoCommit(true);
					stmt.close();
					Conn.close();
			    	sLog[1] = "删除ID为 [ " + s1 + " ] 的小类"; 
			    	sLog[4] = "Yes";
			    	Fun.AddLog(sLog);
			    	return true;
			    }catch (Exception e) {
						Conn.rollback();		//回滚JDBC事务
						////e.printStackTrace();
						Conn.close();
						return false;
						}  
			}else return false;
	    }catch(SQLException e)
        {
            ////e.printStackTrace();
    		sLog[1] = "删除ID为[" + s1 + "]的小类，操作出错!"; 
	    	sLog[4] = "No";
	    	Fun.AddLog(sLog);
            //System.out.print(sql);
            return false;
        }
     }
 
 	
 	/*********************************************************
	* 函数名：AddSpecial
	* 作  用：添加大类
	* 参  数：s1，专题名称；s2，专题说明
	*　		  s3,操作用户，s4，IP地址
	* 返回值：字符串型。返回操作的信息
	***********************************************************/
    public String AddSpecial(String s1,String s2,String s3,String s4)
    {			
		try
    	{
	    	boolean OK = true;
    		Connection Conn = DBConn.getConn();
	    	Statement stmt = Conn.createStatement(1004,1007);
	    	ResultSet rs = null;
    		String sError = "";
	    	String SpecialName = Fun.CheckReplace(s1);
	    	String SpecialInfo = Fun.CheckReplace(s2);
	    	String [] sa1 = new String [2];
	    	String [] sa2 = new String [2];
	    	sa1[0] = s1;
	    	sa1[1] = s2;
	    	sa2[0] = "专题名称";
	    	sa2[1] = "专题说明";
	    	String s = Fun.CheckDate(sa1,sa2);
	    	if (!s.equals("Yes"))
	    	{
	    		OK = false;
	    		sError = s; 
	    	}	
	    	if(OK)
		    {
		    	String sql1 = "select * from Special where SpecialName='" + SpecialName + "'";
		    	ResultSet rs1 = stmt.executeQuery(sql1);
		    	if (rs1.next()) 
		    	{
		    		rs1.close();
		    		OK = false;
		    		sError = "<li>该排专题名称 [ " + s1 + " ] 已经存在，不能重复!";
		    	}
		    }
	    	if(OK)
	    	{	
		    	String NowTime = (new java.util.Date()).toLocaleString();
		    	String [] sLog = new String[5];
		    	String sql = "insert into Special (SpecialName,";
		    	sql += "SpecialInfo,SpecialTime) values (";
				sql += "'" + SpecialName + "',";
				sql += "'" + SpecialInfo + "',";
				sql += "'" + NowTime + "')";
		    	stmt.executeUpdate(sql);
		    	sLog[0] = Fun.CheckReplace(s3);
		    	sLog[1] = "添加专题[" + SpecialName + "]";
		    	sLog[2] = NowTime;
		    	sLog[3] = Fun.CheckReplace(s4); 
		    	sLog[4] = "Yes";
		    	Fun.AddLog(sLog);
				stmt.close();
				Conn.close();
		    	return "Yes";
		    }
		    else return sError;
	    }catch(Exception e)
        {
            //e.printStackTrace();
            //ystem.out.print(e.getMessage());
            return "操作失败!";
        }
     }
 
 
 
 
 	/*********************************************************
	* 函数名：EditSpecial
	* 作  用：修改专题
	* 参  数：s1，专题名称；s2，专题说明，s3,操作用户
	*　		  s4，IP地址，s5，专题ID，s6，原专题名称
	* 返回值：字符串型。返回操作的信息
	***********************************************************/ 
    public String EditSpecial(String s1,String s2,String s3,String s4,String s5,String s6)
    {			
		try
    	{
	    	Connection Conn = DBConn.getConn();
	    	Statement stmt = Conn.createStatement(1004,1007);
	    	ResultSet rs = null;
	    	boolean OK=true;
	    	String sError="";
	    	int SpecialID = Fun.StrToInt(s5);
	    	String SpecialName = Fun.CheckReplace(s1);
	    	String OldSpecialName = Fun.CheckReplace(s6);
	    	String SpecialInfo = Fun.CheckReplace(s2);
	    	String [] sa1 = new String [2];
	    	String [] sa2 = new String [2];
	    	sa1[0] = s1;
	    	sa1[1] = s2;
	    	sa2[0] = "专题名称";
	    	sa2[1] = "专题说明";
	    	String s = Fun.CheckDate(sa1,sa2);
	    	if (!s.equals("Yes"))
	    	{
	    		OK = false;
	    		sError = s; 
	    	}
	    	if (SpecialID==0)
	    	{
	    		OK = false;
	    		sError = "参数传递错误"; 
	    	}
		    if (OK)
		    {
		    	String sql1 = "select * from Special where SpecialName='" + SpecialName + "'";
		    	rs = stmt.executeQuery(sql1);
		    	rs.last();
		     	if ((!OldSpecialName.equals(SpecialName)) && rs.getRow()>0)
		    	{
		    		rs.close();
		    		stmt.close();
		    		sError += "<li>该专题名称 [ " + SpecialName + " ] 已经存在，不能重复!";
		    		OK = false;	
		    	}
		    }
	    	if(OK)
	    	{
		    	String sql = "update Special set ";
		    	sql += "SpecialName='" + SpecialName + "',";
		    	sql += "SpecialInfo='" + SpecialInfo + "'";
		    	sql += " where SpecialID=" + SpecialID;

		    	stmt.executeUpdate(sql);
				stmt.close();
				Conn.close();
				String NowTime = (new java.util.Date()).toLocaleString();
		    	String [] sLog = new String[5];
				sLog[0] = Fun.CheckReplace(s3);
		    	sLog[1] = "修改原专题[" + OldSpecialName + " ]为[" + SpecialName + " ]";
		    	sLog[2] = NowTime;
		    	sLog[3] = Fun.CheckReplace(s4); 
		    	sLog[4] = "Yes";
		    	Fun.AddLog(sLog);
		    	return "Yes";
			    
		    }
		    else return sError;
	    }catch(Exception e)
        {
            //e.printStackTrace();
            //System.out.print(sql);
            return "操作失败!";
        }
     }
 
 	
 	
 	/*********************************************************
	* 函数名：DelSpecial
	* 作  用：删除专题
	* 参  数：s1，专题ID，s2，操作用户，s3，IP地址
	* 返回值：成功　true,否则false
	***********************************************************/ 
    public boolean DelSpecial(String s1,String s2,String s3)
    {			
		String NowTime = (new java.util.Date()).toLocaleString();
		String [] sLog = new String[5];
		sLog[0] = s2;
		sLog[2] = NowTime;
		sLog[3] = s3; 
		try
    	{
	    	Connection Conn = DBConn.getConn();
	    	Statement stmt = Conn.createStatement(1004,1007);
	    	boolean OK=true;
	    	int SpecialID = Fun.StrToInt(s1);
	    	String sql1 = "delete from Special where SpecialID=" + SpecialID;
	    	String sql2 = "delete from News where SpecialID=" + SpecialID;	
			if(SpecialID>0)
			{   
				try
			    {
			    	//用事物处理，防止程序出错。
			    	Conn.setAutoCommit(false);	//更改默认事物处理
			    	stmt.executeUpdate(sql1);
			    	stmt.executeUpdate(sql2);
			    	Conn.commit();
			    	Conn.setAutoCommit(true);
					stmt.close();
					Conn.close();
			    	sLog[1] = "删除ID为[" + s1 + "]的专题"; 
			    	sLog[4] = "Yes";
			    	Fun.AddLog(sLog);
			    	return true;
			    }catch (Exception e) {
						Conn.rollback();		//回滚JDBC事务
						////e.printStackTrace();
						Conn.close();
						return false;
						}  
			}else return false;
	    }catch(SQLException e)
        {
            //e.printStackTrace();
    		sLog[1] = "删除ID为[" + s1 + "] 的专题，操作出错!"; 
	    	sLog[4] = "No";
	    	Fun.AddLog(sLog);
            //System.out.print(sql);
            return false;
        }
     }
 	
 	
 	
 	 //测试
	public static void main(String[] args)
	{	
		AdminClass aClass = new AdminClass();
		//System.out.print(aClass.AddBigClass("11","地方新闻1","地方","znl","172.16.166.51"));
		//boolean b = aClass.DelBigClass("7","dream","172.16.166.50");
		//if (b) System.out.println("Yes");
		//else System.out.println("No");
		//System.out.print(aClass.EditBigClass("5","国内新闻","国内新闻","10","国际新闻","dream","localhost"));
		//System.out.print(aClass.ReadClass("AdminClass.jsp","1"));
		//System.out.print(aClass.EditSClass("1","5","军事新闻","军事新闻","军事","dream","localhost"));
		String [][] as1 = aClass.GetAllClass(true,true,null);
	
		//String [][] sa = new String[5][2];
		//System.out.print(sa.length);
		//if(aClass.DelSClass("fjdlf","http://ww","2")) System.out.print("Yes");
		//else System.out.print("No");
		if(as1==null) System.out.print("null");
		else
		{
			for(int i=0;i<as1.length;i++)
			{
				System.out.println(as1[i][0]+","+as1[i][1]);
			}
		}
		//System.out.print(aClass.ReadSpecial("1")[0]);
	}
}