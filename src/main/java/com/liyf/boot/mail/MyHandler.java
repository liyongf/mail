package com.liyf.boot.mail;

import com.hikvision.artemis.sdk.ArtemisHttpUtil;
import com.hikvision.artemis.sdk.config.ArtemisConfig;
import com.liyf.boot.entity.UserAliase;
import com.liyf.boot.mail.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
public class MyHandler {
    @Autowired
    UserMapper usermapper;

    @Value("${spring.mail.passwords}")
    private String password; //读取配置文件中收件人的参数

    @ResponseBody
    @RequestMapping("getAll")
    public List<UserAliase> getAll(){
        return usermapper.getAll();
    }

    @ResponseBody
    @RequestMapping("login")
    public String getById(HttpServletRequest request){
        String password= request.getParameter("password");
        List<UserAliase>  list=usermapper.getAll();
        int i=0;
        for(UserAliase user:list){
            String passwords = PasswordUtil.encrypt(user.getUsername(), password, PasswordUtil.getStaticSalt());
            if(passwords.equals(user.getPassword())){
                System.out.println(user.getId());
            }
            //usermapper.changePassword(user.getUsername(),passwords);
        }
        return "成功";
    }

    public static void main(String[] args) {
        String passwords = PasswordUtil.encrypt("huangkaizhong", "123456", PasswordUtil.getStaticSalt());

    }
}
