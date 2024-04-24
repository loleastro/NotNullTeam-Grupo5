package org.mercadolibre.NotNullTeam.service;

import org.mercadolibre.NotNullTeam.DTO.request.PostDTO;

public interface IPostService {
    void createPost(PostDTO postDTO);
}
