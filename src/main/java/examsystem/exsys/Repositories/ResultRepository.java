package examsystem.exsys.Repositories;

import examsystem.exsys.Entities.ExamResult;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public class ResultRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<ExamResult> findAll() {
        return entityManager.createQuery(ExamResult.FIND_ALL).getResultList();
    }

    public void save(ExamResult result) {
        entityManager.persist(result);
    }

    public void delete(int id) {
        entityManager.remove(findById(id));

    }

    public void update(ExamResult result) {
        entityManager.merge(result);
    }

    public ExamResult findById(int id) {
        return entityManager.find(ExamResult.class, id);
    }

    public List<ExamResult> findAllByExamId(int id) {
        Query query = entityManager.createNamedQuery(ExamResult.FIND_BY_EXAM_ID);
        query.setParameter("examId",id);
        return query.getResultList();
    }
}
