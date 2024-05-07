package org.mercadolibre.NotNullTeam.service.impl;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mercadolibre.NotNullTeam.DTO.response.buyer.BuyerResponseWithNotSellerListDTO;
import org.mercadolibre.NotNullTeam.DTO.response.seller.SellerFollowersCountDto;
import org.mercadolibre.NotNullTeam.DTO.response.seller.SellerResponseDTO;
import org.mercadolibre.NotNullTeam.exception.error.NotFoundException;
import org.mercadolibre.NotNullTeam.model.Buyer;
import org.mercadolibre.NotNullTeam.model.Seller;
import org.mercadolibre.NotNullTeam.model.User;
import org.mercadolibre.NotNullTeam.repository.ISellerRepository;
import org.mercadolibre.NotNullTeam.util.TypeOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
class SellerServiceImplTest {

    @InjectMocks
    private SellerServiceImpl sellerService;
    @Mock
    ISellerRepository sellerRepository;


    Seller seller;
    Buyer buyerA;
    Buyer buyerC;
    Buyer buyerB;
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
        seller = new Seller(
                new User(1L, "UsuarioUno"),
                new ArrayList<>()
        );
        buyerA = new Buyer(
                new User(2L, "A"),
                new ArrayList<>()
        );
        buyerC = new Buyer(
                new User(4L, "C"),
                new ArrayList<>()
        );
        buyerB = new Buyer(
                new User(3L, "B"),
                new ArrayList<>()
        );
        sellerWithFollowers.addNewFollower(buyerOne);
        buyerOne.addNewFollowed(sellerWithFollowers);
        sellerWithFollowers.addNewFollower(buyerTwo);
        buyerTwo.addNewFollowed(sellerWithFollowers);
    }

    @Test
    @DisplayName("Se obtiene la cantidad de seguidores de un vendedor y da 0")
    public void getFollowersCountWithoutFollowers() {
        SellerFollowersCountDto expectedResult = new SellerFollowersCountDto(sellerWithoutFollowers
                .getUser()
                .getId(), sellerWithoutFollowers.getUsername(), 0);
        when(sellerRepository.findById(sellerWithoutFollowers.getUser().getId())).thenReturn(
                Optional.of(sellerWithoutFollowers));

        SellerFollowersCountDto actualResult = sellerService.getFollowersCount(102L);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    @DisplayName("Se obtiene la cantidad de seguidores de un vendedor y da 2")
    public void getFollowersCountWithFollowers() {
        SellerFollowersCountDto expectedResult = new SellerFollowersCountDto(sellerWithFollowers
                .getUser()
                .getId(), sellerWithFollowers.getUsername(), 2);
        when(sellerRepository.findById(sellerWithFollowers
                .getUser()
                .getId())).thenReturn(Optional.of(sellerWithFollowers));

        SellerFollowersCountDto actualResult = sellerService.getFollowersCount(103L);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    @DisplayName("Se intenta obtener la cantidad de seguidores de un vendedor que no existe y lanza error")
    public void getFollowersCountThrowsSellerNotFound() {
        when(sellerRepository.findById(120L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> sellerService.getFollowersCount(102L));
    }

    @Test
    @DisplayName("Obtener lista de seguidores ordenada por nombre ascendente")
    void getListFollowersOrdered() {
        // Arrange

        seller.setFollowersList(new ArrayList<>(Arrays.asList(buyerC, buyerB, buyerA)));

        SellerResponseDTO expectedSeller = new SellerResponseDTO(seller.getUser().getId(),
                seller.getUser().getName(),
                new ArrayList<>(List.of(new BuyerResponseWithNotSellerListDTO(buyerA
                                .getUser()
                                .getId(), buyerA.getUser().getName()),
                        new BuyerResponseWithNotSellerListDTO(buyerB.getUser().getId(),
                                buyerB.getUser().getName()),
                        new BuyerResponseWithNotSellerListDTO(buyerC.getUser().getId(),
                                buyerC.getUser().getName())

                )));

        when(sellerRepository.findById(1L)).thenReturn(Optional.of(seller));

        // Act
        SellerResponseDTO responseSeller = sellerService.getListFollowersOrdered(seller
                .getUser()
                .getId(), TypeOrder.NAME_ASC);

        // Assert
        assertEquals(expectedSeller, responseSeller);
    }

    @Test
    @DisplayName("Obtener lista de seguidores ordenada por nombre descendente")
    void getListFollowersOrderedDesc() {
        // Arrange
        seller.setFollowersList(new ArrayList<>(Arrays.asList(buyerA, buyerB, buyerC)));

        SellerResponseDTO expectedSeller = new SellerResponseDTO(seller.getUser().getId(),
                seller.getUser().getName(),
                new ArrayList<>(List.of(new BuyerResponseWithNotSellerListDTO(buyerC
                                .getUser()
                                .getId(), buyerC.getUser().getName()),
                        new BuyerResponseWithNotSellerListDTO(buyerB.getUser().getId(),
                                buyerB.getUser().getName()),
                        new BuyerResponseWithNotSellerListDTO(buyerA.getUser().getId(),
                                buyerA.getUser().getName())

                )));

        when(sellerRepository.findById(1L)).thenReturn(Optional.of(seller));

        // Act
        SellerResponseDTO responseSeller = sellerService.getListFollowersOrdered(seller
                .getUser()
                .getId(), TypeOrder.NAME_DESC);

        // Assert
        assertEquals(expectedSeller, responseSeller);
    }

    @Test
    @DisplayName("Obtener la lista de seguidores de un vendedor que no existe se espera una excepcion")
    void getFollowersListByUnknowUserThrowsException() {
        // Arrange
        when(sellerRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class,
                () -> sellerService.getListFollowersOrdered(1L, TypeOrder.NAME_DESC));
    }
}