package es.udc.stembach.backend.model.entities;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface ProjectDao extends PagingAndSortingRepository<Project, Long> {

    Optional<Project> findById(Long projectId);
}
