package services.Person;

import dao.DAOPerson;
import models.Person;

public class ServiceDeletePerson {

    DAOPerson dao;

    public ServiceDeletePerson() {
        dao = new DAOPerson();
    }

    public boolean deletePerson(Person p) {
        return dao.getSelectedList().remove(p);
    }
}
