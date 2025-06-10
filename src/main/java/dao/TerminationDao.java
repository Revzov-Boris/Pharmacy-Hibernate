package dao;

import entities.Contract;
import entities.Pharmacy;
import entities.Termination;
import entities.Worker;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class TerminationDao {
    public List<Termination> getAllTerminations() {
        var factory = new Configuration()
                .addAnnotatedClass(Contract.class)
                .addAnnotatedClass(Termination.class)
                .addAnnotatedClass(Pharmacy.class)
                .addAnnotatedClass(Worker.class)
                .buildSessionFactory();
        try (Session session = factory.openSession()) {
            return session.createQuery("FROM Termination", Termination.class).list();
        }
    }
}
