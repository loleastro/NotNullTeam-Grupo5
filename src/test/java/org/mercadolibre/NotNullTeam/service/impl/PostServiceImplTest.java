package org.mercadolibre.NotNullTeam.service.impl;

import org.junit.jupiter.api.Assertions;
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
import org.mercadolibre.NotNullTeam.util.TypeOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
class PostServiceImplTest {
    @Mock
    IPostRepository postRepository;
    @Mock
    IBuyerRepository iBuyerRepository;

    @InjectMocks
    PostServiceImpl postService;

    private Buyer buyer;
    private Seller seller;
    private int WEEKS = 2;
    private List<Post> postsReturn;

    @BeforeEach
    public void setup() {
        //TODO: implementar builders.
        seller = new Seller(new User(2L, "SecondUser"), new ArrayList<>());
        buyer = new Buyer(new User(1L, "FirstUser"), List.of(seller));

        postsReturn = new ArrayList<>() {
            {
                add(new Post(seller,
                        LocalDate.now().minusDays(10),
                        new Product(1L,
                                "Product1",
                                "Chair",
                                "Gamer",
                                "White",
                                "Very gamer with RGB"),
                        2,
                        100.0));
                add(new Post(seller,
                        LocalDate.now().minusDays(5),
                        new Product(2L,
                                "Product2",
                                "Chair",
                                "Gamer",
                                "White",
                                "Very gamer with RGB"),
                        2,
                        100.0));
            }
        };
    }

    @Test
    @DisplayName("Se obtiene la lista de posteos de los sellers seguidos por un buyer x de hace dos semanas")
    void getPostsByWeeksAgo() {
        when(iBuyerRepository.findById(1L)).thenReturn(Optional.of(buyer));
        when(postRepository.getPostsByWeeksAgo(WEEKS, seller.getUser().getId())).thenReturn(
                postsReturn);

        PostsByFollowedDTO expected = PostMapper.postToPostByFollowed(buyer.getUser().getId(),
                postsReturn);

        // Act
        PostsByFollowedDTO result = postService.getPostsByWeeksAgo(1L, TypeOrder.DATE_ASC);

        assertEquals(2, result.getPosts().size());
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("verificar que el DATE_ASC sea valido como parametro en la funcion getPostsByWeeksAgo")
    public void testGetPostsByDateAscExists() {
        when(iBuyerRepository.findById(1L)).thenReturn(Optional.of(buyer));
        when(postRepository.getPostsByWeeksAgo(2, 1L)).thenReturn(new ArrayList<>());

        PostsByFollowedDTO expected = new PostsByFollowedDTO(1L, new ArrayList<>());

        PostsByFollowedDTO obtainedAsc = postService.getPostsByWeeksAgo(1L, TypeOrder.DATE_ASC);

        Assertions.assertEquals(expected, obtainedAsc);
    }

    @Test
    @DisplayName("verificar que el DATE_DESC sea valido como parametro en la funcion getPostsByWeeksAgo")
    public void testGetPostsByDateDescExists() {
        when(iBuyerRepository.findById(1L)).thenReturn(Optional.of(buyer));
        when(postRepository.getPostsByWeeksAgo(2, 1L)).thenReturn(new ArrayList<>());

        PostsByFollowedDTO expected = new PostsByFollowedDTO(1L, new ArrayList<>());

        PostsByFollowedDTO obtainedDesc = postService.getPostsByWeeksAgo(1L, TypeOrder.DATE_DESC);

        Assertions.assertEquals(expected, obtainedDesc);
    }

    @Test
    @DisplayName("verificar que se lanze una excepcion cuando el order no sea valido en la funcion getPostsByWeeksAgo")
    public void testGetPostsByDateInvalidOrder() {
        when(iBuyerRepository.findById(1L)).thenReturn(Optional.of(buyer));
        when(postRepository.getPostsByWeeksAgo(2, 1L)).thenReturn(new ArrayList<>());

        String order = "invalid";

        Assertions.assertThrows(InvalidParameterException.class, () -> {
            postService.getPostsByWeeksAgo(1L, order);
        });
    }

    @Test
    @DisplayName("obtener los posteos de las ultimas dos semanas de los sellers que un buyer x sigue, con un tipo de " +
            "order valido (DATE_ASC)")
    void testGetPostsByWeeksAgoOrderAsc() {
        when(iBuyerRepository.findById(1L)).thenReturn(Optional.of(buyer));
        when(postRepository.getPostsByWeeksAgo(WEEKS, 2L)).thenReturn(postsReturn);

        PostsByFollowedDTO postsByFollowedDTO = postService.getPostsByWeeksAgo(1L,
                TypeOrder.DATE_ASC);

        assertEquals(PostMapper.postToPostByFollowed(buyer.getUser().getId(), postsReturn),
                postsByFollowedDTO);
    }

    @Test
    @DisplayName("obtener los posteos de las ultimas dos semanas de los sellers que un buyer x sigue, con un tipo de " +
            "order valido (DATE_DESC)")
    void testGetPostsByWeeksAgoOrderDesc() {
        when(iBuyerRepository.findById(1L)).thenReturn(Optional.of(buyer));
        when(postRepository.getPostsByWeeksAgo(WEEKS, 2L)).thenReturn(postsReturn);

        postsReturn.sort(Comparator.comparing(Post::getDate).reversed());

        PostsByFollowedDTO postsByFollowedDTO = postService.getPostsByWeeksAgo(1L,
                TypeOrder.DATE_DESC);

        assertEquals(PostMapper.postToPostByFollowed(buyer.getUser().getId(), postsReturn),
                postsByFollowedDTO);
    }

    @Test
    @DisplayName("obtener los posteos de las ultimas dos semanas de los sellers que un buyer x sigue, con un tipo de " +
            "order valido (DATE_ASC) y no se encuentran posteos")
    void testGetPostsByWeeksAgoOrderAscEmpty() {
        postsReturn = new ArrayList<>();

        when(iBuyerRepository.findById(1L)).thenReturn(Optional.of(buyer));
        when(postRepository.getPostsByWeeksAgo(WEEKS, 2L)).thenReturn(postsReturn);

        PostsByFollowedDTO postsByFollowedDTO = postService.getPostsByWeeksAgo(1L,
                TypeOrder.DATE_ASC);

        assertEquals(PostMapper.postToPostByFollowed(buyer.getUser().getId(), postsReturn),
                postsByFollowedDTO);
    }

    @Test
    @DisplayName("obtener los posteos de las ultimas dos semanas de los sellers que un buyer desconocido sigue, lanza" +
            " excepcion")
    void testGetPostsByWeeksAgoOrderWithNonExistentId() {
        when(iBuyerRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class,
                () -> postService.getPostsByWeeksAgo(1L, TypeOrder.DATE_ASC));
    }
}
