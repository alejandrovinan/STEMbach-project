package es.udc.stembach.backend.model.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SchoolNotFoundException extends Exception{

    private Long schoolId;

    public SchoolNotFoundException(Long schoolId) {
        this.schoolId = schoolId;
    }
}
