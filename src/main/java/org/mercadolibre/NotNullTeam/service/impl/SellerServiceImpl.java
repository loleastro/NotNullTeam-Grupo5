package org.mercadolibre.NotNullTeam.service.impl;

import lombok.RequiredArgsConstructor;
import org.mercadolibre.NotNullTeam.DTO.response.BuyerResponseWithNotSellerListDTO;
import org.mercadolibre.NotNullTeam.DTO.response.SellerFollowersCountDto;
import org.mercadolibre.NotNullTeam.DTO.response.SellerResponseDTO;
import org.mercadolibre.NotNullTeam.exception.error.NotFoundException;
import org.mercadolibre.NotNullTeam.model.Seller;
import org.mercadolibre.NotNullTeam.repository.ISellerRepository;
import org.mercadolibre.NotNullTeam.service.ISellerService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements ISellerService {
    final ISellerRepository iSellerRepository;

    @Override
    public SellerFollowersCountDto getFollowersCount(Long id) {
        Seller seller = iSellerRepository.findById(id).orElseThrow(() -> new NotFoundException("Seller"));

        int followersCount = seller.quantityOfFollowers();

        return new SellerFollowersCountDto(seller.getUser().getId(), seller.getUser().getName(), followersCount);
    }

    @Override
    public SellerResponseDTO getListFollowers(Long userId) {
        Seller seller = iSellerRepository
                .findById(userId)
                .orElseThrow(() -> new NotFoundException("Seller"));

        return new SellerResponseDTO(
                seller.getUser().getId(),
                seller.getUser().getName(),
                seller.getFollowersList().stream().map(
                        s -> new BuyerResponseWithNotSellerListDTO(
                                s.getUser().getId(),s.getUser().getName())).toList());
    }
}
