package com.bytedance.week5.filter;

import com.bytedance.week5.model.dao.UserMapper;
import com.bytedance.week5.model.pojo.User;
import com.bytedance.week5.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Resource
    private UserMapper userMapper;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取token
        String token = request.getHeader("token");
        //(如果token为空)
        if(!StringUtils.hasText(token)){
            //放行
            filterChain.doFilter(request,response);
            return;
        }
        //从token中获取id，并转换成long
        try {
            Claims claims = (Claims) JwtUtil.parseJWT(token);
            String userId = claims.getSubject();
            Long id = Long.parseLong(userId);
            //从数据库获取用户信息
            User user = userMapper.selectById(id);
            if(Objects.isNull(user)){
                throw new RuntimeException("用户未登录");
            }
            //存入SecurityContextHolder
            //TODO 获取权限信息分装进AuthenticationToken
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user,null,null);
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            //放行
            filterChain.doFilter(request,response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("token非法");
        }






    }
}
