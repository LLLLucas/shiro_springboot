package com.lucas.service;

import com.lucas.pojo.User;

public interface UserService {
    public User findByName(String name);
}
