package ru.sapteh.DAO.service;

import junit.framework.TestCase;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Assert;
import org.junit.Test;
import ru.sapteh.DAO.DAO;
import ru.sapteh.model.Manufacturer;
import ru.sapteh.model.Product;

import java.util.Date;

public class ManufactureServiceTest extends TestCase {
    Product testProduct = new Product("jlhaergf",0.2,"aksjrga",false,new Manufacturer("jgvawerfg", new Date()));
    SessionFactory factory = new Configuration().configure().buildSessionFactory();
    DAO<Product, Integer> integerDAO = new ProductService(factory);

    public void testCreate() {
        integerDAO.create(testProduct);

        Assert.assertEquals(testProduct.getTitle(),integerDAO.read(testProduct.getId()).getTitle());
        integerDAO.delete(testProduct);
    }
@Test
    public void testRead() {
        Assert.assertNotNull(integerDAO.read(5));
    }
@Test
    public void testReadAll() {
        Assert.assertNotNull(integerDAO.readAll());
    }

}