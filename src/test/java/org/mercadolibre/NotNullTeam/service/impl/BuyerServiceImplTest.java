package org.mercadolibre.NotNullTeam.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mercadolibre.NotNullTeam.model.Buyer;
import org.mercadolibre.NotNullTeam.model.Seller;
import org.mercadolibre.NotNullTeam.model.User;
import org.mercadolibre.NotNullTeam.repository.IBuyerRepository;
import org.mercadolibre.NotNullTeam.repository.ISellerRepository;
import org.mercadolibre.NotNullTeam.service.ISellerService;
import org.mercadolibre.NotNullTeam.service.ISellerServiceInternal;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class BuyerServiceImplTest {
    @Mock
    IBuyerRepository buyerRepository;
    @Mock
    ISellerRepository sellerRepository;
    @Mock
    ISellerServiceInternal sellerServiceInternal;
    @InjectMocks
    BuyerServiceImpl buyerService;

    private Buyer buyer;
    private Seller seller;

    @BeforeEach
    public void setup() {
        this.buyer = new Buyer(
                new User(1L, "UsuarioUno"),
                new ArrayList<>()
        );
        this.seller = new Seller(
                new User(2L, "UsuarioDos"),
                new ArrayList<>()
        );
    }

    @Test
    @DisplayName("Se sigue a un seller que existe")
    void followSellerSuccesfully() {
        when(buyerRepository.findById(1L)).thenReturn(Optional.of(buyer));
        when(sellerServiceInternal.findById(2L)).thenReturn(seller);

        buyerService.followSeller(buyer.getUser().getId(), seller.getUser().getId());

        verify(buyerRepository, atLeast(1)).update(any());
        verify(sellerRepository, atLeast(1)).update(any());
    }

    @Test
    void getAll() {
    }

    @Test
    void getFollowedListOrdered() {
    }

    @Test
    void unfollowSeller() {
    }

    @Test
    void findBuyerById() {
    }
}