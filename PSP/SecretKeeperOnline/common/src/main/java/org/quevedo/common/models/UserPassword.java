package org.quevedo.common.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//Objeto creado porque el scalars no funciona y quiero pasar una string
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPassword {
    String userPassword;
}
