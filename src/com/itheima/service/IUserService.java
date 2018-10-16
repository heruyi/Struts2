package com.itheima.service;

import com.itheima.domain.User;

import java.util.List;

public interface IUserService {

    User login(String loginName,String loginPwd);

    Integer saveUser(User user);

    Integer modifyUser(User user);

    Integer removeUser(Integer userID);

    User findUserById(Integer userId);

    List<User> findAllUser();

    List<User> findUserByCondition(String userName,String gender,String education,String isUpload);
}
