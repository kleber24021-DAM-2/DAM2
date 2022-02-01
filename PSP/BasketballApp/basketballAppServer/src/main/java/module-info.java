module basketballAppServer {
    requires jakarta.jakartaee.api;
    requires lombok;
    requires org.yaml.snakeyaml;
    requires common;
    requires spring.tx;
    requires spring.jdbc;
    requires java.sql;
    requires io.vavr;
    requires com.google.gson;
    requires com.zaxxer.hikari;
    requires org.apache.logging.log4j;
    requires jjwt.api;
}