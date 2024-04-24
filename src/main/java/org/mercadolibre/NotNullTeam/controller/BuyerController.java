package org.mercadolibre.NotNullTeam.controller;

import lombok.RequiredArgsConstructor;
import org.mercadolibre.NotNullTeam.model.Buyer;
import org.mercadolibre.NotNullTeam.service.IBuyerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BuyerController {

    final IBuyerService iBuyerService;


    @GetMapping("/users/all")
    public ResponseEntity<?> getAll() {
        List<Buyer> buyers = iBuyerService.getAll();
        return ResponseEntity.ok(buyers);
    }

    @PostMapping("/users/{userId}/follow/{userIdToFollow}")
    public ResponseEntity<?> followSeller(@PathVariable Long userId,
                                          @PathVariable Long userIdToFollow) {

        iBuyerService.followSeller(userId, userIdToFollow);
//? o 204
        return ResponseEntity.ok().build();
    }

}
