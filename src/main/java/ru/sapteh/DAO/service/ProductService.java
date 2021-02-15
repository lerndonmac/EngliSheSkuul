package ru.sapteh.DAO.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.sapteh.DAO.DAO;
import ru.sapteh.model.Manufacturer;
import ru.sapteh.model.Product;

import java.util.List;

public class ProductService implements DAO<Product, Integer> {
    private final SessionFactory factory;

    public ProductService(SessionFactory factory) {
        this.factory = factory;
    }
    @Override
    public void create(Product manufacturer) { try (Session session = factory.openSession()){
        session.beginTransaction();
        session.save(manufacturer);
        session.getTransaction().commit();
    }
    }

    @Override
    public Product read(Integer integer) { try (Session session = factory.openSession()){

        return session.get(Product.class, integer);
    }
    }

    @Override
    public List<Product> readAll() { try (Session session = factory.openSession()){
        String hql = "from Product ";
        Query<Product> query = session.createQuery(hql);
        return query.getResultList();

    }
    }

    @Override
    public void update(Product manufacturer) { try (Session session = factory.openSession()){
        session.beginTransaction();
        session.update(manufacturer);
        session.getTransaction().commit();
    }

    }

    @Override
    public void delete(Product manufacturer) { try (Session session = factory.openSession()){
        session.beginTransaction();
        session.delete(manufacturer);
        session.getTransaction().commit();
    }

    }
}
