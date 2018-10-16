package com.itheima.service.impl;

import com.itheima.dao.IUserDao;
import com.itheima.dao.impl.IUserDaoImpl;
import com.itheima.domain.User;
import com.itheima.service.IUserService;

import java.util.List;

public class IUserServiceImpl implements IUserService {

    private IUserDao dao = new IUserDaoImpl();

    @Override
    public User login(String loginName, String loginPwd) {
        return dao.selectUserByInfo(loginName,loginPwd);
    }

    @Override
    public Integer saveUser(User user) {
        return dao.addUser(user);
    }

    @Override
    public Integer modifyUser(User user) {
        return dao.updateUser(user);
    }

    @Override
    public Integer removeUser(Integer userID) {
        return dao.deleteUser(userID);
    }

    @Override
    public User findUserById(Integer userId) {
        return dao.selectUserByUserId(userId);
    }

    @Override
    public List<User> findAllUser() {
        return dao.selectAllUser();
    }

    @Override
    public List<User> findUserByCondition(String userName, String gender, String education, String isUpload) {
        return dao.selectUserByCondition(userName,gender,education,isUpload);
    }
}
