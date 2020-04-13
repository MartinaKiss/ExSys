package examsystem.exsys.backend.repositories.impl;

import examsystem.exsys.backend.ExamElements.Exam;
import examsystem.exsys.backend.repositories.ExamRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public class ExamRepositoryImpl implements ExamRepository {

        @PersistenceContext
        private EntityManager entityManager;

        @Override
        public List<Exam> findAll() {
            return entityManager.createQuery("select n from " + Exam.class.getSimpleName() + " n").getResultList();
        }

        @Override
        public void save(Exam exam) {
            entityManager.persist(exam);
        }

        @Override
        public void delete(int id) throws Exception {
            entityManager.remove(findById(id));

        }

        @Override
        public void update(Exam exam) throws Exception {
            entityManager.merge(exam);
        }


        @Override
        public Exam findById(int id) throws Exception {
            return entityManager.find(Exam.class, id);
        }

        public List<Exam> findAllByTeacherId(int id) throws Exception {
            Query query = entityManager.createNamedQuery(Exam.FIND_BY_TEACHER_ID);
            query.setParameter("teacherId",id);
            return query.getResultList();
        }
}
