package org.mercadolibre.NotNullTeam.service.impl;

import lombok.RequiredArgsConstructor;
import org.mercadolibre.NotNullTeam.DTO.request.PostDTO;
import org.mercadolibre.NotNullTeam.DTO.request.ProductDTO;
import org.mercadolibre.NotNullTeam.exception.error.NotFoundException;
import org.mercadolibre.NotNullTeam.model.Post;
import org.mercadolibre.NotNullTeam.model.Product;
import org.mercadolibre.NotNullTeam.model.Seller;
import org.mercadolibre.NotNullTeam.repository.IPostRepository;
import org.mercadolibre.NotNullTeam.repository.ISellerRepository;
import org.mercadolibre.NotNullTeam.service.IPostService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements IPostService {
    final IPostRepository iPostRepository;
    final ISellerRepository iSellerRepository;

    @Override
    public void createPost(PostDTO postDTO) {
        iPostRepository.createPost(postDtoToPost(postDTO));
    }

    private Post postDtoToPost(PostDTO postDTO) {
        return new Post(findSellerById(postDTO.getUser_id()),
                LocalDate.parse(postDTO.getDate(),
                        DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                productDtoToProduct(postDTO.getProduct()), postDTO.getCategory(),
                postDTO.getPrice());
    }

    private Seller findSellerById(Long id){
        return iSellerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Seller"));
    }

    private Product productDtoToProduct(ProductDTO productDTO) {
        return new Product(productDTO.getProduct_id(),
                productDTO.getProduct_name(),
                productDTO.getType(),
                productDTO.getBrand(),
                productDTO.getColor(),
                productDTO.getNotes());
    }
}
