package ru.sapteh.controls;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import ru.sapteh.model.Product;

public class TileController {
    @FXML
    public ImageView imageView; @FXML
    public Label titleTxt;    @FXML
    public Label costTxt;    @FXML
    public Label activeTxt;

    private Procladka procladka;
    private Product product;

    @FXML
    private void click(MouseEvent event){
        ProductEditWindowController.product = product;
        procladka.onClickListener(product);
    }

    public void setData(Product p, Procladka procladka){
        this.procladka = procladka;
        this.product = p;
        imageView.setImage(product.getMainImage().getImage());
        costTxt.setText(String.format("%.2f",product.getCost()));
        titleTxt.setWrapText(true);
        titleTxt.setText(product.getTitle());
        activeTxt.setText(!product.getIsActive()?"":"Не активен");

    }
}
