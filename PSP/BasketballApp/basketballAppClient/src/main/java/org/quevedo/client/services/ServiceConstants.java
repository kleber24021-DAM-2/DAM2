package org.quevedo.client.services;

import java.time.format.DateTimeFormatter;

public class ServiceConstants {
    private ServiceConstants(){}
    public static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
}
