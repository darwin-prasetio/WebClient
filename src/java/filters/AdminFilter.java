/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.*;

public class AdminFilter implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException { }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
         System.out.println("ADMIN FILTER ntar diganti");
        chain.doFilter(request,response);
       
        return ;
        User userIdentity = (User) req.getSession().getAttribute("userIdentity");
        if (userIdentity == null || !userIdentity.getIsAdmin()) {
            res.sendError(403,"Anda tidak berhak mengakses halaman ini");
        } else {
            chain.doFilter(request,response);
        }
    }

    @Override
    public void destroy() { }

}
