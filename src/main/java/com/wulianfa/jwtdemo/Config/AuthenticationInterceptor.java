package com.wulianfa.jwtdemo.Config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.wulianfa.jwtdemo.Annotations.PassToken;
import com.wulianfa.jwtdemo.Annotations.UserLoginToken;
import com.wulianfa.jwtdemo.Utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @Author wulianfa
 * @Description 验证Token拦截器
 * @Date 23:40 2019/6/20
 */
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        /*1.从 http 请求头中取出 token，
        2.判断是否映射到方法
        3.检查是否有passtoken注释，有则跳过认证
        4.检查有没有需要用户登录的注解，有则需要取出并验证
        5.认证通过则可以访问，不通过会报相关错误信息*/

        //从http请求头中获取token
        String token = request.getHeader("token");
        //判断是否映射到方法上否则直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        //判断方法上是否有PassToken注解否则直接通过
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }
        //判断方法上是否有UserLoginToken注解
        if (method.isAnnotationPresent(UserLoginToken.class)) {
            //方式一
            /*UserLoginToken annotation = method.getAnnotation(UserLoginToken.class);
            if (annotation.required()) {
                if (token == null) {
                    throw new RuntimeException("token不存在，请重新登录。");
                }
            }
            //获取token中userid
            String userid;
            try {
                userid = JWT.decode(token).getAudience().get(0);
            } catch (JWTDecodeException j) {
                throw new RuntimeException("401");
            }
            //TODO 通过userid数据库取用户数据并判断用户是否存在
            //验证token
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("123456")).build();
            try {
                jwtVerifier.verify(token);
            } catch (JWTVerificationException j) {
                throw new RuntimeException("401");
            }
            return true;*/
            //方式二
            UserLoginToken annotation = method.getAnnotation(UserLoginToken.class);
            if (annotation.required()) {
                if (token == null) {
                    throw new RuntimeException("token不存在，请重新登录。");
                }
            }
            boolean b = jwtTokenUtil.validateToken(token, "吴联发");
            if(!b){
                throw new RuntimeException("token失效。");
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
