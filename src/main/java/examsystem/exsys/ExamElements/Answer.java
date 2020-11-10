package examsystem.exsys.ExamElements;


import javax.persistence.*;

@NamedQueries({
        @NamedQuery(name = Answer.FIND_ALL, query = "select n from Answer n"),
        @NamedQuery(name = Answer.FIND_BY_QUESTION_ID, query = "select n from Answer n where n.question.question_Id=:equestionId")
})

@Entity
@Table
public class Answer {

    public static final String FIND_ALL = "Answer.findAll";
    public static final String FIND_BY_QUESTION_ID = "Answer.findAllByQuestionId";

    @GeneratedValue
    @Id
    private int answer_Id;
    private String answerText;
    private boolean isSolution;

    @ManyToOne
    @JoinColumn(name="question_id")
    private Question question;

    public Answer() {
    }

    public int getAnswerId() {
        return answer_Id;
    }

    public void setAnswerId(int answerId) {
        this.answer_Id = answerId;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public boolean isSolution() {
        return isSolution;
    }

    public void setSolution(boolean solution) {
        isSolution = solution;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
