package es.udc.stembach.backend.model.entities;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.PagingAndSortingRepository;

import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface ProjectDao extends PagingAndSortingRepository<Project, Long>, CustomizedProjectDao {

    Optional<Project> findById(Long projectId);

    Iterable<Project> findAllByRevisedIsTrueAndActiveIsTrueAndAssignedIsFalse();

    Page<Project> findAll(Pageable pageable);
}
