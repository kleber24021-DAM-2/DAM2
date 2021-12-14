module nashvilleCensusServer {
    requires jakarta.jakartaee.api;
    requires common;
    requires io.vavr;

    opens org.quevedo.dao;
    opens org.quevedo.services;
    opens org.quevedo.ee.rest;
}