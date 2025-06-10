package dao;

import entities.*;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class OrderDao {
    public List<Order> getAllOrders() {
        var factory = new Configuration()
                .addAnnotatedClass(Order.class)
                .addAnnotatedClass(Manufacturer.class)
                .addAnnotatedClass(Pharmacy.class)
                .addAnnotatedClass(Worker.class)
                .addAnnotatedClass(Medicine.class)
                .buildSessionFactory();
        try (Session session = factory.openSession()) {
            return session.createQuery("FROM Order", Order.class).list();
        }
    }
}
