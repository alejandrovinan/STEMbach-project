package es.udc.stembach.backend.model.entities;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface CenterSTEMCoordinatorDao extends PagingAndSortingRepository<CenterSTEMCoordinator, Long> {
    boolean existsByEmail(String email);

    Optional<CenterSTEMCoordinator> findByEmail(String email);

}
