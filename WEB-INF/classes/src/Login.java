
/***************************************************
 *  
 *  源文件名:  Login.java
 *  功    能： 梦想年华新闻系统 - 用户登录类
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
	* 函数名：LoginCheck
	* 作  用：验证登录
	* 参  数：s1,s2,s3: 字符串型，登录用户名，密码，IP
	* 返回值：布尔型。登录成功返回 Ture，否则返回 False
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
	    		sLog[1] = "用户登录 [ 用户名不存在 ]";
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
		    		sLog[1] = "用户登录";
		    		sLog[4] = "Yes";
		    		UpdateLogin(s3,sLog[2],LoginNum+1,AdminID);
		    	    Fun.AddLog(sLog);
		    		OK = true;
	    		}
	    		else
	    		{
	    			sLog[1] = "用户登录[密码错误]";
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
            sLog[1] = "用户登录[程序异常]";
            sLog[4] = "No";
	    	Fun.AddLog(sLog);
            return false;
        }				
    }
     
   
    /*********************************************************
	* 函数名：UpdateLogin
	* 作  用：更新登录信息
	* 参  数：s1，最后登录的IP地址
	*　　     s2，最后登录的时间
	*　　　　 iNum，登录资料
	*         ID，管理员ID
	* 返回值：布尔型。更新成功返回 Ture，否则返回 False
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
   
   
   
    //测试
	public static void main(String[] args)
	{	
		Login login = new Login();
		if (login.LoginCheck("dream","dream","172.16.166.50"))
			System.out.println("Success!" + login.AdminID);
		else
			System.out.println("Fail");
	}
    
    
}