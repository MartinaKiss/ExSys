package examsystem.exsys.backend.repositories.impl;

import examsystem.exsys.backend.ExamElements.ExamResult;
import examsystem.exsys.backend.repositories.ResultRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public class ResultRepositoryImpl implements ResultRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ExamResult> findAll() {
        return entityManager.createQuery("select n from " + ExamResult.class.getSimpleName() + " n").getResultList();
    }

    @Override
    public void save(ExamResult result) {
        entityManager.persist(result);
    }

    @Override
    public void delete(int id) throws Exception {
        entityManager.remove(findById(id));

    }

    @Override
    public void update(ExamResult result) throws Exception {
        entityManager.merge(result);
    }


    @Override
    public ExamResult findById(int id) throws Exception {
        return entityManager.find(ExamResult.class, id);
    }

    public List<ExamResult> findAllByExamId(int id) throws Exception {
        Query query = entityManager.createNamedQuery(ExamResult.FIND_BY_EXAM_ID);
        query.setParameter("examId",id);
        return query.getResultList();
    }
}
