package dao.requests;

import entities.*;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

import java.math.BigDecimal;
import java.util.List;

public class BigOrdersRequest {
    public static List<Order> getOrders(Long manufacturerId, BigDecimal minPrice) {
        var factory = new Configuration()
                .addAnnotatedClass(Order.class)
                .addAnnotatedClass(Pharmacy.class)
                .addAnnotatedClass(Manufacturer.class)
                .addAnnotatedClass(Worker.class)
                .addAnnotatedClass(Medicine.class)
                .buildSessionFactory();
        try (Session session = factory.openSession()) {
            String query = """
                SELECT o
                FROM Order o
                WHERE o.manufacturer.id = :manufacturerId
                    AND o.priceForOne * o.quantity > :minPrice
                """;
            return session.createQuery(query, Order.class)
                    .setParameter("manufacturerId", manufacturerId)
                    .setParameter("minPrice", minPrice)
                    .getResultList();
        }
    }
}
