package org.mercadolibre.NotNullTeam.controller;

import lombok.RequiredArgsConstructor;
import org.mercadolibre.NotNullTeam.repository.ISellerRepository;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SellerController {
    final ISellerRepository iSellerRepository;
}
