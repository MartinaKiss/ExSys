package examsystem.exsys.backend.ExamElements;


import javax.persistence.*;

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
    private String teacherId;
    private String studentName;
    private String studentNeptun;
    private String studentEmail;
    private int sumOfMaxPoints;
    private int sumOfAttainedPoints;
    private int attainedGrade;

    @ManyToOne
    @JoinColumn(name="exam_id")
    private Exam exam;

    public ExamResult() {
    }

    public int getExamResultId() {
        return examResultId;
    }

    public void setExamResultId(int examResultId) {
        this.examResultId = examResultId;
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

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
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

    public int getSumOfMaxPoints() {
        return sumOfMaxPoints;
    }

    public void setSumOfMaxPoints(int sumOfMaxPoints) {
        this.sumOfMaxPoints = sumOfMaxPoints;
    }

    public int getSumOfAttainedPoints() {
        return sumOfAttainedPoints;
    }

    public void setSumOfAttainedPoints(int sumOfAttainedPoints) {
        this.sumOfAttainedPoints = sumOfAttainedPoints;
    }

    public int getAttainedGrade() {
        return attainedGrade;
    }

    public void setAttainedGrade(int attainedGrade) {
        this.attainedGrade = attainedGrade;
    }
}
