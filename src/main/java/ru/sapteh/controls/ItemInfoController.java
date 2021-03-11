package ru.sapteh.controls;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.sapteh.DAO.DAO;
import ru.sapteh.DAO.service.ProductService;
import ru.sapteh.model.Product;

import java.io.IOException;

public class ItemInfoController {
    @FXML
    public ImageView image;
    @FXML
    public Label costTxt;
    @FXML
    public Label tiltleTxt;
    @FXML
    public Label descTxt;
    @FXML
    public Label manoTxt;
    @FXML
    public Button updateButt;
    @FXML
    public Button delButt;
    @FXML
    private Button salesButt;


    private Product product;

    public void setData(Product p){
        this.product = p;
        image.setImage(product.getMainImage().getImage());
        costTxt.setText(String.format("%.2f",p.getCost()));
        tiltleTxt.setWrapText(true);
        tiltleTxt.setText(product.getTitle());
        descTxt.setText(product.getDescription());
        manoTxt.setText(product.getManufacturerId().getName());
        salesButt.setOnAction(actionEvent -> {
            SalesWindowContrlos.product = product;
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
            stage.setAlwaysOnTop(true);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        });

        updateButt.setOnAction(actionEvent -> {
            ProductEditWindowController.product = product;
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
        delButt.setOnAction(actionEvent -> {
            SessionFactory factory = new Configuration().configure().buildSessionFactory();
            DAO<Product, Integer> productDAO = new ProductService(factory);
            productDAO.delete(product);
            delButt.getScene().getWindow().hide();
        });
    }
}
