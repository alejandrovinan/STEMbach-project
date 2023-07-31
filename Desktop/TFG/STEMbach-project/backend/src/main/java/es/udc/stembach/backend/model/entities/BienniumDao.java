package es.udc.stembach.backend.model.entities;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface BienniumDao extends PagingAndSortingRepository<Biennium, Long> {

    Optional<Biennium> findByDateRange(String dateRange);
}
