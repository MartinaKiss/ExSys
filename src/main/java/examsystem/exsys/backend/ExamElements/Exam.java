package examsystem.exsys.backend.ExamElements;

import examsystem.exsys.backend.Entities.Teacher;

import javax.persistence.*;
import java.util.List;

@NamedQueries({
        @NamedQuery(name = Exam.FIND_ALL, query = "select n from Exam n"),
        @NamedQuery(name = Exam.FIND_BY_TEACHER_ID, query = "select n from Exam n where n.teacher.teacherId=:teacherId")
})

@Entity
@Table
public class Exam {

    public static final String FIND_ALL = "Exam.findAll";
    public static final String FIND_BY_TEACHER_ID = "Exam.findAllByTeacherId";

    @GeneratedValue
    @Id
    private int exam_Id;
    private String examName;
    private String subject;
    private String enterExamCode;
    private String description;

    @ManyToOne
    @JoinColumn(name="teacher_id")
    private Teacher teacher;

    private int gradeFivePointLimit;
    private int gradeFourPointLimit;
    private int gradeThreePointLimit;
    private int gradeTwoPointLimit;

    @OneToMany
    @JoinColumn(name="question_id")
    private List<Question> questions;

    private int numberOfQuestions;
    private int maxSumOfPoints;
    private boolean isWrongAnswerMinusPoint = false;
    private int valueOfMinusPoint = 0;

    @OneToMany()
    @JoinColumn(name="examresult_id")
    private List<ExamResult> completedExams;

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

    public int getMaxSumOfPoints() {
        return maxSumOfPoints;
    }

    public void setMaxSumOfPoints(int maxSumOfPoints) {
        this.maxSumOfPoints = maxSumOfPoints;
    }

    public int getGradeFivePointLimit() {
        return gradeFivePointLimit;
    }

    public void setGradeFivePointLimit(int gradeFivePointLimit) {
        this.gradeFivePointLimit = gradeFivePointLimit;
    }

    public int getGradeFourPointLimit() {
        return gradeFourPointLimit;
    }

    public void setGradeFourPointLimit(int gradeFourPointLimit) {
        this.gradeFourPointLimit = gradeFourPointLimit;
    }

    public int getGradeThreePointLimit() {
        return gradeThreePointLimit;
    }

    public void setGradeThreePointLimit(int gradeThreePointLimit) {
        this.gradeThreePointLimit = gradeThreePointLimit;
    }

    public int getGradeTwoPointLimit() {
        return gradeTwoPointLimit;
    }

    public void setGradeTwoPointLimit(int gradeTwoPointLimit) {
        this.gradeTwoPointLimit = gradeTwoPointLimit;
    }

    public boolean isWrongAnswerMinusPoint() {
        return isWrongAnswerMinusPoint;
    }

    public void setWrongAnswerMinusPoint(boolean wrongAnswerMinusPoint) {
        isWrongAnswerMinusPoint = wrongAnswerMinusPoint;
    }

    public int getValueOfMinusPoint() {
        return valueOfMinusPoint;
    }

    public void setValueOfMinusPoint(int valueOfMinusPoint) {
        this.valueOfMinusPoint = valueOfMinusPoint;
    }

    public void setExamId(int examId) {
        this.exam_Id = examId;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public List<ExamResult> getCompletedExam() {
        return completedExams;
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

    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(int numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

