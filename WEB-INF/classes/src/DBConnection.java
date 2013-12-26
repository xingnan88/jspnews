/***************************************************
 *  
 *  Դ�ļ���:  DBConnecion.java
 *  ��    �ܣ� �����껪����ϵͳ - ���ݿ�������
 *	���ߣ������껪 [DreamTime]
 *	Email:fanwsp@126.com
 *  QQ:122142023 
 * 	CopyRight(c)2005-2006 by DreamTime 
 *
 ****************************************************
*/


package dreamtime.dreamnews;			//ָ�������ڵİ�
import java.sql.*;						//�������ݿ��������
import java.util.*;
import java.io.*;

public class DBConnection					
{

    private String FileName;			//�����ļ���
    private int DBType;					//���ݿ�����
   
    private Connection conn;			//���Ӷ���
	private Statement stmt;				//������
	private ResultSet rs;				//���������
	
    private String AccessDriver;		//����Access��������
    private String AccessURL; 			//����Access�����ַ���	
    private String AccessPath; 			//����Access���ݿ��·��	
    
    private String MySqlDriver;			//MYSQL Server��������
    private String MySqlURL; 			//MYSQL Server�����ַ���
        
    private String SqlDriver;			//SQL Server��������
    private String SqlURL; 				//SQL Server�����ַ���
    
    private String OracleDriver;		//Oracle��������
    private String OracleURL; 			//Oracle�����ַ���
	
    
    
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
		    	//return "�������ݿ��������ϸ���" ;
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
		    	//return "�������ݿ��������ϸ���" ;
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
	    		//return "�������ݿ��������ϸ���" ;
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
	    		//return "�������ݿ��������ϸ���" ;
	    		//System.err.println(e.getMessage());
	    	}
    return conn;
    }
    
    	
   //�ر����ݿ�����
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
				System.out.println(rs.getString("AdminName")+"��¼�ɹ�!");
			}
			else
			{
				System.out.println("��¼ʧ��!");
			}
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage().toString());
		}
	}
    
}
