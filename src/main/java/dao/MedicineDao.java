package dao;

import entities.Medicine;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class MedicineDao {
    public List<Medicine> getAllMedicines() {
        var factory = new Configuration().addAnnotatedClass(Medicine.class).buildSessionFactory();
        try (Session session = factory.openSession()) {
            return session.createQuery("FROM Medicine", Medicine.class).list();
        }
    }
}
