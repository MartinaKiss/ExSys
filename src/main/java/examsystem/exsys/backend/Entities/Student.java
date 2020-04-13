package examsystem.exsys.backend.Entities;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class Student {
    @Id
    @GeneratedValue
    private int studentId;
    private String studentName;
    private String neptunCode;
    private String emailAddress;


    public Student() {
    }

    public String getName() {
        return studentName;
    }

    public void setName(String name) {
        this.studentName = name;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getNeptunCode() {
        return neptunCode;
    }

    public void setNeptunCode(String neptunCode) {
        this.neptunCode = neptunCode;
    }
}

