package com.bytedance.week5.service;

import com.bytedance.week5.domain.ResponseResult;
import com.bytedance.week5.model.pojo.User;

public interface LoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}
