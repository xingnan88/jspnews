    
/***************************************************
 *  
 *  源文件名:  Config.java
 *  功    能： 梦想年华新闻系统 - 系统参数设置类
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

public class Config
{

    DBConnection DBConn = new DBConnection();
    Function Fun = new Function();
    
    public Config()
    {
    	
    }  
    
    
     
    /*********************************************************
	* 函数名：SaveConfig
	* 作  用：保存系统参数设置
	* 参  数：s0:字符串数组,s1，管理员；
	*         s2:IP，b，是否系统参数
	* 返回值：字符串型
	***********************************************************/
    public String SaveConfig(String [] s,String s1,String s2,boolean b)
    {	
    	String NowTime = (new java.util.Date()).toLocaleString();
		String [] sLog = new String[5];
		sLog[0] = s1;
		sLog[2] = NowTime;
		sLog[3] = s2; 
		if(b) sLog[1] = "修改系统配置参数";
		else sLog[1] = "修改网站基本信息参数";
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
	    		sa2[i] = "第 " + (i+1) + " 行输入的数据";
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
		    			sError = "第 " + (i+1) + " 行输入的数据不是[0-999]之间";
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
            return "操作失败!";
        }				
    }
    
    
    
   /*********************************************************
	* 函数名：ReadConfig
	* 作  用：得到系统参数设置
	* 参  数：b，是否系统参数
	* 返回值：布尔型。成功返回 Ture，否则返回 False
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
    
    
    
    
    
    //测试
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