package es.udc.stembach.backend.rest.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecordFileDto {
    private Long id;
    private String fileName;
    private byte[] fileData;
    private long fileSize;
    private Long uploadDate;

    public RecordFileDto() {
    }

    public RecordFileDto(Long id, String fileName, byte[] fileData, long fileSize, Long uploadDate) {
        this.id = id;
        this.fileName = fileName;
        this.fileData = fileData;
        this.fileSize = fileSize;
        this.uploadDate = uploadDate;
    }
}
