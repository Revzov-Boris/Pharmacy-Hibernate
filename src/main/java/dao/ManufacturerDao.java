package dao;

import entities.Manufacturer;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class ManufacturerDao {
    public List<Manufacturer> getAllManufacturers() {
        var factory = new Configuration()
                .addAnnotatedClass(Manufacturer.class)
                .buildSessionFactory();
        try (Session session = factory.openSession()) {
            return session.createQuery("FROM Manufacturer", Manufacturer.class).list();
        }
    }
}
