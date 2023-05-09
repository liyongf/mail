package com.liyf.boot.mail;

import com.alibaba.fastjson.JSONObject;
import com.liyf.boot.mail.util.HttpUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import com.wordnik.swagger.annotations.ApiOperation;

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
    @ResponseBody
    @RequestMapping(value = "/json/data", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String getByJSON(@RequestBody JSONObject jsonParam) {
        // 直接将json信息打印出来
        //System.out.println(jsonParam.toJSONString());
        String cameraIndexCode=jsonParam.getString("cameraIndexCode");
        String url="/api/video/v2/cameras/previewURLs";
        HttpMethod method =HttpMethod.POST;
        com.alibaba.fastjson.JSONObject json = new com.alibaba.fastjson.JSONObject();
        json.put("cameraIndexCode", cameraIndexCode);
        json.put("streamType",0);
        json.put("protocol","rtsp");
        json.put("transmode",1);
        json.put("expand","transcode=0");
        json.put("streamform","ps");
        com.alibaba.fastjson.JSONObject res=new com.alibaba.fastjson.JSONObject();
        try {
            String result = HttpUtils.post(url,method,json);
            if(result!=null){
                res=com.alibaba.fastjson.JSONObject.parseObject(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject data=new JSONObject();
        data.put("url",res.getJSONObject("data").getString("url"));
        // 将获取的json数据封装一层，然后在给返回
        JSONObject result = new JSONObject();
        result.put("code", "0");
        result.put("msg", "success");
        result.put("data", data);
        return result.toJSONString();
    }
}
