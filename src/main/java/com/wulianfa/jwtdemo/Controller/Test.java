package com.wulianfa.jwtdemo.Controller;

import com.wulianfa.jwtdemo.Annotations.UserLoginToken;
import com.wulianfa.jwtdemo.Config.Token;
import com.wulianfa.jwtdemo.Entity.User;
import org.springframework.web.bind.annotation.*;

@RestController
public class Test {

    @PostMapping("/login")
    public String login(){
        User user=new User();
        user.setId("1");
        user.setUsername("小明");
        user.setPassword("123456");
        String token = Token.getToken(user);
        return token;
    }

    @GetMapping("/getMessage")
    @UserLoginToken
    public String getMessage(){
        return "你已通过验证";
    }

}
