/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package services;
import com.simpleblog.Blog;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

/**
 *
 * @author yafithekid
 */
public class WsdlService {
    private static Blog instance = null;
    
    private WsdlService(){ }

    private static void init() throws MalformedURLException{
        URL url = new URL("http://darwinyafiwilly.herokuapp.com/Blog");
        QName qname = new QName("http://simpleblog.com/", "Blog");
        Service service = Service.create(url, qname);
        WsdlService.instance = service.getPort(Blog.class);
        System.out.println(WsdlService.instance.listPost());
    }
    
    public static Blog getInstance(){
        if (WsdlService.instance == null){
            try {
                init();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return WsdlService.instance;
    }
        
}
