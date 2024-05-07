package org.mercadolibre.NotNullTeam.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mercadolibre.NotNullTeam.DTO.request.post.PostDTO;
import org.mercadolibre.NotNullTeam.DTO.response.post.PostsByFollowedDTO;
import org.mercadolibre.NotNullTeam.mapper.PostMapper;
import org.mercadolibre.NotNullTeam.model.*;
import org.mercadolibre.NotNullTeam.repository.IBuyerRepository;
import org.mercadolibre.NotNullTeam.repository.IPostRepository;
import org.mercadolibre.NotNullTeam.repository.ISellerRepository;
import org.mercadolibre.NotNullTeam.service.IPostService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class PostServiceImplTest {
    @Mock
    IPostRepository postRepository;
    @Mock
    ISellerRepository iSellerRepository;
    @Mock
    IBuyerRepository iBuyerRepository;

    @InjectMocks
    PostServiceImpl postService;


    private Buyer buyer;
    private Seller seller;
    private Post post1;
    private Post post2;
    private PostMapper postMapper;
    private LocalDate fecha ;
    private Product product ;
    private int categoria ;
    private Double precio;

    @BeforeEach
    public void setup() {
        this.buyer = new Buyer(
                new User(1L, "UsuarioUno"),
                new ArrayList<>()
        );
        this.seller = new Seller(
                new User(2L, "UsuarioDos"),
                new ArrayList<>()
        );

        this.postMapper = new PostMapper();
        this.fecha =LocalDate.now().minusDays(10); // Fecha hace 10 días
        this.product = new Product();
        this.categoria = 1; // Categoría de ejemplo
        this.precio = 100.0; // Precio de ejemplo

        this.post1 = new Post( seller, fecha, product, categoria, precio);
        this.post2 = new Post( seller, fecha, product, categoria, precio);



    }


    @Test
    @DisplayName("Verificar que se devuelven solo las publicaciones de las últimas dos semanas")
    void getPostsByWeeksAgo() {
        // Arrange
        List<Seller> followedList = Arrays.asList(seller);
        buyer.setFollowedList(followedList);

        when(iBuyerRepository.findById(1L)).thenReturn(Optional.of(buyer));

        //devuelvo la lista de posts
        when(postRepository.getPostsByWeeksAgo
                (2, seller.getUser().getId())).thenReturn(Arrays.asList(post1, post2));

        PostsByFollowedDTO expected =
                PostMapper.postToPostByFollowed(buyer.getUser().getId(), Arrays.asList(post1, post2));

        // Act
        PostsByFollowedDTO result = postService.getPostsByWeeksAgo(1L, "date_asc");

        // Assert
        assertEquals(2, result.getPosts().size());
        assertEquals(expected, result);
    }
}