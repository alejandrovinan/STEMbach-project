package es.udc.stembach.backend.model.entities;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface DefenseDao extends PagingAndSortingRepository<Defense, Long> {

    Optional<Defense> findByProjectInstanceId(Long projectInstanceId);

    Optional<Defense> findById(Long defenseId);
}
