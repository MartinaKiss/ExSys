package examsystem.exsys.Entities;

import javax.persistence.*;

@NamedQueries({
        @NamedQuery(name = Question.FIND_ALL, query = "select n from Question n"),
        @NamedQuery(name = Question.FIND_BY_EXAM_ID, query = "select n from Question n where n.exam.exam_Id=:examId")
})

@Entity
@Table
public class Question{

    public static final String FIND_ALL = "Question.findAll";
    public static final String FIND_BY_EXAM_ID = "Question.findAllByExamId";

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    protected int question_Id;

    @ManyToOne
    @JoinColumn(name="exam_id")
    private Exam exam;

    private String questionText;
    private double attainablePoints;
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;
    private String solution;

    public Question() {
    }

    public int getQuestionId() {
        return question_Id;
    }

    public void setQuestionId(int questionId) {
        this.question_Id = questionId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public double getAttainablePoints() {
        return attainablePoints;
    }

    public void setAttainablePoints(double attainablePoints) {
        this.attainablePoints = attainablePoints;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    public String getAnswer4() {
        return answer4;
    }

    public void setAnswer4(String answer4) {
        this.answer4 = answer4;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

}
