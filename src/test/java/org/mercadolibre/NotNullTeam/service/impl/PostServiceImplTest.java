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
        User buyerUser = User.builder().id(1L).name("FirstUser").build();
        User sellerUser = User.builder().id(2L).name("SecondUser").build();

        seller = Seller.builder().user(sellerUser).followersList(new ArrayList<>()).build();
        buyer = Buyer.builder().user(buyerUser).followedList(List.of(seller)).build();

        Product productOne = Product.builder()
                .id(1L)
                .name("Product1")
                .type("Chair")
                .brand("Gamer")
                .color("White")
                .notes("Very gamer with RGB")
                .build();

        Product productTwo = Product.builder()
                .id(2L)
                .name("Product2")
                .type("Chair")
                .brand("Gamer")
                .color("White")
                .notes("Very gamer with RGB")
                .build();

        Post postOne = Post.builder()
                .seller(seller)
                .date(LocalDate.now().minusDays(10))
                .product(productOne)
                .category(2)
                .price(100.0)
                .build();

        Post postTwo = Post.builder()
                .seller(seller)
                .date(LocalDate.now().minusDays(5))
                .product(productTwo)
                .category(3)
                .price(150.0)
                .build();

        postsReturn = new ArrayList<>() {
            {
                add(postOne);
                add(postTwo);
            }
        };
    }

    @Test
    @DisplayName("Verificar que se devuelven solo las publicaciones de las últimas dos semanas")
    void getPostsByWeeksAgo() {
        //TODO: testear sobre el repo la logica core del filtrado.
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
    @DisplayName("Se verifica que el tipo de ordenamiento por fecha asc exista")
    public void testGetPostsByDateAscExists() {
        when(iBuyerRepository.findById(1L)).thenReturn(Optional.of(buyer));
        when(postRepository.getPostsByWeeksAgo(2, 1L)).thenReturn(new ArrayList<>());

        PostsByFollowedDTO expected = PostsByFollowedDTO.builder()
                .user_id(1L)
                .posts(new ArrayList<>())
                .build();

        PostsByFollowedDTO obtainedAsc = postService.getPostsByWeeksAgo(1L, TypeOrder.DATE_ASC);

        Assertions.assertEquals(expected, obtainedAsc);
    }

    @Test
    @DisplayName("Se verifica que el tipo de ordenamiento por fecha desc exista")
    public void testGetPostsByDateDescExists() {
        when(iBuyerRepository.findById(1L)).thenReturn(Optional.of(buyer));
        when(postRepository.getPostsByWeeksAgo(2, 1L)).thenReturn(new ArrayList<>());

        PostsByFollowedDTO expected = PostsByFollowedDTO.builder()
                .user_id(1L)
                .posts(new ArrayList<>())
                .build();

        PostsByFollowedDTO obtainedDesc = postService.getPostsByWeeksAgo(1L, TypeOrder.DATE_DESC);

        Assertions.assertEquals(expected, obtainedDesc);
    }

    @Test
    @DisplayName("Se verifica que el tipo de ordenamiento por fecha es invalido")
    public void testGetPostsByDateInvalidOrder() {
        when(iBuyerRepository.findById(1L)).thenReturn(Optional.of(buyer));
        when(postRepository.getPostsByWeeksAgo(2, 1L)).thenReturn(new ArrayList<>());

        String order = "invalid";

        Assertions.assertThrows(InvalidParameterException.class, () -> {
            postService.getPostsByWeeksAgo(1L, order);
        });
    }

    @Test
    @DisplayName("Se obtiene la lista de posteos de los sellers seguidos por un buyer ordenados por fecha ascendente.")
    void testGetPostsByWeeksAgoOrderAsc() {
        when(iBuyerRepository.findById(1L)).thenReturn(Optional.of(buyer));
        when(postRepository.getPostsByWeeksAgo(WEEKS, 2L)).thenReturn(postsReturn);

        PostsByFollowedDTO postsByFollowedDTO = postService.getPostsByWeeksAgo(1L,
                TypeOrder.DATE_ASC);

        assertEquals(PostMapper.postToPostByFollowed(buyer.getUser().getId(), postsReturn),
                postsByFollowedDTO);
    }

    @Test
    @DisplayName("Se obtiene la lista de posteos de los sellers seguidos por un buyer ordenados por fecha descendente.")
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
    @DisplayName("Se obtiene la lista de posteos, siendo estos una lista vacia, por lo que retorna un PostsByFollowedDTO con el atributo posts vacio.")
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
    @DisplayName("Se intenta realizar la busqueda de posts pero no se logra encontrar a el buyer con el id solicitado, por lo que lanza NotFoundException.")
    void testGetPostsByWeeksAgoOrderWithNonExistentId() {
        when(iBuyerRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class,
                () -> postService.getPostsByWeeksAgo(1L, TypeOrder.DATE_ASC));
    }
}
