package dao.requests;

import dao.requestDTO.BayerInCityDto;
import dao.requestDTO.PurchasesInPharmacyDto;
import entities.*;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class BayerInCityRequest {
    public static List<BayerInCityDto> getPurchases(Long bayerId, String cityName) {
        var factory = new Configuration()
                .addAnnotatedClass(Cheque.class)
                .addAnnotatedClass(Position.class)
                .addAnnotatedClass(Medicine.class)
                .addAnnotatedClass(Bayer.class)
                .addAnnotatedClass(Pharmacy.class)
                .buildSessionFactory();
        try (Session session = factory.openSession()){
            String query = """
                SELECT new dao.requestDTO.BayerInCityDto(
                            c.id,
                            c.bayer.id,
                            c.pharmacy.id,
                            c.date,
                            SUM(m.price * p.quantity))
                FROM Cheque c, Position p, Medicine m
                WHERE p.cheque.id = c.id
                AND p.medicine.id = m.id
                AND c.bayer.id = :bayerId
                AND c.pharmacy.address LIKE :cityName
                GROUP BY c.id, c.bayer.id, c.pharmacy.id, c.date
                ORDER BY SUM(m.price * p.quantity)
                """;
            return session.createQuery(query, BayerInCityDto.class)
                    .setParameter("bayerId", bayerId)
                    .setParameter("cityName", "%г. " + cityName + "%")

                    .getResultList();
        }
    }
}
