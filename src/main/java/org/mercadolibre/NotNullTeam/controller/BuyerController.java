package org.mercadolibre.NotNullTeam.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.mercadolibre.NotNullTeam.DTO.response.buyer.BuyerResponseDTO;
import org.mercadolibre.NotNullTeam.DTO.response.buyer.BuyerResponseWithNotSellerListDTO;
import org.mercadolibre.NotNullTeam.service.IBuyerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class BuyerController {

    final IBuyerService iBuyerService;


    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        List<BuyerResponseWithNotSellerListDTO> buyers = iBuyerService.getAll();
        return ResponseEntity.ok(buyers);
    }

    @PostMapping("/{userId}/unfollow/{userIdToUnfollow}")
    public ResponseEntity<?> unfollowSeller(@Valid @Min(value = 1, message = "valor minimo 1") @PathVariable Long userId,
                                            @PathVariable Long userIdToUnfollow) {

        iBuyerService.unfollowSeller(userId, userIdToUnfollow);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{userId}/follow/{userIdToFollow}")
    public ResponseEntity<?> followSeller(@PathVariable Long userId,
                                          @PathVariable Long userIdToFollow) {

        iBuyerService.followSeller(userId, userIdToFollow);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}/followed/list")
    public ResponseEntity<BuyerResponseDTO> getFollowedListOrdered(
            @PathVariable Long userId,
            @RequestParam(name = "order", required = false, defaultValue = "name_asc") String order){
        return new ResponseEntity<>(
                iBuyerService.getFollowedListOrdered(userId, order),
                HttpStatus.OK
        );
    }

}
