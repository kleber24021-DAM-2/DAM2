package config;

import dao.utils.StringsUtils;
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
public class ConfigurationSingletonClient {

    private String path_base;

    private ConfigurationSingletonClient() {
        try {
            Yaml yaml = new Yaml();

            Iterable<Object> it = null;

            it = yaml.loadAll(new FileInputStream(StringsUtils.YAML_PATH));

            Map<String, String> map = (Map<String, String>) it.iterator().next();
            this.setPathBase(map.get(StringsUtils.PATH_BASE));

        } catch (FileNotFoundException ex) {
            log.error(ex.getMessage());
        }
    }

    private void setPathBase(String pathBase) {
        this.path_base = pathBase;
    }
}
