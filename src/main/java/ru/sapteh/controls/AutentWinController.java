package ru.sapteh.controls;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.sapteh.DAO.DAO;
import ru.sapteh.DAO.service.AutentService;
import ru.sapteh.model.UsersForAut;

import java.io.IOException;
import java.util.List;

public class AutentWinController {
    @FXML
    public PasswordField passText;
    @FXML
    public TextField loginText;
    @FXML
    public Button enterButt;
    private List<UsersForAut> auts;
    @FXML
public void initialize(){
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        DAO<UsersForAut, Integer> autIntegerDAO = new AutentService(factory);
        auts = autIntegerDAO.readAll();
        enterButt.setOnAction(actionEvent -> {
            for (UsersForAut aut: auts){
                if (loginText.getText().equals(aut.getLogin())) {
                    loginText.setStyle("-fx-background-color: #090");
                    if (passText.getText().equals(aut.getPass())){
                        if (aut.getRule().equals("1")){
                            TileController.chek = true;
                        }
                        Stage primaryStage = new Stage();
                        Parent root;
                        try {
                            root = FXMLLoader.load(getClass().getResource("/ru.sapteh/view/MainWindow.fxml"));
                            primaryStage.setTitle("Start Window");
                            primaryStage.setScene(new Scene(root));
                            primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/school_logo.png")));
                            primaryStage.initModality(Modality.APPLICATION_MODAL);
                            primaryStage.showAndWait();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }else {
                        passText.setStyle("-fx-background-color: #ee0000");
                    }
                }else {
                    loginText.setStyle("-fx-background-color: #e00");
                }
            }
        });
    }
}
