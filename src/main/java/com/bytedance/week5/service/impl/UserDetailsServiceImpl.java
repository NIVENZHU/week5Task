package com.bytedance.week5.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bytedance.week5.domain.LoginUser;
import com.bytedance.week5.model.dao.UserMapper;
import com.bytedance.week5.model.pojo.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

//实现接口方法，对用户信息校验
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Resource
    private UserMapper userMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("nickname",username);
        User user = userMapper.selectOne(queryWrapper);
        if(Objects.isNull(user)){
            throw new RuntimeException("用户名或密码错误");
        }
        //TODO 获取权限信息封装进Authentication
        List<String> list = new ArrayList<>(Arrays.asList(user.getUserType()));
        return new LoginUser(user,list);
    }
}
