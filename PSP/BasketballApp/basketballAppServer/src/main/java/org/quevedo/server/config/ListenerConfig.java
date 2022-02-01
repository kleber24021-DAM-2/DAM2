package org.quevedo.server.config;

import jakarta.inject.Inject;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class ListenerConfig implements ServletContextListener {
    private final Configuration config;

    @Inject
    public ListenerConfig(Configuration config) {
        this.config = config;
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        /* This method is called when the servlet context is initialized(when the Web application is deployed). */
        config.loadConfig(sce.getServletContext().getResourceAsStream(ConfigConsts.FILE_PATH));
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        /* This method is called when the servlet Context is undeployed or Application Server shuts down. */
    }
}
