package es.udc.stembach.backend.rest.dtos;

import es.udc.stembach.backend.model.entities.School;

import java.util.List;
import java.util.stream.Collectors;

public class SchoolConversor {

    public final static SchoolDto toSchoolDto(School school) {
        return new SchoolDto(school.getId(), school.getName(), school.getLocation());
    }

    public final static List<SchoolDto> toSchoolDtos(List<School> schools){
        return schools.stream().map(s -> toSchoolDto(s)).collect(Collectors.toList());
    }
}
