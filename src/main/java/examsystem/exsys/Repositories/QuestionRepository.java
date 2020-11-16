package examsystem.exsys.Repositories;

import examsystem.exsys.ExamElements.Question;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public class QuestionRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public List<Question> findAll() {
        return entityManager.createQuery("select n from " + Question.class.getSimpleName() + " n").getResultList();
    }

    public void save(Question question) {
        entityManager.persist(question);
    }

    public void delete(int id) throws Exception {
        entityManager.remove(findById(id));

    }

    public void update(Question question) {
        entityManager.merge(question);
    }


    public Question findById(int id) {
        return entityManager.find(Question.class, id);
    }

    public List<Question> findAllByExamId(int id) {
        Query query = entityManager.createNamedQuery(Question.FIND_BY_EXAM_ID);
        query.setParameter("examId",id);
        return query.getResultList();
    }
}
