package com.wulianfa.jwtdemo;

import com.wulianfa.jwtdemo.Utils.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JwtDemoApplicationTests {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Test
    public void contextLoads() {

        String www = jwtTokenUtil.generateToken("www");
        System.out.println(www);

        Date expiredDateFromToken = jwtTokenUtil.getExpiredDateFromToken(www);
        System.out.println(expiredDateFromToken);

    }

}
