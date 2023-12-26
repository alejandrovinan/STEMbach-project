package es.udc.stembach.backend.model.services;

import es.udc.stembach.backend.model.entities.Defense;
import es.udc.stembach.backend.model.entities.RecordFile;
import es.udc.stembach.backend.model.entities.UDCTeacher;
import es.udc.stembach.backend.model.exceptions.DuplicateInstanceException;
import es.udc.stembach.backend.model.exceptions.InstanceNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface DefenseService {

    public Defense createDefense(Long projectInstanceId, String place, LocalDateTime date,
                          BigDecimal mark, String observations, List<Long> teachers, List<MultipartFile> files)
            throws InstanceNotFoundException, IOException, DuplicateInstanceException;

    Defense findDefenseDetails(Long defenseId) throws InstanceNotFoundException;

    public Defense updateDefense(Defense defense, List<Long> teachers, List<MultipartFile> files, List<Long> filesToRemove) throws InstanceNotFoundException, IOException;

    public List<RecordFile> findRecordFilesOfDefense(Long defenseId);

    public List<UDCTeacher> findTeachersOfDefense(Long defenseId);

    public Boolean checkProjectDefense(Long projectInstanceId);

}
