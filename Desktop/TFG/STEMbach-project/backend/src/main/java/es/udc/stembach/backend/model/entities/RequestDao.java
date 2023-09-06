package es.udc.stembach.backend.model.entities;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface RequestDao extends PagingAndSortingRepository<Request, Long> {

    List<Request> findAllByStudentGroupId(Long studentGroupId);
}
