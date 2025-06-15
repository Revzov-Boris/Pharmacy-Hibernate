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

    public static boolean isThereBayer(long id) {
        var factory = new Configuration().addAnnotatedClass(Bayer.class).buildSessionFactory();
        try (Session session = factory.openSession()){
            Bayer bayer = session.get(Bayer.class, id);
            return bayer != null;
        }
    }

    public static Bayer getBayer(long id) {
        var factory = new Configuration().addAnnotatedClass(Bayer.class).buildSessionFactory();
        try (Session session = factory.openSession()){
            Bayer bayer = session.get(Bayer.class, id);
            return bayer;
        }
    }

}
