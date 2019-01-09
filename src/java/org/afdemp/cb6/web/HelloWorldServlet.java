package org.afdemp.cb6.web;

import java.io.Writer;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelloWorldServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException {
        try {
            res.setContentType("text/plain; charset=utf-8");
            Writer writer = res.getWriter();
            writer.write("Hello world!");
            writer.close();
        }
        catch(Exception e) {
                throw new ServletException(e.getMessage(), e);
        }
    }
}
