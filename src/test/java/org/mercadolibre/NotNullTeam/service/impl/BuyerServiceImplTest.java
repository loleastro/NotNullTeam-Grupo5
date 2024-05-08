package org.mercadolibre.NotNullTeam.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mercadolibre.NotNullTeam.exception.error.InvalidParameterException;
import org.mercadolibre.NotNullTeam.exception.error.NotFoundException;
import org.mercadolibre.NotNullTeam.model.Buyer;
import org.mercadolibre.NotNullTeam.model.Seller;
import org.mercadolibre.NotNullTeam.model.User;
import org.mercadolibre.NotNullTeam.repository.IBuyerRepository;
import org.mercadolibre.NotNullTeam.repository.ISellerRepository;
import org.mercadolibre.NotNullTeam.service.ISellerServiceInternal;
import org.mercadolibre.NotNullTeam.util.TypeOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        User buyerUser = User.builder().id(1L).name("UsuarioUno").build();
        User sellerUser = User.builder().id(2L).name("UsuarioDos").build();

        buyer = Buyer.builder().user(buyerUser).followedList(new ArrayList<>()).build();
        seller = Seller.builder().user(sellerUser).followersList(new ArrayList<>()).build();
    }

    @Test
    @DisplayName("Se sigue a un seller que existe con exito")
    //TODO: nacho sigue a eze con exito. caso concreto. preciso.
    void followSellerSuccessfully() {
        when(buyerRepository.findById(1L)).thenReturn(Optional.of(buyer));
        when(sellerServiceInternal.findById(2L)).thenReturn(seller);

        buyerService.followSeller(buyer.getUser().getId(), seller.getUser().getId());

        verify(buyerRepository, atLeast(1)).update(any());
        verify(sellerRepository, atLeast(1)).update(any());
        //todo: verificar que los recursos se encuentren en ambas listas
    }

    @Test
    @DisplayName("Se sigue a un seller que no existe y lanza error")
    void followSellerThrowsSellerNotFound() {
        when(buyerRepository.findById(1L)).thenReturn(Optional.of(buyer));
        when(sellerServiceInternal.findById(3L)).thenThrow(new NotFoundException());

        assertThrows(NotFoundException.class,
                () -> buyerService.followSeller(buyer.getUser().getId(), 3L));
    }

    @Test
    @DisplayName("Se deja de seguir a un seller que existe con exito")
    void testUnfollowSellerSuccessfully() {
        when(buyerRepository.findById(1L)).thenReturn(Optional.of(buyer));
        when(sellerServiceInternal.findById(2L)).thenReturn(seller);

        buyerService.unfollowSeller(buyer.getUser().getId(), seller.getUser().getId());

        verify(buyerRepository, atLeast(1)).update(any());
        verify(sellerRepository, atLeast(1)).update(any());
    }

    @Test
    @DisplayName("Se sigue a un seller que no existe y lanza error")
    void unfollowSellerThrowsSellerNotFound() {
        when(buyerRepository.findById(1L)).thenReturn(Optional.of(buyer));
        when(sellerServiceInternal.findById(3L)).thenThrow(new NotFoundException());

        assertThrows(NotFoundException.class,
                () -> buyerService.unfollowSeller(buyer.getUser().getId(), 3L));
    }


    @Test
    @DisplayName("El tipo de ordenamiento es valido por param name_asc")
    void getFollowedListOrderedNameAscSuccessfully() {
        when(buyerRepository.findById(1L)).thenReturn(Optional.of(buyer));
        assertNotNull(buyerService.getFollowedListOrdered(1L, TypeOrder.NAME_ASC));
    }

    @Test
    @DisplayName("El tipo de ordenamiento es valido por param name_desc")
    void getFollowedListOrderedNameDescSuccessfully() {
        when(buyerRepository.findById(1L)).thenReturn(Optional.of(buyer));
        assertNotNull(buyerService.getFollowedListOrdered(1L, TypeOrder.NAME_ASC));
    }


    @Test
    @DisplayName("El tipo de ordenamiento NO es valido")
    void getFollowedListOrderedInvalidParameterException() {
        when(buyerRepository.findById(1L)).thenReturn(Optional.of(buyer));
        String invalidParam = "name_invalid";
        assertThrows(InvalidParameterException.class,
                () -> buyerService.getFollowedListOrdered(1L, invalidParam));
    }


}