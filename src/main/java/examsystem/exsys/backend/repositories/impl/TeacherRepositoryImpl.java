package examsystem.exsys.backend.repositories.impl;

import examsystem.exsys.backend.Entities.Teacher;
import examsystem.exsys.backend.repositories.TeacherRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public class TeacherRepositoryImpl implements TeacherRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Teacher> findAll() {
        return entityManager.createQuery("select n from " + Teacher.class.getSimpleName() + " n").getResultList();
    }

    @Override
    public void save(Teacher teacher) {
        entityManager.persist(teacher);
    }

    @Override
    public void delete(int id) throws Exception {
        entityManager.remove(findById(id));

    }

    @Override
    public void update(Teacher teacher) throws Exception {
        entityManager.merge(teacher);
    }


    @Override
    public Teacher findById(int id) throws Exception {
        return entityManager.find(Teacher.class, id);
    }
}
