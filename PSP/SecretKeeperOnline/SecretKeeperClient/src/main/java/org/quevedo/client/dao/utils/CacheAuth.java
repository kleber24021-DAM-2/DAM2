package org.quevedo.client.dao.utils;

import lombok.Data;

import javax.inject.Singleton;
import java.security.KeyPair;

@Singleton
@Data
public class CacheAuth {
    private int loggedUserId;
    private String loggedUsername;
    private KeyPair loggedKeyPair;
    private String personalCert;
    private String jwt;
}
