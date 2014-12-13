package controllers;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import models.Post;
import models.User;
import services.CookieService;
import services.DBConnector;
import services.WsdlService;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
@ManagedBean(name = "userCtrl")
@SessionScoped
public class UserController implements Serializable {

    @ManagedProperty(value = "#{user}")
    private User user;
    @ManagedProperty(value = "#{users}")
    private ArrayList<User> users = null;

    public UserController() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession session = req.getSession();
        User userIdentity = (User) session.getAttribute("userIdentity");
        if (userIdentity == null || userIdentity.getIsGuest()) {
            CookieService.loginWithCookies();
        }
    }

    public String showCreate() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest req = (HttpServletRequest) context.getExternalContext().getRequest();
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        user.clearAttributes();
        user.setIsNewRecord(true);
        session.setAttribute("user", user);
        return "create";
    }

    public String showUpdate() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> requestParam = context.getExternalContext().getRequestParameterMap();
        HttpServletRequest req = (HttpServletRequest) context.getExternalContext().getRequest();
        HttpSession session = req.getSession();

        if (requestParam.containsKey("id")) {
            User user = (User) session.getAttribute("user");
            user.load(requestParam.get("id"));
            user.setIsNewRecord(false);
            session.setAttribute("user", user);
            return "update";
        } else {
            return "fail";
        }
    }

    public String showDelete() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> requestParam = context.getExternalContext().getRequestParameterMap();
        HttpServletRequest req = (HttpServletRequest) context.getExternalContext().getRequest();
        HttpSession session = req.getSession();

        if (requestParam.containsKey("id")) {
            User user = new User();
            user.load(requestParam.get("id"));
            user.delete();
            return "delete";
        } else {
            return "fail";
        }
    }

    public String doSubmit() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> requestParam = context.getExternalContext().getRequestParameterMap();
        HttpServletRequest req = (HttpServletRequest) context.getExternalContext().getRequest();
        HttpSession session = req.getSession();

        user = (User) session.getAttribute("user");
        if (user != null && user.save()) {
            return "success";
        } else {
            return "fail";
        }

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<User> getUsers() {
        return users;
    }
    
    public ArrayList<User> getUsersData(){
        FacesContext context = FacesContext.getCurrentInstance();
        
        HttpServletRequest req = (HttpServletRequest) context.getExternalContext().getRequest();
        HttpSession session = req.getSession();
        UserController userCtrl = (UserController) session.getAttribute("userCtrl");
        if (userCtrl.getUsers() == null){
            UserController.fetchUsers();
        }
        return userCtrl.getUsers();
    }

    public static boolean fetchUsers() {
        /*
         com.simpleblog.Blog wsdl = WsdlService.getInstance();
         List<com.simpleblog.UserModel> listUser =  wsdl.listUser();
         users = new ArrayList<>();
         System.out.println("fetching users... get "+listUser.size()+"record");
        
         for(com.simpleblog.UserModel userModel :listUser){
         User user = new User();
         user.setId(userModel.getId());
         user.setNama(userModel.getNama());
         user.setEmail(userModel.getEmail());
         user.setRole(userModel.getRole());
         users.add(user);
         }
         return users;*/
        System.out.println("woi");
        FacesContext context = FacesContext.getCurrentInstance();
        
        HttpServletRequest req = (HttpServletRequest) context.getExternalContext().getRequest();
        HttpSession session = req.getSession();
        UserController userCtrl = (UserController) session.getAttribute("userCtrl");
        ArrayList<User> users = new ArrayList<>();
        User user = new User();
        user.setEmail("woi");
        user.setNama("hello");
        users.add(user);
        userCtrl.setUsers(users);
        session.setAttribute("userCtrl", userCtrl);
        return true;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

}
