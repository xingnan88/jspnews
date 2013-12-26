
/***************************************************
 *  
 *  源文件名:  News.java
 *  功    能： 梦想年华新闻系统 - 新闻管理类
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

public class News
{

    DBConnection DBConn = new DBConnection();
    Function Fun = new Function(); 
    
    public News()
    {
    	
    } 
    
    
    /*********************************************************
	* 函数名：ReadNews
	* 作  用：读取类别信息
	* 参  数：s0:新闻ID
	* 返回值：字符串数组型。类别的各项信息
	***********************************************************/
    public String[] ReadNews(String s0)
    {
    	try
    	{	
	    	Connection Conn = DBConn.getConn(); 
		    Statement stmt = Conn.createStatement(1004,1007);
		    ResultSet rs = null;
		    String sql = null;
		    int NewsID = Fun.StrToInt(s0);
		    String [] s = new String[16];
	    	if (NewsID==0)
	    		return null;
	    	else
	    	{
		    	sql = "select * from News where NewsID=" + NewsID;
			    rs = stmt.executeQuery(sql);
			    rs.next();
			    for (int i=0;i<s.length;i++)
			    {
				    s[i] = rs.getString(i+2);
			    }
			 	rs.close();
			    stmt.close();
			    Conn.close();
			    return s;
			 }
		 }catch(Exception e){
		 	//e.printStackTrace();
		 	return null;
		 }
	}
    
     
    /*********************************************************
	* 函数名:ListNews
	* 作  用:添加新闻
	* 参  数：sPage,页面地址; strPage,第几页
	* 返回值:字符串型。返回操作结果的信息
	***********************************************************/
    public String ListNews(String sPage,String strPage)
    {			
		try
    	{
	    	boolean OK = true;
    		Connection Conn = DBConn.getConn();
	    	Statement stmt = Conn.createStatement(1004,1007);
	    	ResultSet rs = null;
    		StringBuffer sb = new StringBuffer();
    		String [] s1 = null;
			int i;
			int intPage = 1; 			
			int intPageSize = 15;
			if (Fun.ReadConfig() && Fun.AdminNewsListNum>0) intPageSize = Fun.AdminNewsListNum;
			
	    	String sSql = "select * from News order by NewsID desc";
	    	rs = stmt.executeQuery(sSql);
	    	
	    	//输出表单信息
    		sb.append("<br><br><table width=\"90%\"  border=\"1\" align=\"center\" cellpadding=\"1\" cellspacing=\"0\">\r\n");
			sb.append("<tr background=\"images/bg.gif\">\r\n");
			sb.append("<td height=\"27\" colspan=\"4\" background=\"images/bg.gif\"><div align=\"center\">");
			sb.append("<strong class=\"title\">新闻列表</strong></div></td></tr>\r\n");
			sb.append("<tr class=\"chinese\" height=\"25\">\r\n");
			sb.append("<td width=\"8%\" height=\"25\"><div align=\"center\" class=\"chinese\"><strong>ID</strong></div></td>\r\n");
			sb.append("<td width=\"50%\"><div align=\"center\" class=\"chinese\"><strong>新闻标题</strong></div></td>\r\n");
			sb.append("<td width=\"26%\"><div align=\"center\" class=\"chinese\"><strong>发布时间</strong></div></td>\r\n");
			sb.append("<td width=\"16%\"><div align=\"center\" class=\"chinese\"><strong>操作</strong></div></td>\r\n");
			sb.append("</tr>\r\n");
			sb.append("<tr>\r\n");
			sb.append("<td height=\"30\" colspan=\"4\" align=\"center\">");
			sb.append("<form name=\"ListNews\" style=\"margin:0 2px 0 0; height:25px;width:500px;font-size:12px\">");
			sb.append("<div align=\"center\"><font color=\"#ff6600\"><strong>新闻管理快速通道</strong>&nbsp;&nbsp;");
			sb.append("请输入要操作新闻的ID:&nbsp;</font>");
			sb.append("<input id=\"NewsID\" type=\"text\" size=\"10\" maxlength=\"8\" class=\"chinese\">&nbsp;");
			sb.append("<input name=\"Submit\" type=\"button\" class=\"button\" value=\"修 改\" onClick=\"fastNews(false);\">&nbsp;");
			sb.append("<input name=\"Submit2\" type=\"button\" class=\"button\" value=\"删 除\" onClick=\"fastNews(true);\">");
			sb.append("</form>");
			sb.append("</td></tr>\r\n");
			
	    	//如果表中没有任何记录，刚给出提示信息
			if (!rs.next())
			{ 
				sb.append("<tr height=\"25\" bgcolor=\"#d6dff7\"  class=\"info1\"><td colspan=\"4\">\r\n");
				sb.append("<div align=\"center\"><b>暂时还没有新闻!</b></div></td></tr>\r\n");
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
				    int NewsID = rs.getInt("NewsID");	
					String NewsTitle = rs.getString("NewsTitle");
					String sTitle = NewsTitle;
					String sImg = "news.gif";
					String sImgTitle = "普通新闻";
					
					if (NewsTitle.length()>20) sTitle = NewsTitle.substring(0,20) + "..";
					
					if(rs.getString("IsImg").equals("Yes")) 
					{
						sImg = "ImgNews.gif";
						sImgTitle = "图片新闻";
					}
					else if(rs.getString("IsImg").equals("Yes")) 
					{
						sImg = "TopNews.gif";
						sImgTitle = "头条新闻";
					}
					sb.append("<tr bgcolor=\"#d6dff7\" height=\"22\">\r\n");
					sb.append("<td bgcolor=\"#d6dff7\" height=\"22\"><div align=\"center\" class=\"chinese\">" + NewsID + "</div></td>\r\n");
					sb.append("<td bgcolor=\"#d6dff7\"><div class=\"news\">&nbsp");
					sb.append("<img src=\"../pic/" + sImg + "\" alt=\"" + sImgTitle + "\">&nbsp;");
					sb.append("<a href=\"../ShowNews.jsp?NewsID=" + NewsID + "\"  Title=\"" + NewsTitle + "\">" + sTitle + "</div></td>\r\n");
					sb.append("<td bgcolor=\"#d6dff7\"><div align=\"center\" class=\"chinese\">" + rs.getString("NewsTime") + "</div></td>\r\n");
					sb.append("<td bgcolor=\"#d6dff7\"><div align=\"center\"  class=\"chinese\">");
					sb.append("<a href=\"ModifyNews.jsp?NewsID=" + NewsID + "\">[修改]</a>");
					sb.append("<a href=\"DelNews.jsp?NewsID=" + NewsID + "\">[删除]</a> </div></td>\r\n");
					sb.append("</tr>\r\n");
					rs.next(); 
					i++;
				}
				sb.append("</table>\r\n");
				sb.append(Fun.Page(sPage,rs,intPage,intPageSize));
			}
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
	* 函数名:AddNews
	* 作  用:添加新闻
	* 参  数:s:字符串数组，添加新闻的各项参数
	*        s1:管理员;s2:IP地址
	* 返回值:字符串型。返回操作结果的信息
	***********************************************************/
    public String AddNews(String [] s,String s1,String s2)
    {			
		try
    	{
	    	boolean OK = true;
    		Connection Conn = DBConn.getConn();
	    	Statement stmt = Conn.createStatement(1004,1007);
	    	ResultSet rs = null;
    		String sError = "";
	    	for(int i=0;i<s.length;i++)
	    	{
	    		if(i!=1) s[i] = Fun.getStrCN(Fun.CheckReplace(s[i]));
	    		else s[i] = Fun.getStrCN(s[i]);
	    	}
	    	String [] sa1 = new String [4];
	    	String [] sa2 = new String [4];
	    	sa1[0] = s[0];
	    	sa1[1] = s[3];
	    	sa1[2] = s[4];
	    	sa1[3] = s[1];
	    	sa2[0] = "新闻标题";
	    	sa2[1] = "新闻发布人";
	    	sa2[2] = "新闻出处";
	    	sa2[3] = "新闻内容";
	    	String sOK = Fun.CheckDate(sa1,sa2);
	    	if (!sOK.equals("Yes"))
	    	{
	    		OK = false;
	    		sError = sOK; 
	    	}
	    	
	    	if(!s[13].equals(""))
	    	{
	    		s[7]="0";
	    		s[8]="0";
	    	}
	    	else
	    	{
	    		if(s[7].equals("") || s[8].equals(""))
	    		{
	    			OK = false;
	    			sError="新闻类别和专题不能同时为空";
	    		}
	    	}
	    	
	    	if(s[14].length()>120) s[14]=s[14].substring(0,120);
	    	if (s[2].equals("") || s[2].equals(""))
	    	{
	    		s[2] = s[0];
	    	}
	    	if(OK)
	    	{	
		    	String NowTime = (new java.util.Date()).toLocaleString();
		    	String [] sLog = new String[5];
		    	String sql = "insert into News (NewsTitle,NewsContent,NewsKey,NewsAuthor,";
		    	sql += "NewsFrom,NewsTime,NewsPicture,BigClassID,SmallClassID,";
				sql += "IsHead,HeadPicture,IsImg,IsHot,SpecialID,NewsInfo,AdminName) values (";
				sql += "'" + s[0] + "',";
				sql += "'" + s[1] + "',";
				sql += "'" + s[2] + "',";
				sql += "'" + s[3] + "',";
				sql += "'" + s[4] + "',";
				sql += "'" + s[5] + "',";
				sql += "'" + s[6] + "',";
				sql += Fun.StrToInt(s[7]) + ",";
				sql += Fun.StrToInt(s[8]) + ",";
				sql += "'" + s[9] + "',";
				sql += "'" + s[10] + "',";
				sql += "'" + s[11] + "',";
				sql += "'" + s[12] + "',";
				sql += Fun.StrToInt(s[13]) + ",";
				sql += "'" + s[14] + "',";
				sql += "'" + s1    + "')";
				//out.println(sql);
				String sql1= "update Admin set NewsNum=NewsNum+1 where AdminName='" + Fun.CheckReplace(s1) + "'";
				try{
					Conn.setAutoCommit(false);
					stmt.executeUpdate(sql);
					stmt.executeUpdate(sql1);
					Conn.commit();
					Conn.setAutoCommit(true);
					stmt.close();
					Conn.close();
			    	sLog[0] = s1;
			    	sLog[1] = "添加新闻[" + s[0] + "]";
			    	sLog[2] = NowTime;
			    	sLog[3] = s2; 
			    	sLog[4] = "Yes";
			    	Fun.AddLog(sLog);
					stmt.close();
					Conn.close();
			    	return "Yes";
			    }catch (Exception e) {
						Conn.rollback();		//回滚JDBC事务
						e.printStackTrace();
						Conn.close();
						return "事务操作失败!";
						}  
		    }
		    else return sError;
	    }catch(Exception e)
        {
            e.printStackTrace();
            //System.out.print(e.getMessage());
            return "添加新闻操作失败!";
        }
     }
    
    
    
    /*********************************************************
	* 函数名:EditNews
	* 作  用:修改新闻
	* 参  数:字符串数组，添加新闻的各项参数
	*         s0:新闻ID,s1:管理员;s2:IP地址
	* 返回值:字符串型。返回操作结果的信息
	***********************************************************/
    public String EditNews(String [] s,String s0,String s1,String s2)
    {			
		try
    	{
	    	boolean OK = true;
    		Connection Conn = DBConn.getConn();
	    	Statement stmt = Conn.createStatement(1004,1007);
	    	ResultSet rs = null;
    		String sError = "";
	    	for(int i=0;i<s.length;i++)
	    	{
	    		s[i] = Fun.getStrCN(Fun.CheckReplace(s[i]));
	    	}
	    	String [] sa1 = new String [4];
	    	String [] sa2 = new String [4];
	    	sa1[0] = s[0];
	    	sa1[1] = s[3];
	    	sa1[2] = s[4];
	    	sa1[3] = s[1];
	    	sa2[0] = "新闻标题";
	    	sa2[1] = "新闻发布人";
	    	sa2[2] = "新闻出处";
	    	sa2[3] = "新闻内容";
	    	int NewsID = Fun.StrToInt(s0);
	    	if (NewsID==0)
	    	{
	    		OK = false;
	    		sError = "新闻ID参数传递错误，请重试!"; 
	    	}
	    	
	    	if(!s[13].equals(""))
	    	{
	    		s[7]="0";
	    		s[8]="0";
	    	}
	    	else
	    	{
	    		if(s[7].equals("") || s[8].equals(""))
	    		{
	    			OK = false;
	    			sError="新闻类别和专题不能同时为空";
	    		}
	    	}
	    	
	    	String sOK = Fun.CheckDate(sa1,sa2);
	    	if (!sOK.equals("Yes"))
	    	{
	    		OK = false;
	    		sError = sOK; 
	    	}
	    	if(s[14].length()>120) s[14]=s[14].substring(0,120);
	    	if (s[2].equals("") || s[2].equals(" "))
	    	{
	    		s[2] = s[0];
	    	}
	    	if(OK)
	    	{	
		    	String NowTime = (new java.util.Date()).toLocaleString();
		    	String [] sLog = new String[5];
		    	String sql = "update News set ";
	    		sql += "NewsTitle='"   + s[0] + "',";
	    		sql += "NewsContent='" + s[1] + "',";
	    		sql += "NewsKey='"     + s[2] + "',";
	    		sql += "NewsAuthor='"  + s[3] + "',";
	    		sql += "NewsFrom='"    + s[4] + "',";
	    		sql += "NewsTime='"    + s[5] + "',";
	    		sql += "NewsPicture='" + s[6] + "',";
	    		sql += "BigClassID="   + Fun.StrToInt(s[7]) + ",";
	    		sql += "SmallClassID=" + Fun.StrToInt(s[8]) + ",";
	    		sql += "IsHead='"      + s[9] + "',";
	    		sql += "HeadPicture='" + s[10]+ "',";
	    		sql += "IsImg='"       + s[11]+ "',";
	    		sql += "IsHot='"   + s[12]+ "',";
	    		sql += "SpecialID="   + Fun.StrToInt(s[13]) + ",";
	    		sql += "NewsInfo='"   + s[14]+ "'";
	    		sql += " where NewsID="+ NewsID;
				stmt.executeUpdate(sql);
				stmt.close();
				Conn.close();
		    	sLog[0] = s1;
		    	sLog[1] = "修改ID为[" + NewsID + "]的新闻";
		    	sLog[2] = NowTime;
		    	sLog[3] = s2; 
		    	sLog[4] = "Yes";
		    	Fun.AddLog(sLog);
				stmt.close();
				Conn.close();
		    	return "Yes";
		    }
		    else return sError;
	    }catch(Exception e)
        {
            e.printStackTrace();
            System.out.print(e.getMessage());
            return "修改新闻操作失败!";
        }
     }
    
    
    /*********************************************************
	* 函数名：DelNews
	* 作  用：删除新闻
	* 参  数：s0:新闻ID；s1:管理员；s2:IP
	* 返回值：成功　true ，否则返回　false
	***********************************************************/ 
	public boolean DelNews(String s0,String s1,String s2)
    {
    	String NowTime = (new java.util.Date()).toLocaleString();
		String [] sLog = new String[5];
		sLog[0] = s1;
		sLog[2] = NowTime;
		sLog[3] = s2; 
    	try{
	    	Connection Conn = DBConn.getConn();
	    	Statement stmt = Conn.createStatement();
	    	int NewsID = Fun.StrToInt(s0);
	    	if (NewsID==0)
	    		return false;
	    	else
	    	{
			  try{
			    	String sql = "delete from News where NewsID=" + NewsID;
			    	String sql1= "update Admin set NewsNum=NewsNum-1 where AdminName='" + Fun.CheckReplace(s1) + "'";
			    	Conn.setAutoCommit(false);
			    	stmt.executeUpdate(sql);
			    	stmt.executeUpdate(sql1);
			    	Conn.commit();
			    	Conn.setAutoCommit(true);
			    	sLog[1] = "删除ID为[" + s0 + "]的新闻";
			    	sLog[4] = "Yes";
			    	Fun.AddLog(sLog);
					stmt.close();
					Conn.close();
			    	return true;
		    	}catch (Exception e) {
						Conn.rollback();		//回滚JDBC事务
						////e.printStackTrace();
						Conn.close();
						return false;
						}  
		    }
    	}catch(Exception e){
    		//e.printStackTrace();
    		System.out.print(e.getMessage());
    		//System.out.print(sql);
    		sLog[1] = "删除新闻[" + s0 + "]出错";
	    	sLog[4] = "No";
	    	Fun.AddLog(sLog);
    		return false;
    	}
	
	}
    
    
    
    
    //测试
	public static void main(String[] args)
	{	
		News n1 = new News();
		String [] as = n1.ReadNews("1");
		//System.out.println(n1.ListNews("ListNews.jsp","1"));
		for(int i=0;i<as.length;i++)
		{
			System.out.println(as[i]);		
		}
	}
}
     