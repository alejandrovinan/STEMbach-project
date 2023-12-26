package es.udc.stembach.backend.model.entities;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface JudgesDao extends PagingAndSortingRepository<Judges, Long> {

    List<Judges> findAllByUdcTeacherIdAndDefenseId(Long udcTeacherId, Long defenseId);

    @Query("SELECT NEW es.udc.stembach.backend.model.entities.UDCTeacher(u.id, u.name, u.surname, u.secondSurname, u.role, u.email, u.dni, u.password, u.faculty) " +
            "FROM UDCTeacher u JOIN Judges j on u.id = j.udcTeacher.id " +
            "WHERE j.defense.id = :defenseId " +
            "ORDER BY u.name, u.surname")
    List<UDCTeacher> findAllTeachersOfDefense(Long defenseId);

    List<Judges> findAllByDefenseId(Long defenseId);
}
