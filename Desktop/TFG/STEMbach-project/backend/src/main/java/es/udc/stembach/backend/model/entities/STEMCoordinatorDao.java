package es.udc.stembach.backend.model.entities;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface STEMCoordinatorDao extends PagingAndSortingRepository<STEMCoordinator, Long> {

    Optional<STEMCoordinator> findByEmail(String email);
}
