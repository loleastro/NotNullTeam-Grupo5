package org.mercadolibre.NotNullTeam.service;

import org.mercadolibre.NotNullTeam.DTO.response.BuyerResponseWithNotSellerListDTO;

import java.util.List;

public interface IBuyerService {
    void followSeller(Long userId, Long sellerToFollowId);

    List<BuyerResponseWithNotSellerListDTO> getAll();
}