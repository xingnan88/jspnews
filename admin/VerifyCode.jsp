<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<%@ page contentType="text/html; charset=gb2312" language="java" import="java.awt.*,java.awt.image.*"%>
<%@ page import="java.util.*,javax.imageio.*,java.io.*"%>
<%@ page import="dreamtime.dreamnews.VerifyCode"%>

<%! String sVerifyCode; 		//��֤���ַ���  %> 

<%	//����ҳ�治����
	response.setHeader("Pragma","No-cache");
	response.setHeader("Cache-Control","no-cache");
	response.setDateHeader("Expires", 0);
	response.reset();
	
	try{
		// ����֤�����SESSION
		//���� runVerifyCode(int i) ,��i�ĳ���Ҫ����֤��λ��
		VerifyCode VC = new VerifyCode();
		session.setAttribute("VerifyCode",VC.runVerifyCode(4));
		//session.setMaxInactiveInterval(60);
		
		// ���ͼ��ҳ��
		sVerifyCode = (String)session.getAttribute("VerifyCode");
		OutputStream outs = response.getOutputStream();
		ImageIO.write(VC.CreateImage(sVerifyCode),"JPEG",outs);
	}catch(Exception e){
		return;
	   }
%>

