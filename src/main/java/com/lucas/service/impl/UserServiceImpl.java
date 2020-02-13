package com.lucas.service.impl;

import com.lucas.mapper.UserMapper;
import com.lucas.pojo.User;
import com.lucas.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Override
    public User findByName(String name) {
        return userMapper.queryUserByName(name);

    }
}
