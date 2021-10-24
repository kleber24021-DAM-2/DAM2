package services.Person;

import dao.DAOPerson;
import models.Person;

public class ServiceModifyPerson {
    DAOPerson dao;

    public ServiceModifyPerson() {
        dao = new DAOPerson();
    }

    public void modifyPerson(Person target, Person newPerson) {
        dao.getSelectedList().set(dao.getSelectedList().indexOf(target), newPerson);
    }
}
