package models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor

public class Person {
    private String name;
    private int age;
    private boolean isMale;
    private LocalDate registryDate;


    @Override
    public String toString() {
        if (name.isBlank() || name.isEmpty()) {
            name = "N/A";
        }
        if (isMale) {
            return name + " " + age + " Hombre " + registryDate.toString();
        }
        return name + " " + age + " Mujer " + registryDate.toString();
    }
}
