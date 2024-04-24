package org.mercadolibre.NotNullTeam.repository;

import org.mercadolibre.NotNullTeam.model.Buyer;
import org.mercadolibre.NotNullTeam.model.Seller;

import java.util.Optional;

public interface IBuyerRepository {

    Optional<Buyer> findById(Long id);
    void followSeller(Buyer buyer, Seller seller);

    void save(Buyer buyer);
}
