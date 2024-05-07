package org.mercadolibre.NotNullTeam.service.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mercadolibre.NotNullTeam.DTO.response.buyer.BuyerResponseDTO;
import org.mercadolibre.NotNullTeam.DTO.response.buyer.BuyerResponseWithNotSellerListDTO;
import org.mercadolibre.NotNullTeam.DTO.response.seller.SellerResponseDTO;
import org.mercadolibre.NotNullTeam.exception.error.NotFoundException;
import org.mercadolibre.NotNullTeam.model.Buyer;
import org.mercadolibre.NotNullTeam.model.Seller;
import org.mercadolibre.NotNullTeam.model.User;
import org.mercadolibre.NotNullTeam.repository.ISellerRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class SellerServiceImplTest {

    @Mock
    ISellerRepository iSellerRepository;

    @InjectMocks
    SellerServiceImpl sellerService;
    Seller seller;
    Buyer buyerA;
    Buyer buyerC;
    Buyer buyerB;

    @BeforeEach
    void setUpBeforeEach() {
        this.seller = new Seller(
                new User(1L, "UsuarioUno"),
                new ArrayList<>()
        );
        this.buyerA = new Buyer(
                new User(2L, "A"),
                new ArrayList<>()
        );
        this.buyerC = new Buyer(
                new User(4L, "C"),
                new ArrayList<>()
        );
        this.buyerB = new Buyer(
                new User(3L, "B"),
                new ArrayList<>()
        );
    }

    @Test
    @DisplayName("get followers list ordered by name_asc")
    void getListFollowersOrdered() {
        // Arrange

        seller.setFollowersList(new ArrayList<>(Arrays.asList(buyerC, buyerB, buyerA)));

        SellerResponseDTO expectedSeller = new SellerResponseDTO(
                seller.getUser().getId(),
                seller.getUser().getName(),
                new ArrayList<>(List.of(
                        new BuyerResponseWithNotSellerListDTO(buyerA.getUser().getId(), buyerA.getUser().getName()),
                        new BuyerResponseWithNotSellerListDTO(buyerB.getUser().getId(), buyerB.getUser().getName()),
                        new BuyerResponseWithNotSellerListDTO(buyerC.getUser().getId(), buyerC.getUser().getName())

                ))
        );

        when(iSellerRepository.findById(1L)).thenReturn(Optional.of(seller));

        // Act
        SellerResponseDTO responseSeller = sellerService.getListFollowersOrdered(seller.getUser().getId(), "name_asc");

        // Assert
        assertEquals(expectedSeller, responseSeller);
    }

    @Test
    @DisplayName("get followers list ordered by name_desc")
    void getListFollowersOrderedDesc() {
        // Arrange
        seller.setFollowersList(new ArrayList<>(Arrays.asList(buyerA,buyerB,buyerC)));

        SellerResponseDTO expectedSeller = new SellerResponseDTO(
                seller.getUser().getId(),
                seller.getUser().getName(),
                new ArrayList<>(List.of(
                        new BuyerResponseWithNotSellerListDTO(buyerC.getUser().getId(), buyerC.getUser().getName()),
                        new BuyerResponseWithNotSellerListDTO(buyerB.getUser().getId(), buyerB.getUser().getName()),
                        new BuyerResponseWithNotSellerListDTO(buyerA.getUser().getId(), buyerA.getUser().getName())

                ))
        );

        when(iSellerRepository.findById(1L)).thenReturn(Optional.of(seller));

        // Act
        SellerResponseDTO responseSeller = sellerService.getListFollowersOrdered(seller.getUser().getId(), "name_desc");

        // Assert
        assertEquals(expectedSeller, responseSeller);
    }

    @Test
    @DisplayName("get followers list by unknow user throws exception")
    void getFollowersListByUnknowUserThrowsException() {
        // Arrange
        when(iSellerRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(
                NotFoundException.class,
                () -> sellerService.getListFollowersOrdered(1L, "name_desc")
        );
    }
}