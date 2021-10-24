package services.Person;

import dao.DAOPerson;
import models.Person;

public class ServiceAddPersona {
    DAOPerson dao;

    public ServiceAddPersona() {
        dao = new DAOPerson();
    }

    public boolean addPerson(Person p) {
        if (p.getRegistryDate() == null || p.getName().isEmpty() || p.getName().isBlank()) {
            return false;
        }
        return dao.getGeneralList().add(p);
    }
}
