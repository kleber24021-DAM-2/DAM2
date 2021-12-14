package org.quevedo.model.serverresponses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class ServerResponse {
    private String message;
    private LocalDate dateOfResponse;
    private boolean incidence;
}
