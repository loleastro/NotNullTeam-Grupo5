package org.mercadolibre.NotNullTeam.bootstrap;

import org.mercadolibre.NotNullTeam.model.*;
import org.mercadolibre.NotNullTeam.repository.IBuyerRepository;
import org.mercadolibre.NotNullTeam.repository.ISellerRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Bootstrap implements InitializingBean {

    @Autowired
    private IBuyerRepository buyerRepository;

    @Autowired
    private ISellerRepository sellerRepository;

    @Override
    public void afterPropertiesSet() {
        User userOne = new User(1L, "Juan Perez");
        User userTwo = new User(2L, "Maria Lopez");
        User userThree = new User(3L, "Pedro Gomez");

        Buyer buyerOne = new Buyer(userOne, new ArrayList<>());
        Buyer buyerTwo = new Buyer(userTwo, new ArrayList<>());
        Seller sellerOne = new Seller(userThree,  new ArrayList<>(List.of(buyerOne, buyerTwo)));

        buyerOne.addNewFollowed(sellerOne);
        buyerTwo.addNewFollowed(sellerOne);

        buyerRepository.save(buyerOne);
        buyerRepository.save(buyerTwo);

        sellerRepository.save(sellerOne);
    }
}
