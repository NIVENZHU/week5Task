package com.bytedance.week5.service.impl;

import com.bytedance.week5.domain.LoginUser;
import com.bytedance.week5.domain.ResponseResult;
import com.bytedance.week5.model.pojo.User;
import com.bytedance.week5.service.LoginService;
import com.bytedance.week5.utils.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {
    @Resource
    private AuthenticationManager authenticationManager;

    @Override
    public ResponseResult login(User user) {

        //获取AuthenticationManager authenticate进行用户认证
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getNickname(),user.getPassword());
        //后面的方法会进入UserDetailsService进行数据库查找，在通过userDetails放行
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        //如果没通过，给出提示，使用jwt存入Response返回
        if(Objects.isNull(authentication)){
            throw new RuntimeException("登录失败");
        }
        //通过后，使用userId生成一个jwt
        //（获取用户id，并变成string）
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String userId = loginUser.getUser().getUserId().toString();
        //生成jwt并且与id组成map返回给前端（此步骤可以顺便把map存入redis）
        String jwt = JwtUtil.createJWT(userId);
        Map<String,String> map = new HashMap<>();
        map.put(userId,jwt);
        return new ResponseResult(200,"登录成功",map);
    }
    //退出登录
    public ResponseResult logout(){
        //获取SecurityContextHolder
        UsernamePasswordAuthenticationToken authentication
                =(UsernamePasswordAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
        //获取用户id
        User user = (User) authentication.getPrincipal();
        //正常情况下删除redis中的用户数据，此处把context设置为null
        SecurityContextHolder.getContext().setAuthentication(null);
        return new ResponseResult(200,"注销成功");
    }

}
