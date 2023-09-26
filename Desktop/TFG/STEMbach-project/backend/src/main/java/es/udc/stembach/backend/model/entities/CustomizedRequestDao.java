package es.udc.stembach.backend.model.entities;

import org.springframework.data.domain.Slice;

public interface CustomizedRequestDao {
    Slice<Request> findAllByProjectId(Long projectId, int page, int size);
}
