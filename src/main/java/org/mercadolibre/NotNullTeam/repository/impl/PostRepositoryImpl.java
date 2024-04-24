package org.mercadolibre.NotNullTeam.repository.impl;

import org.mercadolibre.NotNullTeam.model.Post;
import org.mercadolibre.NotNullTeam.repository.IPostRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PostRepositoryImpl implements IPostRepository {
    List<Post> posts = new ArrayList<>();

    @Override
    public void createPost(Post post) {
        posts.add(post);
    }
}
