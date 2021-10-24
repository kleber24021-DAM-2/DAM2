package services;

import dao.DAOPerson;
import models.Person;

import java.util.List;

public class ServiceGetDaoLists {
    private final DAOPerson dao;

    public ServiceGetDaoLists() {
        dao = new DAOPerson();
    }

    public List<Person> getGeneralList() {
        return dao.getGeneralList();
    }

    public List<Person> getSelectedList() {
        return dao.getSelectedList();
    }
}
