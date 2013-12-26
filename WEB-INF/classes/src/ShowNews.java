
/***************************************************
 *  
 *  源文件名:  ShowNews.java
 *  功    能： 梦想年华新闻系统 - 前台新闻显示类
 *	作者：梦想年华 [DreamTime]
 *	Email:fanwsp@126.com
 *  QQ:122142023 
 * 	CopyRight(c)2005-2006 by DreamTime 
 *
 ****************************************************
*/

package dreamtime.dreamnews;			//指定类所在的包

import java.sql.*;
import java.util.Date;
import dreamtime.dreamnews.Function;
import dreamtime.dreamnews.DBConnection;

public class ShowNews
{

    DBConnection DBConn = new DBConnection();
    Function Fun = new Function(); 
    String DreamNewsTitle;
    String DreamNewsCopyRight;
    
    public ShowNews()
    {
    	if(Fun.ReadConfig())
    	{
    		DreamNewsTitle = Fun.DreamNewsTitle;
    		DreamNewsCopyRight = Fun.DreamNewsCopyRight;
    	}
    	
    	if(DreamNewsTitle==null || DreamNewsTitle.equals("")) 
    		DreamNewsTitle="梦想年华新闻系统";
    		
    	if(DreamNewsCopyRight==null || DreamNewsCopyRight.equals("")) 
    		DreamNewsCopyRight="梦想年华[DreamTime]";
    } 
 
 
 	/*********************************************************
	* 函数名：ShowNews
	* 作  用：前台显示新闻
	* 参  数：新闻ID
	* 返回值：字符串
	***********************************************************/
    public String ShowNews(String s0)
    {
    	try
    	{	
	    	Connection Conn = DBConn.getConn();
		    Statement stmt = Conn.createStatement(1004,1007);
		    ResultSet rs = null;
		    String sql = "";
		    boolean OK = true;
		    boolean IsSpec = false;
		    String sError = "";
		    StringBuffer sb = new StringBuffer();
		    int NewsID = Fun.StrToInt(s0);
		    
		    sb.append("<div id=\"ShowNews\">\r\n");
		    if(NewsID==0) 
		    {
		    	OK = false;
		    	sError = "<div id=\"Content\"><p>非法参数传递!</p></div>";
		    }
		  	
		    if(OK)
		    {	
		    	sql = "select * from News,BigClass,SmallClass where ";
		    	sql += "BigClass.BigClassID=News.BigClassID and ";
		    	sql += "SmallClass.SmallClassID=News.SmallClassID and ";
		    	sql += "NewsID=" + NewsID;
		    	
		    	String sql1 = "select * from News,Special where ";
		    	sql1 += "Special.SpecialID=News.SpecialID and ";
		    	sql1 += "NewsID=" + NewsID;
			    
			    rs = stmt.executeQuery(sql);
			    
			    if(!rs.next())
			    {
			    	OK = false;
		    		sError = "<div id=\"Content\"><p>参数传递错误!</p></div>";
		    		IsSpec = true;
			    }
			    
			    if(IsSpec) 
			    {
			    	rs=stmt.executeQuery(sql1);
			    	if(!rs.next())
				    {
				    	OK = false;
			    		sError = "<div id=\"Content\"><p>参数传递错误!</p></div>";
				    }
				    else OK = true;
			    }
			    
				if(OK)
				{
					rs.first();
					String NewsTitle = rs.getString("NewsTitle");
					String NewsContent = rs.getString("NewsContent");
					String NewsFrom = rs.getString("NewsFrom");
					String NewsAuthor = rs.getString("NewsAuthor");
					String NewsTime = rs.getString("NewsTime");
					String pos = "<img src=\"pic/pos.gif\" border=0>";
					DreamNewsTitle += " 首页"; 
					sb.append("<div id=\"Pos\"><a title=\"" + DreamNewsTitle + "\" href=\"index.jsp\">首页</a>");
					
					if(IsSpec)
					{
						sb.append(pos + "<a title=\"专题新闻\" href=\"Special.jsp\">专题新闻</a>");
						sb.append(pos + "<a title=\"" + rs.getString("SpecialInfo") + "\" href=\"Special.jsp?SpecialID=");
						sb.append(rs.getInt("SpecialID") + "\">" + rs.getString("SpecialName") + "</a>");
					}
					else
					{
						sb.append(pos + "<a title=\"" + rs.getString("BigClassInfo") + "\" href=\"ReadClass.jsp?BigClassID=");
						sb.append(rs.getInt("BigClassID") + "\">" +  rs.getString("BigClassName") + "</a>" );
						sb.append(pos + "<a title=\"" + rs.getString("SmallClassInfo") + "\" href=\"ReadSClass.jsp?SClassID=");
						sb.append(rs.getInt("SmallClassID") + "\">" + rs.getString("SmallClassName") + "</a>");
					}
					
					sb.append(pos + NewsTitle);	
					sb.append("</div>\r\n");
					sb.append("<div id=\"Content\">\r\n");
					sb.append("<div id=\"Title\">" + NewsTitle + "</div>\r\n");
					sb.append("<div id=\"info\">");
					sb.append("发布时间：" + NewsTime + "&nbsp;");
					sb.append("新闻来源：" + NewsFrom + "&nbsp;");
					sb.append("发布人：" + NewsAuthor + "&nbsp;");
					sb.append("</div>\r\n");
					sb.append("<div id=\"NewsContent\">" + NewsContent + "</div>\r\n");
					sb.append("</div>\r\n");						
				}
				rs.close();
    			stmt.close();
    			Conn.close(); 
			}	
			sb.append("</div>\r\n");	
			
			if(OK) return sb.toString();
			else return sError;
			
		}catch(Exception e)
        {
            //e.printStackTrace();
            System.out.print(e.getMessage()); 
            return " ";
        }
    }
    
    
    
    /*********************************************************
	* 函数名：SearchNews
	* 作  用：实现新闻搜索功能
	* 参  数：新闻关键字,页面地址，分页显示的页码
	* 返回值：字符串
	***********************************************************/
    public String SearchNews(String [] s,String sPage,String strPage)
    {
		String sql = "";
    	try
    	{	
	    	Connection Conn = DBConn.getConn();
		    Statement stmt = Conn.createStatement(1004,1007);
		    ResultSet rs = null;
		    //String sql = "";
		    String sClassTitle="没有这个类别";
		    StringBuffer sb = new StringBuffer();
		    int i;
			int intPage = 1; 			
			int intPageSize = 50;
			boolean OK = true;
			int KeyType = Fun.StrToInt(Fun.CheckReplace(s[0]));
			int NewsType = Fun.StrToInt(Fun.CheckReplace(s[1]));
			String sKey = Fun.getStrCN(Fun.CheckReplace(s[2]));
			
			sb.append("<div id=\"News\"><ul>\r\n");
			
			/************** 取得新闻显示参数 **************/
			if (Fun.ReadConfig() && Fun.SearchNewsNum>0) intPageSize = Fun.SearchNewsNum;
			
			if(sKey.equals("") || sKey==null) 
			{
				OK = false;
				sb.append("<li><div id=\"F3\">搜索关键字不能为空!</div></li>\r\n");
			}
			
			if(OK)
			{
				
				/*构造搜索的SQL语句*/
				sql  = "select * from News where ";
				switch(KeyType)
				{													
				case 1: 			//按标题搜索
					sql += "NewsTitle like '%" + sKey + "%'";
					break;
					
				case 2: 			//按内容搜索
					sql += "NewsContent like '%" + sKey + "%'";
					break;
					
				case 3: 			//按新闻关键字搜索
					sql += "NewsKey like '%" + sKey + "%'";
					break;
				
				case 4: 			//按新闻发布时间搜索
					sql += "NewsTime like '%" + sKey + "%'";
					break;
						
				default:			//所有
					sql += "NewsTitle like '%" + sKey + "%'";
					sql += " or NewsContent like '%" + sKey + "%'";
					sql += " or NewsKey like '%" + sKey + "%'";
					break;	
				}
				
				
				switch(NewsType)
				{													
				case 1: 			//头条
					sql += " and IsHead='Yes'";
					break;
					
				case 2: 			//图片
					sql += " and IsImg='Yes'";
					break;
					
				case 3: 			//普通
					sql += " and IsHead='No' and IsImg='No'";
					break;
					
				default:			//所有
					
					break;	
				}
				
			
	    		
	    		
	    		
	    		/*读取新闻，并分页显示*/
	    		rs = stmt.executeQuery(sql);
	    		
	    		//如果表中没有任何记录，则给出提示信息
				if (!rs.next())
				{ 
					sb.append("<li><div id=\"F3\">没有新闻</div></li>\r\n");
					OK = false;
				}
				else 
				{
					//取得待显示页码 
					intPage = Fun.StrToInt(strPage);
					sPage = Fun.CheckReplace(sPage);
					if (intPage==0) intPage=1;	
		
					//将记录指针定位到待显示页的第一条记录上 
					if(!rs.absolute((intPage-1) * intPageSize+1)) rs.absolute(1);
					
					i = 1; 
					while(i<=intPageSize && !rs.isAfterLast())
					{	
	    				String NewsTitle = rs.getString(2);
						String sTitle = NewsTitle;
						String NewsTime = rs.getString(7);
							
						if (NewsTitle.length()>25) sTitle = NewsTitle.substring(0,25) + "..";	
						if(rs.getString(13).equals("Yes")) sTitle += "(图)";
						sb.append("<li><img src=\"pic/li.gif\"> <a title=\"" + NewsTitle + "\" href=\"ShowNews.jsp?NewsID=" + rs.getInt(1) + "\" target=_blank>");
						sb.append(sTitle + "</a><span>[" + NewsTime + "]</span></li>\r\n");	
						if(i%5==0 && i>1) sb.append("<div id=\"Line\"><div id=\"L2\"></div></div>\r\n");
						rs.next();
						i++;
					}
				}
				sb.append("</ul></div>\r\n");
				if(OK) sb.append(Fun.Page(sPage,rs,intPage,intPageSize));	
				rs.close();
				stmt.close();
				Conn.close();
			}			
			return sb.toString();
		}catch(Exception e)
        {
            //e.printStackTrace();
            //System.out.print(e.getMessage()); 
            System.out.print(sql);
            return " ";
        }
    }
    
 
 
 	/*********************************************************
	* 函数名：IDToTitle
	* 作  用：将ID转化为标题
	* 参  数：ID，ID类别，1为大类，2为小类，3为新闻
	* 返回值：字符串
	***********************************************************/
    public String IDToTitle(String s0,int i)
    {
    	try
    	{	
	    	Connection Conn = DBConn.getConn();
		    Statement stmt = Conn.createStatement(1004,1007);
		    ResultSet rs = null;
		    String sql = "";
		    boolean OK = true;
		    int ID = Fun.StrToInt(Fun.CheckReplace(s0));
		    String Title = "";
		    
		    if(ID==0) 
		    {
		    	Title =  DreamNewsTitle;
		    	OK = false;
		    }
		    else
		    {	
		    	switch(i)
				{	
				case 0: 			//网站标题
					Title = DreamNewsTitle;
					OK = false;
					break;
																	
				case 1: 			//大类
					sql = "select BigClassName from BigClass where BigClassID=" + ID;
					break;
					
				case 2: 			//小类
					sql = "select SmallClassName from SmallClass where SmallClassID=" + ID;
					break;
					
				case 3: 			//新闻
					sql = "select NewsTitle from News where NewsID=" + ID;
					break;
					
				case 4: 			//专题
					sql = "select SpecialName from Special where SpecialID=" + ID;
					break;
					
				case 5: 			//专题列表
					Title = "专题列表";
					OK = false;
					break;
					
				default:			//出错
					Title = DreamNewsTitle;
					OK = false;
					break;	
				}
			}
			
				
		    if(OK)
		    {
			    rs = stmt.executeQuery(sql);
			    if(rs.next())
				{
					Title = rs.getString(1);
					rs.close();
	    			stmt.close();
	    			Conn.close(); 
				}
			}
			return Title;
					
		}catch(Exception e)
        {
            ////e.printStackTrace();
            //System.out.print(e.getMessage()); 
            return DreamNewsTitle;
        }
    }
 
 
     //测试
	public static void main(String[] args)
	{	
		ShowNews sNews = new ShowNews();
		System.out.println(sNews.ShowNews("1"));
		String [] s1 = new String[3];
		//s1[0] = "0";
		//s1[1] = "0";
		//s1[2] = "广州";
		//System.out.println(sNews.SearchNews(s1,"Search.jsp?","1"));
		//System.out.println(LC.ShowHeadPic("1"));
		//System.out.println(sNews.IDToTitle("1",5));

	}     
 }
 
 