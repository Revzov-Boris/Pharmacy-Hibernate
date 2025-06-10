package dao;

import entities.Pharmacy;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class PharmacyDao {
    public List<Pharmacy> getAllPharmacys() {
        var factory = new Configuration().addAnnotatedClass(Pharmacy.class).buildSessionFactory();
        try (Session session = factory.openSession()) {
            return session.createQuery("FROM Pharmacy", Pharmacy.class).list();
        }
    }
}
