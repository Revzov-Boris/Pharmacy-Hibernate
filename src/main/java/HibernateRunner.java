import dao.*;
import dao.requests.QuantityMedicineInPharmacy;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import entities.*;

public class HibernateRunner {
    public static void main(String[] args) {
        var factory = new Configuration() // configure("hibernate.properties") as default is exists
                // TODO add ORM classes
                .addAnnotatedClass(Medicine.class)
                .addAnnotatedClass(Worker.class)
                .addAnnotatedClass(Pharmacy.class)
                .addAnnotatedClass(Contract.class)
                .addAnnotatedClass(Termination.class)
                .addAnnotatedClass(Order.class)
                .addAnnotatedClass(Manufacturer.class)
                .addAnnotatedClass(Fullfild.class)
                .addAnnotatedClass(Bayer.class)
                .addAnnotatedClass(Cheque.class)
                .addAnnotatedClass(Position.class)

//                // use database from URL, with user/password
//                .setProperty(URL, "jdbc:postgresql://localhost:5432/rut_head_hunter")
//                .setProperty(USER, "postgres")
//                .setProperty(PASS, "postgres")
//                // display SQL in console
//                .setProperty(SHOW_SQL, true)
//                .setProperty(FORMAT_SQL, true)
//                .setProperty(HIGHLIGHT_SQL, true)
                .buildSessionFactory();
        
        //getMedicines(factory);
        //getContracts(factory);
        //getManufacturers(factory);
        //getOrders(factory);
        //getFullfilds(factory);
        //getBayers(factory);
        //getCheques(factory);
        //getPositions(factory);
        //testDAO();

        System.out.println(QuantityMedicineInPharmacy.getQuantity(00L, 133L));
    }

    public static void getMedicines(SessionFactory factory) {
        Session session = factory.openSession();
        session.createNativeQuery("select * from medicines", Medicine.class)
                .getResultList().forEach(System.out::println);
        session.close();
    }


    public static void getContracts(SessionFactory factory) {
        Session session = factory.openSession();
        session.createNativeQuery("select * from contracts", Contract.class)
                .getResultList().forEach(System.out::println);
        session.close();
    }

    public static void getTerminations(SessionFactory factory) {
        try (Session session = factory.openSession()) {
            session.createQuery("FROM Termination", Termination.class)
                    .getResultList()
                    .forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public static void getManufacturers(SessionFactory factory) {
        Session session = factory.openSession();
        session.createNativeQuery("select * from manufacturers", Manufacturer.class)
                .getResultList().forEach(System.out::println);
        session.close();
    }

    public static void getOrders(SessionFactory factory) {
        Session session = factory.openSession();
        session.createNativeQuery("select * from orders", Order.class)
                .getResultList().forEach(System.out::println);
        session.close();
    }

    public static void getFullfilds(SessionFactory factory) {
        Session session = factory.openSession();
        session.createNativeQuery("select * from fulfilleds", Fullfild.class)
                .getResultList().forEach(System.out::println);
        session.close();
    }

    public static void getBayers(SessionFactory factory) {
        Session session = factory.openSession();
        session.createNativeQuery("select * from bayers", Bayer.class)
                .getResultList().forEach(System.out::println);
        session.close();
    }

    public static void getCheques(SessionFactory factory) {
        Session session = factory.openSession();
        session.createNativeQuery("select * from cheques", Cheque.class)
                .getResultList().forEach(System.out::println);
        session.close();
    }

    public static void getPositions(SessionFactory factory) {
        Session session = factory.openSession();
        session.createNativeQuery("select * from positions", Position.class)
                .getResultList().forEach(System.out::println);
        session.close();
    }

    public static void testDAO() {
        MedicineDao medicineDao = new MedicineDao();
        medicineDao.getAllMedicines().forEach(System.out::println);

    }


    // endregion
}
