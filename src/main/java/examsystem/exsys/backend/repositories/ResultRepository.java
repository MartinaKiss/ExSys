package examsystem.exsys.backend.repositories;

import examsystem.exsys.backend.ExamElements.ExamResult;

import java.util.List;

public interface ResultRepository {

    List<ExamResult> findAll();

    void save(ExamResult result);

    void update(ExamResult result) throws Exception;

    void delete(int id) throws Exception;

    ExamResult findById(int id) throws Exception;

    List<ExamResult> findAllByExamId(int id) throws Exception;
}
