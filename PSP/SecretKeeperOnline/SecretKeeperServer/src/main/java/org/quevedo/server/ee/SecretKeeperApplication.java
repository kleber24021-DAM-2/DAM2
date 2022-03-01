package org.quevedo.server.ee;

import jakarta.annotation.security.DeclareRoles;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath(EEConsts.APP_PATH)
@DeclareRoles({EEConsts.USER})
public class SecretKeeperApplication extends Application {
}
