package org.quevedo.server.config;

import jakarta.inject.Singleton;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.quevedo.common.consts.SecurityConsts;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.Map;

@Log4j2
@Getter
@Singleton
public class Configuration {
    private String path;
    private String user;
    private String password;
    private String driver;
    private KeyPair serverKeys;
    private String generalPass;

    void loadConfig(InputStream fileStream) {
        try {
            Yaml yaml = new Yaml();
            Iterable<Object> it;

            it = yaml.loadAll(fileStream);

            Map<String, String> map = (Map) it.iterator().next();
            this.path = map.get(ConfigConsts.PATH_PARAMETER);
            this.user = map.get(ConfigConsts.USER_PARAMETER);
            this.password = map.get(ConfigConsts.PASS_PARAMETER);
            this.driver = map.get(ConfigConsts.DRIVER_PARAMETER);
            this.generalPass = map.get(ConfigConsts.GENERAL_PASS);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    void loadServerKey(InputStream fileStream) {
        try {
            KeyStore ksLoad = KeyStore.getInstance(SecurityConsts.KEY_STORE_ALGORITHM);
            ksLoad.load(fileStream, generalPass.toCharArray());
            X509Certificate certicateLoaded = (X509Certificate) ksLoad.getCertificate(ConfigConsts.ROOT);
            KeyStore.PasswordProtection pt = new KeyStore.PasswordProtection(ConfigConsts.ROOT.toCharArray());
            KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) ksLoad.getEntry(ConfigConsts.ROOT_PRIVATE, pt);
            PrivateKey keyLoaded = privateKeyEntry.getPrivateKey();
            serverKeys = new KeyPair(certicateLoaded.getPublicKey(), keyLoaded);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
