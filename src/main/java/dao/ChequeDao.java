package dao;

import entities.*;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class ChequeDao {
    public List<Cheque> getAllCheques() {
        var factory = new Configuration()
                .addAnnotatedClass(Cheque.class)
                .addAnnotatedClass(Pharmacy.class)
                .addAnnotatedClass(Bayer.class)
                .buildSessionFactory();
        try (Session session = factory.openSession()) {
            return session.createQuery("FROM Cheque", Cheque.class).list();
        }
    }


    public static boolean saveChequeWithPositions(Cheque cheque, List<Position> positions) {
        var factory = new Configuration()
                .addAnnotatedClass(Cheque.class)
                .addAnnotatedClass(Pharmacy.class)
                .addAnnotatedClass(Bayer.class)
                .addAnnotatedClass(Position.class)
                .addAnnotatedClass(Medicine.class)
                .buildSessionFactory();
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            try {
                System.out.println("Попытка загрузить в БД чек: " + cheque);
                session.persist(cheque);
                session.flush();
                System.out.println("Чек загружен: " + cheque);

                for (Position p : positions) {
                    p.setCheque(cheque);
                    p.setMedicine(session.merge(p.getMedicine()));
                    System.out.println("Попытка загрузить в БД позицию: " + p);
                    session.persist(p);
                    System.out.println("Загружена в БД позиция: " + p);
                }
                session.getTransaction().commit();
                return true;
            } catch (Exception e) {
                if (session.getTransaction().isActive()) {
                    System.out.println("Не вышло загрузить в БД позицию ");
                    session.getTransaction().rollback();
                }
                e.printStackTrace();
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return false;
        }
    }
}
