package com.sdzk.buss.web.common.utils;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

public class MailUtil {
    private static final String HOST = MailConfig.host;
    private static final Integer PORT = MailConfig.port;
    private static final String timeout = MailConfig.timeout;
    private static final String USERNAME = MailConfig.userName;
    private static final String PASSWORD = MailConfig.passWord;
    private static final String sender = MailConfig.sender;
    private static final String receiver = MailConfig.receiver;
    private static final String personal = MailConfig.personal;
    private static final String subject = MailConfig.subject;
    private static JavaMailSenderImpl mailSender = createMailSender();

    /**
     * 邮件发送器
     *
     * @return 配置好的工具
     */
    private static JavaMailSenderImpl createMailSender() {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost(HOST);
        sender.setPort(PORT);
        sender.setUsername(USERNAME);
        sender.setPassword(PASSWORD);
        sender.setDefaultEncoding("Utf-8");
        Properties p = new Properties();
        p.setProperty("mail.smtp.timeout", timeout);
        p.setProperty("mail.smtp.auth", "false");
        sender.setJavaMailProperties(p);
        return sender;
    }

    /**
     * 发送邮件
     *
     * @throws MessagingException 异常
     * @throws UnsupportedEncodingException 异常
     */
    public void sendMail(String content3) throws MessagingException,UnsupportedEncodingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        // 设置utf-8或GBK编码，否则邮件会有乱码
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        messageHelper.setFrom(sender, personal);
        messageHelper.setTo(receiver);
        messageHelper.setSubject(subject);
        Date datatime = new java.sql.Date(new Date().getTime());
        messageHelper.setSentDate(datatime);
        String content1 = "<table style='font-size:20'><tr><td width='70%'><span>";
        String content2 = "中矿安华开发员,您好!</span></td></tr><tr><td>&nbsp;&nbsp;</td></tr><tr><td><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
        String content4 = "</span></td></tr><tr><td>&nbsp;&nbsp;</td></tr><tr><td align='right'>"+datatime.toString()+"</td></tr></table>";
        StringBuffer content = new StringBuffer();
        content.append(content1);
        content.append(content2);
        content.append(content3);
        content.append(content4);
        String html = content.toString();
        messageHelper.setText(html,true);
        mailSender.send(mimeMessage);
    }
}
