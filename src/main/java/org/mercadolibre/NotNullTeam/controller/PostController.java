package org.mercadolibre.NotNullTeam.controller;

import lombok.RequiredArgsConstructor;
import org.mercadolibre.NotNullTeam.DTO.request.PostDTO;
import org.mercadolibre.NotNullTeam.service.IPostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PostController {
    final IPostService iPostService;

    @PostMapping("/products/post")
    public ResponseEntity<?> createPost(@RequestBody PostDTO postDTO) {
        iPostService.createPost(postDTO);
        return ResponseEntity.ok("Product created successfully!");
    }

    @GetMapping("/products/followed/{userId}/list")
    public ResponseEntity<?> getPostsBySellerTwoWeeksAgo(@PathVariable Long userId) {
        return ResponseEntity.ok(iPostService.getPostsBySellerTwoWeeksAgo(userId));
    }
}
