package org.quevedo.server.config;

import jakarta.inject.Singleton;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

@Log4j2
@Getter
@Singleton
public class Configuration {
    private String path;
    private String user;
    private String password;
    private String driver;
    private String mailContentType;
    private String mailProtocol;
    private String mailHost;
    private String mailUser;
    private String mailPass;
    private String mailSmtpPort;
    private String mailSmtpAuth;
    private String mailSmtpStarttlsEnable;

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
            this.mailContentType = map.get(ConfigConsts.MAIL_CONTENT_TYPE);
            this.mailProtocol = map.get(ConfigConsts.MAIL_PROTOCOL);
            this.mailHost = map.get(ConfigConsts.MAIL_HOST);
            this.mailUser = map.get(ConfigConsts.MAIL_USER);
            this.mailPass = map.get(ConfigConsts.MAIL_PASSWORD);
            this.mailSmtpPort = map.get(ConfigConsts.MAIL_SMTP_PORT);
            this.mailSmtpAuth = map.get(ConfigConsts.MAIL_SMTP_AUTH);
            this.mailSmtpStarttlsEnable = map.get(ConfigConsts.MAIL_SMTP_STARTTLS_ENABLE);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
