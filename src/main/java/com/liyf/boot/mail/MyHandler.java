package com.liyf.boot.mail;

import com.liyf.boot.entity.UserAliase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MyHandler {
    @Autowired
    UserMapper usermapper;

    @ResponseBody
    @RequestMapping("getAll")
    public List<UserAliase> getAll(){
        return usermapper.getAll();
    }

    @ResponseBody
    @RequestMapping("loginCheck")
    public String getById(@RequestParam("username") String username, @RequestParam("password") String password){
        UserAliase user=usermapper.getByUsername(username);
        if(user!=null){
            if(user.getPassword().equals(password))
                return "登录成功";
            else return "密码错误";
        }
        else return "该用户不存在";
    }
}
