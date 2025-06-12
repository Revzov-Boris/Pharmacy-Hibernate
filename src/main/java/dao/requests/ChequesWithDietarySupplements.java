package dao.requests;

import entities.*;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class ChequesWithDietarySupplements {
    public static List<Cheque> getCheque() {
        var factory = new Configuration()
                .addAnnotatedClass(Cheque.class)
                .addAnnotatedClass(Position.class)
                .addAnnotatedClass(Medicine.class)
                .addAnnotatedClass(Bayer.class)
                .addAnnotatedClass(Pharmacy.class)
                .buildSessionFactory();
        try (Session session = factory.openSession()){
            String query = """
                SELECT c
                FROM Cheque c, Position p, Medicine m
                WHERE p.cheque.id = c.id
                AND p.medicine.id = m.id
                AND m.isDietarySupplements = TRUE
                """;
            return session.createQuery(query, Cheque.class)
                    .getResultList();
        }
    }
}
