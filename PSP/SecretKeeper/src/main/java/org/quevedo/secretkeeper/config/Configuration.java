package org.quevedo.secretkeeper.config;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.quevedo.secretkeeper.dao.utils.DaoConsts;
import org.yaml.snakeyaml.Yaml;

import javax.inject.Singleton;
import java.util.Map;

@Log4j2
@Getter
@Singleton
public class Configuration {
    private String dbUrl;
    private String user;
    private String password;
    private String driver;
    private String generalPassword;

    public void loadConfig() {
        try {
            Yaml yaml = new Yaml();
            Iterable<Object> it;

            it = yaml.loadAll(getClass().getResourceAsStream(DaoConsts.PATH_CONFIG));

            Map<String, String> map = (Map) it.iterator().next();
            this.dbUrl = map.get(DaoConsts.DB_URL);
            this.user = map.get(DaoConsts.USER);
            this.password = map.get(DaoConsts.PASSWORD);
            this.driver = map.get(DaoConsts.DRIVER);
            this.generalPassword = map.get(DaoConsts.GENERAL_PASSWORD);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }
}
