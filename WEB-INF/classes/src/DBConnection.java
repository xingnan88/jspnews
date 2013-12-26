/***************************************************
 *  
 *  源文件名:  DBConnecion.java
 *  功    能： 梦想年华新闻系统 - 数据库连接类
 *	作者：梦想年华 [DreamTime]
 *	Email:fanwsp@126.com
 *  QQ:122142023 
 * 	CopyRight(c)2005-2006 by DreamTime 
 *
 ****************************************************
*/


package dreamtime.dreamnews;			//指定类所在的包
import java.sql.*;						//导入数据库操作的类
import java.util.*;
import java.io.*;

public class DBConnection					
{

    private String FileName;			//配置文件名
    private int DBType;					//数据库类型
   
    private Connection conn;			//连接对象
	private Statement stmt;				//语句对象
	private ResultSet rs;				//结果集对象
	
    private String AccessDriver;		//保存Access驱动程序
    private String AccessURL; 			//保存Access连接字符串	
    private String AccessPath; 			//保存Access数据库的路径	
    
    private String MySqlDriver;			//MYSQL Server驱动程序
    private String MySqlURL; 			//MYSQL Server连接字符串
        
    private String SqlDriver;			//SQL Server驱动程序
    private String SqlURL; 				//SQL Server连接字符串
    
    private String OracleDriver;		//Oracle驱动程序
    private String OracleURL; 			//Oracle连接字符串
	
    
    
    public DBConnection()
    {
    	conn = null;
    }

	public  Connection getConn()
	{

		DBType= new Function().StrToInt(getPara("DBType"));
	
		switch(DBType)
		{
			case 0:return(getConnToAccess());
			case 1:return(getConnToMySql());
			case 2:return(getConnToSql());
			case 3:return(getConnToOracle());
			default:return null;
		}	
	}
	
	
	
	public String getPara(String ParaName) 
	{
		FileName="../../DBConfig.property";
		Properties prop= new Properties();
		try
		{
			InputStream is = getClass().getResourceAsStream(FileName);
			prop.load(is);
			if(is!=null) is.close();
		}
		catch(Exception e) {
			return "Error!";
		}
		return prop.getProperty(ParaName);
	}
	
	
	
    public Connection getConnToAccess()
    {
		try{
	    	AccessDriver = getPara("AccessDriver");		
	    	AccessURL = getPara("AccessURL");
	    	AccessPath = getPara("AccessPath");
			AccessURL=AccessURL+AccessPath;
	    	Class.forName(AccessDriver).newInstance();
	    	conn = DriverManager.getConnection(AccessURL);
	    	}catch(Exception e){
	    		//e.printStackTrace();
		    	//return "操作数据库出错，请仔细检查" ;
	    		//System.err.println(e.getMessage());
	    	}
	    return conn;
    }
     
     
    public Connection getConnToMySql()
    {
		try{
	 		MySqlDriver = getPara("MySQLDriver");	
	    	MySqlURL = getPara("MySQLURL");
	    	Class.forName(MySqlDriver).newInstance();
	    	conn = DriverManager.getConnection(MySqlURL);
	    	}catch(Exception e){
	    		//e.printStackTrace();
		    	//return "操作数据库出错，请仔细检查" ;
		    	//System.err.println(e.getMessage());
	    	}
	    return conn;
    } 
    
	 
	public Connection getConnToSql()
	{     	
     	try{
     		SqlDriver = getPara("SQLDriver");
        	SqlURL = getPara("SQLURL");
	    	Class.forName(SqlDriver).newInstance();
	    	conn = DriverManager.getConnection(SqlURL);
	    	}catch(Exception e){
	    		e.printStackTrace();
	    		//return "操作数据库出错，请仔细检查" ;
	    		//System.err.println(e.getMessage());
	    	}
	    return conn;
    }
    
	 
    public Connection getConnToOracle()
    {     	
		try{
	 		OracleDriver = getPara("OracleDriver");	
	    	OracleURL = getPara("OracleURL");
	    	Class.forName(OracleDriver).newInstance();
	    	conn = DriverManager.getConnection(OracleURL);
	    	}catch(Exception e){
	    		//e.printStackTrace();
	    		//return "操作数据库出错，请仔细检查" ;
	    		//System.err.println(e.getMessage());
	    	}
    return conn;
    }
    
    	
   //关闭数据库连接
   /* public void Close()
    {
        try{
            Conn.close(); 
        }catch(SQLException sqlexception){
            sqlexception.printStackTrace();
        }
    }*/
    
    
    public static void main(String[] args)
	{	
		DBConnection DBConn = new DBConnection();
		try
		{
			Connection Conn = DBConn.getConn();	
			ResultSet rs = null;
			Statement stmt = Conn.createStatement(1004,1007);
	    	String Sql = "select * from Admin where AdminName='dream' and AdminPwd='dream'";
	    	rs = stmt.executeQuery(Sql);
	    	rs.next();
	    	if (!rs.isAfterLast()) 
			{
				System.out.println(rs.getString("AdminName")+"登录成功!");
			}
			else
			{
				System.out.println("登录失败!");
			}
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage().toString());
		}
	}
    
}
