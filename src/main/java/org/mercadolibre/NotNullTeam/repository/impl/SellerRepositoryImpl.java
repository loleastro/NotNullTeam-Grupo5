package org.mercadolibre.NotNullTeam.repository.impl;

import org.mercadolibre.NotNullTeam.model.Seller;
import org.mercadolibre.NotNullTeam.repository.ISellerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class SellerRepositoryImpl implements ISellerRepository {

    List<Seller> sellers = new ArrayList<>();

    @Override
    public Optional<Seller> findById(Long id) {
        return sellers.stream().filter(
                b -> Objects.equals(b.getUser().getId(), id)
        ).findFirst();
    }
}
