/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import controllers.PostController;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import services.DBConnector;
import services.WsdlService;

@ManagedBean(name = "post")
@SessionScoped
public class Post implements Serializable {

    private final String tablename = "post";
    private boolean isNewRecord;
    private String id;
    private String judul;
    private String tanggal;
    private String konten;
    private boolean published;
    private int userId;
    private ArrayList<Comment> comments;
    
    public void clearAttributes(){
        this.setId(null);
        this.setJudul(null);
        this.setTanggal(null);
        this.setKonten(null);
        this.setPublished(false);
    }
    
    public boolean loadComments() {
        try {
            DBConnector dbc = new DBConnector();
            Statement st = dbc.getCon().createStatement();

            this.comments = new ArrayList<>();
            String query = "SELECT * FROM comment WHERE post_id=" + id + " ORDER BY created_at ASC";
            ResultSet result = st.executeQuery(query);
            while (result.next()) {
                Comment comment = new Comment();
                comment.setId(result.getInt("id"));
                comment.load();
                comments.add(comment);
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean load(String id) {
        System.out.println("Calling firebase...");
        com.simpleblog.Blog wsdl = WsdlService.getInstance();
        com.simpleblog.PostModel postModel = wsdl.getPost(id);
        if (postModel == null) {
            return false;
        } else {
            System.out.println("fetching post " + id + " get " + postModel.getJudul());
            this.setId(postModel.getId());
            this.setJudul(postModel.getJudul());
            this.setKonten(postModel.getKonten());
            this.setTanggal(postModel.getTanggal());
            this.setPublished(Boolean.parseBoolean(postModel.getStatus()));
            return true;
        }
    }

    public boolean save() {
        System.out.println("Calling firebase...");
        com.simpleblog.Blog wsdl = WsdlService.getInstance();
        boolean retval;

        if (this.isNewRecord) {
            retval = wsdl.addPost(judul,konten,tanggal);
        } else {
            retval = wsdl.editPost(id,judul,konten,tanggal);
        }
        System.out.println("Done saving " + judul + "," + konten + "," + tanggal + "...");
        PostController.fetchPosts();
        return retval;
    }

    public boolean delete() {
        System.out.println("Calling firebase...");
        com.simpleblog.Blog wsdl = WsdlService.getInstance();
        boolean retval = wsdl.deletePost(this.id);
        System.out.println("Deleting user " + this.id + " result = " + retval);
        PostController.fetchPosts();
        return retval;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getKonten() {
        return konten;
    }

    public void setKonten(String konten) {
        this.konten = konten;
    }

    public boolean getPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<Comment> getComments() {
        try {
            DBConnector dbc = new DBConnector();
            Statement st = dbc.getCon().createStatement();

            String query = "SELECT * FROM comment WHERE post_id=" + this.id + " ORDER BY created_at";
            System.out.println(query);
            ResultSet result = st.executeQuery(query);
            this.comments = new ArrayList<>();
            while (result.next()) {
                Comment comment = new Comment();
                comment.setId(result.getInt("id"));
                comment.setEmail(result.getString("email"));
                comment.setCreatedAt(result.getTimestamp("created_at").toString());
                comment.setKonten(result.getString("konten"));
                comment.setNama(result.getString("nama"));
                comments.add(comment);
            }
            return comments;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    public String showUpdate() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> requestParam = context.getExternalContext().getRequestParameterMap();
        HttpServletRequest req = (HttpServletRequest) context.getExternalContext().getRequest();
        return req.getContextPath() + "/faces/post/update.xhtml";
    }

    public boolean getIsNewRecord() {
        return isNewRecord;
    }

    public void setIsNewRecord(boolean isNewRecord) {
        this.isNewRecord = isNewRecord;
    }

    boolean isPublished() {
        return this.published;
    }
    
}
