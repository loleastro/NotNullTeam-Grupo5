package org.mercadolibre.NotNullTeam.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    private Long id;
    private Seller seller;
    private LocalDate date;
    private Product product;
    private String category;
    private Double price;
    private Boolean hasPromo;
    private Double discount;
}
