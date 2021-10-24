package services;

import dao.DAOPerson;
import models.Person;

import java.util.List;
import java.util.stream.Collectors;

public class ServicesPerson {
    private final DAOPerson dao = new DAOPerson();

    public boolean addPerson(Person p) {
        if (p.hasNullValues()) {
            return false;
        } else {
            return dao.addPersonToList(p);
        }
    }

    public List<Person> getPersonList() {
        return dao.getPersonList();
    }

    public List<Person> getFilteredData(String selectedOption) {
        List<Person> rawData = dao.getPersonList();
        return rawData.stream().filter(
                person -> {
                    if (selectedOption.equals("Hombre")) {
                        return person.isMale();
                    } else if (selectedOption.equals("Mujer")) {
                        return !person.isMale();
                    } else {
                        return person.isMale() || !person.isMale();
                    }
                }
        ).collect(Collectors.toList());
    }

    public boolean updatePerson(Person toUpdatePerson, Person newPerson) {
        if (newPerson.hasNullValues()) {
            return false;
        } else {
            if (dao.removePerson(toUpdatePerson)) {
                return dao.addPersonToList(newPerson);
            } else
                return false;
        }
    }

    public boolean deletePerson(Person personToDelete) {
        return dao.removePerson(personToDelete);
    }
}
