package ru.sapteh.DAO.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.sapteh.DAO.DAO;
import ru.sapteh.model.Manufacturer;
import ru.sapteh.model.Productsale;

import java.util.List;

public class ProductSaleService implements DAO<Productsale, Integer> {
    private final SessionFactory factory;

    public ProductSaleService(SessionFactory factory) {
        this.factory = factory;
    }
    @Override
    public void create(Productsale manufacturer) { try (Session session = factory.openSession()){
        session.beginTransaction();
        session.save(manufacturer);
        session.getTransaction().commit();
    }
    }

    @Override
    public Productsale read(Integer integer) { try (Session session = factory.openSession()){

        return session.get(Productsale.class, integer);
    }
    }

    @Override
    public List<Productsale> readAll() { try (Session session = factory.openSession()){
        String hql = "from Productsale ";
        Query<Productsale> query = session.createQuery(hql);
        return query.getResultList();

    }
    }

    @Override
    public void update(Productsale manufacturer) { try (Session session = factory.openSession()){
        session.beginTransaction();
        session.update(manufacturer);
        session.getTransaction().commit();
    }

    }

    @Override
    public void delete(Productsale manufacturer) { try (Session session = factory.openSession()){
        session.beginTransaction();
        session.delete(manufacturer);
        session.getTransaction().commit();
    }

    }
}
