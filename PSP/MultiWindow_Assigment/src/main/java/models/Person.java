package models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Person {

    private String name;
    private String surname;
    private int age;
    private boolean isMale;
    private LocalDate birthDate;

    @Override
    public String toString() {
        if (isMale) {
            return (name + " " + surname + ", " + age + " años, hombre, " + birthDate.toString());
        } else
            return (name + " " + surname + ", " + age + " años, mujer, " + birthDate.toString());
    }

    public boolean hasNullValues() {
        return name.isEmpty() || name.isBlank() || surname.isBlank() || surname.isEmpty() || birthDate == null;
    }
}
