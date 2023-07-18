package es.udc.stembach.backend.model.entities;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface CenterHistoryDao extends PagingAndSortingRepository<CenterHistory, Long> {
    Optional<CenterHistory> findBySchoolIdAndEndDateIsNull(Long schoolId);
}
