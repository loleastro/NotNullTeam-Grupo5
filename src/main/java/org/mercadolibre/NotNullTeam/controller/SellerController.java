package org.mercadolibre.NotNullTeam.controller;

import lombok.RequiredArgsConstructor;
import org.mercadolibre.NotNullTeam.DTO.response.SellerFollowersCountDto;
import org.mercadolibre.NotNullTeam.service.ISellerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SellerController {
    final ISellerService iSellerService;

    @GetMapping("/users/{userId}/followers/count")
    public ResponseEntity<SellerFollowersCountDto> getFollowersCount(@PathVariable Long userId) {
        return ResponseEntity.ok(iSellerService.getFollowersCount(userId));
    }

    @GetMapping("users/{userId}/followers/list")
    public ResponseEntity<?> getListFollowers(@PathVariable Long userId) {
        return new ResponseEntity<>(iSellerService.getListFollowers(userId), HttpStatus.OK);
    }

}
