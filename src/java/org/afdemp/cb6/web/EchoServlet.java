package org.afdemp.cb6.web;

import java.io.IOException;
import java.io.Writer;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EchoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {
            res.setContentType("text/plain; charset=utf-8");
            Writer writer = res.getWriter();
            Enumeration e = req.getParameterNames();
            while(e.hasMoreElements()) {
                String name = (String) e.nextElement();
                String value = req.getParameter(name);
                writer.write(name + " = " + value + "\n");
            }
            writer.close();
        }
        catch(Exception e) {
                throw new ServletException(e.getMessage(), e);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }
    
    
    
    
    
    
}
