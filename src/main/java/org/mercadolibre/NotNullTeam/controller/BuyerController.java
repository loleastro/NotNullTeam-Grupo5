package org.mercadolibre.NotNullTeam.controller;

import lombok.RequiredArgsConstructor;
import org.mercadolibre.NotNullTeam.DTO.response.BuyerResponseDTO;
import org.mercadolibre.NotNullTeam.DTO.response.BuyerResponseWithNotSellerListDTO;
import org.mercadolibre.NotNullTeam.service.IBuyerService;
import org.springframework.http.HttpStatus;
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
        List<BuyerResponseWithNotSellerListDTO> buyers = iBuyerService.getAll();
        return ResponseEntity.ok(buyers);
    }

    @PostMapping("/users/{userId}/follow/{userIdToFollow}")
    public ResponseEntity<?> followSeller(@PathVariable Long userId,
                                          @PathVariable Long userIdToFollow) {

        iBuyerService.followSeller(userId, userIdToFollow);
//? o 204
        return ResponseEntity.ok().build();
    }

    @GetMapping("/users/{userId}/followed/list")
    public ResponseEntity<BuyerResponseDTO> getFollowedList(@PathVariable Long userId){
        return new ResponseEntity<>(iBuyerService.getFollowedList(userId), HttpStatus.OK);
    }

}
