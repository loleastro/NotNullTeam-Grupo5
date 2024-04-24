package org.mercadolibre.NotNullTeam.service.impl;

import lombok.RequiredArgsConstructor;
import org.mercadolibre.NotNullTeam.repository.ISellerRepository;
import org.mercadolibre.NotNullTeam.service.ISellerService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements ISellerService {
    final ISellerRepository iSellerRepository;
}
