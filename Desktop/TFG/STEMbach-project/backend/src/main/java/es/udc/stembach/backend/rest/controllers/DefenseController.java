package es.udc.stembach.backend.rest.controllers;

import es.udc.stembach.backend.model.entities.Defense;
import es.udc.stembach.backend.model.exceptions.DuplicateInstanceException;
import es.udc.stembach.backend.model.exceptions.InstanceNotFoundException;
import es.udc.stembach.backend.model.services.DefenseService;
import es.udc.stembach.backend.model.services.UserService;
import es.udc.stembach.backend.rest.common.ErrorsDto;
import es.udc.stembach.backend.rest.dtos.DefenseDto;
import es.udc.stembach.backend.rest.dtos.RecordFileDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static es.udc.stembach.backend.rest.dtos.DefenseConversor.toDefenseDto;
import static es.udc.stembach.backend.rest.dtos.RecordFileDtoConversor.toRecordFilesDto;
import static es.udc.stembach.backend.rest.dtos.UserConversor.toUDCTeacherSummaryDtos;

@RestController
@RequestMapping("/defenses")
public class DefenseController {

    private final static String INSTANCE_NOT_FOUND_CODE= "project.exceptions.InstanceNotFoundException";

    @Autowired
    private MessageSource messageSource;

    @Autowired
    UserService userService;

    @Autowired
    DefenseService defenseService;

    @ExceptionHandler(InstanceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorsDto handleInstanceNotFoundException(InstanceNotFoundException exception, Locale locale) {

        String errorMessage = messageSource.getMessage(INSTANCE_NOT_FOUND_CODE, new Object[] {exception.getName(), exception.getKey()},
                INSTANCE_NOT_FOUND_CODE, locale);

        return new ErrorsDto(errorMessage);
    }

    @PostMapping("/create/{id}")
    public DefenseDto createDefense(@PathVariable Long id,
                                    @RequestParam(value = "place", required = false) String place,
                                    @RequestParam(value = "date", required = false) String date,
                                    @RequestParam(value = "mark", required = false) String mark,
                                    @RequestParam(value = "observations", required = false) String observations,
                                    @RequestPart(value = "recordFiles", required = false)List<MultipartFile> recordFiles,
                                    @RequestParam(value = "teachers", required = false) List<Long> teacherIds) throws InstanceNotFoundException, IOException, DuplicateInstanceException {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        BigDecimal markAux = null;

        if (mark != null && !mark.isEmpty()) {
            // Convert the 'mark' string to BigDecimal
            markAux = new BigDecimal(mark);
        }


        Defense defense = defenseService.createDefense(id,
                        place != "" ? place : null,
                        date != "" ? LocalDateTime.parse(date, formatter) : null,
                        markAux,
                        observations != "" ? observations : null,
                teacherIds != null ? teacherIds : new ArrayList<>(),
                recordFiles != null ? recordFiles : new ArrayList<>()
        );

        DefenseDto defenseDto = toDefenseDto(defense);
        defenseDto.setRecordFileDtos(toRecordFilesDto(defenseService.findRecordFilesOfDefense(defenseDto.getId())));
        defenseDto.setTeacherSummaryDtos(toUDCTeacherSummaryDtos(defenseService.findTeachersOfDefense(defenseDto.getId())));
        return defenseDto;
    }

    @PostMapping("/editDefense/{id}")
    public DefenseDto updateDefense(@PathVariable Long id,
                                    @RequestParam(value = "place", required = false) String place,
                                    @RequestParam(value = "date", required = false) String date,
                                    @RequestParam(value = "mark", required = false) String mark,
                                    @RequestParam(value = "observations", required = false) String observations,
                                    @RequestPart(value = "filesToAdd", required = false)List<MultipartFile> recordFiles,
                                    @RequestParam(value = "filesToRemove", required = false)List<Long> filesToRemove,
                                    @RequestParam(value = "teachers", required = false) List<Long> teacherIds
                                    ) throws InstanceNotFoundException, IOException {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        BigDecimal markAux = null;

        if (mark != null && !mark.isEmpty()) {
            // Convert the 'mark' string to BigDecimal
            markAux = new BigDecimal(mark);
        }

        Defense defense = defenseService.updateDefense(
                new Defense(id,
                        place != "" ? place : null,
                        date != "" ? LocalDateTime.parse(date, formatter) : null,
                        markAux,
                        observations != "" ? observations : null
                        ), teacherIds, recordFiles, filesToRemove
        );

        DefenseDto defenseDto = toDefenseDto(defense);
        defenseDto.setRecordFileDtos(toRecordFilesDto(defenseService.findRecordFilesOfDefense(defenseDto.getId())));
        defenseDto.setTeacherSummaryDtos(toUDCTeacherSummaryDtos(defenseService.findTeachersOfDefense(defenseDto.getId())));
        return defenseDto;
    }

    @GetMapping("/defenseDetails/{id}")
    public ResponseEntity<DefenseDto> findDefenseDetails(@PathVariable Long id) throws InstanceNotFoundException {
        try {
            DefenseDto defenseDto = toDefenseDto(defenseService.findDefenseDetails(id));

            List<RecordFileDto> recordFileDtos = toRecordFilesDto(defenseService.findRecordFilesOfDefense(defenseDto.getId()));
            defenseDto.setRecordFileDtos(recordFileDtos);
            defenseDto.setTeacherSummaryDtos(toUDCTeacherSummaryDtos(defenseService.findTeachersOfDefense(defenseDto.getId())));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(defenseDto);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/checkIfExistsDefense")
    public Boolean checkIfExistsDefense(@RequestParam Long projectInstanceId){
        return defenseService.checkProjectDefense(projectInstanceId);
    }
}
