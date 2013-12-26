
/***************************************************
 *  
 *  Դ�ļ���:  Login.java
 *  ��    �ܣ� �����껪����ϵͳ - �û���¼��
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
import dreamtime.dreamnews.MD5;

public class Login
{

    DBConnection DBConn = new DBConnection();
    Function Fun = new Function(); 
    MD5 md5 = new MD5();
    public static int AdminID;
    public static int AdminType;
    public static int LoginNum;
    
    
    public Login()
    {
       
    }
    
    
    /*********************************************************
	* ��������LoginCheck
	* ��  �ã���֤��¼
	* ��  ����s1,s2,s3: �ַ����ͣ���¼�û��������룬IP
	* ����ֵ�������͡���¼�ɹ����� Ture�����򷵻� False
	***********************************************************/
    public boolean LoginCheck(String s1,String s2,String s3)
    {
    	String [] sLog = new String[5];
    	try
    	{
	    	Connection Conn = DBConn.getConn();
	    	Statement stmt = Conn.createStatement(1004,1007);
	    	ResultSet rs = null;	
	    	boolean OK = true;
	    	String AdminPwd = "";
	    	String User = Fun.CheckReplace(s1);
	    	String Pwd = md5.getMD5ofStr(md5.getMD5ofStr(Fun.CheckReplace(s2)));
	    	String Sql = "select * from Admin where AdminName='" + User + "'";
	    	rs = stmt.executeQuery(Sql);
    		sLog[0] = User;
    		sLog[2] = (new java.util.Date()).toLocaleString();
    		sLog[3] = s3;
	    	if (!rs.next())
    		{
	    		sLog[1] = "�û���¼ [ �û��������� ]";
	    		sLog[4] = "No";
	    		Fun.AddLog(sLog);
	    		OK = false;
    		}
    		else 
    		{
    			AdminPwd = rs.getString("AdminPwd");
    			if(Pwd.equals(AdminPwd))
	    		{	
		    	
		    		AdminID=rs.getInt("AdminID");
		    		AdminType=rs.getInt("AdminType");
		    		LoginNum = rs.getInt("LoginNum");
		    		sLog[1] = "�û���¼";
		    		sLog[4] = "Yes";
		    		UpdateLogin(s3,sLog[2],LoginNum+1,AdminID);
		    	    Fun.AddLog(sLog);
		    		OK = true;
	    		}
	    		else
	    		{
	    			sLog[1] = "�û���¼[�������]";
		    		sLog[4] = "No";
		    	    Fun.AddLog(sLog);
		    	    OK = false;	
	    		}	
    		}
    		return OK;
	    }
	    catch(SQLException e)
        {
            //e.printStackTrace();
            //return e.getMessage().toString();
            sLog[1] = "�û���¼[�����쳣]";
            sLog[4] = "No";
	    	Fun.AddLog(sLog);
            return false;
        }				
    }
     
   
    /*********************************************************
	* ��������UpdateLogin
	* ��  �ã����µ�¼��Ϣ
	* ��  ����s1������¼��IP��ַ
	*����     s2������¼��ʱ��
	*�������� iNum����¼����
	*         ID������ԱID
	* ����ֵ�������͡����³ɹ����� Ture�����򷵻� False
	***********************************************************/
    public boolean UpdateLogin(String s1,String s2,int iNum,int ID)
    {	
    	String sql = "";
    	try
    	{
	    	Connection Conn = DBConn.getConn();
	    	Statement stmt = Conn.createStatement(1004,1007);
	    	ResultSet rs = null;	
    		sql = "update Admin set ";
    		sql += "LastLoginIP='" + s1 + "',";
    		sql += "LastLoginTime='" + s2 + "',";
    		sql += "LoginNum=" + iNum + " where AdminID=" + ID ;
	    	stmt.executeUpdate(sql);
			stmt.close();
			Conn.close();
	    	return true;
	    }catch(SQLException e)
        {
            //e.printStackTrace();
            System.out.print(sql);
            return false;
        }				
    }
   
   
   
    //����
	public static void main(String[] args)
	{	
		Login login = new Login();
		if (login.LoginCheck("dream","dream","172.16.166.50"))
			System.out.println("Success!" + login.AdminID);
		else
			System.out.println("Fail");
	}
    
    
}