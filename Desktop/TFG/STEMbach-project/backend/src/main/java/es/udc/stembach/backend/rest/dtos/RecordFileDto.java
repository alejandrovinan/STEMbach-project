package es.udc.stembach.backend.rest.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecordFileDto {
    private Long id;
    private String fileName;
    private String fileData;
    private long fileSize;
    private Long uploadDate;
    private String fileType;

    public RecordFileDto() {
    }

    public RecordFileDto(Long id, String fileName, String fileData, long fileSize, Long uploadDate, String fileType) {
        this.id = id;
        this.fileName = fileName;
        this.fileData = fileData;
        this.fileSize = fileSize;
        this.uploadDate = uploadDate;
        this.fileType = fileType;
    }
}
