package org.mercadolibre.NotNullTeam.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mercadolibre.NotNullTeam.DTO.response.post.PostsByFollowedDTO;
import org.mercadolibre.NotNullTeam.exception.error.InvalidParameterException;
import org.mercadolibre.NotNullTeam.model.Buyer;
import org.mercadolibre.NotNullTeam.repository.IBuyerRepository;
import org.mercadolibre.NotNullTeam.repository.IPostRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
public class PostServiceImplTest {

    @Mock
    private IPostRepository postRepository;

    @Mock
    private IBuyerRepository buyerRepository;

    @InjectMocks
    private PostServiceImpl postService;

    private Buyer buyer;

    @BeforeEach
    public void setup() {
        buyer = new Buyer();
    }

    @Test
    @DisplayName("Se verifica que el tipo de ordenamiento por fecha asc y desc exista")
    public void testGetPostsByDateExists(){
        when(buyerRepository.findById(1L)).thenReturn(Optional.of(buyer));
        when(postRepository.getPostsByWeeksAgo(2, 1L)).thenReturn(new ArrayList<>());

        PostsByFollowedDTO expected = new PostsByFollowedDTO(1L, new ArrayList<>());

        PostsByFollowedDTO obtainedAsc = postService.getPostsByWeeksAgo(1L, "date_asc");
        PostsByFollowedDTO obtainedDesc = postService.getPostsByWeeksAgo(1L, "date_desc");

        Assertions.assertEquals(expected, obtainedAsc);
        Assertions.assertEquals(expected, obtainedDesc);
    }

    @Test
    @DisplayName("Se verifica que el tipo de ordenamiento por fecha es invalido")
    public void testGetPostsByDateInvalidOrder() {
        when(buyerRepository.findById(1L)).thenReturn(Optional.of(buyer));
        when(postRepository.getPostsByWeeksAgo(2, 1L)).thenReturn(new ArrayList<>());

        String order = "invalid";

        Assertions.assertThrows(InvalidParameterException.class, () -> {
            postService.getPostsByWeeksAgo(1L, order);
        });
    }


}
