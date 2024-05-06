package org.mercadolibre.NotNullTeam.DTO.response.post;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PostCreatedDto {
    private Long id;
    private String message;
    private LocalDate date;
}