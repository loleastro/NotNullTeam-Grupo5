package org.mercadolibre.NotNullTeam.service.impl;

import lombok.RequiredArgsConstructor;
import org.mercadolibre.NotNullTeam.model.Buyer;
import org.mercadolibre.NotNullTeam.model.Seller;
import org.mercadolibre.NotNullTeam.repository.IBuyerRepository;
import org.mercadolibre.NotNullTeam.repository.ISellerRepository;
import org.mercadolibre.NotNullTeam.service.IBuyerService;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class BuyerServiceImpl implements IBuyerService {

    final IBuyerRepository iBuyerRepository;
    final ISellerRepository iSellerRepository;

    @Override
    public void followSeller(Long userId, Long sellerToFollowId) {

        // TODO: ver si podemos tirar la excepcion en el repository si no encuentra el id

        Optional<Buyer> buyerOptional = iBuyerRepository.findById(userId);
        Optional<Seller> sellerOptional = iSellerRepository.findById(sellerToFollowId);

        if(buyerOptional.isPresent() && sellerOptional.isPresent()){
            Buyer buyer = buyerOptional.get();
            Seller seller = sellerOptional.get();
            iBuyerRepository.followSeller(buyer,seller);
        }


    }
}
