/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.*;
import models.Comment;
import models.Post;
import models.User;
import services.CookieService;
import services.DBConnector;
import services.WsdlService;

@ManagedBean(name = "postCtrl")
@SessionScoped
public class PostController implements Serializable {

    private final int POSTS_PER_PAGE = 5;
    @ManagedProperty(value="#{post}")
    private Post post;
    @ManagedProperty(value="#{posts}")
    private ArrayList<Post> posts =null;

    public PostController() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession session = req.getSession();
        User userIdentity = (User) session.getAttribute("userIdentity");
        if (userIdentity == null || userIdentity.getIsGuest()){
            CookieService.loginWithCookies();
        }
//int page = Integer.parseInt(req.getParameter("page"));
        //int page = 1;
        //this.loadPosts((page - 1) * POSTS_PER_PAGE);
    }

    public String doSubmit() {
        if (post.getUserId() == 0) {
            post.setUserId(1);
        }
        if (post.save()) {
            return "success";
        } else {
            return "fail";
        }
    }
    
    public String showView(){
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> requestParam = context.getExternalContext().getRequestParameterMap();
        HttpServletRequest req = (HttpServletRequest) context.getExternalContext().getRequest();
        HttpSession session = req.getSession();
        post = (Post) session.getAttribute("post");
        User userIdentity = (User) session.getAttribute("userIdentity");
        Comment comment = (Comment) session.getAttribute("comment");
        if (comment == null){
            comment = new Comment();
        }
        if (requestParam.containsKey("id")) {
            String post_id = requestParam.get("id");
            post = new Post();
            post.setId(post_id);
            post.load(post_id);
            //comment.setPostId(post_id);
            //comment.setNama(userIdentity.getNama());
            //comment.setEmail(userIdentity.getEmail());
            session.setAttribute("post", post);
            session.setAttribute("comment",comment);
            return "view";
        } else {
            return "fail";
        }
    }
    
    public String showCreate() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> requestParam = context.getExternalContext().getRequestParameterMap();
        HttpServletRequest req = (HttpServletRequest) context.getExternalContext().getRequest();
        //HttpServletResponse res = (HttpServletResponse) context.getExternalContext().getResponse();
        HttpSession session = req.getSession();
        
        post = (Post) session.getAttribute("post");
        post.clearAttributes();
        post.setIsNewRecord(true);
        session.setAttribute("post",post);
        return "create";
    }
    
    public String showDelete(){
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> requestParam = context.getExternalContext().getRequestParameterMap();
        HttpServletRequest req = (HttpServletRequest) context.getExternalContext().getRequest();
        
        if (requestParam.containsKey("id")) {
            this.post = new Post();
            this.post.setId(requestParam.get("id"));
            this.post.delete();
            return "delete";
        }
        return "fail";
    }
  

    public String showIndex() {
        PostController.fetchPosts();
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return req.getContextPath() + "/faces/post/index.xhtml";
    }

    public String showUpdate() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> requestParam = context.getExternalContext().getRequestParameterMap();
        HttpServletRequest req = (HttpServletRequest) context.getExternalContext().getRequest();
        HttpSession session = req.getSession();
        
        User userIdentity = (User) session.getAttribute("userIdentity");
        if (requestParam.containsKey("id")) {
            post = (Post) session.getAttribute("post");
            post.load(requestParam.get("id"));
            post.setIsNewRecord(false);
            session.removeAttribute("post");
            session.setAttribute("post",post);
            return "update";
        } else {
            return "fail";
        }

    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
/*
    public boolean loadPosts(int offset) {
        try {
            DBConnector dbc = new DBConnector();
            Statement st = dbc.getCon().createStatement();

            String query = "SELECT `id` FROM `post` ORDER BY `tanggal` DESC";
            System.out.println(query);
            ResultSet result = st.executeQuery(query);
            posts = new ArrayList<>();

            while (result.next()) {
                Post post = new Post();
                post.load(result.getInt("id"));
                posts.add(post);
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
*/
    public ArrayList<Post> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }

    public String doPublish (){
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> requestParam = context.getExternalContext().getRequestParameterMap();
        HttpServletRequest req = (HttpServletRequest) context.getExternalContext().getRequest();
        HttpSession session = req.getSession();
        post = (Post) session.getAttribute("post");
        User userIdentity = (User) session.getAttribute("userIdentity");
        
        if (requestParam.containsKey("id")) {
            String post_id = requestParam.get("id");
            post.setId(post_id);
            post.load(post_id);
            post.setPublished(true);
            post.setIsNewRecord(false);
            post.save();
            session.setAttribute("post", post);
            return "success";
        } else {
            return "fail";
        }
    }
    
    public ArrayList<Post> getPostsData(){
        FacesContext context = FacesContext.getCurrentInstance();
        
        HttpServletRequest req = (HttpServletRequest) context.getExternalContext().getRequest();
        HttpSession session = req.getSession();
        PostController postCtrl = (PostController) session.getAttribute("postCtrl");
        if (postCtrl.getPosts() == null){
            PostController.fetchPosts();
        }
        return postCtrl.getPosts();
    }

    public static boolean fetchPosts() {
        System.out.println("Calling firebase...");
        com.simpleblog.Blog wsdl = WsdlService.getInstance();
        List<com.simpleblog.PostModel> listPost = wsdl.listPost();
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest req = (HttpServletRequest) context.getExternalContext().getRequest();
        HttpSession session = req.getSession();
        PostController postCtrl = (PostController) session.getAttribute("postCtrl");
        ArrayList<Post> posts = new ArrayList<>();
        System.out.println("fetching post... get " + listPost.size() + "record");

        for (com.simpleblog.PostModel postModel : listPost) {
            Post post = new Post();
            post.setId(postModel.getId());
            post.setJudul(postModel.getJudul());
            post.setKonten(postModel.getKonten());
            post.setTanggal(postModel.getTanggal());
            post.setPublished(Boolean.parseBoolean(postModel.getStatus()));
            posts.add(post);
        }
        postCtrl.setPosts(posts);
        session.setAttribute("postCtrl", postCtrl);
        return true;
    }
    
    
}
