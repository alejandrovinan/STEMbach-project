package es.udc.stembach.backend.model.services;

import es.udc.stembach.backend.model.entities.*;
import es.udc.stembach.backend.model.exceptions.DuplicateInstanceException;
import es.udc.stembach.backend.model.exceptions.InstanceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    @Autowired
    private ProjectInstanceDao projectInstanceDao;

    @Override
    public Defense createDefense(Long projectInstanceId, String place, LocalDateTime date, BigDecimal mark, String observations, List<Long> teachers, List<MultipartFile> files) throws InstanceNotFoundException, IOException, DuplicateInstanceException {

        Optional<Defense> defenseOptional = defenseDao.findByProjectInstanceId(projectInstanceId);
        if(defenseOptional.isPresent()){
            throw new DuplicateInstanceException("project.entities.defense", projectInstanceId);
        }
        Optional<ProjectInstance> projectInstance = projectInstanceDao.findById(projectInstanceId);

        if(projectInstance.isEmpty()){
            throw new InstanceNotFoundException("project.entitites.projectInstance", projectInstanceId);
        }

        Defense defenseAux = defenseDao.save(new Defense(projectInstance.get(), place, date, mark, observations));

        for(Long t: teachers){
            Optional<UDCTeacher> udcTeacher = udcTeacherDao.findById(t);
            if(udcTeacher.isEmpty()){
                throw new InstanceNotFoundException("project.entities.udcTeacher", t);
            } else {
                judgesDao.save(new Judges(udcTeacher.get(), defenseAux));
            }
        }

        for(MultipartFile f: files){
            recordFileDao.save(new RecordFile(StringUtils.getFilename(f.getOriginalFilename()), Base64.getEncoder().encode(f.getBytes()), f.getSize(), LocalDateTime.now(), StringUtils.cleanPath(f.getContentType()), defenseAux));
        }
        return defenseAux;
    }

    @Override
    @Transactional(readOnly = true)
    public Defense findDefenseDetails(Long defenseId) throws InstanceNotFoundException {
        Optional<Defense> defenseOptional = defenseDao.findByProjectInstanceId(defenseId);

        if(defenseOptional.isEmpty()){
            throw new InstanceNotFoundException("project.instance.defense", defenseId);
        }

        return defenseOptional.get();
    }

    @Override
    public Defense updateDefense(Defense defense, List<Long> teachers, List<MultipartFile> files, List<Long> filesToRemove) throws InstanceNotFoundException, IOException {

        Optional<Defense> defenseOptional = defenseDao.findByProjectInstanceId(defense.getId());

        if(defenseOptional.isEmpty()){
            throw new InstanceNotFoundException("project.entities.defense", defense.getId());
        }

        if (defense.getPlace() != null) {
            defenseOptional.get().setPlace(defense.getPlace());
        }

        if(defense.getDate() != null){
            defenseOptional.get().setDate(defense.getDate());
        }

        if(defense.getMark() != null){
            defenseOptional.get().setMark(defense.getMark());
        }

        if(defense.getObservations() != null){
            defenseOptional.get().setObservations(defense.getObservations());
        }

        List<Judges> currentJudges = judgesDao.findAllByDefenseId(defenseOptional.get().getId());
        List<Long> newJudges = new ArrayList<>();

        //Eliminamos los jueces que no se encuentren en la lista recibida

        if (teachers != null) {
            for(Judges j: currentJudges){
                if(!teachers.contains(j.getUdcTeacher().getId())){
                    judgesDao.delete(j);
                } else {
                    newJudges.add(j.getUdcTeacher().getId());
                }
            }


            /*
            * Añadimos a la BD los nuevos jueces correspondientes a la lista recibida
            */
            for(Long id: teachers){
                if(!newJudges.contains(id)){
                    Optional<UDCTeacher> udcTeacherTemp = udcTeacherDao.findById(id);
                    if(udcTeacherTemp.isEmpty()){
                        throw new InstanceNotFoundException("project.entities.udcteacher", id);
                    }

                    judgesDao.save(new Judges(udcTeacherTemp.get(), defenseOptional.get()));
                    newJudges.add(id);
                }
            }
        }

        if (filesToRemove != null) {
            Map<Long, RecordFile> recordFilesToEliminate = recordFileDao.findAllByIdIn(filesToRemove)
                    .stream()
                    .collect(Collectors.toMap(RecordFile::getId, Function.identity()));

            //Eliminamos los archivos marcados para eliminar
            for(Long id: filesToRemove){
                RecordFile recordFileTemp = recordFilesToEliminate.get(id);
                if(recordFileTemp == null){
                    throw new InstanceNotFoundException("project.entities.recordFile", id);
                }

                recordFileDao.delete(recordFileTemp);
            }
        }
        if (files != null) {
            //Guardamos los archivos nuevos
            for(MultipartFile f: files){
                recordFileDao.save(new RecordFile(StringUtils.getFilename(f.getOriginalFilename()), Base64.getEncoder().encode(f.getBytes()), f.getSize(),
                        LocalDateTime.now(), StringUtils.cleanPath(f.getContentType()), defenseOptional.get()));
            }
        }

        return defenseOptional.get();
    }

    @Override
    @Transactional(readOnly = true)
    public List<RecordFile> findRecordFilesOfDefense(Long defenseId) {
        return recordFileDao.findAllByDefenseId(defenseId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UDCTeacher> findTeachersOfDefense(Long defenseId) {
        return judgesDao.findAllTeachersOfDefense(defenseId);
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean checkProjectDefense(Long projectInstanceId) {
        Optional<Defense> defenseOptional = defenseDao.findByProjectInstanceId(projectInstanceId);

        if(defenseOptional.isPresent()){
            return true;
        }
        return false;
    }
}
