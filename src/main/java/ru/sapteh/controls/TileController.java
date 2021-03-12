package ru.sapteh.controls;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import ru.sapteh.model.Product;

public class TileController {
    @FXML
    public ImageView imageView; @FXML
    public Label titleTxt;    @FXML
    public Label costTxt;    @FXML
    public Label activeTxt;
    public Pane click;

    public Procladka procladka;
    private Product product;
    public static Boolean chek = false;


    public void setData(Product p, Procladka procladka){
        this.procladka = procladka;
        this.product = p;
        imageView.setImage(product.getMainImage().getImage());
        costTxt.setText(String.format("%.2f",product.getCost()));
        titleTxt.setWrapText(true);
        titleTxt.setText(product.getTitle());
        activeTxt.setText(!product.getIsActive()?"":"Не активен");
        click.setOnMouseClicked(mouseEvent -> {
            if (chek) {
                ProductEditWindowController.product = product;
                procladka.onClickListener(product);
            }
        });


    }
}
