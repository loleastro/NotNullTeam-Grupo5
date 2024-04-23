package org.mercadolibre.NotNullTeam.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Seller {
    private User user;
    private List<Buyer> followersList;

}
