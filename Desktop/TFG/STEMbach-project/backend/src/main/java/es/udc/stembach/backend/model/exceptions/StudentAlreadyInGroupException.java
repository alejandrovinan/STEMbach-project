package es.udc.stembach.backend.model.exceptions;

public class StudentAlreadyInGroupException extends Exception{

    private Long studentId;

    public StudentAlreadyInGroupException(Long studentId) {
        this.studentId = studentId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
}
