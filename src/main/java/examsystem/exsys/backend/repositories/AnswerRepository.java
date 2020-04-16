package examsystem.exsys.backend.repositories;

import examsystem.exsys.backend.ExamElements.Answer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public class AnswerRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public List<Answer> findAll() {
        return entityManager.createQuery(Answer.FIND_ALL).getResultList();
    }

    public void save(Answer answer) {
        entityManager.persist(answer);
    }

    public void delete(int id) throws Exception {
        entityManager.remove(findById(id));

    }

    public void update(Answer answer) throws Exception {
        entityManager.merge(answer);
    }


    public Answer findById(int id) throws Exception {
        return entityManager.find(Answer.class, id);
    }

    public List<Answer> findAllByQuestionId(int id) throws Exception {
        Query query = entityManager.createNamedQuery(Answer.FIND_BY_QUESTION_ID);
        query.setParameter("question_Id",id);
        return query.getResultList();
    }
}
