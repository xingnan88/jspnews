
/***************************************************
 *  
 *  Դ�ļ���:  ListClass.java
 *  ��    �ܣ� �����껪����ϵͳ - ���������ʾ��
 *	���ߣ������껪 [DreamTime]
 *	Email:fanwsp@126.com
 *  QQ:122142023 
 * 	CopyRight(c)2005-2006 by DreamTime 
 *
 ****************************************************
*/

package dreamtime.dreamnews;			//ָ�������ڵİ�

import java.sql.*;
import dreamtime.dreamnews.Function;
import dreamtime.dreamnews.DBConnection;

public class ListClass
{

    DBConnection DBConn = new DBConnection();
    Function Fun = new Function(); 
    
    public ListClass()
    {
    	
    } 
     
  
  
  	/*********************************************************
	* ��������TopClass
	* ��  �ã�����˵�����
	* ��  ������
	* ����ֵ���ַ���
	***********************************************************/
    public String TopClass()
    {
    	try
    	{	
	    	Connection Conn = DBConn.getConn();
		    Statement stmt = Conn.createStatement(1004,1007);
		    String sql = "select * from BigClass order by BigClassID";
		    ResultSet rs = stmt.executeQuery(sql);
		    int i = 0;
		    rs.last();
		    int iMax = rs.getRow(); 
		    String [][] s = new String[iMax][2];
		    StringBuffer sb = new StringBuffer();
		    if(iMax==0) sb.append("<div id=\"F1\" align=\"center\"><strong>��ʱ��û���κδ�����Ϣ��</strong></div>\r\n");
		   	else
			{
			    sb.append("<table width=100% height=22 align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
				sb.append("<tr>\r\n");
				sb.append("<td align=\"center\" width=\"" + 100/(iMax+2) + "%\" valign=\"bottom\">");
				sb.append("<div id=\"menu\"><a href=\"index.jsp\" title=\"����������ҳ\">��ҳ</a></div></td>\r\n");		
				rs.first();
				for(i=0;i<iMax;i++)
				{
					sb.append("<td align=\"center\" valign=\"bottom\" width=\"" + 100/(iMax+2) + "%\">");
					sb.append("<div id=\"menu\"><a href=\"ReadClass.jsp?BigClassID=" + rs.getInt(1) + "\"");
					sb.append("title=\"" + rs.getString(3) + "\">" + rs.getString(2) + "</a></div>\r\n");
					sb.append("</td>\r\n");	
					rs.next();			
				}  
				sb.append("<td align=\"center\" width=\"" + 100/(iMax+2) + "%\" valign=\"bottom\">");
				sb.append("<div id=\"menu\"><a href=\"Special.jsp\" title=\"ר���б�\">ר��</a></div></td>\r\n");	
				rs.close();
	    		stmt.close();
	    		Conn.close();
	    		sb.append("</tr></table>\r\n");
			}	
			return sb.toString();
		}catch(Exception e)
        {
            //e.printStackTrace();
            //System.out.print(e.getMessage()); 
            return "<div id=\"F1\" align=\"center\"><strong>��������</strong></div>";
        }
    }
    
    
    
    /*********************************************************
	* ��������ShowHotNews
	* ��  �ã���ʾ��������
	* ��  ��������ID�����Ϊ�գ����ʾ���д���
	*         b:�Ƿ�ר��
	* ����ֵ���ַ���
	***********************************************************/
    public String ShowHotNews(String s0,boolean b)
    {
    	try
    	{	
	    	Connection Conn = DBConn.getConn();
		    Statement stmt = Conn.createStatement(1004,1007);
		    String sql = "";
		    int ID = Fun.StrToInt(s0);
		    int iHotNum = 5;
			if (Fun.ReadConfig()) 
		    {
		    	if(ID==0 && Fun.HotNewsNum>0) iHotNum = Fun.HotNewsNum;
		    	else if(Fun.BHotNewsNum>0) iHotNum = Fun.BHotNewsNum;
		    } 
		    
		    if(ID==0) 
		    {
		    	if(b) sql = "select top " + iHotNum + " * from News where IsHot='Yes' and IsHead='No' and IsImg='No' and SpecialID<>0 order by NewsID desc";
		    	else sql = "select top " + iHotNum + " * from News where IsHot='Yes' and IsHead='No' and IsImg='No' order by NewsID desc";
		    }
		    else sql = "select top " + iHotNum + " * from News where IsHot='Yes' and IsHead='No' and IsImg='No' and BigClassID=" + ID + " order by NewsID desc";
		    ResultSet rs = stmt.executeQuery(sql);
		    StringBuffer sb = new StringBuffer();
		    
		    rs.last();
		    if(rs.getRow()<iHotNum) iHotNum=rs.getRow();
		   	if(iHotNum==0) sb.append("<div id=\"F3\">���޿�Ѷ</div>\r\n");
		   	else
			{
				sb.append("<marquee onMouseOver='this.stop()' onMouseOut='this.start()' scrolldelay=200 width=\"460\">\r\n");	
				sb.append("[��Ѷ]&nbsp;&nbsp;");
				rs.first();
				for(int i=0;i<iHotNum;i++)
				{
					String NewsTitle = rs.getString(2);
					String sTitle = NewsTitle;
					String NewsTime = rs.getString(7);
						
					if (NewsTitle.length()>15) sTitle = NewsTitle.substring(0,15) + "..";	
					sb.append("&nbsp;&nbsp;<img src=\"pic/li.gif\" border=0>&nbsp;<a title=\"" + NewsTitle + "\" href=\"ShowNews.jsp?NewsID=" + rs.getInt(1) + "\" target=_blank>\r\n");
					sb.append(sTitle + "</a> [" + NewsTime + "]");	
					rs.next();			
				}  
				sb.append("</marquee>\r\n");	
				rs.close();
	    		stmt.close();
	    		Conn.close();
			}	
			return sb.toString();
		}catch(Exception e)
        {
            //e.printStackTrace();
            //System.out.print(e.getMessage()); 
            return "";
        }
    }
    
    
    
    /*********************************************************
	* ��������ShowHeadNews
	* ��  �ã���ʾ����ͷ������
	* ��  ����s0:����ID�����Ϊ�գ����ʾ���д���
	*         b:�Ƿ�ר������
	* ����ֵ���ַ���
	***********************************************************/
    public String ShowHeadNews(String s0,boolean b)
    {
    	try
    	{	
	    	Connection Conn = DBConn.getConn();
		    Statement stmt = Conn.createStatement(1004,1007);
		    String sql = "";
		    int ID = Fun.StrToInt(s0);
		    int iHeadNum = 2;
		    if (Fun.ReadConfig()) 
		    {
		    	if(ID==0 && Fun.HeadNewsNum>0) iHeadNum = Fun.HeadNewsNum;
		    	else if(Fun.BHeadNewsNum>0) iHeadNum = Fun.BHeadNewsNum;
		    } 
		    
		    if(ID==0) 
		    {
		    	if(b) sql = "select top " + iHeadNum + " * from News where IsHead='Yes' and HeadPicture='' and IsImg='No' and IsHot='No' and SpecialID<>0 order by NewsID desc";
		    	else sql = "select top " + iHeadNum + " * from News where IsHead='Yes' and HeadPicture='' and IsImg='No' and IsHot='No' order by NewsID desc";
		    }
		    else sql = "select top " + iHeadNum + " * from News where IsHead='Yes' and HeadPicture='' and IsImg='No' and IsHot='No' and BigClassID=" + ID + " order by NewsID desc";
		    ResultSet rs = stmt.executeQuery(sql);
		    StringBuffer sb = new StringBuffer();
		   	rs.last();
		    if(rs.getRow()<iHeadNum) iHeadNum=rs.getRow();
		   	if(iHeadNum==0) sb.append("<div id=\"F3\"><br>&nbsp;&nbsp;����ͷ��</div><br>");
		   	else
			{
				rs.first();
				for(int i=0;i<iHeadNum;i++)
				{
					String NewsTitle = rs.getString(2);
					String sTitle = NewsTitle;
					String NewsInfo = rs.getString(16);		
					if (NewsTitle.length()>20) sTitle = NewsTitle.substring(0,20) + "..";	
					sb.append("<div id=\"HeadNews\"><h2><a title=\"" + NewsTitle + "\" href=\"ShowNews.jsp?NewsID=" + rs.getInt(1) + "\" target=_blank>\r\n");
					sb.append(sTitle + "</a></h2></div>\r\n");
					sb.append("<div id=\"HeadNews\"><div id=\"Content\">&nbsp;&nbsp;");
					sb.append(NewsInfo);	
					sb.append("</div></div>\r\n");
					sb.append("<div id=\"more\" align=\"right\">\r\n");
					sb.append("<a title=\"" + NewsInfo + "\" href=\"ShowNews.jsp?NewsID=" + rs.getInt(1) + "\" target=_blank>\r\n");
					sb.append("<img src=\"pic/more.gif\"> ��ϸ</a></div><div id=\"B1\"></div>\r\n");
					if(i>1 && i!=iHeadNum) sb.append("<div id=\"Line\"><div id=\"L2\"></div></div>\r\n");
					rs.next();
				}			
			}  
			rs.close();
    		stmt.close();
    		Conn.close();	
			return sb.toString();
		}catch(Exception e)
        {
            //e.printStackTrace();
            //System.out.print(sql); 
            return " ";
        }
    }
    
    
    
    
    /*********************************************************
	* ��������ShowHeadPic
	* ��  �ã���ʾͷ��ͼƬ����
	* ��  ��������ID�����Ϊ�գ����ʾ���д���
	* ����ֵ���ַ���
	***********************************************************/
    public String ShowHeadPic(String s0,boolean b)
    {
    	try
    	{	
	    	Connection Conn = DBConn.getConn();
		    Statement stmt = Conn.createStatement(1004,1007);
		    String sql = "";
		    int ID = Fun.StrToInt(Fun.CheckReplace(s0));
		    if(ID==0) 
		    {
		    	if(b) sql = "select top 1 * from News where IsHead='Yes' and HeadPicture<>'' and IsImg='No' and IsHot='No' and SpecialID<>0 order by NewsID desc";
		    	else sql = "select top 1 * from News where IsHead='Yes' and HeadPicture<>'' and IsImg='No' and IsHot='No' order by NewsID desc";
		    }
		    else sql = "select top 1 * from News where IsHead='Yes' and HeadPicture<>'' and IsImg='No' and IsHot='No' and BigClassID=" + ID + " order by NewsID desc";
		    ResultSet rs = stmt.executeQuery(sql);
		    StringBuffer sb = new StringBuffer();
		    if(rs.next())
			{
				rs.first();
				int NewsID = rs.getInt(1);
				String NewsTitle = rs.getString(2);
				String sTitle = NewsTitle;
				String NewsInfo = rs.getString(16);
				String HeadPicture = rs.getString(12);		
				if (NewsTitle.length()>15) sTitle = NewsTitle.substring(0,15);
				sb.append("<div id=\"pArea\">\r\n");
				sb.append("<div id=\"Pic\"><a href=\"ShowNews.jsp?NewsID=" + NewsID + "\" target=_blank>\r\n");
				sb.append("<img alt=\"" + NewsTitle + "\" src=\"" + HeadPicture + "\" width=\"240\" height=\"180\"></a></div>\r\n");
				sb.append("<h2><a title=\"" + NewsTitle + "\"  href=\"ShowNews.jsp?NewsID=" + NewsID + "\" target=_blank>" + sTitle + "</a>\r\n");
				sb.append("<div id=\"Content\">&nbsp;&nbsp;&nbsp;&nbsp;" + NewsInfo);
				sb.append("<div id=\"B1\" align=\"right\"><img alt=\"��ϸ\" src=\"pic/more.gif\"> <a href=\"ShowNews.jsp?NewsID=" + NewsID + "\" target=_blank>��ϸ</a></div>\r\n");
				sb.append("</div></h2>\r\n");
				sb.append("</div>");
				sb.append("<div id=\"B3\"></div>\r\n");				
			}  
			rs.close();
    		stmt.close();
    		Conn.close();	
			return sb.toString();
		}catch(Exception e)
        {
            //e.printStackTrace();
            //System.out.print(e.getMessage()); 
            return " ";
        }
    }
    
    
    
    
    /*********************************************************
	* ��������ShowTopNews
	* ��  �ã���ʾ��������
	* ��  ��������ID�����Ϊ�գ����ʾ���д���
	* ����ֵ���ַ���
	***********************************************************/
    public String ShowTopNews(String s0,boolean b)
    {
    	try
    	{	
	    	Connection Conn = DBConn.getConn();
		    Statement stmt = Conn.createStatement(1004,1007);
		    Statement stmt1 = Conn.createStatement(1004,1007);
		    ResultSet rs=null;
		    ResultSet rs1=null;
		    String sql = "";
		    String sql1= "";
		    StringBuffer sb = new StringBuffer();
		    int ID = Fun.StrToInt(s0);
		    int iTopNum = 10;
		    int iImgNum = 6;
		    int iNum = 0;
		    int i=0;
		    if (Fun.ReadConfig()) 
		    {
		    	if(ID==0 && Fun.TopNewsNum>0 && Fun.TopImgNum>0) 
		    	{
		    		iTopNum = Fun.TopNewsNum;
		   		 	iImgNum = Fun.TopImgNum;
		   		}
		   		else if(Fun.BTopNewsNum>0 && Fun.BTopImgNum>0) 
		   		{
		   			iTopNum = Fun.BTopNewsNum;
		   		 	iImgNum = Fun.BTopImgNum;
		   		}
		    }  
		    
    		
    		/*��ȡ���·����ͼƬ����*/
    		if(ID==0)  
    		{
    			if(b) sql1 = "select top " + iImgNum + " * from News where SpecialID<>0 and IsImg='Yes' and NewsPicture<>'' order by NewsID desc";
    			else sql1 = "select top " + iImgNum + " * from News where SpecialID=0 and IsImg='Yes' and NewsPicture<>'' order by NewsID desc";
    		}
    		else sql1 = "select top " + iImgNum + " * from News where SpecialID=0 and IsImg='Yes' and NewsPicture<>'' and BigClassID=" + ID + " order by NewsID desc";
    		rs1 = stmt1.executeQuery(sql1);
            rs1.last();
            iNum = iImgNum;
		    if(rs1.getRow()<iNum) iNum=rs1.getRow();
			if(iNum>0)
			{
				sb.append("<div id=\"B2\"></div>\r\n");
    			sb.append("<div id=\"Pic\"><table width=\"450\"  border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
            	sb.append("<tr>\r\n");
				rs1.first();
				for(i=1;i<=iNum;i++)
				{
	    			String NewsTitle = rs1.getString(2);
					String sTitle = NewsTitle;
					String NewsPicture = rs1.getString(8);			
					if (NewsTitle.length()>10) sTitle = NewsTitle.substring(0,10) + "..";	
					sb.append("<td width=\"150\">\r\n");
	                sb.append("<table width=\"150\"  border=\"0\" cellspacing=\"2\" cellpadding=\"2\">\r\n");
	                sb.append("<tr><td align=\"center\" height=\"80\">\r\n");
					sb.append("<a href=\"ShowNews.jsp?NewsID=" + rs1.getInt(1) + "\" target=_blank>\r\n");
					sb.append("<img src=\"" + NewsPicture + "\" width=\"130\" height=\"80\" alt=\"" + NewsTitle + "\"></a>\r\n");
					sb.append("</td></tr>\r\n");
					sb.append("<tr><td height=\"25\">\r\n");
					sb.append("<a href=\"ShowNews.jsp?NewsID=" + rs1.getInt(1) + "\" target=_blank>\r\n");
					sb.append(sTitle + "</td></tr>\r\n");
	                sb.append("</table>\r\n");
	              	sb.append("</td>\r\n");	
	              	if(i%3==0) sb.append("</tr><tr>\r\n");
	              	rs1.next();
	    		}
	    		rs1.close();
	    		sb.append("</tr></table></div>\r\n");
	    		sb.append("<div id=\"Line\"><div id=\"L2\"></div></div>\r\n");
    		}
    		
    		/****************  ͼƬ���Ŷ�ȡ���� ****************/
		    
		    
		   
		    /*****************  ��ȡ�ı����� *****************/
		    
		    if(ID==0) 
		    {
		    	if(b) sql = "select top " + iTopNum + " * from News where IsHot='No' and IsHead='No' and SpecialID<>0 order by NewsID desc";
		    	else sql = "select top " + iTopNum + " * from News where IsHot='No' and IsHead='No' order by NewsID desc";	
		    }
		    else sql = "select top " + iTopNum + " * from News where IsHot='No' and IsHead='No' and BigClassID=" + ID + " order by NewsID desc";
		    rs = stmt.executeQuery(sql);
		    
		    sb.append("<div id=\"News\"><ul>\r\n");	
		    rs.last();
		    if(rs.getRow()<iTopNum) iTopNum=rs.getRow();
		   	if(iTopNum==0) sb.append("<li><div id=\"F3\">&nbsp;&nbsp;��������</div></li>\r\n");
		   	else
			{
				rs.first();
				for(i=1;i<=iTopNum;i++)
				{
					String NewsTitle = rs.getString(2);
					String sTitle = NewsTitle;
					String NewsTime = rs.getString(7);			
					if (NewsTitle.length()>25) sTitle = NewsTitle.substring(0,25) + "..";	
					if(rs.getString(13).equals("Yes")) sTitle += "(ͼ)";
					sb.append("<li><img src=\"pic/li.gif\"> <a title=\"" + NewsTitle + "\" href=\"ShowNews.jsp?NewsID=" + rs.getInt(1) + "\" target=_blank>\r\n");
					sb.append(sTitle + "</a><span>[" + NewsTime + "]</span></li>\r\n");	
					if(i%5==0 && i>1 && i!=iTopNum) sb.append("<div id=\"Line\"><div id=\"L2\"></div></div>\r\n");
					rs.next();			
				}  	
			}
			sb.append("</ul></div>\r\n");
			
			/***************** ��ȡ�ı����Ž���*****************/
			stmt1.close();	
			rs.close();
    		stmt.close();
    		Conn.close();	
			return sb.toString();
		}catch(Exception e)
        {
            //e.printStackTrace();
            //System.out.print(e.getMessage()); 
            return " ";
        }
    }
    
    
    
   /*********************************************************
	* ��������ShowClassNews
	* ��  �ã���ʾ�������
	* ��  ��������ID�����Ϊ�գ����ʾ���д���
	* ����ֵ���ַ���
	***********************************************************/
    public String ShowClassNews(String s0)
    {
    	try
    	{	
	    	Connection Conn = DBConn.getConn();
		    Statement stmt1 = Conn.createStatement(1004,1007);
		    Statement stmt2 = Conn.createStatement(1004,1007);
		    Statement stmt3 = Conn.createStatement(1004,1007);
		    ResultSet rs1 = null;
		    ResultSet rs2 = null;
		    ResultSet rs3 = null;
		    String sql1 = "";
		    String sql2 = "";
		    String sql3 = "";
		    String sLink ="ReadSClass.jsp?SClassID=";
		    String sStyle="<div id=\"stArea\"><div id=\"sTitle2\"><div id=\"stFont\">";
		    StringBuffer sb = new StringBuffer();
		    int ID = Fun.StrToInt(Fun.CheckReplace(s0));
		    int iNewsNum = 10;  	//������
		    int iImgNum=3;			//ͼƬ��
		    int i=0;
		    int iNum=0;
		    
		    
		     /************** ȡ�ô������ **************/
		    if (Fun.ReadConfig()) 
		    {
		    	if(ID==0 && Fun.ClassImgNum>0 && Fun.ClassNewsNum>0) 
		    	{
		    		iImgNum = Fun.ClassImgNum;
		    		iNewsNum = Fun.ClassNewsNum;
		    	}
		    	else if(Fun.BClassImgNum>0 &&  Fun.BClassNewsNum>0)
		    	{
		    		iImgNum = Fun.BClassImgNum;
		    		iNewsNum = Fun.BClassNewsNum;
		    	}
		    	
		    }
		    
		    
		    
		    
		    /************** ������Ŷ�ȡ **************/
		    if(ID==0) 
		    {
	    		sql1 = "select * from BigClass";
	    		sLink = "ReadClass.jsp?BigClassID=";
		    	sStyle = "<div id=\"tArea\"><div id=\"Title2\"><div id=\"tFont\">";
		    }
	    	else sql1 = "select * from SmallClass where BigClassID=" + ID;
	    			
	    	
	    	rs1 = stmt1.executeQuery(sql1);
	    	while(rs1.next())
	    	{
	    		/*��ȡ������*/
	    		int ClassID = rs1.getInt(1);
	    		sb.append(sStyle + rs1.getString(2) + "</div></div>");
	    		sb.append("<span>");
				sb.append("<img src=\"pic/more.gif\">  <a title=\"" + rs1.getString(3) + "\" href=\"");
				sb.append(sLink + ClassID + "\" target=_blank>����..</a>");
	    		sb.append("</span></div>\r\n");
	    	
	    		/*��ȡÿ����������·����ͼƬ����*/
	    		if(ID==0) sql2 = "select top " + iImgNum + " * from News where SpecialID=0 and IsImg='Yes' and NewsPicture<>'' and BigClassID=" + ClassID + " order by NewsID desc";
				else sql2 = "select top " + iImgNum + " * from News where SpecialID=0 and IsImg='Yes' and NewsPicture<>'' and SmallClassID=" + ClassID + " order by NewsID desc";
	    		 
	    		rs2 = stmt2.executeQuery(sql2);
                rs2.last();
                iNum = iImgNum;
			    if(rs2.getRow()<iNum) iNum=rs2.getRow();
    			if(iNum>0)
    			{
    				sb.append("<div id=\"B2\"></div>\r\n");
    				sb.append("<div id=\"Pic\"><table width=\"450\"  border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
            		sb.append("<tr>\r\n");
	    			rs2.first();
					for(i=1;i<=iNum;i++)
					{	
		    			String NewsTitle = rs2.getString(2);
						String sTitle = NewsTitle;
						String NewsPicture = rs2.getString(8);			
						if (NewsTitle.length()>10) sTitle = NewsTitle.substring(0,10) + "..";	
						sb.append("<td width=\"150\">\r\n");
                        sb.append("<table width=\"150\"  border=\"0\" cellspacing=\"2\" cellpadding=\"2\">\r\n");
                        sb.append("<tr><td align=\"center\" height=\"80\">\r\n");
						sb.append("<a href=\"ShowNews.jsp?NewsID=" + rs2.getInt(1) + "\" target=_blank>\r\n");
						sb.append("<img src=\"" + NewsPicture + "\" width=\"130\" height=\"80\" alt=\"" + NewsTitle + "\"></a>\r\n");
						sb.append("</td></tr>\r\n");
						sb.append("<tr><td height=\"25\">\r\n");
						sb.append("<a href=\"ShowNews.jsp?NewsID=" + rs2.getInt(1) + "\" target=_blank>\r\n");
						sb.append(sTitle + "</td></tr>\r\n");
                        sb.append("</table>\r\n");
                      	sb.append("</td>\r\n");	
                      	if(i%3==0) sb.append("</tr><tr>\r\n");
                      	rs2.next();
		    		}
		    		rs2.close();
		    		sb.append("</tr></table></div>\r\n");
		    		sb.append("<div id=\"Line\"><div id=\"L2\"></div></div>\r\n");
	    		}
	    		
	    		
	    		/********** ��ȡÿ����������·������������ ********/
	    		if(ID==0) 
			    {
			    	sql3 = "select top " + iNewsNum + " * from News where SpecialID=0 and BigClassID=" + ClassID + " order by NewsID desc";
			    }
		    	else 
		    	{
		    		sql3 = "select top " + iNewsNum + " * from News where SpecialID=0 and SmallClassID=" + ClassID + " order by NewsID desc";
		    	}	
	    	
	    		rs3 = stmt3.executeQuery(sql3);
	    		sb.append("<div id=\"News\"><ul>\r\n");
	    		rs3.last();
	    		
	    		iNum = iNewsNum;
			    if(rs3.getRow()<iNewsNum) iNum=rs3.getRow();
			   	if(iNum==0) sb.append("<li><div id=\"F3\">������������</div></li>\r\n");
			   	else
				{
					rs3.first();
					for(i=1;i<=iNum;i++)
					{
	    				String NewsTitle = rs3.getString(2);
						String sTitle = NewsTitle;
						String NewsTime = rs3.getString(7);
							
						if (NewsTitle.length()>25) sTitle = NewsTitle.substring(0,25) + "..";	
						if(rs3.getString(13).equals("Yes")) sTitle += "(ͼ)";
						sb.append("<li><img src=\"pic/li.gif\"> <a title=\"" + NewsTitle + "\" href=\"ShowNews.jsp?NewsID=" + rs3.getInt(1) + "\" target=_blank>\r\n");
						sb.append(sTitle + "</a><span>[" + NewsTime + "]</span></li>\r\n");	
						if(i%5==0 && i>1 && i!=iNum) sb.append("<div id=\"Line\"><div id=\"L2\"></div></div>\r\n");
						rs3.next();
					}
				}
				sb.append("</ul></div>\r\n");	
				rs3.close();
	    	}
	    	rs1.close();
			stmt1.close();
			Conn.close();			
			return sb.toString();
		}catch(Exception e)
        {
            //e.printStackTrace();
            //System.out.print(e.getMessage()); 
            //System.out.print(sql1);
            //System.out.print(sql2);
            //System.out.print(sql3);
            return " ";
        }
    }
    
    
    
    /*********************************************************
	* ��������ShowSClass
	* ��  �ã���ʾС����������
	* ��  ����С��ID,ҳ���ַ����ҳ��ʾ��ҳ��
	* ����ֵ���ַ���
	***********************************************************/
    public String ShowSClassNews(String s0,String sPage,String strPage)
    {
    	try
    	{	
	    	Connection Conn = DBConn.getConn();
		    Statement stmt = Conn.createStatement(1004,1007);
		    ResultSet rs = null;
		    String sql = "";
		    String sClassTitle="û��������";
		    StringBuffer sb = new StringBuffer();
		    int ID = Fun.StrToInt(s0);
		    int i;
			int intPage = 1; 			
			int intPageSize = 10;
			boolean OK = true;
			
			/************** ȡ��������ʾ���� **************/
			if (Fun.ReadConfig() && Fun.ListNewsNum>0) intPageSize = Fun.ListNewsNum;

	    	
	    	/*ȡ�ò����������Ϣ*/
	    	String sql1 = "select SmallClassName from SmallClass where SmallClassID=" + ID;
	    	ResultSet rs1 = stmt.executeQuery(sql1);
	    	if(rs1.first()) sClassTitle = rs1.getString(1);
	    	else OK = false;
	    	sb.append("<div id=\"sTitle1\"><div id=\"stFont\">");
    		sb.append(sClassTitle + "</div></div>\r\n");
    		
    		
    		if(OK)
    		{
			    /************** ������Ŷ�ȡ **************/
		    	sql= "select * from News where SpecialID=0 and SmallClassID=" + ID + " order by NewsID desc";
		    	rs = stmt.executeQuery(sql);
		    	
	    		/*��ȡ���ţ�����ҳ��ʾ*/
	    		sb.append("<div id=\"News\"><ul>\r\n");
	    		
	    		//�������û���κμ�¼���������ʾ��Ϣ
				if (!rs.next())
				{ 
					sb.append("<li><div id=\"F3\">��������</div></li>\r\n");
					OK = false;
				}
				else 
				{
					//ȡ�ô���ʾҳ�� 
					intPage = Fun.StrToInt(strPage);
					sPage = Fun.CheckReplace(sPage);
					if (intPage==0) intPage=1;	
		
					//����¼ָ�붨λ������ʾҳ�ĵ�һ����¼�� 
					if(!rs.absolute((intPage-1) * intPageSize+1)) rs.absolute(1);
					
					i = 1; 
					while(i<=intPageSize && !rs.isAfterLast())
					{	
	    				String NewsTitle = rs.getString(2);
						String sTitle = NewsTitle;
						String NewsTime = rs.getString(7);
							
						if (NewsTitle.length()>25) sTitle = NewsTitle.substring(0,25) + "..";	
						if(rs.getString(13).equals("Yes")) sTitle += "(ͼ)";
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
            return " ";
        }
    }
    
    
    
    
   /*********************************************************
	* ��������ShowSpecial
	* ��  �ã���ʾר���б�
	* ��  ����s0,ר��Id,Ϊ0��ʾ����ר��
	*         ҳ���ַ����ҳ��ʾ��ҳ��
	* ����ֵ���ַ���
	***********************************************************/
    public String ShowSpecial(String s0,String sPage,String strPage)
    {
    	try
    	{	
	    	Connection Conn = DBConn.getConn();
		    Statement stmt = Conn.createStatement(1004,1007);
		    ResultSet rs = null;
		    String sql = "";
		    String sClassTitle="û�����ר��";
		    StringBuffer sb = new StringBuffer();
		    int ID = Fun.StrToInt(Fun.CheckReplace(s0));
		    int i;
			int intPage = 1; 			
			int intPageSize = 10;
			boolean OK = true;
			
			/************** ȡ��������ʾ���� **************/
			if (Fun.ReadConfig() && Fun.ListSpecNum>0) intPageSize = Fun.ListSpecNum;
    		
    		
    		    
		    /************** ר���ȡ **************/
	    	if(ID==0) 
	    	{
	    		sql= "select * from Special order by SpecialID desc";
	    		sb.append("<div id=\"Spec\"><ul>\r\n");
	    	}
	    	else 
	    	{
	    		sql = "select * from News where SpecialID=" + ID + " order by NewsID desc";
	    		sb.append("<div id=\"News\"><ul>\r\n");
	    	}
	    	
	    	
	    	/*��ȡ���ţ�����ҳ��ʾ*/
	    	
	    	rs = stmt.executeQuery(sql);
    		//�������û���κμ�¼���������ʾ��Ϣ
			if (!rs.next())
			{ 
				sb.append("<li><div id=\"F3\">��������</div></li>\r\n");
				OK = false;
			}
			else 
			{
				//ȡ�ô���ʾҳ�� 
				intPage = Fun.StrToInt(strPage);
				sPage = Fun.CheckReplace(sPage);
				if (intPage==0) intPage=1;	
	
				//����¼ָ�붨λ������ʾҳ�ĵ�һ����¼�� 
				if(!rs.absolute((intPage-1) * intPageSize+1)) rs.absolute(1);
				
				i = 1; 
				while(i<=intPageSize && !rs.isAfterLast())
				{	
    				if(ID==0)
    				{
	    				String SpecTitle = rs.getString(2);
	    				String SpecInfo = rs.getString(3);
						String SpecTime = rs.getString(4);
						sb.append("<li><img src=\"pic/spec.gif\" border=0> <a title=\"" + SpecInfo + "\" href=\"Special.jsp?SpecialID=" + rs.getInt(1) + "\" target=_blank>");
						sb.append(SpecTitle + "</a><span>[" + SpecTime + "]</span></li>\r\n");	
						if(i%5==0 && i>1) sb.append("<div id=\"Line\"><div id=\"L2\"></div></div>\r\n");
						rs.next();
						i++;
					}
					else
					{
						String NewsTitle = rs.getString(2);
						String sTitle = NewsTitle;
						String NewsTime = rs.getString(7);
							
						if (NewsTitle.length()>25) sTitle = NewsTitle.substring(0,25) + "..";	
						if(rs.getString(13).equals("Yes")) sTitle += "(ͼ)";
						sb.append("<li><img src=\"pic/li.gif\"> <a title=\"" + NewsTitle + "\" href=\"ShowNews.jsp?NewsID=" + rs.getInt(1) + "\" target=_blank>");
						sb.append(sTitle + "</a><span>[" + NewsTime + "]</span></li>\r\n");	
						if(i%5==0 && i>1) sb.append("<div id=\"Line\"><div id=\"L2\"></div></div>\r\n");
						rs.next();
						i++;
					}
				}
			}
			sb.append("</ul></div>\r\n");
			if(OK) sb.append(Fun.Page(sPage,rs,intPage,intPageSize));	
			rs.close();
			stmt.close();
			Conn.close();			
		return sb.toString();
		}catch(Exception e)
        {
            //e.printStackTrace();
            //System.out.print(e.getMessage()); 
            return " ";
        }
    }
    
    
    
    
    
    
    /*********************************************************
	* ��������ShowLeftNews
	* ��  �ã���ʾ���ר������
	* ��  ����b:Ϊtrue��ʾ��ҳ�������ʾ����ҳ��
	* ����ֵ���ַ���
	***********************************************************/
    public String ShowLeftNews(boolean b)
    {
    	String sql1 = "";
    	String sql2 = "";
    	try
    	{	
	    	Connection Conn = DBConn.getConn();
		    Statement stmt1 = Conn.createStatement(1004,1007);
		    Statement stmt2 = Conn.createStatement(1004,1007);
		    ResultSet rs1 = null;
		    ResultSet rs2 = null;
		    //String sql1 = "";
		    //String sql2 = "";
		    StringBuffer sb = new StringBuffer();
		    int iSpecNum = 10; 			//��ʾר����
		    int iSpecNewsNum = 10;  	//ÿר��������
		    int i=0;
		    
		    /************** ȡ��ר����� **************/
		    if (Fun.ReadConfig()) 
		    {
		    	if(b && Fun.SpecNum>0 && Fun.SpecNewsNum>0) 
		    	{
		    		iSpecNum = Fun.SpecNum;
		    		iSpecNewsNum = Fun.SpecNewsNum;
		    	}
		    	else if(Fun.BSpecNum>0 && Fun.BSpecNewsNum>0) 
		    	{
		    		iSpecNum = Fun.BSpecNum;
		    		iSpecNewsNum = Fun.BSpecNewsNum;
		    	}
		    	
		    }
		    
		    
		    /************** ��ҳר�����Ŷ�ȡ **************/
	    	sql1 = "select top " + iSpecNum + " * from [Special] order by SpecialID desc";
	    	rs1 = stmt1.executeQuery(sql1);
	    	rs1.last();
		    if(rs1.getRow()<iSpecNum) iSpecNum=rs1.getRow();
			if(iSpecNum>0)
			{
				rs1.first();
				for(i=1;i<=iSpecNum;i++)
				{
		    		/*��ȡר�����*/
		    		int SpecialID = rs1.getInt(1);
		    		String SpecialInfo = rs1.getString(3);
		    		String NewsTitle = rs1.getString(2);
		    		String sTitle = NewsTitle;
		    		if (NewsTitle.length()>12) sTitle = NewsTitle.substring(0,12);
		    		sb.append("<div class=\"SpecTitle\"><div class=\"title\">\r\n");
		    		sb.append("<img src=\"pic/spec.gif\" border=0> <a title=\"" + NewsTitle + "\" href=\"Special.jsp?SpecialID=" + SpecialID + "\">");
		    		sb.append(sTitle + "</a></div>");
		    		sb.append("<div class=\"more\"><img src=\"pic/more.gif\"><a title=\"" + SpecialInfo + "\" href=\"Special.jsp?SpecialID=");
		    		sb.append(SpecialID + "\"> ��ϸ</a></div></div>\r\n");	
		    			
		    		/*��ȡÿ��ר�������·��������*/
		    		sql2 = "select top " + iSpecNewsNum + " * from News where SpecialID=" + SpecialID + " order by NewsID desc";
		    		rs2 = stmt2.executeQuery(sql2);
		    		sb.append("<div id=\"B2\"></div>\r\n");
		    		sb.append("<div class=\"SpecContent\">\r\n");
	                rs2.last();	
	                int iNum = iSpecNewsNum;
				    if(rs2.getRow()<iNum) iNum=rs2.getRow();
		    		if(rs2.first())
		    		{
						for(int j=1;j<=iNum;j++)
						{
			    			NewsTitle = rs2.getString(2);
							sTitle = NewsTitle;
							String NewsPicture = rs2.getString(8);
							String NewsInfo = rs2.getString(16);
							String IsImg = rs2.getString(13);			
							if (NewsTitle.length()>20) sTitle = NewsTitle.substring(0,20);
							if (NewsInfo.length()>40) NewsInfo = NewsInfo.substring(0,40);	
							sb.append("<h3><img src=\"pic/li.gif\"><a title=\"" + NewsTitle + "\" href=\"ShowNews.jsp?NewsID=" + rs2.getInt(1) + "\" target=_blank>\r\n");
							sb.append(sTitle + "</a></h3>\r\n");
							if(IsImg.equals("Yes")) 
							{
								sb.append("<a title=\"" + NewsTitle + "\" href=\"ShowNews.jsp?NewsID=" + rs2.getInt(1) + "\" target=_blank>\r\n");
								sb.append("<img src=\"" + NewsPicture + "\" alt=\"" + NewsTitle + "\" width=\"130\" height=\"80\" border=\"0\"></a>\r\n");
								sb.append("<p>" + NewsInfo + "</p>\r\n");
							}
							if(j%5==0 && j>0 && j!=iNum) sb.append("<div id=\"L2\"></div>\r\n");
							if(j==iNum) sb.append("<div id=\"B4\"></div>");	
		                  	rs2.next();
			    		}	
		    		}
		    		sb.append("</div>\r\n");
		    		rs2.close();
		    		if(i>1 && i!=iSpecNum) sb.append("<div id=\"L1\"></div>\r\n");
		    		rs1.next();
		    	}
		    }
	    	rs1.close();
			stmt1.close();
			Conn.close();			
			return sb.toString();
		}catch(Exception e)
        {
            //e.printStackTrace();
            //System.out.print(e.getMessage()); 
            //System.out.print("  sql1=" +sql1 + "  \n");
            //System.out.print("  sql2=" +sql2);
            return " ";
        }
    }
    
    
    
     //����
	public static void main(String[] args)
	{	
		ListClass LC = new ListClass();
		//System.out.println(LC.TopClass());
		//System.out.println(LC.ShowHotNews(null,true));
		//System.out.println(LC.ShowTopNews(null,true));
		//System.out.println(LC.ShowLeftNews(false));
		//System.out.println(LC.ShowHeadNews(null,true));
		//System.out.println(LC.ShowSClassNews("4","ShowSClass.jsp","1"));
		//System.out.println(LC.ShowHeadPic(null,true));
		//System.out.println(LC.ShowSpecial("1","special.jsp","1"));
		//System.out.println(LC.ShowClassNews(null));

	}  
  }