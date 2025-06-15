package dao.requests;

import entities.*;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

public class QuantityMedicineInPharmacy {
    // количество препарата с id = medicineId в аптеке с id = pharmacyId
    public static long getQuantity(Long pharmacyId, Long medicineId) {
        var factory = new Configuration()
                .addAnnotatedClass(Cheque.class)
                .addAnnotatedClass(Position.class)
                .addAnnotatedClass(Medicine.class)
                .addAnnotatedClass(Bayer.class)
                .addAnnotatedClass(Pharmacy.class)
                .buildSessionFactory();
        try (Session session = factory.openSession()) {
            String sqlCountIn = """
                    SELECT COUNT(quantity)
                    FROM orders
                        JOIN fulfilleds ON orders.id = fulfilleds.id
                    WHERE pharmacy_id = :pharmacyId
                        AND medicine_id = :medicineId
                    """;
            Long countIn = session.createNativeQuery(sqlCountIn, Long.class)
                    .setParameter("pharmacyId", pharmacyId)
                    .setParameter("medicineId", medicineId)
                    .uniqueResult();
            if (countIn.equals(0L)) {
                return 0L;
            }
            String sqlSumIn = """
                    SELECT SUM(quantity)
                    FROM orders
                        JOIN fulfilleds ON orders.id = fulfilleds.id
                    WHERE pharmacy_id = :pharmacyId
                        AND medicine_id = :medicineId
                    """;
            String sqlSumOut = """
                    SELECT SUM(quantity)
                    FROM positions
                        JOIN cheques ON cheques.id = positions.cheque_id
                    WHERE pharmacy_id = :pharmacyId
                        AND medicine_id = :medicineId
                    """;
            Long sumIn = session.createNativeQuery(sqlSumIn, Long.class)
                    .setParameter("pharmacyId", pharmacyId)
                    .setParameter("medicineId", medicineId)
                    .uniqueResult();
            Long sumOut = session.createNativeQuery(sqlSumOut, Long.class)
                    .setParameter("pharmacyId", pharmacyId)
                    .setParameter("medicineId", medicineId)
                    .uniqueResult();
            System.out.println("in " + sumIn);
            System.out.println("out " + sumOut);
            if (sumOut == null) return sumIn;
            return sumIn - sumOut;
        }
    }
}
