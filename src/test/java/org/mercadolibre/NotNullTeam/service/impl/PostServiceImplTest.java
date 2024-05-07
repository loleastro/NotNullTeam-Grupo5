package org.mercadolibre.NotNullTeam.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mercadolibre.NotNullTeam.DTO.response.post.PostsByFollowedDTO;
import org.mercadolibre.NotNullTeam.exception.error.InvalidParameterException;
import org.mercadolibre.NotNullTeam.exception.error.NotFoundException;
import org.mercadolibre.NotNullTeam.mapper.PostMapper;
import org.mercadolibre.NotNullTeam.model.*;
import org.mercadolibre.NotNullTeam.repository.IBuyerRepository;
import org.mercadolibre.NotNullTeam.repository.IPostRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class PostServiceImplTest {
    @Mock
    IPostRepository iPostRepository;
    @Mock
    IBuyerRepository iBuyerRepository;

    @InjectMocks
    PostServiceImpl postService;

    private int WEEKS = 2;

    private Seller seller;
    private Buyer buyer;
    private List<Post> postsReturn;

    @BeforeEach
    public void setup() {
        seller = new Seller(new User(2L, "SecondUser"), new ArrayList<>());
        buyer = new Buyer(new User(1L, "FirstUser"), List.of(seller));
        postsReturn = new ArrayList<>(){
            {
                add(new Post(seller, LocalDate.now().minusDays(10),
                        new Product(1L,
                                "Product1",
                                "Chair",
                                "Gamer",
                                "White",
                                "Very gamer with RGB"),
                        2,
                        100.0
                ));
                add(new Post(seller, LocalDate.now().minusDays(5),
                        new Product(2L,
                                "Product2",
                                "Chair",
                                "Gamer",
                                "White",
                                "Very gamer with RGB"),
                        2,
                        100.0
                ));
            }
        };
    }

    @Test
    @DisplayName("Se obtiene la lista de posteos de los sellers seguidos por un buyer ordenados por fecha ascendente.")
    void testGetPostsByWeeksAgoOrderAsc() {
        when(iBuyerRepository.findById(1L)).thenReturn(Optional.of(buyer));
        when(iPostRepository.getPostsByWeeksAgo(WEEKS, 2L)).thenReturn(postsReturn);

        PostsByFollowedDTO postsByFollowedDTO = postService.getPostsByWeeksAgo(1L, "date_asc");

        assertEquals(PostMapper.postToPostByFollowed(buyer.getUser().getId(), postsReturn), postsByFollowedDTO);
    }

    @Test
    @DisplayName("Se obtiene la lista de posteos de los sellers seguidos por un buyer ordenados por fecha descendente.")
    void testGetPostsByWeeksAgoOrderDesc() {
        when(iBuyerRepository.findById(1L))
                .thenReturn(Optional.of(buyer));
        when(iPostRepository.getPostsByWeeksAgo(WEEKS, 2L))
                .thenReturn(postsReturn);

        postsReturn.sort(Comparator.comparing(Post::getDate).reversed());

        PostsByFollowedDTO postsByFollowedDTO = postService.getPostsByWeeksAgo(1L, "date_desc");

        assertEquals(PostMapper.postToPostByFollowed(buyer.getUser().getId(), postsReturn), postsByFollowedDTO);
    }

    @Test
    @DisplayName("Se obtiene la lista de posteos, siendo estos una lista vacia, por lo que retorna un PostsByFollowedDTO con el atributo posts vacio.")
    void testGetPostsByWeeksAgoOrderAscEmpty() {
        postsReturn = new ArrayList<>();

        when(iBuyerRepository.findById(1L))
                .thenReturn(Optional.of(buyer));
        when(iPostRepository.getPostsByWeeksAgo(WEEKS, 2L))
                .thenReturn(postsReturn);

        PostsByFollowedDTO postsByFollowedDTO = postService.getPostsByWeeksAgo(1L, "date_asc");

        assertEquals(PostMapper.postToPostByFollowed(buyer.getUser().getId(), postsReturn), postsByFollowedDTO);
    }

    @Test
    @DisplayName("Se intenta realizar la busqueda de posts pero no se logra encontrar a el buyer con el id solicitado, por lo que lanza NotFoundException.")
    void testGetPostsByWeeksAgoOrderWithNonExistentId() {
        when(iBuyerRepository.findById(1L))
                .thenThrow(NotFoundException.class);
        assertThrows(
                NotFoundException.class,
                () -> postService.getPostsByWeeksAgo(1L, "date_asc")
        );
    }
}