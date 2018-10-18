package com.itheima.interceptor;

import com.itheima.domain.User;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpSession;

public class CheckLoginInterceptor extends MethodFilterInterceptor {
    @Override
    protected String doIntercept(ActionInvocation actionInvocation) throws Exception {
        HttpSession session = ServletActionContext.getRequest().getSession();
        User user = (User)session.getAttribute("user");
        if (user == null){
            System.out.print("未登录");
            return "login";
        }
        return actionInvocation.invoke();
    }
}
