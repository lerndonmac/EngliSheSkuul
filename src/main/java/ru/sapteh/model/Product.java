package ru.sapteh.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.*;

import javax.persistence.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Set;

@Entity@NoArgsConstructor@RequiredArgsConstructor@Setter@Getter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NonNull
    @Column(name = "Title")
    private String title;@Column(name = "Cost")@NonNull
    private Double cost;@Column(name = "Description")
    private String description;@Column(name = "MainImagePath")@NonNull
    private String mainImagePath;@Column(name = "IsActive")@NonNull
    private Boolean isActive;
    @Transient
    private ImageView mainImage;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ManufacturerID")
    @NonNull
    private Manufacturer manufacturerId;

    @OneToMany( mappedBy = "productId",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Productsale> productsales;

    public ImageView getMainImage(){
        InputStream inputStream = getClass().getResourceAsStream("/images/" +mainImagePath.substring(12));
        ImageView image = new ImageView(new Image(inputStream));
        image.setFitHeight(75);
        image.setFitWidth(50);
        image.setOnMouseClicked(mouseEvent -> {
            image.setFitHeight(750);
            image.setFitWidth(500);});
        return image;
    }

}
