
/***************************************************
 *  
 *  Դ�ļ���:  Log.java
 *  ��    �ܣ� �����껪����ϵͳ - ��־������
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

public class Log
{

    DBConnection DBConn = new DBConnection();
    Function Fun = new Function(); 
    
    public Log()
    {
    	
    } 
     
     
     
    /*********************************************************
	* ��������ReadLog
	* ��  �ã���ȡ��־�б�
	* ��  ����sPage,ҳ���ַ; strPage,�ڼ�ҳ
	* ����ֵ���ַ���
	***********************************************************/
    public String ReadLog(String sPage,String strPage)
    {
    	try
    	{	
	    	Connection Conn = DBConn.getConn();
		    Statement stmt = Conn.createStatement(1004,1007);
		    String sql = "select * from Log order by LogID desc";
		    ResultSet rs = stmt.executeQuery(sql);
		    StringBuffer sb = new StringBuffer();
		    int i;
			int intPage = 1; 			
			int intPageSize = 10;
			if (Fun.ReadConfig() && Fun.AdminLogListNum>0) intPageSize = Fun.AdminLogListNum;
		    
		    sb.append("<br><br><table width=\"90%\"  border=\"1\" align=\"center\" cellpadding=\"2\" cellspacing=\"0\">\r\n");
			sb.append("<tr><td height=\"27\" colspan=\"7\"  background=\"images/bg.gif\">");
			sb.append("<div align=\"center\" class=\"title\"><strong>��־����</strong></div></td>");
			sb.append("</tr>\r\n");
			sb.append("<tr>\r\n");
			sb.append("<td height=\"25\" colspan=\"7\"><strong>&nbsp;<a href=\"Admin_Log.jsp?Action=DelAll\">");
			sb.append("<font color=\"#ff6600\">[ɾ��������־]</font></a></strong></td>\r\n");
			sb.append("</tr>\r\n");
			sb.append("<tr>\r\n");
			sb.append("<td height=\"25\" width=\"5%\"><div align=\"center\" class=\"chinese\"><b>ID</b></div></td>\r\n");
			sb.append("<td width=\"12%\"><div align=\"center\" class=\"chinese\"><b>�����û�</b></div></td>\r\n");
			sb.append("<td width=\"23%\"><div align=\"center\" class=\"chinese\"><b>�¼�</b></div></td>\r\n");
			sb.append("<td width=\"26%\"><div align=\"center\" class=\"chinese\"><b>ʱ��</b></div></td>\r\n");
			sb.append("<td width=\"14%\"><div align=\"center\" class=\"chinese\"><b>IP��ַ</b></div></td>\r\n");
			sb.append("<td width=\"10%\"><div align=\"center\" class=\"chinese\"><b>���</b></div></td>\r\n");
			sb.append("<td width=\"10%\"><div align=\"center\" class=\"chinese\"><b>����</b></div></td>\r\n");
			sb.append("</tr>\r\n");
			
			//�������û���κμ�¼���ո�����ʾ��Ϣ
			if (!rs.next())
			{ 
				sb.append("<tr height=\"25\" bgcolor=\"#d6dff7\"  class=\"info1\"><td colspan=\"8\">\r\n");
				sb.append("<div align=\"center\"><b>��ʱû���κ���־!</b></div></td></tr>\r\n");
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
					int LogID = rs.getInt(1);
					String LogType  = rs.getString(3);
					String User = rs.getString(2);
					String LogTime = rs.getString(4);
					String IP = rs.getString(5);
					String sResult = rs.getString(6);
					String sBgColor = "#D6DFF7";
					String sLogType = LogType;
					if(sResult.equals("No"))
					{
						sResult = "<font color='#ff6600'><b>ʧ��</b></font>";
						sBgColor = "#DBE6FE";
					}
					else sResult = "<font color='#000000'><b>�ɹ�</b></font>";
					
					if(LogType.length()>10) sLogType = LogType.substring(0,10);
					sb.append("<tr bgcolor=\"" + sBgColor + "\" height=\"22\">\r\n");
					sb.append("<td height=\"25\" ><div align=\"center\" class=\"chinese\">" + LogID + "</div></td>\r\n");
					sb.append("<td><div align=\"center\" class=\"chinese\">" + User + "</div></td>\r\n");
					sb.append("<td><div align=\"left\" class=\"chinese\" Title=\"" + LogType + "\">" + sLogType + "</div></td>\r\n");
					sb.append("<td><div align=\"center\" class=\"chinese\">" + LogTime + "</div></td>\r\n");
					sb.append("<td><div align=\"center\" class=\"chinese\">" + IP + "</div></td>\r\n");
					sb.append("<td><div align=\"center\" class=\"chinese\">" + sResult + "</div></td>\r\n");
					sb.append("<td><div align=\"center\" class=\"chinese\">[<a href=\"Admin_Log.jsp?Action=Del&LogID=");
					sb.append(LogID);
					sb.append("\">ɾ��</a>]</div></td>\r\n");
					sb.append("</tr>\r\n");
					rs.next();
					i++;
				}
			}
			
			sb.append("</table>");
			sb.append(Fun.Page(sPage,rs,intPage,intPageSize));
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
	* ��������DelLog
	* ��  �ã�ɾ����־
	* ��  ����s0����־ID��s1�������û�
	*         s2:IP���Ƿ�ɾ��������־
	* ����ֵ���ɹ���true �����򷵻ء�false
	***********************************************************/ 
	public boolean DelLog(String s0,String s1,String s2,boolean b)
    {
	   
	    String NowTime = (new java.util.Date()).toLocaleString();
		String [] sLog = new String[5];
		sLog[0] = s1;
		sLog[2] = NowTime;
		sLog[3] = s2; 
		int LogID = Fun.StrToInt(s0);
   		if(!b && LogID==0)
   			return false;
   		else
   		{
			try{
		    	Connection Conn = DBConn.getConn();
		    	Statement stmt = Conn.createStatement();
		    	String sql = "delete from Log where LogID=" + LogID;
		    	if(b) sql = "delete from Log";
		    	stmt.executeUpdate(sql);
		    	stmt.close();
		    	Conn.close();
		    	if(b)
			    {	sLog[1] = "ɾ��������־!"; 
					sLog[4] = "Yes";
					Fun.AddLog(sLog);
				}
		    	return true;
			}catch(Exception e){
				////e.printStackTrace();
				//System.out.print(e.getMessage());
				//return "����û��������������ԣ�" + e.getMessage();
				sLog[1] = "ɾ��������־ʧ�ܣ������쳣!"; 
				sLog[4] = "No";
				Fun.AddLog(sLog);
				return false;
			}
    	}
	
	}
    
    
     
     //����
	public static void main(String[] args)
	{	
		Log log = new Log();
		String sa = log.ReadLog("Log.jsp","1");
		//System.out.println(sa);
		if(log.DelLog(null,"dream","172.16.166.50",true)) System.out.println("Yes");

	}
     
}