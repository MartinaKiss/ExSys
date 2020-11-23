package examsystem.exsys.Entities;

import elemental.json.JsonArray;
import elemental.json.JsonObject;
import org.json.JSONObject;

import javax.persistence.*;
import java.time.LocalDateTime;

@NamedQueries({
        @NamedQuery(name = ExamResult.FIND_ALL, query = "select n from ExamResult n"),
        @NamedQuery(name = ExamResult.FIND_BY_EXAM_ID, query = "select n from ExamResult n where n.exam.exam_Id=:examId")
})

@Entity
@Table
public class ExamResult {

    public static final String FIND_ALL = "ExamResult.findAll";
    public static final String FIND_BY_EXAM_ID = "ExamResult.findAllByExamId";

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int examResultId;
    private String examName;
    private String subject;
    private int teacherId;
    private String studentName;
    private String studentNeptun;
    private String studentEmail;
    private double sumOfMaxPoints;
    private double sumOfAttainedPoints;
    private int attainedGrade;
    private LocalDateTime timeOfSubmission;
    private String answersList;

    @ManyToOne
    @JoinColumn(name="exam_id")
    private Exam exam;

    public ExamResult() {
    }

    public int getExamResultId() {
        return examResultId;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentNeptun() {
        return studentNeptun;
    }

    public void setStudentNeptun(String studentNeptune) {
        this.studentNeptun = studentNeptune;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public double getSumOfMaxPoints() {
        return sumOfMaxPoints;
    }

    public void setSumOfMaxPoints(double sumOfMaxPoints) {
        this.sumOfMaxPoints = sumOfMaxPoints;
    }

    public double getSumOfAttainedPoints() {
        return sumOfAttainedPoints;
    }

    public void setSumOfAttainedPoints(double sumOfAttainedPoints) {
        this.sumOfAttainedPoints = sumOfAttainedPoints;
    }

    public int getAttainedGrade() {
        return attainedGrade;
    }

    public void setAttainedGrade(int attainedGrade) {
        this.attainedGrade = attainedGrade;
    }

    public LocalDateTime getTimeOfSubmission() {
        return timeOfSubmission;
    }

    public void setTimeOfSubmission(LocalDateTime timeOfSubmission) {
        this.timeOfSubmission = timeOfSubmission;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public String getAnswersList() {
        return answersList;
    }

    public void setAnswersList(String answersList) {
        this.answersList = answersList;
    }
}
