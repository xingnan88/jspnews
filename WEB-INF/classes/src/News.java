
/***************************************************
 *  
 *  Դ�ļ���:  News.java
 *  ��    �ܣ� �����껪����ϵͳ - ���Ź�����
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

public class News
{

    DBConnection DBConn = new DBConnection();
    Function Fun = new Function(); 
    
    public News()
    {
    	
    } 
    
    
    /*********************************************************
	* ��������ReadNews
	* ��  �ã���ȡ�����Ϣ
	* ��  ����s0:����ID
	* ����ֵ���ַ��������͡����ĸ�����Ϣ
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
	* ������:ListNews
	* ��  ��:�������
	* ��  ����sPage,ҳ���ַ; strPage,�ڼ�ҳ
	* ����ֵ:�ַ����͡����ز����������Ϣ
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
	    	
	    	//�������Ϣ
    		sb.append("<br><br><table width=\"90%\"  border=\"1\" align=\"center\" cellpadding=\"1\" cellspacing=\"0\">\r\n");
			sb.append("<tr background=\"images/bg.gif\">\r\n");
			sb.append("<td height=\"27\" colspan=\"4\" background=\"images/bg.gif\"><div align=\"center\">");
			sb.append("<strong class=\"title\">�����б�</strong></div></td></tr>\r\n");
			sb.append("<tr class=\"chinese\" height=\"25\">\r\n");
			sb.append("<td width=\"8%\" height=\"25\"><div align=\"center\" class=\"chinese\"><strong>ID</strong></div></td>\r\n");
			sb.append("<td width=\"50%\"><div align=\"center\" class=\"chinese\"><strong>���ű���</strong></div></td>\r\n");
			sb.append("<td width=\"26%\"><div align=\"center\" class=\"chinese\"><strong>����ʱ��</strong></div></td>\r\n");
			sb.append("<td width=\"16%\"><div align=\"center\" class=\"chinese\"><strong>����</strong></div></td>\r\n");
			sb.append("</tr>\r\n");
			sb.append("<tr>\r\n");
			sb.append("<td height=\"30\" colspan=\"4\" align=\"center\">");
			sb.append("<form name=\"ListNews\" style=\"margin:0 2px 0 0; height:25px;width:500px;font-size:12px\">");
			sb.append("<div align=\"center\"><font color=\"#ff6600\"><strong>���Ź������ͨ��</strong>&nbsp;&nbsp;");
			sb.append("������Ҫ�������ŵ�ID:&nbsp;</font>");
			sb.append("<input id=\"NewsID\" type=\"text\" size=\"10\" maxlength=\"8\" class=\"chinese\">&nbsp;");
			sb.append("<input name=\"Submit\" type=\"button\" class=\"button\" value=\"�� ��\" onClick=\"fastNews(false);\">&nbsp;");
			sb.append("<input name=\"Submit2\" type=\"button\" class=\"button\" value=\"ɾ ��\" onClick=\"fastNews(true);\">");
			sb.append("</form>");
			sb.append("</td></tr>\r\n");
			
	    	//�������û���κμ�¼���ո�����ʾ��Ϣ
			if (!rs.next())
			{ 
				sb.append("<tr height=\"25\" bgcolor=\"#d6dff7\"  class=\"info1\"><td colspan=\"4\">\r\n");
				sb.append("<div align=\"center\"><b>��ʱ��û������!</b></div></td></tr>\r\n");
			}
			else 
			{
				//ȡ�ô���ʾҳ�� 
				intPage = Fun.StrToInt(strPage);
				sPage = Fun.CheckReplace(sPage);
				if (intPage==0) intPage=1;	
	
				//����¼ָ�붨λ������ʾҳ�ĵ�һ����¼�� 
				rs.absolute((intPage-1) * intPageSize+1);
				i = 0; 
				while(i < intPageSize && !rs.isAfterLast())
				{		
				    int NewsID = rs.getInt("NewsID");	
					String NewsTitle = rs.getString("NewsTitle");
					String sTitle = NewsTitle;
					String sImg = "news.gif";
					String sImgTitle = "��ͨ����";
					
					if (NewsTitle.length()>20) sTitle = NewsTitle.substring(0,20) + "..";
					
					if(rs.getString("IsImg").equals("Yes")) 
					{
						sImg = "ImgNews.gif";
						sImgTitle = "ͼƬ����";
					}
					else if(rs.getString("IsImg").equals("Yes")) 
					{
						sImg = "TopNews.gif";
						sImgTitle = "ͷ������";
					}
					sb.append("<tr bgcolor=\"#d6dff7\" height=\"22\">\r\n");
					sb.append("<td bgcolor=\"#d6dff7\" height=\"22\"><div align=\"center\" class=\"chinese\">" + NewsID + "</div></td>\r\n");
					sb.append("<td bgcolor=\"#d6dff7\"><div class=\"news\">&nbsp");
					sb.append("<img src=\"../pic/" + sImg + "\" alt=\"" + sImgTitle + "\">&nbsp;");
					sb.append("<a href=\"../ShowNews.jsp?NewsID=" + NewsID + "\"  Title=\"" + NewsTitle + "\">" + sTitle + "</div></td>\r\n");
					sb.append("<td bgcolor=\"#d6dff7\"><div align=\"center\" class=\"chinese\">" + rs.getString("NewsTime") + "</div></td>\r\n");
					sb.append("<td bgcolor=\"#d6dff7\"><div align=\"center\"  class=\"chinese\">");
					sb.append("<a href=\"ModifyNews.jsp?NewsID=" + NewsID + "\">[�޸�]</a>");
					sb.append("<a href=\"DelNews.jsp?NewsID=" + NewsID + "\">[ɾ��]</a> </div></td>\r\n");
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
	* ������:AddNews
	* ��  ��:�������
	* ��  ��:s:�ַ������飬������ŵĸ������
	*        s1:����Ա;s2:IP��ַ
	* ����ֵ:�ַ����͡����ز����������Ϣ
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
	    	sa2[0] = "���ű���";
	    	sa2[1] = "���ŷ�����";
	    	sa2[2] = "���ų���";
	    	sa2[3] = "��������";
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
	    			sError="��������ר�ⲻ��ͬʱΪ��";
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
			    	sLog[1] = "�������[" + s[0] + "]";
			    	sLog[2] = NowTime;
			    	sLog[3] = s2; 
			    	sLog[4] = "Yes";
			    	Fun.AddLog(sLog);
					stmt.close();
					Conn.close();
			    	return "Yes";
			    }catch (Exception e) {
						Conn.rollback();		//�ع�JDBC����
						e.printStackTrace();
						Conn.close();
						return "�������ʧ��!";
						}  
		    }
		    else return sError;
	    }catch(Exception e)
        {
            e.printStackTrace();
            //System.out.print(e.getMessage());
            return "������Ų���ʧ��!";
        }
     }
    
    
    
    /*********************************************************
	* ������:EditNews
	* ��  ��:�޸�����
	* ��  ��:�ַ������飬������ŵĸ������
	*         s0:����ID,s1:����Ա;s2:IP��ַ
	* ����ֵ:�ַ����͡����ز����������Ϣ
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
	    	sa2[0] = "���ű���";
	    	sa2[1] = "���ŷ�����";
	    	sa2[2] = "���ų���";
	    	sa2[3] = "��������";
	    	int NewsID = Fun.StrToInt(s0);
	    	if (NewsID==0)
	    	{
	    		OK = false;
	    		sError = "����ID�������ݴ���������!"; 
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
	    			sError="��������ר�ⲻ��ͬʱΪ��";
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
		    	sLog[1] = "�޸�IDΪ[" + NewsID + "]������";
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
            return "�޸����Ų���ʧ��!";
        }
     }
    
    
    /*********************************************************
	* ��������DelNews
	* ��  �ã�ɾ������
	* ��  ����s0:����ID��s1:����Ա��s2:IP
	* ����ֵ���ɹ���true �����򷵻ء�false
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
			    	sLog[1] = "ɾ��IDΪ[" + s0 + "]������";
			    	sLog[4] = "Yes";
			    	Fun.AddLog(sLog);
					stmt.close();
					Conn.close();
			    	return true;
		    	}catch (Exception e) {
						Conn.rollback();		//�ع�JDBC����
						////e.printStackTrace();
						Conn.close();
						return false;
						}  
		    }
    	}catch(Exception e){
    		//e.printStackTrace();
    		System.out.print(e.getMessage());
    		//System.out.print(sql);
    		sLog[1] = "ɾ������[" + s0 + "]����";
	    	sLog[4] = "No";
	    	Fun.AddLog(sLog);
    		return false;
    	}
	
	}
    
    
    
    
    //����
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
     