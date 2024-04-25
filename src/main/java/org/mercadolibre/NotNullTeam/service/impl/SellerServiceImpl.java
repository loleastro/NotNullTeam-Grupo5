package org.mercadolibre.NotNullTeam.service.impl;

import lombok.RequiredArgsConstructor;
import org.mercadolibre.NotNullTeam.DTO.response.SellerFollowersCountDto;
import org.mercadolibre.NotNullTeam.exception.error.NotFoundException;
import org.mercadolibre.NotNullTeam.model.Seller;
import org.mercadolibre.NotNullTeam.repository.ISellerRepository;
import org.mercadolibre.NotNullTeam.service.IBuyerService;
import org.mercadolibre.NotNullTeam.service.ISellerService;
import org.mercadolibre.NotNullTeam.service.ISellerServiceInternal;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements ISellerService, ISellerServiceInternal {
    final ISellerRepository iSellerRepository;

    @Override
    public SellerFollowersCountDto getFollowersCount(Long id) {
        Seller seller = iSellerRepository.findById(id).orElseThrow(() -> new NotFoundException("Seller"));

        int followersCount = seller.quantityOfFollowers();

        return new SellerFollowersCountDto(seller.getUser().getId(), seller.getUser().getName(), followersCount);
    }


    @Override
    public Seller findById(Long id){
        return iSellerRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Seller"));
    }
}
