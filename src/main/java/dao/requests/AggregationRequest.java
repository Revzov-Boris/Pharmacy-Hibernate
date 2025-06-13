package dao.requests;

import entities.*;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

import java.math.BigDecimal;
import java.util.List;

public class AggregationRequest {
    public static BigDecimal avgCheque(String cityName) {
        var factory = new Configuration()
                .addAnnotatedClass(Cheque.class)
                .addAnnotatedClass(Position.class)
                .addAnnotatedClass(Medicine.class)
                .addAnnotatedClass(Bayer.class)
                .addAnnotatedClass(Pharmacy.class)
                .buildSessionFactory();
        String hqlForCount = """
                SELECT c 
                FROM Cheque c
                WHERE c.pharmacy.address LIKE :cityName
                """;

        String sql = """
        SELECT AVG(sum_price) 
        FROM (
            SELECT c.id, SUM(m.price * p.quantity) as sum_price
            FROM cheques c
            JOIN positions p ON p.cheque_id = c.id
            JOIN medicines m ON p.medicine_id = m.id
            JOIN pharmacys ph ON c.pharmacy_id = ph.id
            WHERE ph.address LIKE :cityName
            GROUP BY c.id
        ) 
        """;
        try (Session session = factory.openSession()){
            List<Cheque> cheques = session.createQuery(hqlForCount, Cheque.class).setParameter("cityName", "%г. " + cityName + "%").getResultList();
            if (cheques.isEmpty()) {
                return BigDecimal.valueOf(0L);
            }
            BigDecimal avg = session.createNativeQuery(sql, BigDecimal.class).setParameter("cityName","%г. " + cityName + "%").uniqueResult();
            return avg;
        }

    }


    public static Double avgTimeOfManufacturer(Long manufacturerId) {
        var factory = new Configuration()
                .addAnnotatedClass(Order.class)
                .addAnnotatedClass(Fullfild.class)
                .addAnnotatedClass(Medicine.class)
                .addAnnotatedClass(Worker.class)
                .addAnnotatedClass(Pharmacy.class)
                .addAnnotatedClass(Manufacturer.class)
                .buildSessionFactory();
        String sql = """
                SELECT AVG(fulfilleds.date - orders.date) 
                FROM orders 
                    JOIN fulfilleds ON orders.id = fulfilleds.id
                WHERE orders.manufacturer_id = :manufacturerId
                """;
        String hqlForCount = """
                SELECT o.id
                FROM Order o, Manufacturer m
                WHERE o.manufacturer.id = m.id
                    AND m.id = :manufacturerId
                """;
        try (Session session = factory.openSession()) {
            List<Long> ordersId = session.createQuery(hqlForCount, Long.class).setParameter("manufacturerId", manufacturerId).getResultList();
            if (ordersId.isEmpty()) {
                return 0.0;
            }
            return session.createNativeQuery(sql, Double.class).setParameter("manufacturerId", manufacturerId).uniqueResult();
        }
    }


    public static BigDecimal revenueForDietarySupplements(Long pharmacyId) {
        var factory = new Configuration()
                .addAnnotatedClass(Cheque.class)
                .addAnnotatedClass(Position.class)
                .addAnnotatedClass(Medicine.class)
                .addAnnotatedClass(Pharmacy.class)
                .addAnnotatedClass(Bayer.class)
                .buildSessionFactory();
        String hql = """
                	SELECT SUM(m.price * p.quantity)
                	FROM Cheque c, Position p, Medicine m
                	WHERE p.cheque.id = c.id
                        AND p.medicine.id = m.id
                        AND m.isDietarySupplements
                        AND c.pharmacy.id = :pharmacyId
                """;
        try (Session session = factory.openSession()) {
            BigDecimal result = session.createQuery(hql, BigDecimal.class).setParameter("pharmacyId", pharmacyId).uniqueResult();
            if (result == null) return BigDecimal.valueOf(0.0);
            return session.createQuery(hql, BigDecimal.class).setParameter("pharmacyId", pharmacyId).uniqueResult();
        }
    }
}
