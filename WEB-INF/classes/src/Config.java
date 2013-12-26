    
/***************************************************
 *  
 *  Դ�ļ���:  Config.java
 *  ��    �ܣ� �����껪����ϵͳ - ϵͳ����������
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

public class Config
{

    DBConnection DBConn = new DBConnection();
    Function Fun = new Function();
    
    public Config()
    {
    	
    }  
    
    
     
    /*********************************************************
	* ��������SaveConfig
	* ��  �ã�����ϵͳ��������
	* ��  ����s0:�ַ�������,s1������Ա��
	*         s2:IP��b���Ƿ�ϵͳ����
	* ����ֵ���ַ�����
	***********************************************************/
    public String SaveConfig(String [] s,String s1,String s2,boolean b)
    {	
    	String NowTime = (new java.util.Date()).toLocaleString();
		String [] sLog = new String[5];
		sLog[0] = s1;
		sLog[2] = NowTime;
		sLog[3] = s2; 
		if(b) sLog[1] = "�޸�ϵͳ���ò���";
		else sLog[1] = "�޸���վ������Ϣ����";
    	try
    	{
	    	Connection Conn = DBConn.getConn();
	    	Statement stmt = Conn.createStatement(1004,1007);
			int iMax = s.length;
			String [] sa1 = new String [iMax];
	    	String [] sa2 = new String [iMax];
	    	int [] iArray = new int [iMax];
	    	boolean OK = true;
	    	String sError = "";
	    	String sql = "";
	    	for(int i=0;i<iMax;i++) 
	    	{
	    		sa1[i] = Fun.CheckReplace(s[i]);
	    		iArray[i] = Fun.StrToInt(sa1[i]);
	    		sa2[i] = "�� " + (i+1) + " �����������";
	    	}
	    	
	    	String sOK = Fun.CheckDate(sa1,sa2);
	    	if (!sOK.equals("Yes"))
	    	{
	    		OK = false;
	    		sError = sOK; 
	    	}
	    	
	    	
	    	if(OK && b)
	    	{
	    		for(int i=0;i<iMax;i++)
		    	{
		    		if(iArray[i]<1 || iArray[i]>999) 
		    		{
		    			sError = "�� " + (i+1) + " ����������ݲ���[0-999]֮��";
		    			OK = false;
		    			break;
		    		}
		    	}
		    }
	    	
	    	
	    	if(OK)
	    	{
	    		if(!b) 
	    		{
	    			sql ="update Config set ";
	    			sql += "DreamNewsTitle='" + s[0] + "',";
					sql += "DreamNewsCopyRight='" + s[1] + "',";
					sql += "DreamNewsEmail='" + s[2] + "'";
				}
	    		else
	    		{
		    		sql = "update Config set ";
					sql += "AdminUserListNum=" + iArray[0] + ",";
					sql += "AdminLogListNum=" + iArray[1] + ",";
					sql += "AdminNewsListNum=" + iArray[2] + ",";
					sql += "HotNewsNum=" + iArray[3] + ",";
					sql += "HeadNewsNum=" + iArray[4] + ",";
					sql += "TopNewsNum=" + iArray[5] + ",";
					sql += "TopImgNum=" + iArray[6] + ",";
					sql += "ClassNewsNum=" + iArray[7] + ",";
					sql += "ClassImgNum=" + iArray[8] + ",";
					sql += "SpecNum=" + iArray[9] + ",";
					sql += "SpecNewsNum=" + iArray[10] + ",";
					sql += "BHotNewsNum=" + iArray[11] + ",";
					sql += "BHeadNewsNum=" + iArray[12] + ",";
					sql += "BTopNewsNum=" + iArray[13] + ",";
					sql += "BTopImgNum=" + iArray[14] + ",";
					sql += "BClassNewsNum=" + iArray[15] + ",";
					sql += "BClassImgNum=" + iArray[16] + ",";
					sql += "BSpecNum=" + iArray[17] + ",";
					sql += "BSpecNewsNum=" + iArray[18] + ",";
					sql += "ListSpecNum=" + iArray[19] + ",";
					sql += "ListNewsNum=" + iArray[20] + ",";
					sql += "SearchNewsNum=" + iArray[21];
	    		}
	    		stmt.executeUpdate(sql);
				stmt.close();
				Conn.close();
			    sLog[4] = "Yes";
			    Fun.AddLog(sLog);
	    		return "Yes";	
	    	}
	    	else return sError;
	    }catch(SQLException e)
        {
            e.printStackTrace();
            sLog[4] = "No";
            Fun.AddLog(sLog);
            return "����ʧ��!";
        }				
    }
    
    
    
   /*********************************************************
	* ��������ReadConfig
	* ��  �ã��õ�ϵͳ��������
	* ��  ����b���Ƿ�ϵͳ����
	* ����ֵ�������͡��ɹ����� Ture�����򷵻� False
	***********************************************************/
    public String [] ReadConfig(boolean b)
    {	
    	try
    	{
	    	Connection Conn = DBConn.getConn();
	    	Statement stmt = Conn.createStatement(1004,1007);
	    	String sql = "select top 1 * from Config";
	    	ResultSet rs = stmt.executeQuery(sql);
	    	int iMax = 3;
	    	if(b) iMax = 22;
	    	String [] sArray = new String [iMax];
	    	int [] iArray = new int[iMax];
	    	rs.next();
	    	if(b)
	    	{
				for(int i=0;i<22;i++) 
				{
					iArray[i] = rs.getInt(i+2);
					sArray[i] = Integer.toString(iArray[i]);
				}
			}
			else for(int i=0;i<3;i++) sArray[i] = rs.getString(i+24);

			stmt.close();
			Conn.close();
	    	return sArray;
	    }catch(SQLException e)
        {
            e.printStackTrace();
            return null;
        }				
    }
    
    
    
    
    
    //����
	public static void main(String[] args)
	{	
		 Config config = new Config();
		 //config.ReadConfig(true);
		 String  [] s1 = config.ReadConfig(true);
		 String [] s2 = new String [22];
		 
		 for(int i=0;i<s1.length;i++)
		 {
		 	s1[i] = "10";
		 }
		 System.out.println(config.SaveConfig(s1,"http://ww","172.16.166.50",true));
		 for(int i=0;i<s1.length;i++)
		 {
		 	System.out.println(s2[i]);
		 }
	}
}