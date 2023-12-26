package es.udc.stembach.backend.model.entities;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface RecordFileDao extends PagingAndSortingRepository<RecordFile, Long> {

    List<RecordFile> findAllByDefenseId(Long defenseId);

    List<RecordFile> findAllByIdIn(List<Long> ids);
}
