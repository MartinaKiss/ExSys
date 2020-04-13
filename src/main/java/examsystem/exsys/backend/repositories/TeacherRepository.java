package examsystem.exsys.backend.repositories;

import examsystem.exsys.backend.Entities.Teacher;

import java.util.List;

public interface TeacherRepository {

    List<Teacher> findAll();

    void save(Teacher teacher);

    void update(Teacher teacher) throws Exception;

    void delete(int id) throws Exception;

    Teacher findById(int id) throws Exception;
}
