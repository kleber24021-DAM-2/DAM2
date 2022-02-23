package org.quevedo.server.ee;

import jakarta.annotation.security.DeclareRoles;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("/api")
@DeclareRoles({"user"})
public class SecretKeeperApplication extends Application {
}
