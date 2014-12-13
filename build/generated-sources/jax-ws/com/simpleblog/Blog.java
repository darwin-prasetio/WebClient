
package com.simpleblog;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.8
 * Generated source version: 2.2
 * 
 */
@WebService(name = "Blog", targetNamespace = "http://simpleblog.com/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface Blog {


    /**
     * 
     * @param email
     * @param konten
     * @param createdAt
     * @param nama
     * @param idPost
     * @return
     *     returns java.lang.Boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "addComment", targetNamespace = "http://simpleblog.com/", className = "com.simpleblog.AddComment")
    @ResponseWrapper(localName = "addCommentResponse", targetNamespace = "http://simpleblog.com/", className = "com.simpleblog.AddCommentResponse")
    public Boolean addComment(
        @WebParam(name = "created_at", targetNamespace = "")
        String createdAt,
        @WebParam(name = "id_post", targetNamespace = "")
        String idPost,
        @WebParam(name = "nama", targetNamespace = "")
        String nama,
        @WebParam(name = "email", targetNamespace = "")
        String email,
        @WebParam(name = "konten", targetNamespace = "")
        String konten);

    /**
     * 
     * @param email
     * @param role
     * @param nama
     * @param password
     * @return
     *     returns java.lang.Boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "addUser", targetNamespace = "http://simpleblog.com/", className = "com.simpleblog.AddUser")
    @ResponseWrapper(localName = "addUserResponse", targetNamespace = "http://simpleblog.com/", className = "com.simpleblog.AddUserResponse")
    public Boolean addUser(
        @WebParam(name = "nama", targetNamespace = "")
        String nama,
        @WebParam(name = "password", targetNamespace = "")
        String password,
        @WebParam(name = "email", targetNamespace = "")
        String email,
        @WebParam(name = "role", targetNamespace = "")
        String role);

    /**
     * 
     * @param id
     * @return
     *     returns int
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "search", targetNamespace = "http://simpleblog.com/", className = "com.simpleblog.Search")
    @ResponseWrapper(localName = "searchResponse", targetNamespace = "http://simpleblog.com/", className = "com.simpleblog.SearchResponse")
    public int search(
        @WebParam(name = "id", targetNamespace = "")
        String id);

    /**
     * 
     * @param judul
     * @param konten
     * @param tanggal
     * @return
     *     returns java.lang.Boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "addPost", targetNamespace = "http://simpleblog.com/", className = "com.simpleblog.AddPost")
    @ResponseWrapper(localName = "addPostResponse", targetNamespace = "http://simpleblog.com/", className = "com.simpleblog.AddPostResponse")
    public Boolean addPost(
        @WebParam(name = "judul", targetNamespace = "")
        String judul,
        @WebParam(name = "konten", targetNamespace = "")
        String konten,
        @WebParam(name = "tanggal", targetNamespace = "")
        String tanggal);

    /**
     * 
     * @param id
     * @param email
     * @param role
     * @param nama
     * @param password
     * @return
     *     returns java.lang.Boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "editUser", targetNamespace = "http://simpleblog.com/", className = "com.simpleblog.EditUser")
    @ResponseWrapper(localName = "editUserResponse", targetNamespace = "http://simpleblog.com/", className = "com.simpleblog.EditUserResponse")
    public Boolean editUser(
        @WebParam(name = "password", targetNamespace = "")
        String password,
        @WebParam(name = "id", targetNamespace = "")
        String id,
        @WebParam(name = "nama", targetNamespace = "")
        String nama,
        @WebParam(name = "role", targetNamespace = "")
        String role,
        @WebParam(name = "email", targetNamespace = "")
        String email);

    /**
     * 
     * @return
     *     returns java.util.List<java.lang.Object>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "listPost", targetNamespace = "http://simpleblog.com/", className = "com.simpleblog.ListPost")
    @ResponseWrapper(localName = "listPostResponse", targetNamespace = "http://simpleblog.com/", className = "com.simpleblog.ListPostResponse")
    public List<Object> listPost();

    /**
     * 
     * @param id
     * @return
     *     returns java.lang.Boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "publishPost", targetNamespace = "http://simpleblog.com/", className = "com.simpleblog.PublishPost")
    @ResponseWrapper(localName = "publishPostResponse", targetNamespace = "http://simpleblog.com/", className = "com.simpleblog.PublishPostResponse")
    public Boolean publishPost(
        @WebParam(name = "id", targetNamespace = "")
        String id);

    /**
     * 
     * @param judul
     * @param id
     * @param konten
     * @param tanggal
     * @return
     *     returns java.lang.Boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "editPost", targetNamespace = "http://simpleblog.com/", className = "com.simpleblog.EditPost")
    @ResponseWrapper(localName = "editPostResponse", targetNamespace = "http://simpleblog.com/", className = "com.simpleblog.EditPostResponse")
    public Boolean editPost(
        @WebParam(name = "id", targetNamespace = "")
        int id,
        @WebParam(name = "judul", targetNamespace = "")
        String judul,
        @WebParam(name = "konten", targetNamespace = "")
        String konten,
        @WebParam(name = "tanggal", targetNamespace = "")
        String tanggal);

    /**
     * 
     * @return
     *     returns java.util.List<com.simpleblog.User>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "listUser", targetNamespace = "http://simpleblog.com/", className = "com.simpleblog.ListUser")
    @ResponseWrapper(localName = "listUserResponse", targetNamespace = "http://simpleblog.com/", className = "com.simpleblog.ListUserResponse")
    public List<User> listUser();

    /**
     * 
     * @param id
     * @return
     *     returns java.lang.Boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "deletePost", targetNamespace = "http://simpleblog.com/", className = "com.simpleblog.DeletePost")
    @ResponseWrapper(localName = "deletePostResponse", targetNamespace = "http://simpleblog.com/", className = "com.simpleblog.DeletePostResponse")
    public Boolean deletePost(
        @WebParam(name = "id", targetNamespace = "")
        String id);

    /**
     * 
     * @param id
     * @return
     *     returns java.util.List<java.lang.Object>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "listComment", targetNamespace = "http://simpleblog.com/", className = "com.simpleblog.ListComment")
    @ResponseWrapper(localName = "listCommentResponse", targetNamespace = "http://simpleblog.com/", className = "com.simpleblog.ListCommentResponse")
    public List<Object> listComment(
        @WebParam(name = "id", targetNamespace = "")
        String id);

    /**
     * 
     * @param id
     * @return
     *     returns java.lang.Boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "deleteUser", targetNamespace = "http://simpleblog.com/", className = "com.simpleblog.DeleteUser")
    @ResponseWrapper(localName = "deleteUserResponse", targetNamespace = "http://simpleblog.com/", className = "com.simpleblog.DeleteUserResponse")
    public Boolean deleteUser(
        @WebParam(name = "id", targetNamespace = "")
        String id);

}
