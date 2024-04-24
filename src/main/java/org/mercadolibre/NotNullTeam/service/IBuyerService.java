package org.mercadolibre.NotNullTeam.service;

import org.mercadolibre.NotNullTeam.model.Buyer;

import java.util.List;

public interface IBuyerService {
    public void followSeller(Long userId, Long sellerToFollowId);

    List<Buyer> getAll();
}
