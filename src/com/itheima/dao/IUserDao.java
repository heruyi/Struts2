package com.itheima.dao;
import com.itheima.domain.User;

import java.util.List;

public interface IUserDao {

    User selectUserByInfo(String loginName,String loginPwd);

    public User selectUserByUserId(Integer userId);

    public int addUser(User user);

    public int deleteUser(Integer userID);

    public int updateUser(User user);

    public List<User> selectAllUser();

    public  List<User> selectUserByCondition(String  userName,String gender,String education,String isUpload);
}
