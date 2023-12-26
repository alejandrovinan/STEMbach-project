package es.udc.stembach.backend.model.entities;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface SchoolDao extends PagingAndSortingRepository<School, Long> {

    Optional<School> findById(Long id);
    Optional<School> findByName(String name);
}
