package dao;

import entities.Contract;
import entities.Pharmacy;
import entities.Termination;
import entities.Worker;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

import java.time.LocalDate;
import java.util.List;

public class TerminationDao {
    public List<Termination> getAllTerminations() {
        var factory = new Configuration()
                .addAnnotatedClass(Contract.class)
                .addAnnotatedClass(Termination.class)
                .addAnnotatedClass(Pharmacy.class)
                .addAnnotatedClass(Worker.class)
                .buildSessionFactory();
        try (Session session = factory.openSession()) {
            return session.createQuery("FROM Termination", Termination.class).list();
        }
    }


    public boolean addTerminations(List<Contract> contracts) {
        var factory = new Configuration()
                .addAnnotatedClass(Contract.class)
                .addAnnotatedClass(Termination.class)
                .addAnnotatedClass(Pharmacy.class)
                .addAnnotatedClass(Worker.class)
                .buildSessionFactory();
        if (!contracts.isEmpty()) {
            try (Session session = factory.openSession()) {
                // Добавляем расторжения контрактов
                session.beginTransaction();
                for (Contract contract : contracts) {
                    //создаём новый экземпляр (присоединяем к текущей сессии) контракта, т.к. contracts получены из другой сессии
                    Contract managedContract = session.merge(contract);
                    Termination termination = new Termination(managedContract.getId(), managedContract, LocalDate.now());
                    session.persist(termination);
                }
                session.getTransaction().commit();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
                System.out.println("не добавлись(");
                return false;
            }
        }
        return true;
    }
}
