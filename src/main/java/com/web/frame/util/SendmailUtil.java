package com.web.frame.util;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sun.mail.util.MailSSLSocketFactory;
import java.util.Date;
import java.util.Properties;

@Component
public class SendmailUtil {

	// 邮件服务器主机名
	// QQ邮箱的 SMTP 服务器地址为: smtp.qq.com
	private static String myEmailSMTPHost = "smtp.163.com";

	//发件人邮箱
	private static String myEmailAccount = "jnwtxxkj@163.com";

	//发件人邮箱密码（授权码）
	//在开启SMTP服务时会获取到一个授权码，把授权码填在这里
	private static String myEmailPassword = "jnwtxxkj123";
	
	@Value("${url}")
    private String url;
	
	public static void main(String[] args) throws Exception {
		SendmailUtil sendmailUtil = new SendmailUtil();
//		sendmailUtil.sendBindMail("1243817476@qq.com", "sl02kf92kgkl92ksjjfkks2", "https://wwww.baidu.com");
//		sendmailUtil.sendFindMail("1243817476@qq.com", "sl02kf92kgkl92ksjjfkks2", "123456");
		sendmailUtil.sendVerifyMail("1243817476@qq.com","aaa");
		
	}
	
	public void sendVerifyMail(String toMail, String walletId) throws Exception {
		
		String content = "<h3><span style=\"font-family: 宋体; color: rgb(72, 80, 193);\">"
				+ "点击下方链接重置密码（10分钟内有效）</span><br>"
				+ "<a href=\""+url+"/password/reset?walletId="+walletId+"\" target=\"_blank\">"+url+"/password/reset?walletId="+walletId+"</a></h3>";
		sendEmail(toMail, "钱包密码重置", content);
	}

	public void sendBindMail(String toMail,String walletAddr,String url) throws Exception {
		
		 String content = "<html>"+
	        		"	<body>"+
	        		"		<div id=\"mailContentContainer\" class=\"qmbox qm_con_body_content qqmail_webmail_only\" style=\"\">"+
	        		"			<table dir=\"ltr\">"+
	        		"				<tbody>"+
	        		"					"+
	        		"					<tr>"+
	        		"						<td id=\"i2\" style=\"padding:0; font-family:\'Microsoft Yahei\', Verdana, Simsun, sans-serif; font-size:25px; color:#2672ec;\">钱包邮箱绑定</td>"+
	        		"					</tr>"+
	        		"					<tr>"+
	        		"						<td id=\"i4\" style=\"padding:0; padding-top:25px; font-family:\'Microsoft Yahei\', Verdana, Simsun, sans-serif; font-size:14px; color:#2a2a2a;\">您的钱包地址：<span id=walletAddr\">"+walletAddr+"</span></td>"+
	        		"					</tr>"+
	        		"					<tr>"+
	        		"						<td style=\"padding:0; padding-top:25px; font-family:\'Microsoft Yahei\', Verdana, Simsun, sans-serif; font-size:14px; color:#2a2a2a;\">"+
	        		"										<a href=\""+url+"\""+
	        		"											 rel=\"noopener\" target=\"_blank\">去绑定您的钱包 </a><span style=\"color: black;font-size: 10px;\">（有效期30分钟）</span>"+
	        		"						</td>"+
	        		"					</tr>"+
	        		"					<tr>"+
	        		"						<td style=\"padding:0; padding-top:25px; font-family:\'Microsoft Yahei\', Verdana, Simsun, sans-serif; font-size:14px; color:#2a2a2a;\">谢谢!</td>"+
	        		"					</tr>"+
	        		"					<tr>"+
	        		"						<td id=\"i8\" style=\"padding:0; font-family:\'Microsoft Yahei\', Verdana, Simsun, sans-serif; font-size:14px; color:#2a2a2a;\">望天科技</td>"+
	        		"					</tr>"+
	        		"				</tbody>"+
	        		"			</table>"+
	        		"			"+
	        		"		</div>"+
	        		"	</body>"+
	        		"</html>";
		 
		 sendEmail(toMail, "钱包邮箱绑定", content);
	}
	
	/**
	 * 钱包密码找回
	 * @param toMail
	 * @param walletAddr
	 * @param url
	 * @throws Exception
	 */
	public void sendFindMail(String toMail,String walletAddr,String payPsw) throws Exception {
		
		 String content = "<html>"+
	        		"	<body>"+
	        		"		<div id=\"mailContentContainer\" class=\"qmbox qm_con_body_content qqmail_webmail_only\" style=\"\">"+
	        		"			<table dir=\"ltr\">"+
	        		"				<tbody>"+
	        		"					"+
	        		"					<tr>"+
	        		"						<td id=\"i2\" style=\"padding:0; font-family:\'Microsoft Yahei\', Verdana, Simsun, sans-serif; font-size:25px; color:#2672ec;\">钱包支付密码重置</td>"+
	        		"					</tr>"+
	        		"					<tr>"+
	        		"						<td id=\"i4\" style=\"padding:0; padding-top:25px; font-family:\'Microsoft Yahei\', Verdana, Simsun, sans-serif; font-size:14px; color:#2a2a2a;\">您的钱包地址：<span id=walletAddr\">"+walletAddr+"</span></td>"+
	        		"					</tr>"+
	        		"					<tr>"+
	        		"						<td id=\"i4\" style=\"padding:0; padding-top:25px; font-family:\'Microsoft Yahei\', Verdana, Simsun, sans-serif; font-size:14px; color:#2a2a2a;\">您的支付密码：<span id=walletAddr\">"+payPsw+"</span></td>"+
	        		"					</tr>"+
	        		"					<tr>"+
	        		"						<td style=\"padding:0; padding-top:25px; font-family:\'Microsoft Yahei\', Verdana, Simsun, sans-serif; font-size:14px; color:#2a2a2a;\">谢谢!</td>"+
	        		"					</tr>"+
	        		"					<tr>"+
	        		"						<td id=\"i8\" style=\"padding:0; font-family:\'Microsoft Yahei\', Verdana, Simsun, sans-serif; font-size:14px; color:#2a2a2a;\">望天科技</td>"+
	        		"					</tr>"+
	        		"				</tbody>"+
	        		"			</table>"+
	        		"			"+
	        		"		</div>"+
	        		"	</body>"+
	        		"</html>";
		 
		 sendEmail(toMail, "钱包支付密码重置", content);
	}
	
	
	/**
	 * 邮件单发（自由编辑短信，并发送，适用于私信）
	 *
	 * @param toEmailAddress 收件箱地址
	 * @param emailTitle     邮件主题
	 * @param emailContent   邮件内容
	 * @throws Exception
	 */
	public void sendEmail(String toEmailAddress, String emailTitle, String emailContent) throws Exception {

		Properties props = new Properties();

		// 开启debug调试
		props.setProperty("mail.debug", "false");

		// 发送服务器需要身份验证
		props.setProperty("mail.smtp.auth", "true");

		// 端口号
		props.put("mail.smtp.port", 465);

		// 设置邮件服务器主机名
		props.setProperty("mail.smtp.host", myEmailSMTPHost);

		// 发送邮件协议名称
		props.setProperty("mail.transport.protocol", "smtp");

		/** SSL认证，注意腾讯邮箱是基于SSL加密的，所以需要开启才可以使用 **/
		MailSSLSocketFactory sf = new MailSSLSocketFactory();
		sf.setTrustAllHosts(true);

		//设置是否使用ssl安全连接（一般都使用）
		props.put("mail.smtp.ssl.enable", "true");
		props.put("mail.smtp.ssl.socketFactory", sf);

		//创建会话
		Session session = Session.getInstance(props);

		//获取邮件对象
		//发送的消息，基于观察者模式进行设计的
		Message msg = new MimeMessage(session);
		
		
		msg.addHeader("X-Mailer","Microsoft Outlook Express 6.00.2900.2869");

		//设置邮件标题
		msg.setSubject(emailTitle);

		//设置显示的发件时间
		msg.setSentDate(new Date());
		
		 Multipart mainPart = new MimeMultipart();
        // 创建一个包含HTML内容的MimeBodyPart
        BodyPart html = new MimeBodyPart();
        // 设置HTML内容
        html.setContent(emailContent, "text/html; charset=utf-8");
        mainPart.addBodyPart(html);
        // 将MiniMultipart对象设置为邮件内容
        msg.setContent(mainPart);
		
		//设置发件人邮箱
		// InternetAddress 的三个参数分别为: 发件人邮箱, 显示的昵称(只用于显示, 没有特别的要求), 昵称的字符集编码
		msg.setFrom(new InternetAddress(myEmailAccount, "望天API", "UTF-8"));

		//得到邮差对象
		Transport transport = session.getTransport();

		//连接自己的邮箱账户
		// 密码不是自己QQ邮箱的密码，而是在开启SMTP服务时所获取到的授权码
		// connect(host, user, password)
		transport.connect(myEmailSMTPHost, myEmailAccount, myEmailPassword);

		// 发送邮件
		transport.sendMessage(msg, new Address[] { new InternetAddress(toEmailAddress) });

		// 将该邮件保存到本地
//		OutputStream out = new FileOutputStream("MyEmail.eml");
//		msg.writeTo(out);
//		out.flush();
//		out.close();

		transport.close();
	}
}
