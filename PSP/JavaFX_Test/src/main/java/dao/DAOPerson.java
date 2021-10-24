package dao;

import models.Person;

import java.util.ArrayList;
import java.util.List;


public class DAOPerson {

    private static List<Person> generalList;
    private static List<Person> selectedList;

    public DAOPerson() {
        if (generalList == null) {
            generalList = new ArrayList<>();
            selectedList = new ArrayList<>();
        }
    }

    public List<Person> getGeneralList() {
        return generalList;
    }

    public List<Person> getSelectedList() {
        return selectedList;
    }
}
