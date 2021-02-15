package ru.sapteh.controls;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.sapteh.DAO.DAO;
import ru.sapteh.DAO.service.ManufactureService;
import ru.sapteh.DAO.service.ProductService;
import ru.sapteh.model.Manufacturer;
import ru.sapteh.model.Product;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.*;

public class MainWindowController {
    private static SessionFactory factory;
    //product
    @FXML
    public TableView<Product> tableProduct;@FXML
    public TableColumn<Product,String> columnTitle;@FXML
    public TableColumn<Product,Integer> columnCost;@FXML
    public TableColumn<Product, Boolean> columnIsActive;@FXML
    public TableColumn<Product, ImageView> columnMainImg;@FXML
    public TextField serchProductTxt;@FXML
    public ChoiceBox<Manufacturer> productSortByManufactorCombo;@FXML
    public Label CountOfRows;@FXML
    public Button updateButton;@FXML
    public Button deleteButton;@FXML
    public Button createButton;
    private final static ObservableList<Product> productObservableList = FXCollections.observableArrayList();
    private final static ObservableList<Manufacturer> manufacturerObservableList = FXCollections.observableArrayList();


    @FXML
    public void initialize() {
        initDateBaseProduct();
        columnTitle.setCellValueFactory(new PropertyValueFactory<Product, String>("title"));
        columnCost.setCellValueFactory(new PropertyValueFactory<Product, Integer>("cost"));
        columnIsActive.setCellValueFactory(new PropertyValueFactory<Product, Boolean>("isActive"));
        columnMainImg.setCellValueFactory(new PropertyValueFactory<Product, ImageView>("mainImage"));

        tableProduct.setItems(productObservableList);
        initDataBaseManufacture();
        productSortByManufactorCombo.setItems(manufacturerObservableList);
        productSortByManufactorCombo.setOnAction(actionEvent -> {
            ObservableList<Product> products = FXCollections.observableArrayList();
            for (int i = 0; i < productObservableList.size()-1 ; i++) {
                Product p = productObservableList.get(i);
                if (p.getManufacturerId().equals(productSortByManufactorCombo.getValue())){
                    products.add(p);
                }
            }
            columnTitle.setCellValueFactory(new PropertyValueFactory<Product, String>("title"));
            columnCost.setCellValueFactory(new PropertyValueFactory<Product, Integer>("cost"));
            columnIsActive.setCellValueFactory(new PropertyValueFactory<Product, Boolean>("isActive"));
            columnMainImg.setCellValueFactory(new PropertyValueFactory<Product, ImageView>("mainImage"));
            tableProduct.setItems(products);
            CountOfRows.setText(String.valueOf(products.size()));
        });
        serchProductTxt.setOnKeyPressed(actionEvent -> {
            ObservableList<Product> products = FXCollections.observableArrayList();
            for (int i = 0; i < productObservableList.size()-1 ; i++) {
                Product p = productObservableList.get(i);
                if (p.getTitle().contains(serchProductTxt.getText())){
                    products.add(p);
                }
            }
            columnTitle.setCellValueFactory(new PropertyValueFactory<Product, String>("title"));
            columnCost.setCellValueFactory(new PropertyValueFactory<Product, Integer>("cost"));
            columnIsActive.setCellValueFactory(new PropertyValueFactory<Product, Boolean>("isActive"));
            columnMainImg.setCellValueFactory(new PropertyValueFactory<Product, ImageView>("mainImage"));
            tableProduct.setItems(products);
            CountOfRows.setText(String.valueOf(products.size()));
        });
        CountOfRows.setText(String.valueOf(productObservableList.size()));
        tableProduct.getSelectionModel().selectedItemProperty().addListener(((observable,oldUser,product)-> {ProductEditWindowController.product = product;}));


        updateButton.setOnAction(actionEvent -> {
            Stage stage = new Stage();
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/ru.sapteh/view/EditProductWindow.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage.setTitle("Edit Window");
            assert root != null;
            stage.setScene(new Scene(root));
            stage.show();
        });
        createButton.setOnAction(actionEvent -> {
            Stage stage = new Stage();
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/ru.sapteh/view/CreateProductWindow.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage.setTitle("Edit Window");
            assert root != null;
            stage.setScene(new Scene(root));
            stage.show();
        });

    }
    public static void initDateBaseProduct(){
        factory = new Configuration().configure().buildSessionFactory();
        DAO<Product,Integer> productDAO = new ProductService(factory);
        productObservableList.addAll(productDAO.readAll());
        factory.close();
    }
    public static void initDataBaseManufacture(){
        factory = new Configuration().configure().buildSessionFactory();
        DAO<Manufacturer, Integer> manufacturerDAO = new ManufactureService(factory);
//        manufacturerObservableList.addAll(manufacturerDAO.readAll());
        for (int i = 0; i <productObservableList.size()-1 ; i++) {
            if (manufacturerObservableList.contains(productObservableList.get(i).getManufacturerId())) {
            }
            else {manufacturerObservableList.add(productObservableList.get(i).getManufacturerId());}
        }
        factory.close();
    }
}
