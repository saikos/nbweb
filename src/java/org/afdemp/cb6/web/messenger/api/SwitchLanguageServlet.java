package org.afdemp.cb6.web.messenger.api;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.afdemp.cb6.web.messenger.view.I18N;

public class SwitchLanguageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        
        String newLang = req.getParameter("lang");
        if (!I18N.isSupported(newLang)) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        
        session.setAttribute("lang", newLang);
        //generate a tiny JSON object to indicate successful status
        resp.getWriter().write("{\"status\":\"OK\"}");
    }        
}
