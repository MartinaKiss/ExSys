package examsystem.exsys.backend.repositories.impl;

import examsystem.exsys.backend.ExamElements.Question;
import examsystem.exsys.backend.repositories.QuestionRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public class QuestionRepositoryImpl implements QuestionRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Question> findAll() {
        return entityManager.createQuery("select n from " + Question.class.getSimpleName() + " n").getResultList();
    }

    @Override
    public void save(Question question) {
        entityManager.persist(question);
    }

    @Override
    public void delete(int id) throws Exception {
        entityManager.remove(findById(id));

    }

    @Override
    public void update(Question question) throws Exception {
        entityManager.merge(question);
    }


    @Override
    public Question findById(int id) throws Exception {
        return entityManager.find(Question.class, id);
    }

    public List<Question> findAllByExamId(int id) throws Exception {
        Query query = entityManager.createNamedQuery(Question.FIND_BY_EXAM_ID);
        query.setParameter("examId",id);
        return query.getResultList();
    }
}
