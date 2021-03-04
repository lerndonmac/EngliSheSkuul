package ru.sapteh.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Setter
@Getter
public class Productsale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;@NonNull
    @Column(name = "SaleDate")
    private Date saleDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProductId")
    private Product productId;@NonNull
    @Column(name = "Quantity")
    private int quantity;
}
