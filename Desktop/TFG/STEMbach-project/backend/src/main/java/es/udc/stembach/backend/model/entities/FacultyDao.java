package es.udc.stembach.backend.model.entities;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface FacultyDao extends PagingAndSortingRepository<Faculty, Long> {

    Optional<Faculty> findByName(String name);
}
