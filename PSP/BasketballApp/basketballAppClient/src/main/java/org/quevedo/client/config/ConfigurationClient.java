package org.quevedo.client.config;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.quevedo.client.dao.utils.StringConstants;
import org.yaml.snakeyaml.Yaml;

import javax.inject.Singleton;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

@Log4j2
@Singleton
@Data
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
}