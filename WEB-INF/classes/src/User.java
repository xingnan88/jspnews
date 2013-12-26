
/***************************************************
 *  
 *  Դ�ļ���:  User.java
 *  ��    �ܣ� �����껪����ϵͳ - �û�������
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

public class User
{

    DBConnection DBConn = new DBConnection();
    Function Fun = new Function(); 
    MD5 md5 = new MD5();
	public String AdminName,AdminPwd,UserName,UserSex,UserTel;
	public String UserQQ,UserEmail,UserAddress,UserZip,UserInfo;
	public String AddTime,LastLoginTime,LastLoginIP;
	public String sAdminType,UserBirthday;
	public int AdminType,BirthYear,BirthMonth,BirthDay;
	public int NewsNum,LoginNum;
	public String [] sType = new String[4];
	
    
    
    public User()
    {
    	sType[1] = "��ͨ����Ա";
    	sType[2] = "ϵͳ����Ա";
    	sType[3] = "��������Ա";  
    }
    
    
    public boolean ReadUser(int AdminID)
    {
    try{	
    	Connection Conn = DBConn.getConn();
    	Statement stmt = Conn.createStatement(1004,1007);
    	ResultSet rs = null;
    	String Sql = "select * from Admin where AdminID=" + AdminID;
    	rs = stmt.executeQuery(Sql);
    	rs.next();
    	AdminName = rs.getString("AdminName");
    	AdminType = rs.getInt("AdminType");
    	UserName = rs.getString("UserName");
    	UserSex = rs.getString("UserSex");
    	UserBirthday = rs.getString("UserBirthday");
    	UserTel = rs.getString("UserTel");
    	UserQQ = rs.getString("UserQQ");
    	UserEmail = rs.getString("UserEmail");
    	UserAddress = rs.getString("UserAddress");
    	UserZip = rs.getString("UserZip");
    	UserInfo = rs.getString("UserInfo");
    	AddTime = rs.getString("AddTime");
    	LastLoginTime = rs.getString("LastLoginTime");
    	LastLoginIP = rs.getString("LastLoginIP");
    	NewsNum = rs.getInt("NewsNum");
    	LoginNum = rs.getInt("LoginNum");
    	
    	String [] s = UserBirthday.split("-");
    	BirthYear = Fun.StrToInt(s[0]);
    	BirthMonth = Fun.StrToInt(s[1]);
    	BirthDay = Fun.StrToInt(s[2]);
    	rs.close();
    	stmt.close();
    	Conn.close();
    	return true;
    	}catch(Exception e){ return false;}
    }
    
    
    
   /***********************************************************
	* ��������UserList
	* ��  �ã�����Ա�û��б�
	* ��  ����sPage,ҳ���ַ; strPage,�ڼ�ҳ
	* ����ֵ���ַ���
	***********************************************************/
    public String UserList(String sPage,String strPage)
    {
    	try
    	{
	    	Connection Conn = DBConn.getConn();
	    	Statement stmt = Conn.createStatement(1004,1007);
	    	ResultSet rs = null;
	    	String sSql = null;
	    	StringBuffer sb = new StringBuffer();
			int i;
			int intPage = 1; 			
			int intPageSize = 10;
			if (Fun.ReadConfig() && Fun.AdminUserListNum>0) intPageSize = Fun.AdminUserListNum;
	    	sSql = "select * from Admin order by AdminID desc";
	    	rs = stmt.executeQuery(sSql);	    	
	    	 
	    	sb.append("<br><br><table width=\"90%\"  border=\"1\" align=\"center\" cellpadding=\"2\" cellspacing=\"\">\r\n");
			sb.append("<tr background=\"images/bg.gif\">\r\n");
			sb.append("<td height=\"27\" colspan=\"7\" background=\"images/bg.gif\"><div align=\"center\" class=\"title\">");
			sb.append("<strong>���й���Ա�û��б�</strong></div></td>\r\n");
			sb.append("</tr>\r\n");
			sb.append("<tr height=\"25\" class=\"chinese\">\r\n");
			sb.append("<td width=\"5%\"><div align=\"center\"><b>ID</b></div></td>\r\n");
			sb.append("<td width=\"12%\"><div align=\"center\"><b>�û���</b></div></td>\r\n");
			sb.append("<td width=\"13%\"><div align=\"center\"><b>�û�Ȩ��</b></div></td>\r\n");
			sb.append("<td width=\"12%\"><div align=\"center\"><b>��ʵ����</div></td>\r\n");
			sb.append("<td width=\"26%\"><div align=\"center\"><b>�������</b></div></td>\r\n");
			sb.append("<td width=\"8%\"><div align=\"center\"><b>����</b></div></td>\r\n");
			sb.append("<td width=\"24%\">\r\n");
			sb.append("<div align=\"center\">���� </div></td>\r\n");
			sb.append("</tr>\r\n");
		
			//�������û���κμ�¼���ո�����ʾ��Ϣ
			if (!rs.next())
			{ 
				sb.append("<tr height=\"25\" bgcolor=\"#d6dff7\"  class=\"info1\"><td colspan=\"7\">\r\n");
				sb.append("<div align=\"center\">��ʱû�й����û�!</div></td></tr>\r\n");
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
					int AdminID = rs.getInt("AdminID");
					AdminType = rs.getInt("AdminType");
					sAdminType = sType[AdminType];
					sb.append("<tr height=\"25\" bgcolor=\"#d6dff7\" class=\"chinese\">\r\n");
					sb.append("<td><div align=\"center\" class=\"chinese\">" + AdminID + "</div></td>\r\n");
					sb.append("<td><div align=\"center\" class=\"chinese\">" + rs.getString("AdminName") + "</div></td>\r\n");
					sb.append("<td><div align=\"center\" class=\"chinese\">" + sAdminType + "</div></td>\r\n");
					sb.append("<td><div align=\"center\" class=\"chinese\">" + rs.getString("UserName") + "</div></td>\r\n");
					sb.append("<td><div align=\"center\" class=\"chinese\">" + rs.getString("AddTime") + "</div></td>\r\n");
					sb.append("<td><div align=\"center\" class=\"chinese\">" + rs.getInt("NewsNum") + "</div></td>\r\n");
					sb.append("<td><div align=\"center\" class=\"chinese\">[<a href=\"Admin_User.jsp?Action=View&AdminID=" + AdminID + "\">���</a>]");
					sb.append("[<a href=\"Admin_User.jsp?Action=Edit&AdminID=" + AdminID + "\">�޸�</a>]");
					sb.append("[<a href=\"Admin_User.jsp?Action=Del&AdminID="+ AdminID + "\">ɾ��</a>]</div></td>\r\n");
					sb.append("</tr>\r\n");
					rs.next(); 
					i++;
				}
				sb.append("</table>\r\n");
				sb.append(Fun.Page(sPage,rs,intPage,intPageSize));
				sb.append("<br><br>");
			}
			rs.close();
    		stmt.close();
    		Conn.close();
			return sb.toString();
		}catch(Exception e)
		{
			//e.printStackTrace();
			return "No";
			//return e.getMessage().toString(); 
		}
    }
    
   
   /*********************************************************
	* ��������AddUser
	* ��  �ã���ӹ���Ա��
	* ��  ������
	* ����ֵ���ַ���
	***********************************************************/    
    public String AddUser()
    {
	try{	
		StringBuffer sb = new StringBuffer();
      	sb.append("<br><br><form  name=\"User\" method=\"POST\" action=\"Admin_User.jsp\" onSubmit=\"return CheckUser();\">\r\n");
		sb.append("<table width=\"90%\"  border=\"1\" align=\"center\" cellpadding=\"2\" cellspacing=\"0\">\r\n");
		sb.append("<tr background=\"images/bg.gif\">\r\n");
		sb.append("<td height=\"25\" colspan=\"2\" class=\"chinese\" background=\"images/bg.gif\"><div align=\"center\"><span class=\"title\">");
		sb.append("<strong>����µĹ����û�</strong></span><span class=\"info1\">  (�� * Ϊ������)</span></div></td>\r\n");
		sb.append("</tr>\r\n");
		sb.append("<tr bgcolor=\"#d6dff7\">\r\n");
		sb.append("<td width=\"35%\" height=\"25\" class=\"chinese\"><div align=\"right\">�û�����</div></td>\r\n");
		sb.append("<td width=\"65%\"><input name=\"AdminName\" type=\"text\" class=\"chinese\" id=\"AdminName\" size=\"20\" maxlength=\"16\">");
		sb.append("<span class=\"info1\"> * </span></td></tr>\r\n");
		sb.append("<tr bgcolor=\"#d6dff7\">\r\n");
		sb.append("<td height=\"25\" class=\"chinese\"><div align=\"right\">���룺</div></td>\r\n");
		sb.append("<td><input name=\"AdminPwd\" type=\"password\" class=\"chinese\" id=\"AdminPwd\" size=\"20\" maxlength=\"16\">");
		sb.append("<span class=\"info1\"> * (4-16λ)</span></td></tr>\r\n");
		sb.append("<tr bgcolor=\"#d6dff7\">\r\n");
		sb.append("<td height=\"25\" class=\"chinese\"><div align=\"right\">ȷ�����룺</div></td>\r\n");
		sb.append("<td><input name=\"AdminPwd2\" type=\"password\" class=\"chinese\" id=\"AdminPwd2\" size=\"20\" maxlength=\"16\">");
		sb.append("<span class=\"info1\"> * </span></td></tr>\r\n");
		sb.append("<tr bgcolor=\"#d6dff7\">\r\n");
		sb.append("<td height=\"25\" class=\"chinese\"><div align=\"right\">Ȩ�����ã�</div></td>\r\n");
		sb.append("<td><select name=\"AdminType\" class=\"chinese\" id=\"AdminType\">\r\n");
		sb.append("<option value=\"1\" selected>��ͨ����Ա</option>\r\n");
		sb.append("<option value=\"2\">ϵͳ����Ա</option>\r\n");
		sb.append("<option value=\"3\">��������Ա</option>\r\n");
		sb.append("</select></td>\r\n");
		sb.append("</tr>\r\n");
		sb.append("<tr bgcolor=\"#d6dff7\">\r\n");
		sb.append("<td height=\"25\" class=\"chinese\"><div align=\"right\">��ʵ������</div></td>\r\n");
		sb.append("<td><input name=\"UserName\" type=\"text\" class=\"chinese\" id=\"UserName\" size=\"20\" maxlength=\"16\">");
		sb.append("<span class=\"info1\"> * </span></td></tr>\r\n");
		sb.append("<tr bgcolor=\"#d6dff7\">\r\n");
		sb.append("<td height=\"25\" class=\"chinese\"><div align=\"right\">�Ա�</div></td>\r\n");
		sb.append("<td class=\"chinese\"><input name=\"UserSex\" type=\"radio\" value=\"��\" checked>��\r\n");
		sb.append("<input name=\"UserSex\" type=\"radio\" value=\"Ů\">Ů</td>\r\n");
		sb.append("</tr>\r\n");
		sb.append("<tr bgcolor=\"#d6dff7\">\r\n");
		sb.append("<td height=\"25\" class=\"chinese\"><div align=\"right\">�������ڣ�</div></td>\r\n");
		sb.append("<td class=\"chinese\"><select name=\"Year\" class=\"chinese\" id=\"Year\">\r\n");
		for(int Year=1900;Year<=2000;Year++)
		{
			sb.append("<option value=\"" + Year + "\">" + Year + "</option>");
		}
		
		sb.append("\r\n");
		sb.append("</select>��\r\n");
		sb.append("<select name=\"Month\" class=\"chinese\" id=\"Month\">\r\n");
			
		for(int Month=1;Month<=12;Month++)
		{
			sb.append("<option value=\"" + Month + "\">" + Month + "</option>");
		}
		
		sb.append("\r\n");
		sb.append("</select>��\r\n");
		sb.append("<select name=\"Day\" class=\"chinese\" id=\"Day\">\r\n");
		
		for(int Day=1;Day<=31;Day++)
		{
			sb.append("<option value=\"" + Day + "\">" + Day + "</option>");
		}
		
		sb.append("\r\n");
		sb.append("</select>��</td>\r\n");
		sb.append("</tr>\r\n");
		sb.append("<tr bgcolor=\"#d6dff7\">\r\n");
		sb.append("<td height=\"25\" class=\"chinese\"><div align=\"right\">�����ʼ���</div></td>\r\n");
		sb.append("<td><input name=\"UserEmail\" type=\"text\" class=\"chinese\" id=\"UserEmail\" size=\"20\" maxlength=\"50\">");
		sb.append("<span class=\"info1\"> * </span></td></tr>\r\n");
		sb.append("<tr bgcolor=\"#d6dff7\">\r\n");
		sb.append("<td height=\"25\" class=\"chinese\"><div align=\"right\">��ϵ�绰��</div></td>\r\n");
		sb.append("<td><input name=\"UserTel\" type=\"text\" class=\"chinese\" id=\"UserTel\" size=\"20\" maxlength=\"11\"></td>\r\n");
		sb.append("</tr>\r\n");
		sb.append("<tr bgcolor=\"#d6dff7\">\r\n");
		sb.append("<td height=\"25\" class=\"chinese\"><div align=\"right\">QQ��</div></td>\r\n");
		sb.append("<td><input name=\"UserQQ\" type=\"text\" class=\"chinese\" id=\"UserQQ\" size=\"20\" maxlength=\"10\"></td>\r\n");
		sb.append("</tr>\r\n");
		sb.append("<tr bgcolor=\"#d6dff7\">\r\n");
		sb.append("<td height=\"25\" class=\"chinese\"><div align=\"right\">ͨѶ��ַ��</div></td>\r\n");
		sb.append("<td><input name=\"UserAddress\" type=\"text\" class=\"chinese\" id=\"UserAddress\" size=\"40\" maxlength=\"80\"></td>\r\n");
		sb.append("</tr>\r\n");
		sb.append("<tr bgcolor=\"#d6dff7\">\r\n");
		sb.append("<td height=\"25\" class=\"chinese\"><div align=\"right\">�ʱࣺ</div></td>\r\n");
		sb.append("<td><input name=\"UserZip\" type=\"text\" class=\"chinese\" id=\"UserZip\" size=\"20\" maxlength=\"6\"></td>\r\n");
		sb.append("</tr>\r\n");
		sb.append("<tr bgcolor=\"#d6dff7\">\r\n");
		sb.append("<td height=\"25\" class=\"chinese\"><div align=\"right\">���˼�飺</div></td>\r\n");
		sb.append("<td><textarea name=\"UserInfo\" cols=\"40\" rows=\"4\" class=\"chinese\" id=\"UserInfo\"></textarea></td>\r\n");
		sb.append("</tr>\r\n");
		sb.append("<tr>\r\n");
		sb.append("<td height=\"25\" colspan=\"2\"><div align=\"center\">\r\n");
		sb.append("<input name=\"Submit\" type=\"submit\" class=\"button\" value=\" ȷ �� \">&nbsp;\r\n");
		sb.append("<input name=\"Action\" type=\"hidden\" id=\"Action\" value=\"SaveAdd\">");
		sb.append("<input name=\"Reset\" type=\"reset\" class=\"button\" id=\"Reset\" value=\" �� �� \">\r\n");
		sb.append("<input name=\"Return\" type=\"button\" class=\"button\" id=\"Return\"");
		sb.append("onClick=\"javascript:location.href='Admin_User.jsp'\" value=\"�����û��б�\">\r\n");
		sb.append("</div></td>\r\n");
		sb.append("</tr>\r\n");
		sb.append("</table>\r\n");
		sb.append("</form><br><br>\r\n");
		return sb.toString();
		}catch(Exception e){
			////e.printStackTrace();
			//System.out.print(e.getMessage());
    		return "No";
    	}	
    }
    
    
   /*********************************************************
	* ��������EditUser
	* ��  �ã��༭����Ա��Ϣ
	* ��  ����s,�ַ����ͣ�����ԱID
	*         b, ������ �Ƿ������޸�����
	* ����ֵ���ַ���
	***********************************************************/
    public String EditUser(String s,boolean b)
    {
    try{
    	
		String sError = "";
		boolean OK = true;
		String sAction = "Person.jsp";
		String sModType = "�޸ĸ�������";
		String sCheck = "onSubmit=\"return CheckPerson();\"";
		
		if(b) 
		{
			sAction = "Admin_User.jsp";
			sModType = "�޸Ĺ���Ա�û�����";
			sCheck = "onSubmit=\"return CheckEditUser();\"";
		}
		int AdminID = Fun.StrToInt(Fun.CheckReplace(s));
		if (AdminID==0)
		{
			OK = false;
			sError = "No";
		}
		
		if(OK)
		{
			if(!ReadUser(AdminID))
			{
				OK = false;
				sError = "No";
			}
		}
		if(OK)
		{	  	
	    	StringBuffer sb = new StringBuffer();		
			sb.append("<br><br>\r\n");
			sb.append("<form name=\"User\" method=\"POST\" action=\"");
			sb.append(sAction);
			sb.append("\" " + sCheck + ">\r\n");
			sb.append("<table width=\"90%\"  border=\"1\" align=\"center\" cellpadding=\"2\" cellspacing=\"0\">\r\n");
			sb.append("<tr background=\"images/bg.gif\">\r\n");
			sb.append("<td height=\"25\" colspan=\"2\" class=\"chinese\" background=\"images/bg.gif\"><div align=\"center\"><span class=\"title\">");
			sb.append("<strong>" + sModType + "</strong></span><span class=\"info1\">  (�� * Ϊ������)</span></div></td>\r\n");
			sb.append("</tr>\r\n");
			sb.append("<tr bgcolor=\"#d6dff7\">\r\n");
			sb.append("<td width=\"34%\" height=\"25\" class=\"chinese\"><div align=\"right\">�û�����</div></td>\r\n");
			sb.append("<td width=\"66%\"><input name=\"AdminName\" readonly type=\"text\" class=\"chinese\" id=\"AdminName\" value=\"");
			sb.append(AdminName);
			sb.append("\" size=\"20\" maxlength=\"16\"><input name=\"AdminID\" type=\"hidden\" id=\"AdminID\" value=\"");
			sb.append(AdminID);
			sb.append("\"></td></tr>\r\n");
			if(b)
			{	
				sb.append("<tr bgcolor=\"#d6dff7\">\r\n");
				sb.append("<td height=\"25\" class=\"chinese\"><div align=\"right\">���룺</div></td>\r\n");
				sb.append("<td><input name=\"AdminPwd\" type=\"password\" class=\"chinese\" id=\"AdminPwd\" value=\"");
				//sb.append(AdminPwd);
				sb.append("\" size=\"20\" maxlength=\"16\"><span class=\"info1\">  (�������ʾ���޸�����)</span></td>\r\n");
				sb.append("</tr>\r\n");
				sb.append("<tr bgcolor=\"#d6dff7\">\r\n");
				sb.append("<td height=\"25\" class=\"chinese\"><div align=\"right\">ȷ�����룺</div></td>\r\n");
				sb.append("<td class=\"chinese\"><input name=\"AdminPwd2\" type=\"password\" class=\"chinese\" id=\"AdminPwd\" value=\"");
				//sb.append(AdminPwd2);
				sb.append("\" size=\"20\" maxlength=\"16\"> </td>\r\n");
				sb.append("</tr>\r\n");
				sb.append("<tr bgcolor=\"#d6dff7\">\r\n");
				sb.append("<td height=\"25\" class=\"chinese\"><div align=\"right\">Ȩ�����ã�</div></td>\r\n");
				sb.append("<td><select name=\"AdminType\" class=\"chinese\" id=\"AdminType\">\r\n");
				sb.append("  <option value=\"1\" ");
				sb.append(((Integer.toString(AdminType).equals("1"))?"SELECTED":""));
				sb.append(">��ͨ����Ա</option>\r\n");
				sb.append("  <option value=\"2\" ");
				sb.append(((Integer.toString(AdminType).equals("2"))?"SELECTED":""));
				sb.append(">ϵͳ����Ա</option>\r\n");
				sb.append("  <option value=\"3\" ");
				sb.append(((Integer.toString(AdminType).equals("3"))?"SELECTED":""));
				sb.append(">��������Ա</option>\r\n");
				sb.append("</select></td>\r\n");
				sb.append("</tr>\r\n");
			}
			sb.append("<tr bgcolor=\"#d6dff7\">\r\n");
			sb.append("<td height=\"25\" class=\"chinese\"><div align=\"right\">��ʵ������</div></td>\r\n");
			sb.append("<td><input name=\"UserName\" type=\"text\" class=\"chinese\" value=\"");
			sb.append(UserName);
			sb.append("\"  size=\"20\" maxlength=\"16\"></td>\r\n");
			sb.append("</tr>\r\n");
			sb.append("<tr bgcolor=\"#d6dff7\">\r\n");
			sb.append("<td height=\"25\" class=\"chinese\"><div align=\"right\">�Ա�</div></td>\r\n");
			sb.append("<td class=\"chinese\"><input ");
			sb.append((UserSex.equals("��"))?"CHECKED":"");
			sb.append(" name=\"UserSex\" type=\"radio\" value=\"��\" checked>\r\n");
			sb.append("��\r\n");
			sb.append("<input ");
			sb.append((UserSex.equals("Ů"))?"CHECKED":"");
			sb.append(" name=\"UserSex\" type=\"radio\" value=\"Ů\">\r\n");
			sb.append("Ů</td>\r\n");
			sb.append("</tr>\r\n");
			sb.append("<tr bgcolor=\"#d6dff7\">\r\n");
			sb.append("<td height=\"25\" class=\"chinese\"><div align=\"right\">�������ڣ�</div></td>\r\n");
			sb.append("<td class=\"chinese\"><select name=\"Year\" class=\"chinese\" id=\"Year\">\r\n");
			for(int Year=1900;Year<=2000;Year++)
			{
				String sSelect = (Year==BirthYear)?"SELECTED":"";
				sb.append("<option value=\"" + Year + "\"" + sSelect + ">" + Year + "</option>");
			}
	
			sb.append("\r\n");
			sb.append("</select>��\r\n");
			sb.append("<select name=\"Month\" class=\"chinese\" id=\"Month\">\r\n");
			
			for(int Month=1;Month<=12;Month++)
			{
				String sSelect = (Month==BirthMonth)?"SELECTED":"";
				sb.append("<option value=\"" + Month + "\"" + sSelect + ">" + Month + "</option>");
			}
	
			sb.append("\r\n");
			sb.append("</select>��\r\n");
			sb.append("<select name=\"Day\" class=\"chinese\" id=\"Day\">\r\n");
			
			for(int Day=1;Day<=31;Day++)
			{
				String sSelect = (Day==BirthDay)?"SELECTED":"";
				sb.append("<option value=\"" + Day + "\"" + sSelect + ">" + Day + "</option>");
			}
	
			sb.append("\r\n");
			sb.append("</select>��</td>\r\n");
			sb.append("</tr>\r\n");
			sb.append("<tr bgcolor=\"#d6dff7\">\r\n");
			sb.append("<td height=\"25\" class=\"chinese\"><div align=\"right\">�����ʼ���</div></td>\r\n");
			sb.append("<td><input name=\"UserEmail\" type=\"text\" class=\"chinese\" id=\"UserEmail2\" value=\"");
			sb.append(UserEmail);
			sb.append("\" size=\"20\" maxlength=\"50\"></td>\r\n");
			sb.append("</tr>\r\n");
			sb.append("<tr bgcolor=\"#d6dff7\">\r\n");
			sb.append("<td height=\"25\" class=\"chinese\"><div align=\"right\">��ϵ�绰��</div></td>\r\n");
			sb.append("<td><input name=\"UserTel\" type=\"text\" class=\"chinese\" id=\"UserTel2\" value=\"");
			sb.append(UserTel);
			sb.append("\" size=\"20\" maxlength=\"11\"></td>\r\n");
			sb.append("</tr>\r\n");
			sb.append("<tr bgcolor=\"#d6dff7\">\r\n");
			sb.append("<td height=\"25\" class=\"chinese\"><div align=\"right\">QQ��</div></td>\r\n");
			sb.append("<td><input name=\"UserQQ\" type=\"text\" class=\"chinese\" id=\"UserQQ2\" value=\"");
			sb.append(UserQQ);
			sb.append("\" size=\"20\" maxlength=\"10\"></td>\r\n");
			sb.append("</tr>\r\n");
			sb.append("<tr bgcolor=\"#d6dff7\">\r\n");
			sb.append("<td height=\"25\" class=\"chinese\"><div align=\"right\">ͨѶ��ַ��</div></td>\r\n");
			sb.append("<td><input name=\"UserAddress\" type=\"text\" class=\"chinese\" id=\"UserAddress2\" value=\"");
			sb.append(UserAddress);
			sb.append("\" size=\"20\" maxlength=\"50\"></td>\r\n");
			sb.append("</tr>\r\n");
			sb.append("<tr bgcolor=\"#d6dff7\">\r\n");
			sb.append("<td height=\"25\" class=\"chinese\"><div align=\"right\">�ʱࣺ</div></td>\r\n");
			sb.append("<td><input name=\"UserZip\" type=\"text\" class=\"chinese\" id=\"UserZip2\" value=\"");
			sb.append(UserZip);
			sb.append("\" size=\"20\" maxlength=\"6\"></td>\r\n");
			sb.append("</tr>\r\n");
			sb.append("<tr bgcolor=\"#d6dff7\">\r\n");
			sb.append("<td height=\"25\" class=\"chinese\"><div align=\"right\">���˼�飺</div></td>\r\n");
			sb.append("<td><textarea name=\"UserInfo\" cols=\"40\" rows=\"4\" class=\"chinese\" id=\"textarea\">");
			sb.append(UserInfo);
			sb.append("</textarea></td>\r\n");
			sb.append("</tr>\r\n");
			sb.append("<tr>\r\n");
			sb.append("<td height=\"30\" colspan=\"2\"><div align=\"center\">\r\n");
			sb.append("<input name=\"Submit\" type=\"submit\" class=\"button\" value=\" ȷ �� \">&nbsp;\r\n");
			sb.append("<input name=\"Action\" type=\"hidden\" id=\"Action\" value=\"SaveEdit\">\r\n");
			sb.append("<input name=\"Return\" type=\"button\" class=\"button\" id=\"Return\"");
			sb.append("onClick=\"javascript:history.go(-1)\" value=\"ȡ������\">\r\n");
			sb.append("</div></td>\r\n");
			sb.append("</tr>\r\n");
			sb.append("</table>\r\n");
			sb.append("</form><br><br>\r\n");
			return sb.toString();
		}else return sError;
		
		}catch(Exception e){
			////e.printStackTrace();
			//System.out.print(e.getMessage());
    		return "No";
    	}	
		
	}
	
	
   /*********************************************************
	* ��������ViewUser
	* ��  �ã��鿴����Ա��ϸ��Ϣ
	* ��  �����ַ��� s ����ԱID
	* ����ֵ���ַ���
	***********************************************************/
	public String ViewUser(String s)
    {
    try{	
		StringBuffer sb = new StringBuffer();
		String sError = "";
		boolean OK = true;
		int AdminID = Fun.StrToInt(Fun.CheckReplace(s));
		if (AdminID==0)
		{
			OK = false;
			//sError = "�Ƿ��Ĳ�������";
			sError = "No";
			
		}
		
		if(OK)
		{
			if(!ReadUser(AdminID))
			{
				OK = false;
				//sError = "���ݿ��������������!";
				sError = "No";
			}
		}
		if(OK)
		{	
			sAdminType = sType[AdminType];
			sb.append("<br><br>\r\n");
			sb.append("<table width=\"90%\" border=\"1\" align=\"center\" cellpadding=\"4\" cellspacing=\"0\">\r\n");
			sb.append("<tr background=\"images/bg.gif\">\r\n");
			sb.append("<td class=\"chinese\" colspan=\"4\" background=\"images/bg.gif\"><div align=\"center\"><span class=\"title\">");
			sb.append("<strong>��������û���ϸ��Ϣ</strong></span></div></td>\r\n");
			sb.append("</tr>\r\n");
			sb.append("<tr bgcolor=\"#d6dff7\">\r\n");
			sb.append("<td bgcolor=\"#d6dff7\" class=\"chinese\"><div align=\"right\">�û�ID��</div></td>\r\n");
			sb.append("<td class=\"chinese\">&nbsp;");
			sb.append(AdminID);
			sb.append("</td>\r\n");
			sb.append("<td bgcolor=\"#d6dff7\" class=\"chinese\"><div align=\"right\">���ʱ�䣺</div></td>\r\n");
			sb.append("<td class=\"chinese\">&nbsp;");
			sb.append(AddTime);
			sb.append("</td>\r\n");
			sb.append("</tr>\r\n");
			sb.append("<tr bgcolor=\"#d6dff7\">\r\n");
			sb.append("<td width=\"18%\" class=\"chinese\"><div align=\"right\">�û����� </div></td>\r\n");
			sb.append("<td width=\"30%\" class=\"chinese\">&nbsp;");
			sb.append(AdminName);
			sb.append("</td>\r\n");
			sb.append("<td width=\"22%\" class=\"chinese\"><div align=\"right\">��¼������</div></td>\r\n");
			sb.append("<td width=\"30%\"><span class=\"chinese\">&nbsp;");
			sb.append(LoginNum);
			sb.append(" </span></td>\r\n");
			sb.append("</tr>\r\n");
			sb.append("<tr bgcolor=\"#d6dff7\">\r\n");
			sb.append("<td class=\"chinese\"><div align=\"right\">����ԱȨ�ޣ� </div></td>\r\n");
			sb.append("<td class=\"chinese\">&nbsp;");
			sb.append(sAdminType);
			sb.append("</td>\r\n");
			sb.append("<td class=\"chinese\"><div align=\"right\">����¼��ʱ�䣺</div></td>\r\n");
			sb.append("<td class=\"chinese\"><span class=\"chinese\">&nbsp;");
			sb.append(LastLoginTime);
			sb.append(" </span></td>\r\n");
			sb.append("</tr>\r\n");
			sb.append("<tr bgcolor=\"#d6dff7\">\r\n");
			sb.append("<td class=\"chinese\"><div align=\"right\">�Ա� </div></td>\r\n");
			sb.append("<td class=\"chinese\">&nbsp;");
			sb.append(UserSex);
			sb.append(" </td>\r\n");
			sb.append("<td class=\"chinese\"><div align=\"right\">����¼��IP��</div></td>\r\n");
			sb.append("<td class=\"chinese\">&nbsp;");
			sb.append(LastLoginIP);
			sb.append(" </td>\r\n");
			sb.append("</tr>\r\n");
			sb.append("<tr bgcolor=\"#d6dff7\">\r\n");
			sb.append("<td class=\"chinese\"><div align=\"right\">�������ڣ� </div></td>\r\n");
			sb.append("<td class=\"chinese\">&nbsp;");
			sb.append(UserBirthday);
			sb.append(" </td>\r\n");
			sb.append("<td class=\"chinese\"><div align=\"right\">�������������</div></td>\r\n");
			sb.append("<td class=\"chinese\">&nbsp;");
			sb.append(NewsNum);
			sb.append(" </td>\r\n");
			sb.append("</tr>\r\n");
			sb.append("<tr bgcolor=\"#d6dff7\">\r\n");
			sb.append("<td class=\"chinese\"><div align=\"right\">�����ʼ��� </div></td>\r\n");
			sb.append("<td><span class=\"chinese\">&nbsp;");
			sb.append(UserEmail);
			sb.append(" </span></td>\r\n");
			sb.append("<td colspan=\"2\"><div align=\"center\" class=\"chinese\">����Ա������Ϣ</div></td>\r\n");
			sb.append("</tr>\r\n");
			sb.append("<tr bgcolor=\"#d6dff7\">\r\n");
			sb.append("<td class=\"chinese\"><div align=\"right\">��ϵ�绰�� </div></td>\r\n");
			sb.append("<td><span class=\"chinese\">&nbsp;");
			sb.append(UserTel);
			sb.append(" </span></td>\r\n");
			sb.append("<td colspan=\"2\" rowspan=\"4\" valign=\"top\"><div align=\"right\"></div>      \r\n");
			sb.append("<div align=\"left\"><span class=\"chinese\">");
			sb.append(UserInfo);
			sb.append("</span></di></td>\r\n");
			sb.append("</tr>\r\n");
			sb.append("<tr bgcolor=\"#d6dff7\">\r\n");
			sb.append("<td class=\"chinese\"><div align=\"right\">QQ�� </div></td>\r\n");
			sb.append("<td><span class=\"chinese\">&nbsp;");
			sb.append(UserQQ);
			sb.append(" </span></td>\r\n");
			sb.append("</tr>\r\n");
			sb.append("<tr bgcolor=\"#d6dff7\">\r\n");
			sb.append("<td class=\"chinese\"><div align=\"right\">ͨѶ��ַ�� </div></td>\r\n");
			sb.append("<td><span class=\"chinese\">&nbsp;");
			sb.append(UserAddress);
			sb.append(" </span></td>\r\n");
			sb.append("</tr>\r\n");
			sb.append("<tr bgcolor=\"#d6dff7\">\r\n");
			sb.append("<td class=\"chinese\"><div align=\"right\">�ʱࣺ </div></td>\r\n");
			sb.append("<td><span class=\"chinese\">&nbsp;");
			sb.append(UserZip);
			sb.append("</span></td>\r\n");
			sb.append("</tr>\r\n");
			sb.append("<tr bgcolor=\"#d6dff7\">\r\n");
			sb.append("<td colspan=\"4\" class=\"chinese\"><div align=\"center\"><a href=\"#\"");
			sb.append("onClick=\"javascript:history.go(-1)\"><strong>����</strong></a></div></td>\r\n");
			sb.append("</tr>\r\n");
			sb.append("</table>\r\n");
			sb.append("<br><br>\r\n");
			
			return sb.toString();
		}else return sError;
    	}catch(Exception e){return "No";}
    }
	
	
	
	
	/*********************************************************
	* ������:SaveAdd()
	* ��  ��:����û�
	* ��  ��:s,�ַ������飬����û��ĸ������
	*        s1:����Ա;s2:IP��ַ
	* ����ֵ:�ַ����͡����ز����������Ϣ
	***********************************************************/
	public String SaveAdd(String [] s,String s1,String s2)
    {
		String NowTime = (new java.util.Date()).toLocaleString();
		String [] sLog = new String[5];
		sLog[0] = s1;
		sLog[2] = NowTime;
		sLog[3] = s2; 
	    try{
	    	boolean OK = true;
	    	String sError = "";
	    	Connection Conn = DBConn.getConn();
	    	Statement stmt = Conn.createStatement(1004,1007);
	    	ResultSet rs = null;
	    	for(int i=0;i<s.length;i++)
	    	{
	    		s[i] = Fun.getStrCN(Fun.CheckReplace(s[i]));
	    	}
	    	String [] sa1 = new String [4];
	    	String [] sa2 = new String [4];
	    	sa1[0] = s[0];
	    	sa1[1] = s[1];
	    	sa1[2] = s[4];
	    	sa1[3] = s[7];
	    	sa2[0] = "����Ա�û���";
	    	sa2[1] = "����Ա����";
	    	sa2[2] = "����Ա��ʵ����";
	    	sa2[3] = "�����ʼ�";
	    	int iAdminType = Fun.StrToInt(s[2]);
	    	if (iAdminType == 0) iAdminType = 1;
	    	
	    	String sOK = Fun.CheckDate(sa1,sa2);
	    	if (!sOK.equals("Yes"))
	    	{
	    		OK = false;
	    		sError = sOK; 
	    	}
	
	    	String sql1 = "select * from Admin where AdminName='" + s[0] + "'";
	    	ResultSet rs1 = stmt.executeQuery(sql1);
	    	if (rs1.next()) 
	    	{
	    		OK = false;
	    		sError = "���û����Ѿ����ڣ�����������!";
	    	}
	    	
	    	if(OK)
	    	{
		    	s[1] = md5.getMD5ofStr(md5.getMD5ofStr(s[1]));
				String sql = "insert into Admin (AdminName,AdminPwd,AdminType,AddTime,UserName,UserSex,";
				sql += "UserBirthday,UserEmail,UserQQ,UserTel,UserAddress,UserZip,UserInfo) values (";
				sql += "'" + s[0] + "',";
				sql += "'" + s[1] + "',";
				sql += iAdminType + ",";
				sql += "'" + s[3] + "',";
				sql += "'" + s[4] + "',";
				sql += "'" + s[5] + "',";
				sql += "'" + s[6] + "',";
				sql += "'" + s[7] + "',";
				sql += "'" + s[8] + "',";
				sql += "'" + s[9] + "',";
				sql += "'" + s[10] + "',";
				sql += "'" + s[11] + "',";
				sql += "'" + s[12] + "')";
				//out.println(sql);
				stmt.executeUpdate(sql);
				stmt.close();
				Conn.close();
				sLog[1] = "����µĹ���Ա�û�["+s[0]+"]";
				sLog[4] = "Yes";
				Fun.AddLog(sLog);
	    		return "Yes";
		    }
		    else return sError;

    	}catch(Exception e){
    		sLog[1] = "����µĹ���Ա�û�["+s[0]+"]����ʧ��";
    		sLog[4] = "No";
			Fun.AddLog(sLog);
    		////e.printStackTrace();
    		//System.out.print(e.getMessage());
    		//System.out.print(sql);
    		return "����û�����������˶��������!";
    	}
	
	}
	
	
	
	/*********************************************************
	* ������:SaveEdit()
	* ��  ��:�޸��û����ϣ�
	* ��  ��:s,�ַ������飬�޸��û��ĸ������
	*         s1:����Ա;s2:IP��ַ,
	*		  b:�����ͣ��Ƿ񳬼�����Ա
	* ����ֵ:�ַ����͡����ز����������Ϣ
	***********************************************************/
	
	public String SaveEdit(String [] s,String s1,String s2,boolean b)
    {
    	
    	String NowTime = (new java.util.Date()).toLocaleString();
		String [] sLog = new String[5];
		sLog[0] = s1;
		sLog[2] = NowTime;
		sLog[3] = s2; 
		sLog[1] = "�޸�IDΪ [" + s[0] + " ] �Ĺ���Ա�û�����";
		
    	try{
    		
    		boolean OK = true;
	    	String sError = "";
    		
    		for(int i=0;i<s.length;i++)
	    	{
	    		s[i] = Fun.getStrCN(Fun.CheckReplace(s[i]));
	    	}
	    	
	    	String [] sa1 = new String [3];
	    	String [] sa2 = new String [3];
	    	sa1[0] = s[0];
	    	sa1[1] = s[4];
	    	sa1[2] = s[7];
	    	sa2[0] = "����Ա�û���";
	    	sa2[1] = "����Ա��ʵ����";
	    	sa2[2] = "�����ʼ�";
	    	
	    	String sOK = Fun.CheckDate(sa1,sa2);
	    	if (!sOK.equals("Yes"))
	    	{
	    		OK = false;
	    		sError = sOK; 
	    	}
			
			if(OK)
			{
		    	Connection Conn = DBConn.getConn();
		    	Statement stmt = Conn.createStatement(1004,1007);
		    	ResultSet rs = null;
		    	int iAdminType = Fun.StrToInt(s[2]);
	    		if (iAdminType == 0) iAdminType = 1;
	    		String sql = "update Admin set ";
	    		sql += "AddTime='" + s[3] + "',";
	    		sql += "UserName='" + s[4] + "',";
	    		sql += "UserSex='" + s[5] + "',";
	    		sql += "UserBirthday='" + s[6] + "',";
	    		sql += "UserEmail='" + s[7] + "',";
	    		sql += "UserQQ='" + s[8] + "',";
	    		sql += "UserTel='" + s[9] + "',";
	    		sql += "UserAddress='" + s[10] + "',";
	    		sql += "UserZip='" + s[11] + "',";
	    		sql += "UserInfo='" + s[12] + "'";
	    		String sql1 = " where AdminID=" + s[0];
		    	if(b)
			    {
			    	sql += ",AdminType=" + iAdminType;
			    	if (s[1]!=null && !s[1].equals("")) 
			    	{
			    		s[1] = md5.getMD5ofStr(md5.getMD5ofStr(s[1]));	
			    		sql += ",AdminPwd='" + s[1] + "'";
			    		sLog[1] += "������!";
			    	}
			    }
		    	sql += sql1;
				stmt.executeUpdate(sql);
				stmt.close();
				Conn.close();
				sLog[4] = "Yes";
				Fun.AddLog(sLog);
		    	return "Yes";
		    } return sError;
    	}catch(Exception e){
    		sLog[4] = "No";
			Fun.AddLog(sLog);
    		////e.printStackTrace();
    		//System.out.print(e.getMessage());
    		//System.out.print(sql);
    		return "�޸��û�����������˶��������!";
    	}
    }
	
	
	
	/*********************************************************
	* ������:SaveEditPwd()
	* ��  ��:�޸��û�����
	* ��  ��: s[0],������ss[1]�������룬s[2]:����Ա;
	*		  s[3]:IP��ַ,s[4],����ԱID
	* ����ֵ:�ַ����͡����ز����������Ϣ
	***********************************************************/
	
	public String SaveEditPwd(String [] s)
    {
    	
    	String NowTime = (new java.util.Date()).toLocaleString();
		String [] sLog = new String[5];
		sLog[0] = s[2];
		sLog[2] = NowTime;
		sLog[3] = s[3];
		String sql=null;
    	try{	
    		boolean OK = true;
	    	String sError = "";
    		
    		for(int i=0;i<s.length;i++)
	    	{
	    		s[i] = Fun.getStrCN(Fun.CheckReplace(s[i]));
	    	}
	    	
	    	String [] sa1 = new String [1];
	    	String [] sa2 = new String [1];
	    	sa1[0] = s[0];
	    	sa2[0] = "������";
	    	int AdminID = Fun.StrToInt(s[4]);
	    	
	    	String sOK = Fun.CheckDate(sa1,sa2);
	    	if (!sOK.equals("Yes"))
	    	{
	    		OK = false;
	    		sError = sOK; 
	    	}
	    	
			if (OK && AdminID==0)
	    	{
	    		OK = false;
	    		sError = "����Ĳ�������";
	    	}
			
			if(OK)
			{
		    	Connection Conn = DBConn.getConn();
		    	Statement stmt = Conn.createStatement(1004,1007);
		    	ResultSet rs = null;
		    	ResultSet rs1 = null;
		    	s[0] = md5.getMD5ofStr(md5.getMD5ofStr(s[0]));
		    	s[1] = md5.getMD5ofStr(md5.getMD5ofStr(s[1]));
		    	String sql1 = "select AdminPwd from Admin where AdminID="+AdminID;
		    	rs1 = stmt.executeQuery(sql1);
		    	rs1.next();
		    	if(s[0].equals(rs1.getString("AdminPwd")))
		    	{
		    		sql = "update Admin set ";
		    		sql += "AdminPwd='" + s[1] + "'";
		    		sql += " where AdminID=" + AdminID;
			    	
					stmt.executeUpdate(sql);
					stmt.close();
					Conn.close();
					sLog[1] = "�û�[" + s[2] + "]�޸�����!"; 
					sLog[4] = "Yes";
					Fun.AddLog(sLog);
			    	return "Yes";
			    }
			    else 
			    {
			    	sLog[1] = "�û� [ " + s[2] + " ] �޸�����ʧ�ܣ�ԭ�������!"; 
					sLog[4] = "Yes";
					Fun.AddLog(sLog);
			    	return "ԭ�������";
			    }
		    } 
		    else return sError;
    	}catch(Exception e){
    		sLog[1] = "�û� [ " + s[2] + " ] �޸�����ʧ�ܣ������쳣!";
    		sLog[4] = "No";
			Fun.AddLog(sLog);
    		//e.printStackTrace();
    		//System.out.print();
    		System.out.print(sql);
    		return "�޸��������!" + e.getMessage();
    	}
    }
	
	
	
	/*********************************************************
	* ��������Del
	* ��  �ã�ɾ���û�
	* ��  ����s1���û�Id��s1�������û���s2,IP
	* ����ֵ���ɹ���true �����򷵻ء�false
	***********************************************************/ 
	public boolean Del(String AdminID,String s1,String s2)
    {
	   
	    String NowTime = (new java.util.Date()).toLocaleString();
		String [] sLog = new String[5];
		sLog[0] = s1;
		sLog[2] = NowTime;
		sLog[3] = s2; 
    	try{
	    	Connection Conn = DBConn.getConn();
	    	Statement stmt = Conn.createStatement();
	    	String sql = "delete from Admin where AdminID=" + Fun.StrToInt(AdminID);
	    	stmt.executeUpdate(sql);
	    	stmt.close();
	    	Conn.close();
	    	sLog[1] = "ɾ��IDΪ[" + AdminID + "]�Ĺ���Ա�û�!"; 
			sLog[4] = "Yes";
			Fun.AddLog(sLog);
	    	return true;
    	}catch(Exception e){
    		////e.printStackTrace();
    		//System.out.print(e.getMessage());
			sLog[1] = "ɾ��IDΪ[" + AdminID + "]�Ĺ���Ա�û�ʧ��!"; 
			sLog[4] = "No";
			Fun.AddLog(sLog);
    		//return "����û��������������ԣ�" + e.getMessage();
    		return false;
    	}
	
	}
	
    //����
	public static void main(String[] args)
	{	
		User user = new User();
		String [] s = new String[14];
		s[0] = "Admin1";
		s[1] = "admin";
		s[2] = "3";
		s[3] = (new java.util.Date()).toLocaleString();
		s[4] = "znl";
		s[5] = "Ů";
		s[6] = "1984-12-20";
		s[7] = "fanwsp@126.com";
		s[13] = "27";
		for(int i=8;i<s.length-1;i++)
		{
			s[i] = "����";
		}
		System.out.println(user.UserList("Admin_User.jsp","1"));
		//System.out.println(user.AddUser());
		//System.out.println(user.ViewUser("1"));
		//System.out.print(user.SaveEdit(s));
		//System.out.print(s[0]);
		//System.out.print(user.Del("35","http://''","IP"));
	}
    
    
}