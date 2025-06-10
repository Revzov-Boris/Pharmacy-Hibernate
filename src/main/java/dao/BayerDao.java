package dao;

import entities.Bayer;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class BayerDao {

    public List<Bayer> getAllBayers() {
        var factory = new Configuration().addAnnotatedClass(Bayer.class).buildSessionFactory();
        try (Session session = factory.openSession()) {
            return session.createQuery("FROM Bayer", Bayer.class).list();
        }
    }

}
