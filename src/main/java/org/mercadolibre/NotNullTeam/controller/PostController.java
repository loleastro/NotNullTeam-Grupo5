package org.mercadolibre.NotNullTeam.controller;

import lombok.RequiredArgsConstructor;
import org.mercadolibre.NotNullTeam.DTO.request.PostDTO;
import org.mercadolibre.NotNullTeam.service.IPostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostController {
    final IPostService iPostService;

    @PostMapping("/products/post")
    public ResponseEntity<?> createPost(@RequestBody PostDTO postDTO) {
        iPostService.createPost(postDTO);
        return ResponseEntity.ok("Product created successfully!");
    }
}
