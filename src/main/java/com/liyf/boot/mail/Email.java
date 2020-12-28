package com.liyf.boot.mail;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.InputStream;

import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class Email {
    @Autowired
    private JavaMailSender mailSender; //自动注入的Bean
    @Value("${spring.mail.username}")
    private String sender; //读取配置文件中的参数
    @Value("${spring.mail.users}")
    private String users; //读取配置文件中收件人的参数

    /**
     * 邮件发送功能
     * @param
     * @param
     * @return
     */
    @RequestMapping("/api/email")
    @ApiOperation(value = "用户发送邮件操作", httpMethod = "GET", response = String.class, notes = "用户发送邮件操作，提供用户管理-用户发送邮件操作")
    public String sendEmail() {
        MimeMessage message = null;
        try {
            message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            /**发送者邮箱，即为开通了smtp服务的邮箱*/
            helper.setFrom(sender);
            /**发送到的邮箱*/
            String[] toUsers=users.split("\\|");
            helper.setTo(toUsers);
            helper.setSubject("主题：带附件的邮件");
            helper.setText("带附件的邮件内容");
            String rscId = "picture";
            helper.setText("<html><body>系统崩溃了<img src='cid:picture' style = 'width:600px;height:300px'/></body></html>", true);
            //注意项目路径问题，自动补用项目路径
            ClassPathResource  sourceFile  = new ClassPathResource("images/timg.jpg");
            //File file1 = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "images/timg.jpg");.g
            InputStream inputStream=sourceFile.getInputStream();
            File targetFile = new File("src/main/resources/targetFile.tmp");
            FileUtils.copyInputStreamToFile(inputStream, targetFile);
            FileSystemResource file = new FileSystemResource(targetFile);
            //加入邮件内容的图片
            helper.addInline(rscId, file);
            /**附件图片*/
            helper.addAttachment("beautiful girl.jpg", file);
        } catch (Exception e){
            e.printStackTrace();
        }
        mailSender.send(message);
        return  "success";
    }
}
