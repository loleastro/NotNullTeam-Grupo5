package org.mercadolibre.NotNullTeam.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mercadolibre.NotNullTeam.DTO.response.seller.SellerFollowersCountDto;
import org.mercadolibre.NotNullTeam.exception.error.NotFoundException;
import org.mercadolibre.NotNullTeam.model.Buyer;
import org.mercadolibre.NotNullTeam.model.Seller;
import org.mercadolibre.NotNullTeam.model.User;
import org.mercadolibre.NotNullTeam.repository.ISellerRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class SellerServiceImplTest {
    @Mock
    private ISellerRepository sellerRepository;
    @InjectMocks
    private SellerServiceImpl sellerService;
    Buyer buyerOne;
    Buyer buyerTwo;
    Seller sellerWithoutFollowers;
    Seller sellerWithFollowers;
    @BeforeEach
    public void setup() {
        buyerOne = new Buyer(
                new User(100L, "Seguidor Numero Uno"),
                new ArrayList<Seller>()
        );
        buyerTwo = new Buyer(
                new User(101L, "Seguidor Numero Uno"),
                new ArrayList<Seller>()
        );
        sellerWithoutFollowers = new Seller(
                new User(102L, "Lonely seller"),
                new ArrayList<Buyer>()
        );
        sellerWithFollowers = new Seller(
                new User(103L, "Popular seller"),
                new ArrayList<Buyer>()
        );
        sellerWithFollowers.addNewFollower(buyerOne);
        buyerOne.addNewFollowed(sellerWithFollowers);
        sellerWithFollowers.addNewFollower(buyerTwo);
        buyerTwo.addNewFollowed(sellerWithFollowers);
    }

    @Test
    @DisplayName("Se obtiene la cantidad de seguidores de un vendedor y da 0")
    public void getFollowersCountWithoutFollowers() {
        SellerFollowersCountDto expectedResult = new SellerFollowersCountDto(
                sellerWithoutFollowers.getUser().getId(),
                sellerWithoutFollowers.getUsername(),
                0
        );
        when(sellerRepository
                .findById(sellerWithoutFollowers.getUser().getId()))
                .thenReturn(Optional.of(sellerWithoutFollowers)
                );

        SellerFollowersCountDto actualResult = sellerService.getFollowersCount(102L);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    @DisplayName("Se obtiene la cantidad de seguidores de un vendedor y da 2")
    public void getFollowersCountWithFollowers() {
        SellerFollowersCountDto expectedResult = new SellerFollowersCountDto(
                sellerWithFollowers.getUser().getId(),
                sellerWithFollowers.getUsername(),
                2
        );
        when(sellerRepository
                .findById(sellerWithFollowers.getUser().getId()))
                .thenReturn(Optional.of(sellerWithFollowers)
                );

        SellerFollowersCountDto actualResult = sellerService.getFollowersCount(103L);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    @DisplayName("Se intenta obtener la cantidad de seguidores de un vendedor que no existe y lanza error")
    public void getFollowersCountThrowsSellerNotFound() {
        when(sellerRepository.findById(120L)).thenThrow(new NotFoundException());

        assertThrows(
                NotFoundException.class,
                () -> sellerService.getFollowersCount(102L)
        );
    }
}