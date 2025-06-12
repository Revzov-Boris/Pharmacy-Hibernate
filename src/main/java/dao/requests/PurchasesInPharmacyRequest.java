package dao.requests;

import dao.requestDTO.PurchasesInPharmacyDto;
import entities.*;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class PurchasesInPharmacyRequest {
    public static List<PurchasesInPharmacyDto> getPurchases(Long pharmacyId) {
        var factory = new Configuration()
                .addAnnotatedClass(Cheque.class)
                .addAnnotatedClass(Position.class)
                .addAnnotatedClass(Medicine.class)
                .addAnnotatedClass(Bayer.class)
                .addAnnotatedClass(Pharmacy.class)
                .buildSessionFactory();
        try (Session session = factory.openSession()){
            String query = """
                SELECT new dao.requestDTO.PurchasesInPharmacyDto(
                            c.id,
                            c.bayer.id,
                            c.pharmacy.id,
                            c.date,
                            SUM(m.price * p.quantity))
                FROM Cheque c, Position p, Medicine m
                WHERE p.cheque.id = c.id
                AND p.medicine.id = m.id
                AND c.pharmacy.id = :pharmacyId
                GROUP BY c.id, c.bayer.id, c.pharmacy.id, c.date
                ORDER BY SUM(m.price * p.quantity)
                """;
            return session.createQuery(query, PurchasesInPharmacyDto.class)
                    .setParameter("pharmacyId", pharmacyId)
                    .getResultList();
        }
    }
}
