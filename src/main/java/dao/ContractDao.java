package dao;

import entities.Contract;
import entities.Pharmacy;
import entities.Worker;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

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
}
