package com.example.rest.db;

import com.example.rest.api.Task;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class TaskDAO extends AbstractDAO<Task> {

    public TaskDAO(SessionFactory factory) {
        super(factory);
    }

    public Optional<Task> findById(Long id) {
        TypedQuery query = currentSession().getNamedQuery("findTaskById");
        query.setParameter("id", id);
        List<Task> tasks = query.getResultList();
        Iterator<Task> itr = tasks.iterator();
        if (itr.hasNext()) return Optional.ofNullable(itr.next());
        else return Optional.ofNullable(null);
    }

    public List<Task> findAll() {
        EntityManager em = currentSession().getSessionFactory().createEntityManager();
        Query query = em.createQuery("SELECT t from Task t");
        List<Task> tasks = query.getResultList();
        return tasks;
    }

    public Task createOrReplace(final Task task) {

        if (isPresent(Long.valueOf(task.getId()))) {
            return updateTask(task);
        } else {
            return createTask(task);
        }


    }


    public Task updateTask(Task task) {

        Task foundTask = findById(Long.valueOf(task.getId())).orElse(new Task());
        updateTask(foundTask, task);

        EntityManager em = currentSession().getSessionFactory().createEntityManager();
        EntityTransaction Tx = em.getTransaction();
        Tx.begin();
        currentSession().update(foundTask);
        Tx.commit();

        return foundTask;
    }

    public Task createTask(final Task task) {

        Task newTask = new Task();
        updateTask(newTask, task);

        EntityManager em = currentSession().getSessionFactory().createEntityManager();
        EntityTransaction Tx = em.getTransaction();

        Tx.begin();
        em.persist(newTask);
        Tx.commit();

        return newTask;
    }

    public Integer deleteTask(final Long id) {

        Optional<Task> foundTask = findById(id);

        if (foundTask.isPresent()) {
            EntityManager em = currentSession().getSessionFactory().createEntityManager();
            EntityTransaction Tx = em.getTransaction();
            Tx.begin();
            TypedQuery query = currentSession().getNamedQuery("deleteTaskById");
            query.setParameter("id", id);
            query.executeUpdate();
            //currentSession().delete(foundTask);
            Tx.commit();
            return 1;
        }
        return 0;
    }

    private void updateTask(Task foundTask, final Task task) {
        foundTask.setDescription(task.getDescription());
        foundTask.setChecked(null == task.getChecked() ? "N" : task.getChecked() );
        foundTask.setDate(task.getDate());
    }

    private Boolean isPresent(final Long id) {
        if (id != 0) {
            Optional<Task> foundTask = findById(id);
            return foundTask.isPresent();
        } else return Boolean.FALSE;
    }
}