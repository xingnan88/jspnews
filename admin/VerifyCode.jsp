<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<%@ page contentType="text/html; charset=gb2312" language="java" import="java.awt.*,java.awt.image.*"%>
<%@ page import="java.util.*,javax.imageio.*,java.io.*"%>
<%@ page import="dreamtime.dreamnews.VerifyCode"%>

<%! String sVerifyCode; 		//验证码字符串  %> 

<%	//设置页面不缓存
	response.setHeader("Pragma","No-cache");
	response.setHeader("Cache-Control","no-cache");
	response.setDateHeader("Expires", 0);
	response.reset();
	
	try{
		// 将认证码存入SESSION
		//调用 runVerifyCode(int i) ,把i改成所要的验证码位数
		VerifyCode VC = new VerifyCode();
		session.setAttribute("VerifyCode",VC.runVerifyCode(4));
		//session.setMaxInactiveInterval(60);
		
		// 输出图象到页面
		sVerifyCode = (String)session.getAttribute("VerifyCode");
		OutputStream outs = response.getOutputStream();
		ImageIO.write(VC.CreateImage(sVerifyCode),"JPEG",outs);
	}catch(Exception e){
		return;
	   }
%>

