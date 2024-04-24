package org.mercadolibre.NotNullTeam.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Buyer {
    private User user;
    private List<Seller> followedList;

    public void addNewFollowed(Seller seller) {
        followedList.add(seller);
    }
}
