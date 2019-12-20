package com.vicmob.config;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailUtils {
    /**
     * 发送邮件 使用qq邮箱发送邮件
     * @param sendaddress  发件人电子邮箱
     * @param receiveAddress 收件人电子邮箱
     * @param headMessage 邮件标题
     * @param bodyMessage 邮件内容
     */
    public static void  sendEmail(String sendaddress, String receiveAddress, String headMessage, String bodyMessage) {
        Properties props = new Properties();
        try {
            // 开启debug调试
            props.setProperty("mail.debug", "true");
            // 发送服务器需要身份验证
            props.setProperty("mail.smtp.auth", "true");
            // 设置邮件服务器主机名
            props.setProperty("mail.smtp.host", "smtp.exmail.qq.com");
            // 发送邮件协议名称
            props.setProperty("mail.transport.protocol", "smtp");
            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            props.put("mail.smtp.ssl.enable", "true");
            props.put("mail.smtp.ssl.socketFactory", sf);
            props.put("mail.smtp.auth", "true");

            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.socketFactory.fallback", "false");
            props.put("mail.smtp.socketFactory.port", "465");

            Session session = Session.getInstance(props);

            Message msg = new MimeMessage(session);
            msg.setSubject(headMessage);
            StringBuilder builder = new StringBuilder();
            builder.append(bodyMessage);
            msg.setText(builder.toString());
            msg.setFrom(new InternetAddress(sendaddress));

            Transport transport = session.getTransport();
            transport.connect("smtp.exmail.qq.com",
                    sendaddress, "Vicmob888888"
            );

            transport.sendMessage(msg, new Address[]{new InternetAddress(receiveAddress)});
            transport.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
