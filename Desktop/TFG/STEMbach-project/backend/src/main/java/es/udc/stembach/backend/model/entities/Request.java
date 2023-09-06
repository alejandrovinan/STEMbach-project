package es.udc.stembach.backend.model.entities;

import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Setter
public class Request {

    public enum requestStatus {PENDING, ASSIGNED, REVOKED};

    private Long id;
    private LocalDateTime requestDate;
    private requestStatus status;
    private StudentGroup studentGroup;
    private Project project;

    public Request() {
    }

    public Request(LocalDateTime requestDate, requestStatus status, StudentGroup studentGroup, Project project) {
        this.requestDate = requestDate;
        this.status = status;
        this.studentGroup = studentGroup;
        this.project = project;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public LocalDateTime getRequestDate() {
        return requestDate;
    }

    public requestStatus getStatus() {
        return status;
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "studentGroupId")
    public StudentGroup getStudentGroup() {
        return studentGroup;
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "projectId")
    public Project getProject() {
        return project;
    }
}
