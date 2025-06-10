package dao;

import entities.Worker;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class WorkerDao {
    public List<Worker> getAllWorkers() {
        var factory = new Configuration().addAnnotatedClass(Worker.class).buildSessionFactory();
        try (Session session = factory.openSession()) {
            return session.createQuery("FROM Worker", Worker.class).list();
        }
    }
}
