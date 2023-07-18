package es.udc.stembach.backend.model.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FacultyNotFoundException extends Exception{

    private Long facultyId;

    public FacultyNotFoundException(Long facultyId) {
        this.facultyId = facultyId;
    }
}
