package org.quevedo.client.dao.utils;

import lombok.Data;

import javax.inject.Singleton;

@Singleton
@Data
public class CacheAuth {
    private String user;
    private String pass;
    private String jwt;
}
