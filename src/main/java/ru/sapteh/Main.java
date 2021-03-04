package ru.sapteh;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/ru.sapteh/view/MainWindow.fxml"));
        stage.setTitle("Start Window");
        stage.setScene(new Scene(root));
        stage.getIcons().add(new Image("/school_logo.png"));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
