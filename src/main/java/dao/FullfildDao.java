package dao;

import entities.*;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class FullfildDao {
    public List<Fullfild> getAllFullfild() {
        var factory = new Configuration()
                .addAnnotatedClass(Order.class)
                .addAnnotatedClass(Fullfild.class)
                .addAnnotatedClass(Medicine.class)
                .addAnnotatedClass(Pharmacy.class)
                .addAnnotatedClass(Worker.class)
                .addAnnotatedClass(Manufacturer.class)
                .buildSessionFactory();
        try (Session session = factory.openSession()) {
            return session.createQuery("FROM Fullfild", Fullfild.class).list();
        }
    }
}
