package com.hongdu.service;

import com.hongdu.dto.UserLoginDTO;
import com.hongdu.entity.User;

public interface UserService {

    /**
     * 微信登录
     * @param userLoginDTO
     * @return
     */
    User wxLogin(UserLoginDTO userLoginDTO);
}
