package org.mercadolibre.NotNullTeam.service;

import org.mercadolibre.NotNullTeam.DTO.response.SellerFollowersCountDto;

public interface ISellerService {
    SellerFollowersCountDto getFollowersCount(Long userId);
}
