package org.mercadolibre.NotNullTeam.bootstrap;

import org.mercadolibre.NotNullTeam.model.*;
import org.mercadolibre.NotNullTeam.repository.IBuyerRepository;
import org.mercadolibre.NotNullTeam.repository.ISellerRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        Buyer buyerOne = new Buyer(userOne, null);
        Buyer buyerTwo = new Buyer(userTwo, null);
        Seller sellerOne = new Seller(userThree, List.of(buyerOne, buyerTwo));

        buyerOne.setFollowedList(List.of(sellerOne));
        buyerTwo.setFollowedList(List.of(sellerOne));

        buyerRepository.save(buyerOne);
        buyerRepository.save(buyerTwo);

        sellerRepository.save(sellerOne);
    }
}
