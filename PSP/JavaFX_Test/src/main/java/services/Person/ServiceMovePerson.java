package services.Person;

import dao.DAOPerson;
import models.Person;

public class ServiceMovePerson {

    DAOPerson dao;

    public ServiceMovePerson() {
        dao = new DAOPerson();
    }

    public void moveToSelected(Person p) {
        dao.getGeneralList().remove(p);
        dao.getSelectedList().add(p);
    }

    public void moveToGeneral(Person p) {
        dao.getSelectedList().remove(p);
        dao.getGeneralList().add(p);
    }
}
