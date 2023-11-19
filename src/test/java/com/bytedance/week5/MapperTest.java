package com.bytedance.week5;

import com.bytedance.week5.model.dao.UserMapper;
import com.bytedance.week5.model.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class MapperTest {
    @Resource
    private UserMapper userMapper;

    @Test
    public void testUserMapper(){
        User user = userMapper.selectById(1);
        System.out.println(user.getUserName());
    }

    @Test
    public void pass(){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        System.out.println(bCryptPasswordEncoder.encode("1234"));
    }
}
