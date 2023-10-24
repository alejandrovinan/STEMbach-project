package es.udc.stembach.backend.model.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class RecordFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;
    @Lob
    private byte[] fileData;
    private long fileSize;
    private LocalDateTime uploadDate;
    private String fileType;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "defenseId")
    private Defense defense;

    public RecordFile() {
    }

    public RecordFile(String fileName, byte[] fileData, long fileSize, LocalDateTime uploadDate, String fileType, Defense defense) {
        this.fileName = fileName;
        this.fileData = fileData;
        this.fileSize = fileSize;
        this.uploadDate = uploadDate;
        this.fileType = fileType;
        this.defense = defense;
    }

    public RecordFile(Long id, String fileName, byte[] fileData, long fileSize, LocalDateTime uploadDate, String fileType, Defense defense) {
        this.id = id;
        this.fileName = fileName;
        this.fileData = fileData;
        this.fileSize = fileSize;
        this.uploadDate = uploadDate;
        this.fileType = fileType;
        this.defense = defense;
    }
}
