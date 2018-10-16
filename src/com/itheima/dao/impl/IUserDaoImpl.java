package com.itheima.dao.impl;

import com.itheima.Utils.JNDIUtil;
import com.itheima.dao.IUserDao;
import com.itheima.domain.User;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class IUserDaoImpl implements IUserDao {

    private QueryRunner qr = new QueryRunner(JNDIUtil.getDataSource());


    @Override
    public User selectUserByInfo(String loginName, String loginPwd) {
        try{
            return qr.query("select * from S_User where logonName=? and logonPwd=? ", new BeanHandler<User>(User.class),loginName,loginPwd);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public User selectUserByUserId(Integer userid) {
        try{
            return qr.query("select * from S_User where userID = ? ", new BeanHandler<User>(User.class),userid);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public int addUser(User user) {
        try{
            return qr.update("insert into S_User(userID,userName,logonName,logonPwd,gender,birthday,education,telephone,hobby,path,filename,remark)values(?,?,?,?,?,?,?,?,?,?,?,?)",
                    user.getUserID(),user.getUserName(),user.getLogonName(),user.getLogonPwd(),user.getGender(),user.getBirthday(),user.getEducation(),user.getTelephone(),
                    user.getHobby(),user.getPath(),user.getFilename(),user.getRemark());
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteUser(Integer userid) {
        try{
            return qr.update("delete from S_User where userID = ?",userid);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public int updateUser(User user) {
        try{
            return qr.update("update S_User set userName=?,logonName=?,logonPwd=?,gender=?,birthday=?,education=?,telephone=?,hobby=?,path=?,filename=?,remark=? where userID = ?",
                    user.getUserName(),user.getLogonName(),user.getLogonPwd(),user.getGender(),user.getBirthday(),user.getEducation(),user.getTelephone(),
                    user.getHobby(),user.getPath(),user.getFilename(),user.getRemark(),user.getUserID());
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> selectAllUser() {
        try{
            return qr.query("select * from S_User ", new BeanListHandler<User>(User.class));
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> selectUserByCondition(String userName, String gender, String education, String isUpload) {
        if(StringUtils.isBlank(userName) && StringUtils.isBlank(gender) && StringUtils.isBlank(education) && StringUtils.isBlank(isUpload)){
            return selectAllUser();
        }else{
            try {
                List<Object> parameters = new ArrayList<Object>();
                String sql = " select * from S_User ";
                StringBuffer ss = new StringBuffer(sql);
                ss.append(" where 1=1 ");
                if(StringUtils.isNotBlank(userName)){
                    ss.append(" and userName like ? ");
                    parameters.add("%"+userName+"%");
                }
                if(StringUtils.isNotBlank(gender)){
                    ss.append(" and gender = ? ");
                    parameters.add(gender);
                }
                if(StringUtils.isNotBlank(education)){
                    ss.append(" and education = ? ");
                    parameters.add(education);
                }
                if(StringUtils.isNotBlank(isUpload)){
                    if("true".equals(isUpload)){
                        ss.append(" and filename is not null ");
                    }else{
                        ss.append(" and filename is null ");
                    }
                }
                sql = ss.toString();
                return qr.query(sql, new BeanListHandler<User>(User.class),parameters.toArray());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
