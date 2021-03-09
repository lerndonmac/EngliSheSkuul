package ru.sapteh.controls;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.sapteh.DAO.DAO;
import ru.sapteh.DAO.service.ProductService;
import ru.sapteh.model.Manufacturer;
import ru.sapteh.model.Product;
import java.io.*;
import java.util.Comparator;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainWindowController {
    //fields from view
    public TextField serchProductTxt;@FXML
    private ChoiceBox<Manufacturer> productSortByManufactorCombo;@FXML
    private Label CountOfRows;@FXML
    private Button updateButton;@FXML
    private Button deleteButton;@FXML
    private Button createButton;@FXML
    private Button costSortButt;@FXML
    private Button salesButt;@FXML
    private FlowPane flowPane;
    //non-fxml fields
    private static SessionFactory factory;
    private static ObservableList<Product> productObservableList = FXCollections.observableArrayList();
    private final static ObservableList<Manufacturer> manufacturerObservableList = FXCollections.observableArrayList();
    private Product choosenProduct;
    private static final AtomicBoolean sorted = new AtomicBoolean(false);
    @FXML
    public void initialize() throws FileNotFoundException {
        manufacturerObservableList.clear();
        productObservableList.clear();
        initDateBaseProduct();
        initDataBaseManufacture();
        initProducts(productObservableList);
        productSortByManufactorCombo.setItems(manufacturerObservableList);
        CountOfRows.setText(String.valueOf(productObservableList.size()));
        initButtons();
    }
    //initialize buttons logic
    public void initButtons(){
        serchProductTxt.setOnKeyReleased(actionEvent -> {
            ObservableList<Product> products = FXCollections.observableArrayList();
            products.clear();
            for (Product p:productObservableList){
                if (p.getTitle().toLowerCase(Locale.ROOT).contains(serchProductTxt.getText().toLowerCase(Locale.ROOT))){
                    products.add(p);
                }
            }
            try {
                if (!serchProductTxt.getText().equals("")) {
                    initProducts(products);
                }
                else {
                    initProducts(productObservableList);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            CountOfRows.setText(String.valueOf(products.size()));
        });
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("All");
        manufacturerObservableList.add(manufacturer);
        productSortByManufactorCombo.setValue(manufacturer);

        productSortByManufactorCombo.setOnAction(actionEvent -> {
            productObservableList.clear();
            initDateBaseProduct();
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
            productObservableList = products;
            try {
                initProducts(productObservableList);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            CountOfRows.setText(String.valueOf(products.size()));
        });
        updateButton.setOnAction(actionEvent -> {
            ProductEditWindowController.product = choosenProduct;
            Stage stage = new Stage();
            stage.getIcons().add(new Image("/school_logo.png"));
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
            stage.getIcons().add(new Image("/school_logo.png"));
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
        deleteButton.setOnAction(actionEvent -> {
            factory = new Configuration().configure().buildSessionFactory();
            DAO<Product, Integer> productDAO = new ProductService(factory);
            productDAO.delete(choosenProduct);

            try {
                initialize();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
        costSortButt.setOnAction(actionEvent -> {
            if (!sorted.get()){
                productObservableList.sort(Comparator.comparing(Product::getCost));
                try {
                    initProducts(productObservableList);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                sorted.set(true);
            }else {
                productObservableList.sort((x, y)-> -Double.compare(x.getCost(),y.getCost()));
                try {
                    initProducts(productObservableList);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                sorted.set(false);

            }
        });
        salesButt.setOnAction(actionEvent -> {
            SalesWindowContrlos.product = choosenProduct;
            Stage stage = new Stage();
            stage.getIcons().add(new Image("/school_logo.png"));
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/ru.sapteh/view/SalesWindow.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage.setTitle("Sales Window");
            assert root != null;
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        });
    }
    //создание плиток на основе листа
    public void initProducts(ObservableList<Product> products) throws FileNotFoundException {
        flowPane.getChildren().clear();
        flowPane.setVgap(20);
        flowPane.setHgap(5);
        for (Product product : products){
            AnchorPane pane = new AnchorPane();
            Label nameLable = new Label();
            Label costLable = new Label();
            ImageView imageView = new ImageView();
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
                pane.setStyle("-fx-background-color: gray");
                activeLable.setLayoutY(340);
                activeLable.setMaxWidth(200);
                deleteButton.setOnAction(actionEvent -> {
                });
            }

            imageView.setImage(product.getMainImage().getImage());
            imageView.setFitWidth(200);
            imageView.setFitHeight(300);

            pane.setPrefWidth(200);
            pane.setOnMouseClicked(mouseEvent -> choosenProduct = product);
            pane.getChildren().add(imageView);
            pane.getChildren().add(nameLable);
            pane.getChildren().add(costLable);
            flowPane.getChildren().add(pane);
        }
    }
    //filling lists by objects from database
    public static void initDateBaseProduct(){
        factory = new Configuration().configure().buildSessionFactory();
        DAO<Product,Integer> productDAO = new ProductService(factory);
        productObservableList.addAll(productDAO.readAll());
        factory.close();
    }
    public static void initDataBaseManufacture(){
        factory = new Configuration().configure().buildSessionFactory();
        for (int i = 0; i <productObservableList.size()-1 ; i++) {
            if (!manufacturerObservableList.contains(productObservableList.get(i).getManufacturerId())) {
                manufacturerObservableList.add(productObservableList.get(i).getManufacturerId());
            }
        }
        factory.close();
    }
}