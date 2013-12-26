
/***************************************************
 *  
 *  Դ�ļ���:  AdminClass.java
 *  ��    �ܣ� �����껪����ϵͳ - ������������
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

public class AdminClass
{

    DBConnection DBConn = new DBConnection();
    Function Fun = new Function(); 
    
    public AdminClass()
    {
    	
    } 
     
  
   /*********************************************************
	* ��������GetAllClass
	* ��  �ã���ȡ���д�����Ϣ
	* ��  ����������b��True ��ʾ���࣬�����ʾС��
	*		   s1��ָ�������ID
	* ����ֵ���ַ�������ά���͡����ĸ�����Ϣ
	***********************************************************/
    public String[][] GetAllClass(boolean b,boolean b1,String s1)
    {
    	try
    	{	
	    	Connection Conn = DBConn.getConn();
		    Statement stmt = Conn.createStatement(1004,1007);
		    String sql = "";
		    int iMax = 0;
		    int ID = Fun.StrToInt(s1);
			if(!b1)
			{
			    if (b)								//��ȡ����
			    {
			    	sql = "select * from BigClass";
			    }
			    else							    //��ȡС��
			    {
			    	sql = "select * from SmallClass";
			    	if (ID>0) 
			    		sql = "select * from SmallClass where BigClassID=" + ID;
			    }
			}else sql = "select * from Special";	//��ȡר��
			
		    ResultSet rs = stmt.executeQuery(sql); 
		    rs.last();
		    iMax = rs.getRow(); 
		    if(iMax==0) return null;		    
		    else
		    {
		    	String [][] sArray = new String[iMax][3];
			    rs.first();
				int i=0;
				do
				{
		    		sArray[i][0] = Integer.toString(rs.getInt(1));
		    		sArray[i][1] = rs.getString(2);
		    		if(!b) 
		    		{
		    			sArray[i][2] = Integer.toString(rs.getInt(4));
		    		}
		    		i++;
			    }while(rs.next());
				rs.close();
			    stmt.close();
			    Conn.close();
			    return sArray;
		    }
		    
		 }catch(Exception e){
		 	////e.printStackTrace();
		 	return null;
		 }
	}
  
    
    /*********************************************************
	* ��������ReadBigClass
	* ��  �ã���ȡ�����Ϣ
	* ��  ����s:���ID,b:Ϊtrue��ʾΪ���࣬����ΪС��
	* ����ֵ���ַ��������͡����ĸ�����Ϣ
	***********************************************************/
    public String[] ReadBigClass(String s,boolean b)
    {
    	try
    	{	
	    	Connection Conn = DBConn.getConn();
		    Statement stmt = Conn.createStatement(1004,1007);
		    ResultSet rs = null;
		    String sql = null;
		    String [] sa = new String [3];
		    if (b) 
		    {
		    	sql = "select * from BigClass where BigClassID=" + Fun.StrToInt(s);
			    rs = stmt.executeQuery(sql);
			    rs.next();
			    sa[0] = rs.getString("BigClassName");
			    sa[1] = rs.getString("BigClassInfo");
			 }
			 else
			 {
			 	sql = "select * from SmallClass where SmallClassID=" + Fun.StrToInt(s);
			 	rs = stmt.executeQuery(sql);
			    rs.next();
			    sa[0] = rs.getString("SmallClassName");
			    sa[1] = rs.getString("SmallClassInfo");
			    sa[2] = rs.getString("BigClassID");	
			 }
		 	rs.close();
		    stmt.close();
		    Conn.close();
		    return sa;
		 }catch(Exception e){
		 	////e.printStackTrace();
		 	return null;
		 }
	}
	
	
	
	/*********************************************************
	* ��������ReadClassNews()
	* ��  �ã�������е�������
	* ��  ����s:����ID,b:Ϊtrue��ʾΪ���࣬����ΪС��
	*         b1:�Ƿ�Ϊר��
	* ����ֵ����������
	***********************************************************/
    public int [] ReadClassNews(int ID,boolean b,boolean b1)
    {
    	try
    	{	
	    	Connection Conn1 = DBConn.getConn();
		    Statement stmt1 = Conn1.createStatement(1004,1007);
		    ResultSet rsNum = null;
		    String sql = null;
		    int [] iArray = new int[2];
			if(!b1)
			{
			    if (b) 
			    {
			    	sql = "select count(*) from News where BigClassID=" + ID;
				    rsNum = stmt1.executeQuery(sql);
				    if(rsNum.next()) iArray[0] = rsNum.getInt(1);
				    else iArray[0]=0;
				    sql = "select count(*) from SmallClass where BigClassID=" + ID;
				    rsNum = stmt1.executeQuery(sql);
				    if(rsNum.next()) iArray[1] = rsNum.getInt(1);
				    else iArray[1]=0;
				 }
				 else
				 {
				 	sql = "select count(*) from News where SmallClassID=" + ID;
				    rsNum = stmt1.executeQuery(sql);
				    if(rsNum.next()) iArray[0] = rsNum.getInt(1);
				    else iArray[0]=0;
				 }
			}
			else
			{
				sql = "select count(*) from News where SpecialID=" + ID;
			    rsNum = stmt1.executeQuery(sql);
			    if(rsNum.next()) iArray[0] = rsNum.getInt(1);
			    else iArray[0]=0;
			}
		 	rsNum.close();
		    stmt1.close();
		    Conn1.close();
		    return iArray;
		 }catch(Exception e){
		 	//e.printStackTrace();
		 	return null;
		 }
	}
    
    
    
    /*********************************************************
	* ��������ReadClass
	* ��  �ã���ȡ�������
	* ��  ������
	* ����ֵ���ַ���
	***********************************************************/
    public String ReadClass()
    {
    	try
    	{	
	    	Connection Conn = DBConn.getConn();
		    Statement stmt = Conn.createStatement(1004,1007);
		    Statement stmt1 = Conn.createStatement(1004,1007);
		    String sql = "select * from BigClass order by BigClassID";
		    ResultSet rs = stmt.executeQuery(sql);
		    StringBuffer sb = new StringBuffer();
		    
		    sb.append("<br><br><table width=\"90%\"  border=\"1\" align=\"center\" cellpadding=\"2\" cellspacing=\"0\">\r\n");
			sb.append("<tr><td height=\"27\" colspan=\"3\"  background=\"images/bg.gif\">");
			sb.append("<div align=\"center\" class=\"title\"><strong>����������</strong></div></td>");
			sb.append("</tr>\r\n");
			sb.append("<tr>\r\n");
			sb.append("<td height=\"25\" colspan=\"4\"><strong>&nbsp;<a href=\"Admin_Class.jsp?Action=AddBigClass\">");
			sb.append("<font color=\"#ff6600\">[���Ӵ���]</font></a></strong></td>\r\n");
			sb.append("</tr>\r\n");
			sb.append("<tr>\r\n");
			sb.append("<td height=\"25\"><div align=\"center\" class=\"chinese\"><b>�������</b></div></td>\r\n");
			sb.append("<td><div align=\"center\" class=\"chinese\"><b>���ʱ��</b></div></td>\r\n");
			sb.append("<td><div align=\"center\" class=\"chinese\"><b>����</b></div></td>\r\n");
			sb.append("</tr>\r\n");
		
			//�������û���κμ�¼���ո�����ʾ��Ϣ
			if (!rs.next())
			{ 
				sb.append("<tr height=\"25\" bgcolor=\"#d6dff7\"  class=\"info1\"><td colspan=\"3\">\r\n");
				sb.append("<div align=\"center\"><b>��ʱû���κδ���!</b></div></td></tr>\r\n");
			}
			else 
			{
				while(!rs.isAfterLast())
				{
					int BigClassID = rs.getInt("BigClassID");
					String BigClassName = rs.getString("BigClassName");
					String BigClassInfo = rs.getString("BigClassInfo");
					int [] iBNum = ReadClassNews(BigClassID,true,false);
					sb.append("<tr class=\"chinese\">\r\n");
					sb.append("<td width=\"40%\" height='22' title=\"");
					sb.append(BigClassInfo);
					sb.append("\"><div align=\"left\">&nbsp;<img src='images/+.gif'><b>");
					sb.append(BigClassName + "</b>&nbsp;[" + BigClassID + "]");
					sb.append("&nbsp;[" + iBNum[0] + "/" + iBNum[1] + "]</div></td>\r\n");
					sb.append("<td width=\"30%\"><div align=\"center\">");
					sb.append(rs.getString("AddTime"));
					sb.append("</div></td>\r\n");
					sb.append("<td width=\"30%\" class=\"chinese\"><div align=\"right\">[<a href=\"Admin_Class.jsp?Action=AddSClass&BigClassID=");
					sb.append(BigClassID);
					sb.append("\">��������</a>][<a href=\"Admin_Class.jsp?Action=EditBigClass&BigClassID=");
					sb.append(BigClassID);
					sb.append("\">�޸�</a>][<a href=\"Admin_Class.jsp?Action=DelBig&BigClassID=");
					sb.append(BigClassID);
					sb.append("\">ɾ��</a>]</div></td>\r\n");
					sb.append("</tr>\r\n");
					
					String sql1 = "select * from SmallClass where BigClassID=" + BigClassID;
			    	ResultSet rs1= stmt1.executeQuery(sql1);
			
					while(rs1.next())
					{
						int SClassID = rs1.getInt("SmallClassID");
						int [] iSNum = ReadClassNews(SClassID,false,false);
						sb.append("<tr bgcolor=\"#d6dff7\">\r\n");
						sb.append("<td class=\"chinese\" height=\"20\" Title=\"" + rs1.getString("SmallClassInfo") + "\"><div align=\"left\" class=\"chines\">");
						sb.append("&nbsp;&nbsp;&nbsp;<img src='images/-.gif'>");
						sb.append(rs1.getString("SmallClassName") + "&nbsp;[" + SClassID + "]&nbsp;[" + iSNum[0] + "]</div></td>\r\n");
						sb.append("<td class=\"chinese\"><div align=\"center\" class=\"chines\">");
						sb.append(rs1.getString("AddTime") + "</div></td>\r\n");
						sb.append("<td><div align=\"right\" class=\"chinese\">[<a href=\"Admin_Class.jsp?Action=EditSClass&SClassID=");
						sb.append(SClassID);
						sb.append("\">�޸�</a>] [<a href=\"Admin_Class.jsp?Action=DelSmall&SClassID=");
						sb.append(SClassID);
						sb.append("\">ɾ��</a>]</div></td>\r\n");
						sb.append("</tr>\r\n");
					}
					rs.next();	
				}
			}
			
			sb.append("</table>");
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
	* ��������ReadSpecial
	* ��  �ã���ȡר���б�
	* ��  ������
	* ����ֵ���ַ���
	***********************************************************/
    public String ReadSpecial()
    {
    	try
    	{	
	    	Connection Conn = DBConn.getConn();
		    Statement stmt = Conn.createStatement(1004,1007);
		    String sql = "select * from Special";
		    ResultSet rs = stmt.executeQuery(sql);
		    StringBuffer sb = new StringBuffer();

		    sb.append("<br><br><table width=\"90%\"  border=\"1\" align=\"center\" cellpadding=\"2\" cellspacing=\"0\">\r\n");
			sb.append("<tr><td height=\"27\" colspan=\"3\"  background=\"images/bg.gif\">");
			sb.append("<div align=\"center\" class=\"title\"><strong>ר�����</strong></div></td>");
			sb.append("</tr>\r\n");
			sb.append("<tr>\r\n");
			sb.append("<td height=\"25\" colspan=\"4\"><strong>&nbsp;<a href=\"Admin_Special.jsp?Action=Add\">");
			sb.append("<font color=\"#ff6600\">[����ר��]</font></a></strong></td>\r\n");
			sb.append("</tr>\r\n");
			sb.append("<tr>\r\n");
			sb.append("<td height=\"25\"><div align=\"center\" class=\"chinese\"><b>ר������ ר��ID/������</b></div></td>\r\n");
			sb.append("<td><div align=\"center\" class=\"chinese\"><b>���ʱ��</b></div></td>\r\n");
			sb.append("<td><div align=\"center\" class=\"chinese\"><b>����</b></div></td>\r\n");
			sb.append("</tr>\r\n");
			
			//�������û���κμ�¼���ո�����ʾ��Ϣ
			if (!rs.next())
			{ 
				sb.append("<tr height=\"25\" bgcolor=\"#d6dff7\"  class=\"info1\"><td colspan=\"3\">\r\n");
				sb.append("<div align=\"center\"><b>��ʱû���κ�ר��!</b></div></td></tr>\r\n");
			}
			else 
			{
				while(!rs.isAfterLast())
				{
					int SpecialID = rs.getInt("SpecialID");
					String SpecialName = rs.getString("SpecialName");
					String SpecialInfo = rs.getString("SpecialInfo");
					int [] iSpNum = ReadClassNews(SpecialID,false,true);
					sb.append("<tr class=\"chinese\" bgcolor=\"#d6dff7\">\r\n");
					sb.append("<td width=\"50%\" height='22' title=\"");
					sb.append(SpecialInfo);
					sb.append("\"><div align=\"left\">&nbsp;<img src='images/+.gif'><b>");
					sb.append(SpecialName + "</b>&nbsp;[" + SpecialID + "]/");
					sb.append("[" + iSpNum[0] + "]</div></td>\r\n");
					sb.append("<td width=\"30%\"><div align=\"center\">");
					sb.append(rs.getString("SpecialTime"));
					sb.append("</div></td>\r\n");
					sb.append("<td width=\"20%\" class=\"chinese\"><div align=\"right\">");
					sb.append("[<a href=\"Admin_Special.jsp?Action=Edit&SpecialID=");
					sb.append(SpecialID);
					sb.append("\">�޸�</a>][<a href=\"Admin_Special.jsp?Action=Del&SpecialID=");
					sb.append(SpecialID);
					sb.append("\">ɾ��</a>]</div></td>\r\n");
					sb.append("</tr>\r\n");
					rs.next();
				}
			}
			
			sb.append("</table>");
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
	* ��������ReadSpecial()
	* ��  �ã���ȡĳһר�����Ϣ
	* ��  ����s:ר��ID
	* ����ֵ���ַ��������͡����ĸ�����Ϣ
	***********************************************************/
    public String[] ReadSpecial(String s)
    {
    	try
    	{	
	    	Connection Conn = DBConn.getConn();
		    Statement stmt = Conn.createStatement(1004,1007);
		    ResultSet rs = null;
		    String sql = null;
		    String [] sa = new String [2];
		    int SpecialID = Fun.StrToInt(s);
		    if(SpecialID>0)
		    {
		    	sql = "select * from Special where SpecialID=" + SpecialID;
			    rs = stmt.executeQuery(sql);
			    rs.next();
			    sa[0] = rs.getString(2);
			    sa[1] = rs.getString(3);
			 	rs.close();
			    stmt.close();
			    Conn.close();
			    return sa;
			 }
			 else return null;
		 }catch(Exception e){
		 	//e.printStackTrace();
		 	return null;
		 }
	}
    
    
    
   /*********************************************************
	* ��������AddBigClass
	* ��  �ã���Ӵ���
	* ��  ����s1������ID��s2��������⣻
	*����     s3������˵����s4,�����û���s5��IP��ַ
	* ����ֵ���ַ����͡����ز�������Ϣ
	***********************************************************/
    public String AddBigClass(String s1,String s2,String s3,String s4,String s5)
    {			
		try
    	{
	    	boolean OK = true;
    		Connection Conn = DBConn.getConn();
	    	Statement stmt = Conn.createStatement(1004,1007);
	    	Statement stmt1 = Conn.createStatement(1004,1007);
	    	ResultSet rs = null;
    		String sError = "";
	    	int BigClassID = Fun.StrToInt(s1);
	    	String BigClassName = Fun.CheckReplace(s2);
	    	String BigClassInfo = Fun.CheckReplace(s3);
	    	String [] sa1 = new String [3];
	    	String [] sa2 = new String [3];
	    	sa1[0] = s1;
	    	sa1[1] = s2;
	    	sa1[2] = s3;
	    	sa2[0] = "��������";
	    	sa2[1] = "�������";
	    	sa2[2] = "����˵��";
	    	String s = Fun.CheckDate(sa1,sa2);
	    	if (BigClassID==0)
	    	{
	    		OK = false;
	    		sError = "�����������Ǵ��� 0 ������!";
	    	}
	    	if (!s.equals("Yes"))
	    	{
	    		OK = false;
	    		sError = s; 
	    	}	
	    	if(OK)
		    {
		    	String sql1 = "select * from BigClass where BigClassID=" + BigClassID;
		    	String sql2 = "select * from BigClass where BigClassName='" + BigClassName + "'";
		    	ResultSet rs1 = stmt.executeQuery(sql1);
		    	ResultSet rs2 = stmt1.executeQuery(sql2);
		    	if (rs1.next()) 
		    	{
		    		rs1.close();
		    		OK = false;
		    		sError = "<li>���������� [ " + s1 + " ] �Ѿ����ڣ������ظ�!";
		    	}
		    	if (rs2.next())
		    	{
		    		rs2.close();
		    		stmt1.close();
		    		OK = false;
		    		sError += "<li>�ô������ [ " + s2 + " ] �Ѿ����ڣ������ظ�!";	
		    	}
		    }
	    	if(OK)
	    	{
		    	
		    	String NowTime = (new java.util.Date()).toLocaleString();
		    	String [] sLog = new String[5];
		    	String sql = "insert into BigClass (BigClassID,BigClassName,";
		    	sql += "BigClassInfo,AddTime) values (";
		    	sql += BigClassID + ",";
				sql += "'" + BigClassName + "',";
				sql += "'" + BigClassInfo + "',";
				sql += "'" + NowTime + "')";
		    	stmt.executeUpdate(sql);
		    	sLog[0] = Fun.CheckReplace(s4);
		    	sLog[1] = "��Ӵ���[" + BigClassName + "]";
		    	sLog[2] = NowTime;
		    	sLog[3] = Fun.CheckReplace(s5); 
		    	sLog[4] = "Yes";
		    	Fun.AddLog(sLog);
				stmt.close();
				Conn.close();
				
		    	return "Yes";
		    }
		    else return sError;
	    }catch(Exception e)
        {
            ////e.printStackTrace();
            //ystem.out.print(e.getMessage());
            return "����ʧ��!";
        }
     }
     
    
   /*********************************************************
	* ��������EditBigClass
	* ��  �ã��޸Ĵ���
	* ��  ����s1��������ID��s2��������⣻
	*����     s3������˵����s4������ԭID��s5��ԭ������⣻
	*         s6���û���s7��IP
	* ����ֵ���ַ����͡����ز�������Ϣ
	***********************************************************/ 
    public String EditBigClass(String s1,String s2,String s3,String s4,String s5,String s6,String s7)
    {			
		try
    	{
	    	Connection Conn = DBConn.getConn();
	    	Connection Conn1 = DBConn.getConn();
	    	Statement stmt = Conn.createStatement(1004,1007);
	    	Statement stmt1 = Conn1.createStatement(1004,1007);
	    	Statement stmt2 = Conn1.createStatement(1004,1007);
	    	ResultSet rs1 = null;
	    	ResultSet rs2 = null;
	    	boolean OK=true;
	    	String sError="";
	    	int NewBigClassID = Fun.StrToInt(s1);
	    	int OldBigClassID = Fun.StrToInt(s4);
	    	String NewBigClassName = Fun.CheckReplace(s2);
	    	String OldBigClassName = Fun.CheckReplace(s5);
	    	String BigClassInfo = Fun.CheckReplace(s3);
	    	String [] sa1 = new String [3];
	    	String [] sa2 = new String [3];
	    	sa1[0] = s1;
	    	sa1[1] = s2;
	    	sa1[2] = s3;
	    	sa2[0] = "��������";
	    	sa2[1] = "�������";
	    	sa2[2] = "����˵��";
	    	String s = Fun.CheckDate(sa1,sa2);
	    	if (NewBigClassID==0)
	    	{
	    		OK = false;
	    		sError = "�����������Ǵ��� 0 ������!";
	    	}
	    	if (!s.equals("Yes"))
	    	{
	    		OK = false;
	    		sError = s; 
	    	}
		    if (OK)
		    {
		    	String sql1 = "select * from BigClass where BigClassID=" + NewBigClassID;
		    	String sql2 = "select * from BigClass where BigClassName='" + NewBigClassName + "'";
		    	rs1 = stmt1.executeQuery(sql1);
		    	rs2 = stmt2.executeQuery(sql2);
		    	rs1.last();
		    	rs2.last(); 
		    	if ((NewBigClassID != OldBigClassID) && rs1.getRow()>0) 
		    	{
		    		rs1.close();
		    		stmt1.close();
		    		sError = "<li>���������� [ " + s1 + " ] �Ѿ����ڣ������ظ�!";
		    		OK = false;
		    	}
		     	if ((!OldBigClassName.equals(NewBigClassName)) && rs2.getRow()>0)
		    	{
		    		rs2.close();
		    		stmt2.close();
		    		sError += "<li>�ô������ [ " + NewBigClassName + " ] �Ѿ����ڣ������ظ�!";
		    		OK = false;	
		    	}
		    }
	    	if(OK)
	    	{
		    	String sql = "update BigClass set ";
		    	sql += "BigClassID='" + NewBigClassID + "',";
		    	sql += "BigClassName='" + NewBigClassName + "',";
		    	sql += "BigClassInfo='" + BigClassInfo + "'";
		    	sql += " where BigClassID=" + OldBigClassID;
		    	String sql1 = "update SmallClass set BigClassID=" + NewBigClassID + " where BigClassID=" + OldBigClassID;
		    	String sql2 = "update News set BigClassID=" + NewBigClassID + " where BigClassID=" + OldBigClassID;
		    	try 
		    	{
			    	Conn.setAutoCommit(false); 		//����JDBCĬ�������ύ��ʽ
			    	stmt.executeUpdate(sql);
			    	stmt.executeUpdate(sql1);
			    	stmt.executeUpdate(sql2);
			    	Conn.commit();					//ȷ������
			    	Conn.setAutoCommit(true);		//��ΪĬ��
					stmt.close();
					Conn.close();
					String NowTime = (new java.util.Date()).toLocaleString();
			    	String [] sLog = new String[5];
					sLog[0] = Fun.CheckReplace(s6);
			    	sLog[1] = "�޸�ԭ����[" + OldBigClassName + "]Ϊ[" + NewBigClassName + "]";
			    	sLog[2] = NowTime;
			    	sLog[3] = Fun.CheckReplace(s7); 
			    	sLog[4] = "Yes";
			    	Fun.AddLog(sLog);
			    	return "Yes";
			    }catch (Exception e) {
					Conn.rollback();		//�ع�JDBC����
					//e.printStackTrace();
					Conn.close();
					return "�������ʧ��!";
					}
		    }
		    else return sError;
	    }catch(Exception e)
        {
            ////e.printStackTrace();
            //System.out.print(sql);
            return "����ʧ��!";
        }
     }
     
     
   /*********************************************************
	* ��������DelBigClass
	* ��  �ã�ɾ������
	* ��  ����s1��������ID��s2�������û���s3��IP��ַ
	* ����ֵ���ַ����͡����ز�������Ϣ
	***********************************************************/ 
    public boolean DelBigClass(String s1,String s2,String s3)
    {			
		
		String NowTime = (new java.util.Date()).toLocaleString();
		String [] sLog = new String[5];
		sLog[0] = s2;
		sLog[2] = NowTime;
		sLog[3] = s3; 
		try
    	{
	    	Connection Conn = DBConn.getConn();
	    	Statement stmt = Conn.createStatement(1004,1007);
	    	boolean OK=true;
	    	int BigClassID = Fun.StrToInt(s1);
	    	String sql1 = "delete from BigClass where BigClassID=" + BigClassID;
	    	String sql2 = "delete from SmallClass where BigClassID=" + BigClassID;
	    	String sql3 = "delete from News where BigClassID=" + BigClassID;	
		    if (BigClassID>0)
	    	{ 
			    try{	
			    	Conn.setAutoCommit(false); 		//����JDBCĬ�������ύ��ʽ	
			    	stmt.executeUpdate(sql1);
			    	stmt.executeUpdate(sql2);
			    	stmt.executeUpdate(sql3);
			    	Conn.commit();
			    	Conn.setAutoCommit(true); 
					stmt.close();
					Conn.close();
					sLog[0] = Fun.CheckReplace(s2);
			    	sLog[1] = "ɾ��IDΪ [ " + s1 + " ] �Ĵ���";
			    	sLog[2] = NowTime;
			    	sLog[3] = Fun.CheckReplace(s3); 
			    	sLog[4] = "Yes";
			    	Fun.AddLog(sLog);
			    	return true;
			     }catch (Exception e) {
						Conn.rollback();			//�ع�JDBC����
						//e.printStackTrace();
						Conn.close();
						return false;
					}
			}else return false;
	    }catch(SQLException e)
        {
            ////e.printStackTrace();
    		sLog[1] = "ɾ��IDΪ[" + s1 + "]�Ĵ��࣬��������!"; 
	    	sLog[4] = "No";
	    	Fun.AddLog(sLog);
            //System.out.print(sql);
            return false;
        }
     }
 
 	
 	/*********************************************************
	* ��������AddSClass
	* ��  �ã���Ӵ���
	* ��  ����s2��С����⣻s3��С��˵��
	*����     s4,�������࣬s5,�����û���s6��IP��ַ
	* ����ֵ���ַ����͡����ز�������Ϣ
	***********************************************************/
    public String AddSClass(String s2,String s3,String s4,String s5,String s6)
    {			
		try
    	{
	    	boolean OK = true;
    		Connection Conn = DBConn.getConn();
	    	Statement stmt = Conn.createStatement(1004,1007);
	    	Statement stmt1 = Conn.createStatement(1004,1007);
	    	ResultSet rs = null;
    		String sError = "";
    		int BigClassID = Fun.StrToInt(s4);
	    	String SClassName = Fun.CheckReplace(s2);
	    	String SClassInfo = Fun.CheckReplace(s3);
	    	String [] sa1 = new String [2];
	    	String [] sa2 = new String [2];
	    	sa1[0] = s2;
	    	sa1[1] = s3;
	    	sa2[0] = "С�����";
	    	sa2[1] = "С��˵��";
	    	String s = Fun.CheckDate(sa1,sa2);
	    	if (!s.equals("Yes"))
	    	{
	    		OK = false;
	    		sError = s; 
	    	}
	    	if (BigClassID==0)
	    	{
	    		OK = false;
	    		sError = "��������������ݴ���������!"; 
	    	}	
	    	if(OK)
		    {
		    	String sql2 = "select * from SmallClass where SmallClassName='" + SClassName + "' and BigClassID=" + BigClassID;
		    	ResultSet rs2 = stmt1.executeQuery(sql2);
		    	if (rs2.next())
		    	{
		    		rs2.close();
		    		stmt1.close();
		    		OK = false;
		    		sError += "<li>��С����� [ " + s2 + " ] �Ѿ����ڣ������ظ�!";	
		    	}
		    }
	    	if(OK)
	    	{	
		    	String NowTime = (new java.util.Date()).toLocaleString();
		    	String [] sLog = new String[5];
		    	String sql = "insert into SmallClass (SmallClassName,";
		    	sql += "SmallClassInfo,BigClassID,AddTime) values (";
				sql += "'" + SClassName + "',";
				sql += "'" + SClassInfo + "',";
				sql += BigClassID + ",";
				sql += "'" + NowTime + "')";
		    	stmt.executeUpdate(sql);
		    	sLog[0] = Fun.CheckReplace(s5);
		    	sLog[1] = "���С��[" + SClassName + "]";
		    	sLog[2] = NowTime;
		    	sLog[3] = Fun.CheckReplace(s6); 
		    	sLog[4] = "Yes";
		    	Fun.AddLog(sLog);
				stmt.close();
				Conn.close();
		    	return "Yes";
		    }
		    else return sError;
	    }catch(SQLException e)
        {
            ////e.printStackTrace();
            //ystem.out.print(e.getMessage());
            return "����ʧ��!";
        }
     }
 
 
 
 	/*********************************************************
	* ��������EditSClass
	* ��  �ã��޸�С��
	* ��  ����s0���������࣬s1,С��ID;s2��С����⣻
	*����     s3��С��˵����s4��ԭС����⣻
	*         s5���û���s6��IP
	* ����ֵ���ַ����͡����ز�������Ϣ
	***********************************************************/ 
    public String EditSClass(String s0,String s1,String s2,String s3,String s4,String s5,String s6)
    {			
		try
    	{
	    	Connection Conn = DBConn.getConn();
	    	Statement stmt = Conn.createStatement(1004,1007);
	    	Statement stmt1 = Conn.createStatement(1004,1007);
	    	ResultSet rs1 = null;
	    	ResultSet rs2 = null;
	    	boolean OK=true;
	    	String sError="";
	    	int BigClassID = Fun.StrToInt(s0);
	    	int SmallClassID = Fun.StrToInt(s1);
	    	String NewSClassName = Fun.CheckReplace(s2);
	    	String OldSClassName = Fun.CheckReplace(s4);
	    	String SClassInfo = Fun.CheckReplace(s3);
	    	String [] sa1 = new String [3];
	    	String [] sa2 = new String [3];
	    	sa1[0] = s0;
	    	sa1[1] = s2;
	    	sa1[2] = s3;
	    	sa2[0] = "��������";
	    	sa2[1] = "С�����";
	    	sa2[2] = "С��˵��";
	    	String s = Fun.CheckDate(sa1,sa2);
	    	if (!s.equals("Yes"))
	    	{
	    		OK = false;
	    		sError = s; 
	    	}
	    	if (SmallClassID==0 || BigClassID==0)
	    	{
	    		OK = false;
	    		sError = "���������ݴ���������!"; 
	    	}
		    if (OK)
		    {
		    	String sql1 = "select * from SmallClass where SmallClassName='" + NewSClassName + "'";
		    	rs1 = stmt1.executeQuery(sql1);
		    	rs1.last();
		     	if ((!OldSClassName.equals(s2)) && rs1.getRow()>0)
		    	{
		    		rs1.close();
		    		stmt1.close();
		    		sError += "<li>��С����� [ " + s2 + " ] �Ѿ����ڣ������ظ�!";
		    		OK = false;	
		    	}
		    }
	    	if(OK)
	    	{
		    	String sql = "update SmallClass set ";
		    	sql += "SmallClassName='" + NewSClassName + "',";
		    	sql += "SmallClassInfo='" + SClassInfo + "',";
		    	sql += "BigClassID=" + BigClassID;
		    	sql += " where SmallClassID=" + SmallClassID;
		 		//Conn.setAutoCommit(false);
		    	stmt.executeUpdate(sql);
		    	//Conn.commit();
		    	//Conn.setAutoCommit(true);
				stmt.close();
				Conn.close();
				String NowTime = (new java.util.Date()).toLocaleString();
		    	String [] sLog = new String[5];
				sLog[0] = Fun.CheckReplace(s5);
		    	sLog[1] = "�޸�ԭС��[" + OldSClassName + "]Ϊ[" + NewSClassName + "]";
		    	sLog[2] = NowTime;
		    	sLog[3] = Fun.CheckReplace(s6); 
		    	sLog[4] = "Yes";
		    	Fun.AddLog(sLog);
		    	return "Yes";
		    }
		    else return sError;
	    }catch(SQLException e)
        {
            ////e.printStackTrace();
            //System.out.print(sql);
            return "����ʧ��!";
        }
     }
     
 	
 	
 	/*********************************************************
	* ��������DelSClass
	* ��  �ã�ɾ��С��
	* ��  ����s1��С��ID��s2�������û���s3��IP��ַ
	* ����ֵ���ɹ���true,����false
	***********************************************************/ 
    public boolean DelSClass(String s1,String s2,String s3)
    {			
		String NowTime = (new java.util.Date()).toLocaleString();
		String [] sLog = new String[5];
		sLog[0] = s2;
		sLog[2] = NowTime;
		sLog[3] = s3; 
		try
    	{
	    	Connection Conn = DBConn.getConn();
	    	Statement stmt = Conn.createStatement(1004,1007);
	    	boolean OK=true;
	    	int SClassID = Fun.StrToInt(s1);
	    	String sql1 = "delete from SmallClass where SmallClassID=" + SClassID;
	    	String sql2 = "delete from News where SmallClassID=" + SClassID;	
			if(SClassID>0)
			{   
				try
			    {
			    	//�����ﴦ����ֹ�������
			    	Conn.setAutoCommit(false);	//����Ĭ�����ﴦ��
			    	stmt.executeUpdate(sql1);
			    	stmt.executeUpdate(sql2);
			    	Conn.commit();
			    	Conn.setAutoCommit(true);
					stmt.close();
					Conn.close();
			    	sLog[1] = "ɾ��IDΪ [ " + s1 + " ] ��С��"; 
			    	sLog[4] = "Yes";
			    	Fun.AddLog(sLog);
			    	return true;
			    }catch (Exception e) {
						Conn.rollback();		//�ع�JDBC����
						////e.printStackTrace();
						Conn.close();
						return false;
						}  
			}else return false;
	    }catch(SQLException e)
        {
            ////e.printStackTrace();
    		sLog[1] = "ɾ��IDΪ[" + s1 + "]��С�࣬��������!"; 
	    	sLog[4] = "No";
	    	Fun.AddLog(sLog);
            //System.out.print(sql);
            return false;
        }
     }
 
 	
 	/*********************************************************
	* ��������AddSpecial
	* ��  �ã���Ӵ���
	* ��  ����s1��ר�����ƣ�s2��ר��˵��
	*��		  s3,�����û���s4��IP��ַ
	* ����ֵ���ַ����͡����ز�������Ϣ
	***********************************************************/
    public String AddSpecial(String s1,String s2,String s3,String s4)
    {			
		try
    	{
	    	boolean OK = true;
    		Connection Conn = DBConn.getConn();
	    	Statement stmt = Conn.createStatement(1004,1007);
	    	ResultSet rs = null;
    		String sError = "";
	    	String SpecialName = Fun.CheckReplace(s1);
	    	String SpecialInfo = Fun.CheckReplace(s2);
	    	String [] sa1 = new String [2];
	    	String [] sa2 = new String [2];
	    	sa1[0] = s1;
	    	sa1[1] = s2;
	    	sa2[0] = "ר������";
	    	sa2[1] = "ר��˵��";
	    	String s = Fun.CheckDate(sa1,sa2);
	    	if (!s.equals("Yes"))
	    	{
	    		OK = false;
	    		sError = s; 
	    	}	
	    	if(OK)
		    {
		    	String sql1 = "select * from Special where SpecialName='" + SpecialName + "'";
		    	ResultSet rs1 = stmt.executeQuery(sql1);
		    	if (rs1.next()) 
		    	{
		    		rs1.close();
		    		OK = false;
		    		sError = "<li>����ר������ [ " + s1 + " ] �Ѿ����ڣ������ظ�!";
		    	}
		    }
	    	if(OK)
	    	{	
		    	String NowTime = (new java.util.Date()).toLocaleString();
		    	String [] sLog = new String[5];
		    	String sql = "insert into Special (SpecialName,";
		    	sql += "SpecialInfo,SpecialTime) values (";
				sql += "'" + SpecialName + "',";
				sql += "'" + SpecialInfo + "',";
				sql += "'" + NowTime + "')";
		    	stmt.executeUpdate(sql);
		    	sLog[0] = Fun.CheckReplace(s3);
		    	sLog[1] = "���ר��[" + SpecialName + "]";
		    	sLog[2] = NowTime;
		    	sLog[3] = Fun.CheckReplace(s4); 
		    	sLog[4] = "Yes";
		    	Fun.AddLog(sLog);
				stmt.close();
				Conn.close();
		    	return "Yes";
		    }
		    else return sError;
	    }catch(Exception e)
        {
            //e.printStackTrace();
            //ystem.out.print(e.getMessage());
            return "����ʧ��!";
        }
     }
 
 
 
 
 	/*********************************************************
	* ��������EditSpecial
	* ��  �ã��޸�ר��
	* ��  ����s1��ר�����ƣ�s2��ר��˵����s3,�����û�
	*��		  s4��IP��ַ��s5��ר��ID��s6��ԭר������
	* ����ֵ���ַ����͡����ز�������Ϣ
	***********************************************************/ 
    public String EditSpecial(String s1,String s2,String s3,String s4,String s5,String s6)
    {			
		try
    	{
	    	Connection Conn = DBConn.getConn();
	    	Statement stmt = Conn.createStatement(1004,1007);
	    	ResultSet rs = null;
	    	boolean OK=true;
	    	String sError="";
	    	int SpecialID = Fun.StrToInt(s5);
	    	String SpecialName = Fun.CheckReplace(s1);
	    	String OldSpecialName = Fun.CheckReplace(s6);
	    	String SpecialInfo = Fun.CheckReplace(s2);
	    	String [] sa1 = new String [2];
	    	String [] sa2 = new String [2];
	    	sa1[0] = s1;
	    	sa1[1] = s2;
	    	sa2[0] = "ר������";
	    	sa2[1] = "ר��˵��";
	    	String s = Fun.CheckDate(sa1,sa2);
	    	if (!s.equals("Yes"))
	    	{
	    		OK = false;
	    		sError = s; 
	    	}
	    	if (SpecialID==0)
	    	{
	    		OK = false;
	    		sError = "�������ݴ���"; 
	    	}
		    if (OK)
		    {
		    	String sql1 = "select * from Special where SpecialName='" + SpecialName + "'";
		    	rs = stmt.executeQuery(sql1);
		    	rs.last();
		     	if ((!OldSpecialName.equals(SpecialName)) && rs.getRow()>0)
		    	{
		    		rs.close();
		    		stmt.close();
		    		sError += "<li>��ר������ [ " + SpecialName + " ] �Ѿ����ڣ������ظ�!";
		    		OK = false;	
		    	}
		    }
	    	if(OK)
	    	{
		    	String sql = "update Special set ";
		    	sql += "SpecialName='" + SpecialName + "',";
		    	sql += "SpecialInfo='" + SpecialInfo + "'";
		    	sql += " where SpecialID=" + SpecialID;

		    	stmt.executeUpdate(sql);
				stmt.close();
				Conn.close();
				String NowTime = (new java.util.Date()).toLocaleString();
		    	String [] sLog = new String[5];
				sLog[0] = Fun.CheckReplace(s3);
		    	sLog[1] = "�޸�ԭר��[" + OldSpecialName + " ]Ϊ[" + SpecialName + " ]";
		    	sLog[2] = NowTime;
		    	sLog[3] = Fun.CheckReplace(s4); 
		    	sLog[4] = "Yes";
		    	Fun.AddLog(sLog);
		    	return "Yes";
			    
		    }
		    else return sError;
	    }catch(Exception e)
        {
            //e.printStackTrace();
            //System.out.print(sql);
            return "����ʧ��!";
        }
     }
 
 	
 	
 	/*********************************************************
	* ��������DelSpecial
	* ��  �ã�ɾ��ר��
	* ��  ����s1��ר��ID��s2�������û���s3��IP��ַ
	* ����ֵ���ɹ���true,����false
	***********************************************************/ 
    public boolean DelSpecial(String s1,String s2,String s3)
    {			
		String NowTime = (new java.util.Date()).toLocaleString();
		String [] sLog = new String[5];
		sLog[0] = s2;
		sLog[2] = NowTime;
		sLog[3] = s3; 
		try
    	{
	    	Connection Conn = DBConn.getConn();
	    	Statement stmt = Conn.createStatement(1004,1007);
	    	boolean OK=true;
	    	int SpecialID = Fun.StrToInt(s1);
	    	String sql1 = "delete from Special where SpecialID=" + SpecialID;
	    	String sql2 = "delete from News where SpecialID=" + SpecialID;	
			if(SpecialID>0)
			{   
				try
			    {
			    	//�����ﴦ����ֹ�������
			    	Conn.setAutoCommit(false);	//����Ĭ�����ﴦ��
			    	stmt.executeUpdate(sql1);
			    	stmt.executeUpdate(sql2);
			    	Conn.commit();
			    	Conn.setAutoCommit(true);
					stmt.close();
					Conn.close();
			    	sLog[1] = "ɾ��IDΪ[" + s1 + "]��ר��"; 
			    	sLog[4] = "Yes";
			    	Fun.AddLog(sLog);
			    	return true;
			    }catch (Exception e) {
						Conn.rollback();		//�ع�JDBC����
						////e.printStackTrace();
						Conn.close();
						return false;
						}  
			}else return false;
	    }catch(SQLException e)
        {
            //e.printStackTrace();
    		sLog[1] = "ɾ��IDΪ[" + s1 + "] ��ר�⣬��������!"; 
	    	sLog[4] = "No";
	    	Fun.AddLog(sLog);
            //System.out.print(sql);
            return false;
        }
     }
 	
 	
 	
 	 //����
	public static void main(String[] args)
	{	
		AdminClass aClass = new AdminClass();
		//System.out.print(aClass.AddBigClass("11","�ط�����1","�ط�","znl","172.16.166.51"));
		//boolean b = aClass.DelBigClass("7","dream","172.16.166.50");
		//if (b) System.out.println("Yes");
		//else System.out.println("No");
		//System.out.print(aClass.EditBigClass("5","��������","��������","10","��������","dream","localhost"));
		//System.out.print(aClass.ReadClass("AdminClass.jsp","1"));
		//System.out.print(aClass.EditSClass("1","5","��������","��������","����","dream","localhost"));
		String [][] as1 = aClass.GetAllClass(true,true,null);
	
		//String [][] sa = new String[5][2];
		//System.out.print(sa.length);
		//if(aClass.DelSClass("fjdlf","http://ww","2")) System.out.print("Yes");
		//else System.out.print("No");
		if(as1==null) System.out.print("null");
		else
		{
			for(int i=0;i<as1.length;i++)
			{
				System.out.println(as1[i][0]+","+as1[i][1]);
			}
		}
		//System.out.print(aClass.ReadSpecial("1")[0]);
	}
}