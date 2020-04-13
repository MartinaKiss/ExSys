package examsystem.exsys.backend.Entities;

import examsystem.exsys.backend.ExamElements.Exam;

import javax.persistence.*;
import java.util.List;

@NamedQuery(name = Teacher.FIND_ALL, query = "select n from Teacher n")

@Table
@Entity
public class Teacher {
    public static final String FIND_ALL = "Teacher.findAll";
    @Id
    @GeneratedValue
    private int teacherId;
    private String teacherName;
    private String neptunCode;
    private String emailAddress;

    @OneToMany
    @JoinColumn(name="exam_id")
    private List<Exam> exams;
    private String password;

    public Teacher() {
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String name) {
        this.teacherName= name;
    }

    public String getNeptunCode() {
        return neptunCode;
    }

    public void setNeptunCode(String neptunCode) {
        this.neptunCode = neptunCode;
    }

    public List<Exam> getExams() {
        return exams;
    }

    public void setExams(List<Exam> exams) {
        this.exams = exams;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
