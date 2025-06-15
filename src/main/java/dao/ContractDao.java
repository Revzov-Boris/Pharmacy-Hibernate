package dao;

import entities.Contract;
import entities.Pharmacy;
import entities.Termination;
import entities.Worker;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

public class ContractDao {
    public List<Contract> getAllContracts() {
        var factory = new Configuration()
                .addAnnotatedClass(Contract.class)
                .addAnnotatedClass(Worker.class)
                .addAnnotatedClass(Pharmacy.class)
                .buildSessionFactory();
        try (Session session = factory.openSession()) {
            return session.createQuery("FROM Contract", Contract.class).list();
        }
    }

    // возвращает контракты, действующие более n лет
    public List<Contract> oldContract(int n) {
        var factory = new Configuration()
                .addAnnotatedClass(Contract.class)
                .addAnnotatedClass(Worker.class)
                .addAnnotatedClass(Pharmacy.class)
                .addAnnotatedClass(Termination.class)
                .buildSessionFactory();
        String sql = """
                SELECT contracts.*
                FROM contracts
                    LEFT JOIN terminations ON contracts.id = terminations.id
                WHERE terminations.id IS NULL
                    AND contracts.date + (INTERVAL '1 YEAR' * :n) <= CURRENT_DATE
                """;
        List<Contract> contracts = new ArrayList<>();
        try (Session session = factory.openSession()) {
            contracts = session.createNativeQuery(sql, Contract.class).setParameter("n", n).getResultList();
        }
        return contracts;
    }


    public boolean addContracts(List<Contract> contracts) {
        var factory = new Configuration()
                .addAnnotatedClass(Contract.class)
                .addAnnotatedClass(Termination.class)
                .addAnnotatedClass(Pharmacy.class)
                .addAnnotatedClass(Worker.class)
                .buildSessionFactory();
        try (Session session = factory.openSession()){
            session.beginTransaction();
            for (Contract contract : contracts) {
                //создаём новый экземпляр (присоединяем к текущей сессии) контракта, т.к. contracts получены из другой сессии
                Contract managedContract = session.merge(contract);
                session.persist(managedContract);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
}
