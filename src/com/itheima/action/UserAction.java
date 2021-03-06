package com.itheima.action;

import com.itheima.domain.User;
import com.itheima.service.IUserService;
import com.itheima.service.impl.IUserServiceImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.util.TokenHelper;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class UserAction extends ActionSupport implements ModelDriven<User> {

    private User user = new User();
    private IUserService service = new IUserServiceImpl();

    public File getUpload() {
        return upload;
    }

    public void setUpload(File upload) {
        this.upload = upload;
    }

    public String getUploadFileName() {
        return uploadFileName;
    }

    public void setUploadFileName(String uploadFileName) {
        this.uploadFileName = uploadFileName;
    }

    public UserAction() {
    }

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

    private File upload;
    private String uploadFileName;

    public String add() throws Exception {

        String filePath = ServletActionContext.getServletContext().getRealPath("/files");
        String dir = generateChildPath(filePath);
        String fileName = TokenHelper.generateGUID() + "_" + uploadFileName;
        user.setPath(dir);
        user.setFilename(fileName);
        upload.renameTo(new File(filePath + File.separator + dir,fileName));
        int res = service.saveUser(user);
        if( res > 0){
            return SUCCESS;
        }
        return null;
    }

    private String generateChildPath(String filePath) {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String time = format.format(date);
        File file = new File(filePath,time);
        if (!file.exists()){
            file.mkdirs();
        }
        return time;
    }

    private List<User> users;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String findAll(){
        users = service.findAllUser();
        return SUCCESS;
    }

    public String findUserById(){
        user = service.findUserById(user.getUserID());
        ActionContext.getContext().getValueStack().push(user);
        return SUCCESS;
    }


    private InputStream inputStream;

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String getOldFileName() {
        return oldFileName;
    }

    public void setOldFileName(String oldFileName) {
        this.oldFileName = oldFileName;
    }

    private String oldFileName;

    public String download()throws Exception {

        User dbUser = service.findUserById(user.getUserID());
        String filePath = ServletActionContext.getServletContext().getRealPath("/files");
        oldFileName = dbUser.getFilename().substring(dbUser.getFilename().indexOf("_")+1);
        inputStream = new FileInputStream(filePath + File.separator + dbUser.getPath() + File.separator + dbUser.getFilename());
        return SUCCESS;
    }

    public String delete()throws Exception{
        int res = service.removeUser(user.getUserID());
        if (res > 0){
            return SUCCESS;
        }
        return null;
    }

    public String editUI() throws Exception{
        user = service.findUserById(user.getUserID());
        ActionContext.getContext().getValueStack().push(user);
        return null;
    }

}
