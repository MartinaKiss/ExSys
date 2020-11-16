package examsystem.exsys.Entities;

import examsystem.exsys.ExamElements.Exam;

import javax.persistence.*;

@NamedQueries({
        @NamedQuery(name = Teacher.FIND_ALL, query = "select n from Teacher n"),
        @NamedQuery(name = Teacher.FIND_BY_EMAIL, query = "select n from Teacher n where n.emailAddress=:emailAddress")
})

@Table
@Entity
public class Teacher {
    public static final String FIND_ALL = "Teacher.findAll";
    public static final String FIND_BY_EMAIL = "Teacher.findByEmail";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int teacherId;

    private String teacherFirstName;
    private String teacherLastName;
    private String teacherTitle;
    private String neptunCode;
    private String emailAddress;
    private String password;

    public Teacher() {
    }

    public String getTeacherFirstName() {
        return teacherFirstName;
    }

    public void setTeacherFirstName(String name) {
        this.teacherFirstName= name;
    }

    public String getTeacherLastName() {
        return teacherLastName;
    }

    public void setTeacherLastName(String teacherLastName) {
        this.teacherLastName = teacherLastName;
    }

    public String getTeacherTitle() {
        return teacherTitle;
    }

    public void setTeacherTitle(String teacherTitle) {
        this.teacherTitle = teacherTitle;
    }

    public String getNeptunCode() {
        return neptunCode;
    }

    public void setNeptunCode(String neptunCode) {
        this.neptunCode = neptunCode;
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
