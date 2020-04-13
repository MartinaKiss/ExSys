package examsystem.exsys.backend.ExamElements;

import javax.persistence.*;
import java.util.List;

@NamedQueries({
        @NamedQuery(name = Question.FIND_ALL, query = "select n from Question n"),
        @NamedQuery(name = Question.FIND_BY_EXAM_ID, query = "select n from Question n where n.exam.exam_Id=:examId")
})

@Entity
@Table
public class Question{

    public static final String FIND_ALL = "Question.findAll";
    public static final String FIND_BY_EXAM_ID = "Question.findAllByExamId";

    @GeneratedValue
    @Id
    protected int question_Id;

    @ManyToOne
    @JoinColumn(name="exam_id")
    private Exam exam;

    private String questionText;
    private int attainablePoints;

    @OneToMany
    @JoinColumn(name="answer_id")
    private List<Answer> answers;

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

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public int getAttainablePoints() {
        return attainablePoints;
    }

    public void setAttainablePoints(int attainablePoints) {
        this.attainablePoints = attainablePoints;
    }
}
