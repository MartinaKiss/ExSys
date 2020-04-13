package examsystem.exsys.backend.ExamElements;

import java.util.List;

public class ExamSheet {
    private String examName;
    private String subject;
    private String teacherId;
    private String studentId;
    private int examSheetId;
    private List<Question> questions;

    public ExamSheet(String examName, String subject, String teacherId, String studentId, int examSheetId,
                     List<Question> questions) {
        this.examName = examName;
        this.subject = subject;
        this.teacherId = teacherId;
        this.studentId = studentId;
        this.examSheetId = examSheetId;
        this.questions = questions;
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

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public int getExamSheetId() {
        return examSheetId;
    }

    public void setExamSheetId(int examSheetId) {
        this.examSheetId = examSheetId;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
