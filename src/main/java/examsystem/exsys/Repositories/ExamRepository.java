package examsystem.exsys.Repositories;

import examsystem.exsys.ExamElements.Exam;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public class ExamRepository {

        @PersistenceContext
        private EntityManager entityManager;

        public List<Exam> findAll() {
            return entityManager.createNamedQuery(Exam.FIND_ALL).getResultList();
        }

        public void save(Exam exam) {
            entityManager.persist(exam);
        }

        public void delete(Exam exam) throws Exception {
            entityManager.remove(findById(exam.getExamId()));

        }

        public void update(Exam exam) {
            entityManager.merge(exam);
        }

        public Exam findById(int id) {
            return entityManager.find(Exam.class, id);
        }

        public Exam findByEnterExamCode(String enterExamCode) {
            Query query = entityManager.createNamedQuery(Exam.FIND_BY_ENTER_EXAM_CODE);
            query.setParameter("enterExamCode", enterExamCode);
            return (Exam) query.getSingleResult();


        }

        public List<Exam> findAllByTeacherId(int id) {
            Query query = entityManager.createNamedQuery(Exam.FIND_BY_TEACHER_ID);
            query.setParameter("teacherId", id);
            System.out.println(query.getParameters());
            return query.getResultList();
        }
}
