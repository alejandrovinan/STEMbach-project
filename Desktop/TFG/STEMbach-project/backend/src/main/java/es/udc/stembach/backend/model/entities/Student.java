package es.udc.stembach.backend.model.entities;

import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Setter
public class Student extends User{

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name="studentGroupId")
    private StudentGroup studentGroup;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name="schoolId")
    private School school;
    private String dni;

    public Student() {
    }

    public Student(String name, String surname, String secondSurname, RoleType role, StudentGroup studentGroup, School school, String dni) {
        super(name, surname, secondSurname, role);
        this.studentGroup = studentGroup;
        this.school = school;
        this.dni = dni;
    }

    public StudentGroup getStudentGroup() {
        return studentGroup;
    }

    public School getSchool() {
        return school;
    }

    public String getDni() {
        return dni;
    }
}
