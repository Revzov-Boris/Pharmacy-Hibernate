package dao;

import entities.*;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class ChequeDao {
    public List<Cheque> getAllCheques() {
        var factory = new Configuration()
                .addAnnotatedClass(Cheque.class)
                .addAnnotatedClass(Pharmacy.class)
                .addAnnotatedClass(Bayer.class)
                .buildSessionFactory();
        try (Session session = factory.openSession()) {
            return session.createQuery("FROM Cheque", Cheque.class).list();
        }
    }
}
