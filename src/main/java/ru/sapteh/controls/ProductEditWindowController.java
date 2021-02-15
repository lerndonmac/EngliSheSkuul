package ru.sapteh.controls;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.sapteh.DAO.DAO;
import ru.sapteh.DAO.service.ManufactureService;
import ru.sapteh.DAO.service.ProductService;
import ru.sapteh.model.Manufacturer;
import ru.sapteh.model.Product;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ProductEditWindowController {
    private static SessionFactory factory;
    private final static ObservableList<Manufacturer> manufactors = FXCollections.observableArrayList();
    public static Product product;
    @FXML
    public TextField titleText;@FXML
    public TextField costText;@FXML
    public TextField photoText;@FXML
    public ComboBox<Manufacturer> manufactureCombo;@FXML
    public CheckBox isActive;@FXML
    public Button updateButton;@FXML
    public Button pathSelect;@FXML
    public void initialize() throws FileNotFoundException {
        initManufactures();
        manufactureCombo.setItems(manufactors);
        titleText.setText(product.getTitle());
        costText.setText(String.valueOf(product.getCost()));
        photoText.setText(product.getMainImage().getImage().getUrl());
        isActive.setSelected(product.getIsActive());
        updateButton.setOnAction(actionEvent -> {
            factory = new Configuration().configure().buildSessionFactory();
            DAO<Product , Integer> productDAO = new ProductService(factory);
            Boolean b = isActive.isAllowIndeterminate();
            product.setTitle(titleText.getText());
            product.setCost(Double.valueOf(costText.getText()));
            product.setIsActive(b);
            product.setMainImagePath(photoText.getText());
            product.setManufacturerId(manufactureCombo.getValue());
            productDAO.update(product);
            factory.close();
        });
        pathSelect.setOnAction(actionEvent -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File("C:\\JavaProjects\\EngliSheSkuul\\src\\main\\resources\\Товарышколы"));
            photoText.setText(fileChooser.showOpenDialog(new Stage()).getAbsolutePath());
        });

    }
    private static void initManufactures(){
        factory = new Configuration().configure().buildSessionFactory();
        DAO<Manufacturer,Integer> manufacturerDAO = new ManufactureService(factory);
        manufactors.addAll(manufacturerDAO.readAll());
        factory.close();
    }
}
