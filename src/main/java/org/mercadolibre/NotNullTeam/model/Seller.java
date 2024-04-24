package org.mercadolibre.NotNullTeam.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Seller {
    private User user;
    @JsonIgnore
    private List<Buyer> followersList;

    public void addNewFollower(Buyer buyer) {
        followersList.add(buyer);
    }

}
