/***************************************************
 *  
 *  Դ�ļ���:  Function.java
 *  ��    �ܣ� �����껪����ϵͳ - ����������
 *	���ߣ������껪 [DreamTime]
 *	Email:fanwsp@126.com
 *  QQ:122142023 
 * 	CopyRight(c)2005-2006 by DreamTime 
 *
 ****************************************************
*/



package dreamtime.dreamnews;			//ָ�������ڵİ�

import java.sql.*;
import dreamtime.dreamnews.DBConnection;
public class Function					
{        
	DBConnection DBConn = new DBConnection();
	public static int AdminUserListNum;
	public static int AdminLogListNum;
	public static int AdminNewsListNum;
	
	public static int HotNewsNum;
	public static int TopNewsNum;
	public static int TopImgNum;
	public static int HeadNewsNum;
	public static int ClassNewsNum;
	public static int ClassImgNum;
	public static int SpecNum;
	public static int SpecNewsNum;
	
	public static int BHotNewsNum;
	public static int BTopNewsNum;
	public static int BTopImgNum;
	public static int BHeadNewsNum;
	public static int BClassNewsNum;
	public static int BClassImgNum;
	public static int BSpecNum;
	public static int BSpecNewsNum;
	
	//ר�����
	public static int ListSpecNum;
	
	public static int ListNewsNum;
	public static int SearchNewsNum;
	
	
	//��վȫ����Ϣ
	public static String DreamNewsTitle;
	public static String DreamNewsCopyRight;
	public static String DreamNewsEmail;
	
	public Function()
	{
	
	}
		
	
	public boolean ReadConfig()
	{
	try{
		Connection Conn = DBConn.getConn();
    	Statement stmt = Conn.createStatement(1004,1007);
    	String sSql = "select * from Config";	
    	ResultSet rs = stmt.executeQuery(sSql);
    	String [] s = new String [10];
    	if(rs.next())
    	{
			//��̨�������
			AdminUserListNum = rs.getInt("AdminUserListNum");
			AdminLogListNum = rs.getInt("AdminLogListNum");
			AdminNewsListNum = rs.getInt("AdminNewsListNum");
			
			//��ҳ����
			HotNewsNum = rs.getInt("HotNewsNum");
			HeadNewsNum = rs.getInt("HeadNewsNum");
			TopNewsNum = rs.getInt("TopNewsNum");
			TopImgNum = rs.getInt("TopImgNum");
			ClassNewsNum = rs.getInt("ClassNewsNum");
			ClassImgNum = rs.getInt("ClassImgNum");
			SpecNum = rs.getInt("SpecNum");
			SpecNewsNum = rs.getInt("SpecNewsNum");
			
			//һ���������
			BHotNewsNum = rs.getInt("BHotNewsNum");
			BHeadNewsNum = rs.getInt("BHeadNewsNum");
			BTopNewsNum = rs.getInt("BTopNewsNum");
			BTopImgNum = rs.getInt("BTopImgNum");
			BClassNewsNum = rs.getInt("BClassNewsNum");
			BClassImgNum = rs.getInt("BClassImgNum");
			BSpecNum = rs.getInt("BSpecNum");
			BSpecNewsNum = rs.getInt("BSpecNewsNum");
			
			//����������ʾ����
			ListNewsNum = rs.getInt("ListNewsNum");
			
			SearchNewsNum = rs.getInt("SearchNewsNum");
			
			//ר���б���
			ListSpecNum = rs.getInt("ListSpecNum");
			
			//��վȫ����Ϣ����
			DreamNewsTitle = rs.getString("DreamNewsTitle");
			DreamNewsCopyRight = rs.getString("DreamNewsCopyRight");
			DreamNewsEmail = rs.getString("DreamNewsEmail");
			if(DreamNewsTitle==null) DreamNewsTitle = "�����껪����ϵͳ";
			if(DreamNewsCopyRight==null) DreamNewsCopyRight = "�����껪";
			if(DreamNewsEmail==null) DreamNewsEmail = "fanwsp@126.com";
			
			rs.close();
			stmt.close();
			Conn.close();
			return true;
		}
		else return false;
		}catch(Exception e){
			//System.out.print(e.getMessage());
			//e.printStackTrace();
			return false;
		}
	}
	
	
	
	/*********************************************************
	* ��������CheckReplace
	* ��  �ã�ת��SQL�����ַ���
	* ��  ����s: �ַ����ͣ���ת�����ַ�
	* ����ֵ��ת���Ժ���ַ���
	* �����ã�String s2 = CheckReplace(s1);
	***********************************************************/	
	public String CheckReplace(String s)
	{
	    try
	    {
		    if(s == null || s.equals("")) return "";
			else
			{
				StringBuffer stringbuffer = new StringBuffer();
				for(int i = 0; i < s.length(); i++)
				{
					char c = s.charAt(i);
					switch(c)
					{							
											
					case 34: // '"'
						stringbuffer.append("&quot;");
						break;
		
					case 39: // '\''									
						stringbuffer.append("&#039;");
						break;
	
					case 124: // '|'
						stringbuffer.append("");
						break;
					
					case '&':
						stringbuffer.append("&amp;");
						break;
					
					case '<':
						stringbuffer.append("&lt;");
						break;
					
					case '>':
						stringbuffer.append("&gt;");
						break;
					
					default:
						stringbuffer.append(c);
						break;
					}
				}
				return stringbuffer.toString().trim();		//����ת���Ժ���ַ���
	  		}
  		}catch(Exception e){ 
  			return "";
  		}
	}
		
		
	
	
	/*********************************************************
	* ��������CheckDate
	* ��  �ã���������Ƿ�Ϊ��
	* ��  �������顡s1: ���ı�����s2������������
	* ����ֵ��ת���Ժ���ַ���
	***********************************************************/	
	public String CheckDate(String [] s1,String [] s2)
	{
	    boolean OK = true;
	    StringBuffer sb = new StringBuffer();
	    try
	    {
		    for(int i = 0; i < s1.length; i++)
		    {
			    if(s1[i] == null || s1[i].equals("") || s1[i].equals(" "))
				{					
					sb.append("<li> [ " + s2[i] + " ] ����Ϊ��!"); 											
					OK = false;
		  		}
		  	}
		  	if (OK) return "Yes";
		  	else return sb.toString().trim();
		 }catch(Exception e) { return "����ʧ�ܣ�";}
	}

	
	/*********************************************************
	* ��������getStrCN
	* ��  �ã�ת���ַ�����
	* ��  �������顡s1:��ת�����ַ�
	* ����ֵ��ת���Ժ���ַ���
	***********************************************************/
	public String getStrCN(String s)
    {
	    if(s == null) s = "";
	    try
	    {
		    byte abyte0[] = s.getBytes("GBK");
		    s = new String(abyte0);
	    }catch(Exception e) {s="";}
	    return s;
    }
	
	
   /***********************************************************
	* ��������StrToInt
	* ��  �ã����ַ���תΪ����
	* ��  ����s: �ַ�����
	* ����ֵ������
	***********************************************************/	
	public int StrToInt(String s)
	{
	  try
	  {
		int i = Integer.parseInt(CheckReplace(s));
		return i;		//����ת���Ժ���ַ���
	  }catch(Exception e)
	  {
	  	return 0;
	  }
  		
	}
	
   /***************************************************************
	* ��������StringToBoolean
	* ��  �ã�ͷ����Ϣ��ʾ
	* ��  ����AdminLogin: �����ͣ��û��Ƿ��¼��ʶ
	* ����ֵ���ַ���
	* �����ã�out.println(BottomInfo());
	*****************************************************************/	
	public boolean StringToBoolean(String s)
	{
		if (s != null && s.equals("Yes")) return true; 
		else return false;
	}


	/**********************************************************
	* ��������Page
	* ��  �ã���ҳ��ʾ
	* ��  ����sPage: �ַ��ͣ���ǰҳ���ļ�·��
	*         ����в�����Ҫ���ݵĲ������������������"&"����
	*         ҳ���ַ���������"?"
	*		  ���� sPage = request.getServletPath()�õ� 
	* ����ֵ����ʾ��ҳ���ַ���
	* �����ã�out.println(Page(sPage);
	************************************************************/
	public String Page(String sPage,ResultSet rs,int intPage,int intPageSize)
	{
		
		StringBuffer sb = new StringBuffer();
		String s=null;
		
		//�����Ƿ������õı�������
		int intRowCount; 				//��¼���� 
		int intPageCount; 				//��ҳ�� 
		int i=0;
		try{
			
			rs.last(); 
			//��ȡ��¼����
			intRowCount = rs.getRow();
			
			//������ҳ�� 
			if(intRowCount % intPageSize == 0) intPageCount = intRowCount/intPageSize;
			else intPageCount = (int)Math.floor(intRowCount/intPageSize)+1;
			if(intPageCount == 0) intPageCount = 1;
			
			if(intPage < 1) intPage = 1;
			if(intPage > intPageCount) intPage = intPageCount;
		
			if(intRowCount>intPageSize)
			{
				s  = "<table width=\"90%\"  border=\"0\" align=\"center\" cellpadding=\"2\" cellspacing=\"0\"><tr>";
				s += "<td width=\"80%\" height=\"30\" class=\"chinese\"><span class=\"chinese\">";
				s += "��ǰ��"+intPage+"ҳ/��"+ intPageCount+"ҳ,��"+intRowCount+"����¼,"+intPageSize+"��/ҳ"; 
				
				int showye = intPageCount;
				if(showye>10) showye=10;
				for(i=1;i<=showye;i++)
				{
					if(i==intPage)	s += " " + i + " ";
					else s += " <a href=\""+sPage+"intPage="+i+"\">" +i+"</a> ";
				}
				
				s += "</span></td>";
				s += "<td width=\"20%\">";
				s += "<table width=\"100%\" border=\"0\">";
				s += "<tr><td><div align=\"right\"><span class=\"chinese\">";
				s += "<select name=\"ipage\" class=\"chinese\" onChange=\"MM_jumpMenu('self',this,0)\">";
				s += "<option value=\"\" selected>��ѡ��</option>";
				
				for(i=1;i<=intPageCount;i++)
				{
					String sSelect = (i==intPage)?"SELECTED":"";
					s += "<option value=\"" + sPage + "intPage=" + i + "\"" + sSelect + ">��" + i + "ҳ</option>";
				}
				
				s += "</select></span></div>";
				s += "</td></tr></table>";
				s += "</td></tr></table>";
				return s;
			}
			else return "";
		}catch(Exception e){
			//e.printStackTrace();
			return "��ҳ����!";//+ e.getMessage();	
		}
	}
	
	
	/*********************************************************
	* ��������AddLog
	* ��  �ã������־
	* ��  ����s[0]���������û���
	*����     s[1]����־���ͣ�
	*�������� s[2]������ʱ�䣻
	*         s[3]����¼IP��ַ��
	*         s[4]�����������
	* ����ֵ�������͡������־�ɹ����� Ture�����򷵻� False
	***********************************************************/
    public boolean AddLog(String[] s)
    {	
    	try
    	{
	    	Connection Conn = DBConn.getConn();
	    	Statement stmt = Conn.createStatement(1004,1007);
	    	ResultSet rs = null;	
	    	for(int i=0;i<s.length;i++)
	    	{
	    		s[i] = getStrCN(CheckReplace(s[i]));
	    	}
	    	String sql = "insert into Log (User,LogType,LogTime,IP,Result) values (";
	    	sql += "'" + s[0] + "',";
			sql += "'" + s[1] + "',";
			sql += "'" + s[2] + "',";
			sql += "'" + s[3] + "',";
			sql += "'" + s[4] + "')";
	    	stmt.executeUpdate(sql);
			stmt.close();
			Conn.close();
	    	return true;
	    }catch(SQLException e)
        {
            //e.printStackTrace();
            //System.out.print(sql);
            return false;
        }				
    }
    
	
	
	
	/**********************************************************
	* ��������OutError
	* ��  �ã����������Ϣ
	* ��  ����������Ϣ
	* ����ֵ���ַ���
	************************************************************/
	public String OutError(String s)
    {
    try{
		StringBuffer sb = new StringBuffer();		
		sb.append("<br><br><table width=\"60%\"  border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
		sb.append("<tr><td align=\"center\" valign=\"top\">\r\n");
		sb.append("<table width=\"90%\"  border=\"1\" align=\"center\" cellpadding=\"6\" cellspacing=\"1\">\r\n");
		sb.append("<tr class=\"chinese\" height=\"25\"><td height=\"27\" background=\"images/bg.gif\" class=\"info\">\r\n");
		sb.append("<div align=\"center\" class=\"title\">����ҳ��</div></td></tr>\r\n");
		sb.append("<tr class=\"chinese\" height=\"25\"><td><table cellspacing=\"4\" cellpadding=\"1\">\r\n");
		sb.append("<tr><td width=\"511\" height=\"80\" align=\"middle\" valign=\"top\">\r\n");
		sb.append("<p align=\"left\"><span class=\"info1\">��������</span></p><div align=\"left\" class=\"info1\">");
		sb.append( s + "</div></td></tr></table></td></tr>\r\n");
		sb.append("<tr><td background=\"images/bg.gif\" height=\"20\" valign=\"middle\"><div align=\"center\" class=\"chinese\">\r\n");
		sb.append("<a href=\"#\" onClick=\"javascript:history.go(-1)\">����</a></div></td></tr></table></td></tr></table><br><br>\r\n");
		return sb.toString();
		}catch(Exception e){
			//e.printStackTrace();
			//System.out.print(e.getMessage());
    		return "��������!";
    	}	
		
	}
	
	/**********************************************************
	* ��������OutWarn
	* ��  �ã����������Ϣ
	* ��  ����������Ϣ
	* ����ֵ���ַ���
	************************************************************/
	public String OutWarn(String s)
    {
    try{
		StringBuffer sb = new StringBuffer();	
		sb.append("<br><br><form name=\"form1\" method=\"post\" action=\"\">\r\n");
		sb.append("<table border=\"1\" align=\"center\" cellpadding=\"1\" cellspacing=\"2\">\r\n");
		sb.append("<tr><td width=\"400\" height=\"80\" align=\"middle\" valign=\"top\">\r\n");
		sb.append("<div align=\"left\" class=\"info1\">ϵͳ���棺<br><br>\r\n");
		sb.append("&nbsp;&nbsp;&nbsp;&nbsp;");
		sb.append(s);
		sb.append("</div></td></tr>\r\n");
		sb.append("<tr><td height=\"20\" align=\"middle\" valign=\"top\"><div align=\"center\">\r\n");
		sb.append("<input name=\"Submit\" type=\"button\" class=\"button\" value=\"ȡ��\" onClick=\"javascript:history.go(-1);\">&nbsp;&nbsp;\r\n");
		sb.append("<input name=\"OK\" type=\"hidden\" id=\"OK\" value=\"Yes\">\r\n");
		sb.append("<input name=\"Submit2\" type=\"submit\" class=\"button\" value=\"ȷ��\">\r\n");
		sb.append("</div></td>\r\n");
		sb.append("</tr></table></form>\r\n");
		return sb.toString();
	}catch(Exception e){
		//e.printStackTrace();
		//System.out.print(e.getMessage());
		return "��������!";
    	}
    }
    
    
    
    /**********************************************************
	* ��������OutCopyRight
	* ��  �ã�����ײ�&��Ȩ��Ϣ
	* ��  ������
	* ����ֵ���ַ���
	************************************************************/
	public String OutCopyRight()
    {
    try{
		StringBuffer sb = new StringBuffer();	
		sb.append("<table width=\"750\" height=\"20\" align=\"center\"><tr>");     
		sb.append("<td width=\"100%\"><div id=\"CopyRight\">");     
		sb.append("<div id=\"Text\">�����껪����ϵͳ V1.0 &nbsp;&nbsp;����������");
		sb.append("<a href=\"mailto:fanwsp@126.com\"><strong>�����껪</strong></a></div>");
		sb.append("<span>CopyRight &copy; 2005-2006 " + DreamNewsCopyRight + " All Rights Reserved </span>");
		sb.append("<span>Email:<a href=\"mailto:" + DreamNewsEmail + "\">" + DreamNewsEmail + "</a></span></div>"); 
		sb.append("</td></tr></table><div id=\"B4\"></div>");
		return sb.toString();
	}catch(Exception e){
		//e.printStackTrace();
		//System.out.print(e.getMessage());
		return "Power By DreamTime";
    	}
    }
    	
    	
    	
	
	public static void main(String[] args)
	{	
		Function Fun = new Function();

		if(Fun.ReadConfig()) System.out.println(Fun.DreamNewsTitle);
	
		/*s[0] = "Admin1";
		s[1] = "admin";
		s[2] = "3";
		s[3] = (new java.util.Date()).toLocaleString();
		s[4] = "znl";
		s[5] = "Ů";
		s[6] = "1984-12-20";
		s[7] = "fanwsp@126.com";
		for(int i=8;i<s.length;i++)
		{
			s[i] = "����";
		}*/
		//System.out.print(Fun.OutError("��������!"));
		/*if(Fun.DelLog("2")) System.out.print("Yes");
		//System.out.print(s[0]);
		for(int i=0;i<s.length;i++)
		{
			System.out.println(s[i]);
		}*/
	}
	
}