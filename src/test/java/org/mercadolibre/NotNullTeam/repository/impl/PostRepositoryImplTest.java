package org.mercadolibre.NotNullTeam.repository.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mercadolibre.NotNullTeam.model.Post;
import org.mercadolibre.NotNullTeam.model.Seller;
import org.mercadolibre.NotNullTeam.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PostRepositoryImplTest {

    private PostRepositoryImpl postRepository;
    private Seller seller;

    @BeforeEach
    void setUp() {
        postRepository = new PostRepositoryImpl();
        seller = new Seller(new User(1L, "seller1"), new ArrayList<>());
    }

    @Test
    @DisplayName("Obtener los posts de las últimas dos semanas de un seller x correctamente")
    void getPostsByWeeksAgoReturnsPostsFromLastTwoWeeks() {
        Post post1 = new Post(seller, LocalDate.now().minusDays(14), null, 0, 0.0);
        Post post2 = new Post(seller, LocalDate.now().minusDays(15), null, 0, 0.0);
        postRepository.createPost(post1);
        postRepository.createPost(post2);

        List<Post> posts = postRepository.getPostsByWeeksAgo(2, seller.getUser().getId());

        assertEquals(1, posts.size());
        assertEquals(post1, posts.get(0));
    }

    @Test
    @DisplayName("Obtener los posts de las ultimas dos semanas de un seller x cuando no hay posts en las ultimas " +
            "dos semanas")
    void getPostsByWeeksAgoReturnsEmptyWhenNoPostsInLastTwoWeeks() {
        Post post = new Post(seller, LocalDate.now().minusDays(20), null, 0, 0.0);
        postRepository.createPost(post);

        List<Post> posts = postRepository.getPostsByWeeksAgo(2, seller.getUser().getId());

        assertTrue(posts.isEmpty());
    }

    @Test
    @DisplayName("Obtener los posts de las ultimas dos semanas de un seller que no existe")
    void getPostsByWeeksAgoReturnsEmptyWhenSellerDoesNotExist() {
        List<Post> posts = postRepository.getPostsByWeeksAgo(2, 999L);
        assertTrue(posts.isEmpty());
    }
}
