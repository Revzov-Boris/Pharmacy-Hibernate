package dao;

import entities.*;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class PositionDao {
    public List<Position> getAllPositions() {
        var factory = new Configuration()
                .addAnnotatedClass(Position.class)
                .addAnnotatedClass(Cheque.class)
                .addAnnotatedClass(Medicine.class)
                .addAnnotatedClass(Bayer.class)
                .addAnnotatedClass(Pharmacy.class)
                .buildSessionFactory();
        try (Session session = factory.openSession()) {
            return session.createQuery("FROM Position", Position.class).list();
        }
    }
}
