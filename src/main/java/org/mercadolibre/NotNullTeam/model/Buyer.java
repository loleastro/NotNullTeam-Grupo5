package org.mercadolibre.NotNullTeam.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Buyer {
    private User user;
    private List<Seller> followedList = new ArrayList<>();

    public void addNewFollowed(Seller seller) {
        followedList.add(seller);
    }
}
