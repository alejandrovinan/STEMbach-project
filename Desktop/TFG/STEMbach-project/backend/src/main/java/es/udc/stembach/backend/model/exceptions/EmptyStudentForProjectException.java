package es.udc.stembach.backend.model.exceptions;

public class EmptyStudentForProjectException extends Exception{

    private Long projectInstanceId;

    public EmptyStudentForProjectException(Long projectInstanceId) {
        this.projectInstanceId = projectInstanceId;
    }

    public Long getProjectInstanceId() {
        return projectInstanceId;
    }

    public void setProjectInstanceId(Long projectInstanceId) {
        this.projectInstanceId = projectInstanceId;
    }
}
