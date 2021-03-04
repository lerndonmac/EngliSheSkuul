package ru.sapteh.controls;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
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
    public TextField serchProductTxt;@FXML
    public ChoiceBox<Manufacturer> productSortByManufactorCombo;@FXML
    public Label CountOfRows;@FXML
    public Button updateButton;@FXML
    public Button deleteButton;@FXML
    public Button createButton;
    private final static ObservableList<Product> productObservableList = FXCollections.observableArrayList();
    private final static ObservableList<Manufacturer> manufacturerObservableList = FXCollections.observableArrayList();
    @FXML
    private FlowPane flowPane;





    @FXML
    public void initialize() throws FileNotFoundException {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("All");
        manufacturerObservableList.add(manufacturer);
        productSortByManufactorCombo.setValue(manufacturerObservableList.get(0));
        initDateBaseProduct();
        initDataBaseManufacture();
        initProducts(productObservableList);


        productSortByManufactorCombo.setItems(manufacturerObservableList);
        productSortByManufactorCombo.setOnAction(actionEvent -> {
            ObservableList<Product> products = FXCollections.observableArrayList();
            for (Product p:productObservableList){
                if (productSortByManufactorCombo.getValue().equals(manufacturer)){
                    products = productObservableList;
                }else {
                    if (p.getManufacturerId().equals(productSortByManufactorCombo.getValue())) {
                        products.add(p);
                    }
                }
            }

            try {
                initProducts(products);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
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
            CountOfRows.setText(String.valueOf(products.size()));
        });
        CountOfRows.setText(String.valueOf(productObservableList.size()));


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
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
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
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        });


    }
    public void initProducts(ObservableList<Product> products) throws FileNotFoundException {

        flowPane.getChildren().clear();
        for (Product product : products){
            Label nameLable = new Label();
           Label costLable = new Label();

            ImageView imageView = new ImageView();

           AnchorPane pane = new AnchorPane();
           Label activeLable = new Label();

            nameLable.setText(product.getTitle());
            nameLable.setLayoutY(300);
            nameLable.setMaxWidth(200);

            costLable.setText("Цена: "+product.getCost());
            costLable.setLayoutY(320);
            costLable.setMaxWidth(200);

            pane.setPrefHeight(340);
            if (!product.getIsActive()) {
                pane.setPrefHeight(360);
                pane.getChildren().add(activeLable);
                activeLable.setText("Не активен");
                activeLable.setTextFill(Color.RED);
                activeLable.setLayoutY(340);
                activeLable.setMaxWidth(200);
            }

            imageView.setImage(product.getMainImage().getImage());
            imageView.setFitWidth(200);
            imageView.setFitHeight(300);


            pane.setPrefWidth(200);
            pane.getChildren().add(imageView);
            pane.getChildren().add(nameLable);
            pane.getChildren().add(costLable);
            flowPane.getChildren().add(pane);
        }
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
        for (int i = 0; i <productObservableList.size()-1 ; i++) {
            if (!manufacturerObservableList.contains(productObservableList.get(i).getManufacturerId())) {
                manufacturerObservableList.add(productObservableList.get(i).getManufacturerId());
            }
        }
        factory.close();
    }
}
