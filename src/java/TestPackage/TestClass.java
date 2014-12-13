/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestPackage;

import com.simpleblog.Blog;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

/**
 *
 * @author Willy
 */
public class TestClass {

    public static void main(String[] args) throws MalformedURLException {
        URL url = new URL("http://darwinyafiwilly.herokuapp.com/Blog");
        QName qname = new QName("http://simpleblog.com/", "Blog");
        Service service = Service.create(url, qname);
        Blog newWebService = service.getPort(Blog.class);
        System.out.println(newWebService.addPost("halo", "halo", "halo"));
    }
}
