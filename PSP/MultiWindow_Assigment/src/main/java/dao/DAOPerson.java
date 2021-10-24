package dao;

import models.Person;

import java.util.ArrayList;
import java.util.List;

public class DAOPerson {

    private static List<Person> personList;

    public DAOPerson(){
        if(personList == null){
            personList = new ArrayList<>();
        }
    }

    public boolean addPersonToList(Person p){
        return personList.add(p);
    }

    public List<Person> getPersonList(){
        return personList;
    }

    public boolean removePerson(Person p){
        return personList.remove(p);
    }
}
