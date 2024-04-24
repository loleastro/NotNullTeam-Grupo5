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

import java.util.*;


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

    /*
    @Override
    public BuyerResponseDTO getFollowedList(Long userId) {
        Buyer buyer =
                iBuyerRepository
                        .findById(userId)
                        .orElseThrow(() -> new NotFoundException("Buyer"));

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
    }
    */

    @Override
    public BuyerResponseDTO getFollowedListOrdered(Long userId, String order) {
        Buyer buyer =
                iBuyerRepository
                        .findById(userId)
                        .orElseThrow(() -> new NotFoundException("Buyer"));

        List<Seller> followedList = buyer.getFollowedList();


        if (order != null) {
            if (order.equals("name_asc")) {
                followedList.sort(Comparator.comparing(Seller::getUsername));
            } else if (order.equals("name_desc")) {
                followedList.sort(Comparator.comparing(Seller::getUsername).reversed());
            }
        }


        return new BuyerResponseDTO(
                buyer.getUser().getId(),
                buyer.getUser().getName(),
                followedList.stream().map(
                        s -> new SellerResponseWithNotBuyerListDTO(
                                s.getUser().getId(),
                                s.getUser().getName()
                        )).toList()
        );
    }


    public void unfollowSeller(Long userId, Long userIdToUnfollow) {
        Buyer buyer =
                iBuyerRepository.findById(userId).orElseThrow(() -> new NotFoundException("Buyer"));
        Seller seller =
                iSellerRepository.findById(userIdToUnfollow).orElseThrow(() -> new NotFoundException("Seller"));

        buyer.removeFollowed(seller);
        seller.removeFollower(buyer);
    }
}
