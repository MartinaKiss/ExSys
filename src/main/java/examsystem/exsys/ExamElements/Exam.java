package examsystem.exsys.ExamElements;

import examsystem.exsys.Entities.Teacher;

import javax.persistence.*;

@NamedQueries({
        @NamedQuery(name = Exam.FIND_ALL, query = "select n from Exam n"),
        @NamedQuery(name = Exam.FIND_BY_TEACHER_ID, query = "select n from Exam n where n.teacher.teacherId=:teacherId"),
        @NamedQuery(name = Exam.FIND_BY_ENTER_EXAM_CODE, query = "select n from Exam n where n.enterExamCode=:enterExamCode")
})

@Entity
@Table
public class Exam {

    public static final String FIND_ALL = "Exam.findAll";
    public static final String FIND_BY_TEACHER_ID = "Exam.findAllByTeacherId";
    public static final String FIND_BY_ENTER_EXAM_CODE = "Exam.findByEnterExamCode";

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int exam_Id;
    private String examName;
    private String subject;

    private String enterExamCode;

    private String description;

    @ManyToOne
    @JoinColumn(name="teacher_id")
    private Teacher teacher;

    private double gradeFivePointLimit;
    private double gradeFourPointLimit;
    private double gradeThreePointLimit;
    private double gradeTwoPointLimit;
    private double maxSumOfPoints;
    private boolean isWrongAnswerMinusPoint = false;
    private double valueOfMinusPoint = 0;
    private boolean isExamActive = false;

    public Exam() {
    }

    public int getExamId() {
        return exam_Id;
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

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public double getMaxSumOfPoints() {
        return maxSumOfPoints;
    }

    public void setMaxSumOfPoints(double maxSumOfPoints) {
        this.maxSumOfPoints = maxSumOfPoints;
    }

    public double getGradeFivePointLimit() {
        return gradeFivePointLimit;
    }

    public void setGradeFivePointLimit(double gradeFivePointLimit) {
        this.gradeFivePointLimit = gradeFivePointLimit;
    }

    public double getGradeFourPointLimit() {
        return gradeFourPointLimit;
    }

    public void setGradeFourPointLimit(double gradeFourPointLimit) {
        this.gradeFourPointLimit = gradeFourPointLimit;
    }

    public double getGradeThreePointLimit() {
        return gradeThreePointLimit;
    }

    public void setGradeThreePointLimit(double gradeThreePointLimit) {
        this.gradeThreePointLimit = gradeThreePointLimit;
    }

    public double getGradeTwoPointLimit() {
        return gradeTwoPointLimit;
    }

    public void setGradeTwoPointLimit(double gradeTwoPointLimit) {
        this.gradeTwoPointLimit = gradeTwoPointLimit;
    }

    public boolean isWrongAnswerMinusPoint() {
        return isWrongAnswerMinusPoint;
    }

    public void setWrongAnswerMinusPoint(boolean wrongAnswerMinusPoint) {
        isWrongAnswerMinusPoint = wrongAnswerMinusPoint;
    }

    public double getValueOfMinusPoint() {
        return valueOfMinusPoint;
    }

    public void setValueOfMinusPoint(double valueOfMinusPoint) {
        this.valueOfMinusPoint = valueOfMinusPoint;
    }

    public void setExamId(int examId) {
        this.exam_Id = examId;
    }

    public static String getFindAll() {
        return FIND_ALL;
    }

    public static String getFindByTeacherId() {
        return FIND_BY_TEACHER_ID;
    }

    public String getEnterExamCode() {
        return enterExamCode;
    }

    public void setEnterExamCode(String enterExamCode) {
        this.enterExamCode = enterExamCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String isExamActive() {
//        return isExamActive;
        if (isExamActive){
            return "Aktív";
        }
        else{
            return "Inaktív";
        }
    }

    public void setExamActive(boolean examActive) {
        isExamActive = examActive;
    }
}

