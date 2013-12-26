
/***************************************************
 *  
 *  Դ�ļ���:  ShowNews.java
 *  ��    �ܣ� �����껪����ϵͳ - ǰ̨������ʾ��
 *	���ߣ������껪 [DreamTime]
 *	Email:fanwsp@126.com
 *  QQ:122142023 
 * 	CopyRight(c)2005-2006 by DreamTime 
 *
 ****************************************************
*/

package dreamtime.dreamnews;			//ָ�������ڵİ�

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
    		DreamNewsTitle="�����껪����ϵͳ";
    		
    	if(DreamNewsCopyRight==null || DreamNewsCopyRight.equals("")) 
    		DreamNewsCopyRight="�����껪[DreamTime]";
    } 
 
 
 	/*********************************************************
	* ��������ShowNews
	* ��  �ã�ǰ̨��ʾ����
	* ��  ��������ID
	* ����ֵ���ַ���
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
		    	sError = "<div id=\"Content\"><p>�Ƿ���������!</p></div>";
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
		    		sError = "<div id=\"Content\"><p>�������ݴ���!</p></div>";
		    		IsSpec = true;
			    }
			    
			    if(IsSpec) 
			    {
			    	rs=stmt.executeQuery(sql1);
			    	if(!rs.next())
				    {
				    	OK = false;
			    		sError = "<div id=\"Content\"><p>�������ݴ���!</p></div>";
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
					DreamNewsTitle += " ��ҳ"; 
					sb.append("<div id=\"Pos\"><a title=\"" + DreamNewsTitle + "\" href=\"index.jsp\">��ҳ</a>");
					
					if(IsSpec)
					{
						sb.append(pos + "<a title=\"ר������\" href=\"Special.jsp\">ר������</a>");
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
					sb.append("����ʱ�䣺" + NewsTime + "&nbsp;");
					sb.append("������Դ��" + NewsFrom + "&nbsp;");
					sb.append("�����ˣ�" + NewsAuthor + "&nbsp;");
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
	* ��������SearchNews
	* ��  �ã�ʵ��������������
	* ��  �������Źؼ���,ҳ���ַ����ҳ��ʾ��ҳ��
	* ����ֵ���ַ���
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
		    String sClassTitle="û��������";
		    StringBuffer sb = new StringBuffer();
		    int i;
			int intPage = 1; 			
			int intPageSize = 50;
			boolean OK = true;
			int KeyType = Fun.StrToInt(Fun.CheckReplace(s[0]));
			int NewsType = Fun.StrToInt(Fun.CheckReplace(s[1]));
			String sKey = Fun.getStrCN(Fun.CheckReplace(s[2]));
			
			sb.append("<div id=\"News\"><ul>\r\n");
			
			/************** ȡ��������ʾ���� **************/
			if (Fun.ReadConfig() && Fun.SearchNewsNum>0) intPageSize = Fun.SearchNewsNum;
			
			if(sKey.equals("") || sKey==null) 
			{
				OK = false;
				sb.append("<li><div id=\"F3\">�����ؼ��ֲ���Ϊ��!</div></li>\r\n");
			}
			
			if(OK)
			{
				
				/*����������SQL���*/
				sql  = "select * from News where ";
				switch(KeyType)
				{													
				case 1: 			//����������
					sql += "NewsTitle like '%" + sKey + "%'";
					break;
					
				case 2: 			//����������
					sql += "NewsContent like '%" + sKey + "%'";
					break;
					
				case 3: 			//�����Źؼ�������
					sql += "NewsKey like '%" + sKey + "%'";
					break;
				
				case 4: 			//�����ŷ���ʱ������
					sql += "NewsTime like '%" + sKey + "%'";
					break;
						
				default:			//����
					sql += "NewsTitle like '%" + sKey + "%'";
					sql += " or NewsContent like '%" + sKey + "%'";
					sql += " or NewsKey like '%" + sKey + "%'";
					break;	
				}
				
				
				switch(NewsType)
				{													
				case 1: 			//ͷ��
					sql += " and IsHead='Yes'";
					break;
					
				case 2: 			//ͼƬ
					sql += " and IsImg='Yes'";
					break;
					
				case 3: 			//��ͨ
					sql += " and IsHead='No' and IsImg='No'";
					break;
					
				default:			//����
					
					break;	
				}
				
			
	    		
	    		
	    		
	    		/*��ȡ���ţ�����ҳ��ʾ*/
	    		rs = stmt.executeQuery(sql);
	    		
	    		//�������û���κμ�¼���������ʾ��Ϣ
				if (!rs.next())
				{ 
					sb.append("<li><div id=\"F3\">û������</div></li>\r\n");
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
            System.out.print(sql);
            return " ";
        }
    }
    
 
 
 	/*********************************************************
	* ��������IDToTitle
	* ��  �ã���IDת��Ϊ����
	* ��  ����ID��ID���1Ϊ���࣬2ΪС�࣬3Ϊ����
	* ����ֵ���ַ���
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
				case 0: 			//��վ����
					Title = DreamNewsTitle;
					OK = false;
					break;
																	
				case 1: 			//����
					sql = "select BigClassName from BigClass where BigClassID=" + ID;
					break;
					
				case 2: 			//С��
					sql = "select SmallClassName from SmallClass where SmallClassID=" + ID;
					break;
					
				case 3: 			//����
					sql = "select NewsTitle from News where NewsID=" + ID;
					break;
					
				case 4: 			//ר��
					sql = "select SpecialName from Special where SpecialID=" + ID;
					break;
					
				case 5: 			//ר���б�
					Title = "ר���б�";
					OK = false;
					break;
					
				default:			//����
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
 
 
     //����
	public static void main(String[] args)
	{	
		ShowNews sNews = new ShowNews();
		System.out.println(sNews.ShowNews("1"));
		String [] s1 = new String[3];
		//s1[0] = "0";
		//s1[1] = "0";
		//s1[2] = "����";
		//System.out.println(sNews.SearchNews(s1,"Search.jsp?","1"));
		//System.out.println(LC.ShowHeadPic("1"));
		//System.out.println(sNews.IDToTitle("1",5));

	}     
 }
 
 