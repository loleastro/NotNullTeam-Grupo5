package org.mercadolibre.NotNullTeam.DTO.request.product;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
//TODO: usar camel case con @JsonProperty snakecase
public class ProductDTO {
    @NotNull(message = "El id no puede estar vacío.")
    @Min(value = 1, message = "El id debe ser mayor a cero.")
    private Long product_id;

    @NotBlank(message = "El campo no puede estar vacío.")
    @Size(max = 39, message = "La longitud no puede superar los 40 caracteres.")
    @Pattern(regexp = "^[a-zA-Z0-9 ]*$", message = "El campo no puede poseer caracteres especiales.")
    private String product_name;

    @NotBlank(message = "El campo no puede estar vacío.")
    @Size(max = 14, message = "La longitud no puede superar los 15 caracteres.")
    @Pattern(regexp = "^[a-zA-Z0-9 ]*$", message = "El campo no puede poseer caracteres especiales.")
    private String type;

    @NotBlank(message = "El campo no puede estar vacío.")
    @Size(max = 24, message = "La longitud no puede superar los 25 caracteres.")
    @Pattern(regexp = "^[a-zA-Z0-9 ]*$", message = "El campo no puede poseer caracteres especiales.")
    private String brand;

    @NotBlank(message = "El campo no puede estar vacío.")
    @Size(max = 14, message = "La longitud no puede superar los 15 caracteres.")
    @Pattern(regexp = "^[a-zA-Z0-9 ]*$", message = "El campo no puede poseer caracteres especiales.")
    private String color;

    @Size(max = 79, message = "La longitud no puede superar los 80 caracteres.")
    @Pattern(regexp = "^[a-zA-Z0-9 ]*$", message = "El campo no puede poseer caracteres especiales.")
    private String notes;
}
