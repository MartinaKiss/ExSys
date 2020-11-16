package examsystem.exsys.Repositories;

import examsystem.exsys.Entities.Teacher;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public class TeacherRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Teacher> findAll() {
        return entityManager.createNamedQuery(Teacher.FIND_ALL).getResultList();
    }

    public void save(Teacher teacher) {
        entityManager.persist(teacher);
    }

    public void delete(Teacher teacher) {
        entityManager.remove(findById(teacher.getTeacherId()));

    }

    public void update(Teacher teacher) {
        entityManager.merge(teacher);
    }

    public Teacher findById(int id) {
        return entityManager.find(Teacher.class, id);
    }

    public Teacher findByEmail(String emailAddress){
        Query query = entityManager.createNamedQuery(Teacher.FIND_BY_EMAIL);
        query.setParameter("emailAddress", emailAddress);
        return (Teacher) query.getSingleResult();
    }
}
