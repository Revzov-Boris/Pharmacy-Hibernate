package dao;

import entities.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrderDao {
    private SessionFactory buildSessionFactory() {
        return new Configuration()
                .addAnnotatedClass(Order.class)
                .addAnnotatedClass(Fullfild.class)
                .addAnnotatedClass(Manufacturer.class)
                .addAnnotatedClass(Medicine.class)
                .addAnnotatedClass(Pharmacy.class)
                .addAnnotatedClass(Worker.class)
                .buildSessionFactory();
    }


    public List<Order> getAllOrders() {
        var factory = buildSessionFactory();
        try (Session session = factory.openSession()) {
            return session.createQuery("FROM Order", Order.class).list();
        }
    }

    // удаляет невыполненные закупки производителя с id = manufacturerId
    public boolean removeUnfulfilledOrders(Long manufacturerId) {
        try (var factory = buildSessionFactory()){
            List<Order> unfulfilledOrders;
            try {
                unfulfilledOrders = getUnfulfilledOrders(manufacturerId);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
                return false;
            }

            if (!unfulfilledOrders.isEmpty()) {
                try (Session session = factory.openSession()) {
                    // либо удалятся все, либо ни одна
                    session.beginTransaction();
                    for (Order order : unfulfilledOrders) {
                        session.remove(order);
                    }
                    session.getTransaction().commit();
                    return true;
                }
            }
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return false;
        }
    }


    public List<Order> getUnfulfilledOrders(Long manufacturerId) {
        String hql = """
                    SELECT o
                    FROM Order o
                        LEFT JOIN Fullfild f ON o.id = f.id
                    WHERE f.id IS NULL
                        AND o.manufacturer.id = :manufacturerId
                """;
        List<Order> unfulfilledOrders = new ArrayList<>();
        try (Session session = buildSessionFactory().openSession()) {
            unfulfilledOrders = session.createQuery(hql, Order.class).
                    setParameter("manufacturerId", manufacturerId)
                    .getResultList();
            return unfulfilledOrders;
        } catch (Exception e) {
            // возвращает пустой список в случае ошибки
            return unfulfilledOrders;
        }
    }
}
