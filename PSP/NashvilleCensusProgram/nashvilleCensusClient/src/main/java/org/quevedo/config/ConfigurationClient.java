package org.quevedo.config;

import org.quevedo.dao.utils.StringConstants;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.yaml.snakeyaml.Yaml;

import javax.inject.Singleton;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

@Log4j2
@Singleton
@Getter
public class ConfigurationClient {
    private String pathBase;

    private ConfigurationClient() {
        try {
            Yaml yaml = new Yaml();
            Iterable<Object> it;
            it = yaml.loadAll(new FileInputStream(StringConstants.YAML_PATH));
            Map<String, String> map = (Map<String, String>) it.iterator().next();
            this.setPathBase(map.get(StringConstants.PATH_BASE));
        } catch (FileNotFoundException exception) {
            log.error(exception.getMessage());
        }
    }

    private void setPathBase(String pathBase) {
        this.pathBase = pathBase;
    }
}

