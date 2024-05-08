package org.mercadolibre.NotNullTeam.mapper;

import org.mercadolibre.NotNullTeam.DTO.request.post.PostDTO;
import org.mercadolibre.NotNullTeam.DTO.request.product.ProductDTO;
import org.mercadolibre.NotNullTeam.DTO.response.post.PostResponseDTO;
import org.mercadolibre.NotNullTeam.DTO.response.post.PostsByFollowedDTO;
import org.mercadolibre.NotNullTeam.model.Post;
import org.mercadolibre.NotNullTeam.model.Product;
import org.mercadolibre.NotNullTeam.model.Seller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PostMapper {

    public static PostsByFollowedDTO postToPostByFollowed(Long id, List<Post> posts){
        return new PostsByFollowedDTO(id, posts.stream()
                .map(PostMapper::postToPostResponseDto)
                .toList());
    }

    public static PostResponseDTO postToPostResponseDto(Post post) {
        String formattedDate = post.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        ProductDTO productDTO = productToProductDto(post.getProduct());
        return PostResponseDTO.builder()
                .user_id(post.getSeller().getUser().getId())
                .post_id(post.getId())
                .date(formattedDate)
                .product(productDTO)
                .category(post.getCategory())
                .price(post.getPrice())
                .build();
    }

    public static ProductDTO productToProductDto(Product product) {
        return ProductDTO.builder()
                .product_name(product.getName())
                .type(product.getType())
                .brand(product.getBrand())
                .color(product.getColor())
                .notes(product.getNotes())
                .build();
    }

    public static Post postDtoToPost(PostDTO postDTO, Seller seller) {
        LocalDate formattedDate = LocalDate.parse(
                postDTO.getDate(),
                DateTimeFormatter.ofPattern("dd-MM-yyyy")
        );
        return Post.builder()
                .id(Post.fetchId())
                .seller(seller)
                .date(formattedDate)
                .product(productDtoToProduct(postDTO.getProduct()))
                .category(postDTO.getCategory())
                .price(postDTO.getPrice())
                .build();
    }

    public static Product productDtoToProduct(ProductDTO productDTO) {
        return Product.builder()
                .id(productDTO.getProduct_id())
                .name(productDTO.getProduct_name())
                .type(productDTO.getType())
                .brand(productDTO.getBrand())
                .color(productDTO.getColor())
                .notes(productDTO.getNotes())
                .build();
    }
}
