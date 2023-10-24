package es.udc.stembach.backend.model.services;

import es.udc.stembach.backend.model.entities.*;
import es.udc.stembach.backend.model.exceptions.InstanceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DefenseServiceImpl implements DefenseService{

    @Autowired
    private UDCTeacherDao udcTeacherDao;

    @Autowired
    private DefenseDao defenseDao;

    @Autowired
    private JudgesDao judgesDao;

    @Autowired
    private RecordFileDao recordFileDao;

    @Override
    public Defense createDefense(Defense defense, List<Long> teachers, List<MultipartFile> files) throws InstanceNotFoundException, IOException {

        Defense defenseAux = defenseDao.save(defense);

        for(Long t: teachers){
            Optional<UDCTeacher> udcTeacher = udcTeacherDao.findById(t);
            if(udcTeacher.isEmpty()){
                throw new InstanceNotFoundException("project.entities.udcTeacher", t);
            } else {
                judgesDao.save(new Judges(udcTeacher.get(), defenseAux));
            }
        }

        for(MultipartFile f: files){
            recordFileDao.save(new RecordFile(StringUtils.getFilename(f.getOriginalFilename()), f.getBytes(), f.getSize(), LocalDateTime.now(), StringUtils.cleanPath(f.getContentType()), defenseAux));
        }
        return defenseAux;
    }

    @Override
    public Defense updateDefense(Defense defense, List<Long> teachers, List<MultipartFile> files) throws InstanceNotFoundException {

        Optional<Defense> defenseOptional = defenseDao.findById(defense.getId());

        if(defenseOptional.isEmpty()){
            throw new InstanceNotFoundException("project.entities.defense", defense.getId());
        }

        return null;
    }

    @Override
    public List<RecordFile> findRecordFilesOfDefense(Long defenseId) {
        return recordFileDao.findAllByDefenseId(defenseId);
    }

    @Override
    public List<UDCTeacher> findTeachersOfDefense(Long defenseId) {
        return judgesDao.findAllTeachersOfDefense(defenseId);
    }
}
