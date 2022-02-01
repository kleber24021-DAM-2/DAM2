package org.quevedo.server.ee.rest;

import jakarta.annotation.security.DeclareRoles;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.quevedo.server.ee.utils.EEConst;

@ApplicationPath(EEConst.APP_PATH)
@DeclareRoles({EEConst.ADMIN, EEConst.NORMAL})
public class BasketballServerApplication extends Application {
}
