package dao.requests;

import entities.*;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class CurrentContractsInPharmacyRequest {
    public static List<Contract> getCurrentContracts(Long pharmacyId) {
        var factory = new Configuration()
                .addAnnotatedClass(Termination.class)
                .addAnnotatedClass(Contract.class)
                .addAnnotatedClass(Pharmacy.class)
                .addAnnotatedClass(Worker.class)
                .buildSessionFactory();
        try (Session session = factory.openSession()){
            String query = """
                SELECT c
                FROM Contract c
                    LEFT JOIN Termination t ON c.id = t.id
                WHERE t.id IS NULL
                    AND c.pharmacy.id = :pharmacyId
                """;
            return session.createQuery(query, Contract.class)
                .setParameter("pharmacyId", pharmacyId)
                .getResultList();
        }
    }
}
