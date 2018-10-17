package com.itheima.action;

import com.itheima.domain.User;
import com.itheima.service.IUserService;
import com.itheima.service.impl.IUserServiceImpl;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.struts2.ServletActionContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

public class UserAction extends ActionSupport implements ModelDriven<User> {

    private User user = new User();
    private IUserService service = new IUserServiceImpl();

    @Override
    public User getModel() {
        return user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String login(){
        User dbUser = service.login(user.getLogonName(),user.getLogonPwd());
        if (dbUser == null){
            addActionError("用户不存在或者用户名或密码错误");
            return INPUT;
        }
        HttpSession session = ServletActionContext.getRequest().getSession();
        session.setAttribute("user",dbUser);
        return SUCCESS;
    }
}
