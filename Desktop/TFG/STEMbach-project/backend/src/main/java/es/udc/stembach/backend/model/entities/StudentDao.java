package es.udc.stembach.backend.model.entities;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface StudentDao extends PagingAndSortingRepository<Student, Long> {

    Optional<Student> findByDni(String dni);

    Iterable<Student> findByStudentGroupId(Long groupId);
}
