package es.udc.stembach.backend.model.exceptions;

public class MaxStudentsInProjectException extends Exception{

    private Long projectId;
    private Integer students;

    public MaxStudentsInProjectException(Long projectId, Integer students) {
        this.projectId = projectId;
        this.students = students;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Integer getStudents() {
        return students;
    }

    public void setStudents(Integer students) {
        this.students = students;
    }
}
