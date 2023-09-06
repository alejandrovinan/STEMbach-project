package es.udc.stembach.backend.model.entities;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface ProjectDao extends PagingAndSortingRepository<Project, Long>, CustomizedProjectDao {

    Optional<Project> findById(Long projectId);
}
