package org.mercadolibre.NotNullTeam.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.mercadolibre.NotNullTeam.DTO.response.BuyerResponseDTO;
import org.mercadolibre.NotNullTeam.DTO.response.BuyerResponseWithNotSellerListDTO;
import org.mercadolibre.NotNullTeam.DTO.response.SellerResponseWithNotBuyerListDTO;
import org.mercadolibre.NotNullTeam.exception.error.NotFoundException;
import org.mercadolibre.NotNullTeam.model.Buyer;
import org.mercadolibre.NotNullTeam.model.Seller;
import org.mercadolibre.NotNullTeam.repository.IBuyerRepository;
import org.mercadolibre.NotNullTeam.repository.ISellerRepository;
import org.mercadolibre.NotNullTeam.service.IBuyerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class BuyerServiceImpl implements IBuyerService {

    final IBuyerRepository iBuyerRepository;
    final ISellerRepository iSellerRepository;
    final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void followSeller(Long userId, Long sellerToFollowId) {

        Buyer buyer =
                iBuyerRepository.findById(userId).orElseThrow(() -> new NotFoundException("Buyer"));
        Seller seller =
                iSellerRepository.findById(sellerToFollowId).orElseThrow(() -> new NotFoundException("Seller"));

        buyer.addNewFollowed(seller);
        seller.addNewFollower(buyer);
    }

    @Override
    public List<BuyerResponseWithNotSellerListDTO> getAll() {
        return iBuyerRepository
                .findAll()
                .stream()
                .map(e -> new BuyerResponseWithNotSellerListDTO(e.getUser().getId(), e.getUser().getName())).toList();
    }

    @Override
    public BuyerResponseDTO getFollowedList(Long userId) {
        Optional<Buyer> buyerOptional = iBuyerRepository.findById(userId);
        if(buyerOptional.isPresent()){
            Buyer buyer = buyerOptional.get();
            return new BuyerResponseDTO(
                    buyer.getUser().getId(),
                    buyer.getUser().getName(),
                    buyer.getFollowedList().stream().map(
                            s -> new SellerResponseWithNotBuyerListDTO(
                                    s.getUser().getId(),
                                    s.getUser().getName()
                            )
                    ).toList()
            );
        } else {
            throw new NotFoundException("Buyer");
        }
    }
}
