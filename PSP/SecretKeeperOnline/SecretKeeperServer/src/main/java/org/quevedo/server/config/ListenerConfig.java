package org.quevedo.server.config;

import jakarta.inject.Inject;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class ListenerConfig implements ServletContextListener {
    private final Configuration config;

    @Inject
    public ListenerConfig(Configuration config){
        this.config = config;
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContextListener.super.contextInitialized(sce);
        config.loadConfig(sce.getServletContext().getResourceAsStream(ConfigConsts.FILE_PATH));
        config.loadServerKey(sce.getServletContext().getResourceAsStream(ConfigConsts.KEYSTORE_PATH));
    }
}
