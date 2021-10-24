/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package configuration;

import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Laura
 */
public class ConfigYaml {

    private static ConfigYaml config;
    private String user;
    private String pass;

    private ConfigYaml() {

    }

    public static ConfigYaml getInstance() {
        if (config == null) {
            try {
                Yaml yaml = new Yaml();
                InputStream in = Files.newInputStream(Paths.get("propertiesFiles/users.yml"));
                // Parse the YAML document in a stream and produce the corresponding Java object.
                config = yaml.loadAs(in, ConfigYaml.class);

            } catch (IOException ex) {
                Logger.getLogger(ConfigYaml.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return config;
    }


    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }


}
