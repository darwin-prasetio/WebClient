/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import controllers.UserController;
import java.io.Serializable;
import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import services.CookieService;
import services.DBConnector;
import services.WsdlService;

@ManagedBean(name = "userIdentity", eager = true)
@SessionScoped
public class User implements Serializable {

    public final static String ADMIN = "admin";
    public static final String EDITOR = "editor";
    final public static String OWNER = "owner";
    private boolean isNewRecord;
    private boolean isLoggedIn;
    //private int id;
    private String id;
    private String email;
    private String password;
    private String nama;
    private String role;
    /*
     private boolean isAdmin;
     private boolean isOwner;
     private boolean isEditor;
     */
    private final String tablename = "user";

    public void clearAttributes() {
        this.setId(null);
        this.setEmail(null);
        this.setPassword(null);
        this.setNama(null);
        this.setRole(null);
        this.setIsLoggedIn(false);
    }

    public boolean getIsNewRecord() {
        return isNewRecord;
    }

    public void setIsNewRecord(boolean isNewRecord) {
        this.isNewRecord = isNewRecord;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public boolean getIsGuest() {
        return role == null || role.length() == 0;
    }

    public boolean getIsAdmin() {
        return role == User.ADMIN;
    }

    public boolean getIsEditor() {
        return role == User.EDITOR;
    }

    public boolean getIsOwner() {
        return role == User.OWNER;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getIsLoggedIn() {
        return this.isLoggedIn;
    }

    public void setIsLoggedIn(boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }

    public void setIsOwner(boolean isOwner) {
        this.role = User.OWNER;
    }

    public void setIsEditor(boolean isEditor) {
        this.role = User.EDITOR;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String login() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        com.simpleblog.Blog wsdl = WsdlService.getInstance();

        User user = (User) request.getSession().getAttribute("userIdentity");
        if (user == null) {
            user = new User();
        }

        List<com.simpleblog.UserModel> users = wsdl.listUser();
        for (com.simpleblog.UserModel precil : users) {
            System.out.println(this.email+" "+precil.getEmail());
            if (precil.getEmail().equals(this.email) && precil.getPassword().equals(this.password)) {
                user.setId(precil.getId());
                user.setEmail(precil.getEmail());
                user.setPassword(precil.getPassword());
                user.setRole(precil.getRole());
                user.setNama(precil.getNama());
                user.setIsLoggedIn(true);
                request.getSession().setAttribute("userIdentity", user);
                CookieService.setCookie("email", email, CookieService.DEFAULT_AGE);
                CookieService.setCookie("password", password, CookieService.DEFAULT_AGE);
                return "success";
            }
        }
        FacesMessage message = new FacesMessage();
        message.setDetail("Invalid Username/Password combination");
        //message.setSummary("Login Incorrect");
        message.setSeverity(FacesMessage.SEVERITY_ERROR);
        context.addMessage("form:error", message);
        return "fail";
    }

    public String logout() {
        this.setIsLoggedIn(false);
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();

        //request.getSession().invalidate();
        CookieService.clearCookie("email");
        CookieService.clearCookie("password");
        return request.getContextPath() + "/login.xhtml";
    }

    public boolean load(String id) {
        com.simpleblog.Blog wsdl = WsdlService.getInstance();
        com.simpleblog.UserModel userModel = wsdl.getUser(id);
        System.out.println("fetching user " + id + " get " + userModel.getEmail());
        if (userModel == null) {
            return false;
        } else {
            this.setId(userModel.getId());
            this.setEmail(userModel.getEmail());
            this.setRole(userModel.getRole());
            this.setNama(userModel.getNama());
            return true;
        }
        
    }

    public boolean save() {
        com.simpleblog.Blog wsdl = WsdlService.getInstance();
        boolean retval;

        if (this.isNewRecord) {
            retval = wsdl.addUser(nama, password, email, role);
        } else {
            retval = wsdl.editUser(password, id, nama, role, email);
        }
        System.out.println("Saving " + nama + "," + password + "," + email + "," + role + "...");
        System.out.println("boolean = " + retval);
        UserController.fetchUsers();
        return retval;
    }

    public boolean delete() {
        com.simpleblog.Blog wsdl = WsdlService.getInstance();
        boolean retval = wsdl.deleteUser(this.id);
        System.out.println("Deleting user " + this.id + " result = " + retval);
        UserController.fetchUsers();
        return retval;
    }
}
