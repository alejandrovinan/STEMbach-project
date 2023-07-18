package es.udc.stembach.backend.model.entities;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface UDCTeacherDao extends PagingAndSortingRepository<UDCTeacher, Long> {
    boolean existsByEmail(String email);

    Optional<UDCTeacher> findByEmail(String email);
}
