package es.udc.stembach.backend.model.services;

import es.udc.stembach.backend.model.entities.Defense;
import es.udc.stembach.backend.model.entities.RecordFile;
import es.udc.stembach.backend.model.entities.UDCTeacher;
import es.udc.stembach.backend.model.exceptions.InstanceNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface DefenseService {

    Defense createDefense(Defense defense, List<Long> teachers, List<MultipartFile> files) throws InstanceNotFoundException, IOException;

    Defense updateDefense(Defense defense, List<Long> teachers, List<MultipartFile> files) throws InstanceNotFoundException;

    List<RecordFile> findRecordFilesOfDefense(Long defenseId);

    List<UDCTeacher> findTeachersOfDefense(Long defenseId);
}
