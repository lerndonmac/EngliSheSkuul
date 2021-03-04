package ru.sapteh.controls;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;
import ru.sapteh.DAO.DAO;
import ru.sapteh.DAO.service.ProductService;
import ru.sapteh.model.Product;
import ru.sapteh.model.Productsale;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class SalesWindowContrlos {
    public static Product product;
    @FXML
    public ListView<Date> salesView;
    private ObservableList<Date> productsales = FXCollections.observableArrayList();
    @FXML
    public void initialize(){
        initSales(product);


        salesView.setItems(productsales);
    }
    private void initSales(Product product){
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
        String hql = "select SaleDate from productsale where ProductID = ?";
        Query query = session.createNativeQuery(hql);
        query.setParameter(1,product.getId());
        List<Date> list = query.getResultList();
        productsales.addAll(list);


    }

}
