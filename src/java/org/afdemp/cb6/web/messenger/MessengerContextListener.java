package org.afdemp.cb6.web.messenger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MessengerContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        MessengerConfig.getInstance().setup(sce.getServletContext());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        
    }
    
}
