package es.udc.stembach.backend.rest.dtos;

import es.udc.stembach.backend.model.entities.Faculty;

import java.util.List;
import java.util.stream.Collectors;

public class FacultyConversor {

    public final static FacultyDto toFacultyDto(Faculty faculty){
        return new FacultyDto(faculty.getId(), faculty.getName());
    }

    public final static List<FacultyDto> toFacultyDtos(List<Faculty> facultyList){
        return facultyList.stream().map(f -> toFacultyDto(f)).collect(Collectors.toList());
    }
}
