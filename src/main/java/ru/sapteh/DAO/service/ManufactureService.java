package ru.sapteh.DAO.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.sapteh.DAO.DAO;
import ru.sapteh.model.Manufacturer;

import java.util.List;

public class ManufactureService implements DAO<Manufacturer , Integer> {
    private final SessionFactory factory;

    public ManufactureService(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public void create(Manufacturer manufacturer) { try (Session session = factory.openSession()){
            session.beginTransaction();
            session.save(manufacturer);
            session.getTransaction().commit();
        }
    }

    @Override
    public Manufacturer read(Integer integer) { try (Session session = factory.openSession()){

        return session.get(Manufacturer.class, integer);
    }
    }

    @Override
    public List<Manufacturer> readAll() { try (Session session = factory.openSession()){
        String hql = "from Manufacturer ";
        Query<Manufacturer> query = session.createQuery(hql);
        return query.getResultList();

    }
    }

    @Override
    public void update(Manufacturer manufacturer) { try (Session session = factory.openSession()){
        session.beginTransaction();
        session.update(manufacturer);
        session.getTransaction().commit();
    }

    }

    @Override
    public void delete(Manufacturer manufacturer) { try (Session session = factory.openSession()){
        session.beginTransaction();
        session.delete(manufacturer);
        session.getTransaction().commit();
    }

    }
}
