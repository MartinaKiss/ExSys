package examsystem.exsys.backend.repositories;

import examsystem.exsys.backend.ExamElements.Question;

import java.util.List;

public interface QuestionRepository {

    List<Question> findAll();

    void save(Question question);

    void update(Question question) throws Exception;

    void delete(int id) throws Exception;

    Question findById(int id) throws Exception;

    List<Question> findAllByExamId(int id) throws Exception;


}
