package examsystem.exsys.backend.repositories;

import examsystem.exsys.backend.ExamElements.Exam;

import java.util.List;

public interface ExamRepository {
    
    List<Exam> findAll();

    void save(Exam exam);

    void update(Exam exam) throws Exception;

    void delete(int id) throws Exception;

    Exam findById(int id) throws Exception;

    List<Exam> findAllByTeacherId(int id) throws Exception;
}
