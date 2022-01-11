package org.quevedo.server.config;

import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import org.quevedo.server.dao.utils.DBConnectionPool;

@WebListener
public class ListenerConfig implements ServletContextListener{
    private final Configuration config;
    private final DBConnectionPool dbConnectionPool;

    @Inject
    public ListenerConfig(Configuration config, DBConnectionPool dbConnectionPool) {
        this.config = config;
        this.dbConnectionPool = dbConnectionPool;
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        /* This method is called when the servlet context is initialized(when the Web application is deployed). */
        config.loadConfig(sce.getServletContext().getResourceAsStream(ConfigConsts.FILE_PATH));
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        /* This method is called when the servlet Context is undeployed or Application Server shuts down. */
        dbConnectionPool.closePool();
    }
}
