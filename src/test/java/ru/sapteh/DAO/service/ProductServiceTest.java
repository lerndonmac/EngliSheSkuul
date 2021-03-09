package ru.sapteh.DAO.service;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Assert;
import org.junit.Test;
import ru.sapteh.DAO.DAO;
import ru.sapteh.model.Product;

import java.util.List;
public class ProductServiceTest {
    private List<Product> products;
    private SessionFactory factory;

    @Test
    public void testReadAll() {
        factory = new Configuration().configure().buildSessionFactory();
        DAO<Product, Integer> productDAO = new ProductService(factory);
        products = productDAO.readAll();

        Assert.assertNotNull(products.get(0));
    }

    @Test
    public void testRead() {
        factory = new Configuration().configure().buildSessionFactory();
        DAO<Product, Integer> productDAO = new ProductService(factory);
        Product product = productDAO.read(1);

        Assert.assertNotNull(product);
    }
}