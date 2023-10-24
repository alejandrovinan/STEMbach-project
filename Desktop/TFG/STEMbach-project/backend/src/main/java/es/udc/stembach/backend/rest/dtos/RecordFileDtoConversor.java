package es.udc.stembach.backend.rest.dtos;

import es.udc.stembach.backend.model.entities.RecordFile;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

public class RecordFileDtoConversor {

    private final static long toMillis(LocalDateTime date) {
        return date.truncatedTo(ChronoUnit.MINUTES).atZone(ZoneOffset.systemDefault()).toInstant().toEpochMilli();
    }

    public final static RecordFileDto toRecordFileDto(RecordFile recordFile){
        return new RecordFileDto(recordFile.getId(), recordFile.getFileName(), recordFile.getFileData(), recordFile.getFileSize(), toMillis(recordFile.getUploadDate()));
    }

    public final static List<RecordFileDto> toRecordFilesDto(List<RecordFile> recordFiles){
        return recordFiles.stream().map(r -> toRecordFileDto(r)).collect(Collectors.toList());
    }
}
