package com.wulianfa.jwtdemo.Controller;

import com.wulianfa.jwtdemo.Annotations.UserLoginToken;
import com.wulianfa.jwtdemo.Config.Token;
import com.wulianfa.jwtdemo.Entity.User;
import com.wulianfa.jwtdemo.Utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class Test {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/login")
    public String login(){
        /*User user=new User();
        user.setId("1");
        user.setUsername("小明");
        user.setPassword("123456");
        String token = Token.getToken(user);*/
        String token = jwtTokenUtil.generateToken("吴联发");
        return token;
    }

    @GetMapping("/getMsg")
    @UserLoginToken
    public String getMessage(){
        return "你已通过验证";
    }

}
