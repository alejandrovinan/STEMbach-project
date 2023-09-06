package es.udc.stembach.backend.model.entities;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface StudentGroupDao extends PagingAndSortingRepository<StudentGroup, Long> {
}
