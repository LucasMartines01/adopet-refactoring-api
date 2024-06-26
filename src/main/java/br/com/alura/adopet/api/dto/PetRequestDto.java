package br.com.alura.adopet.api.dto;

import br.com.alura.adopet.api.model.TipoPet;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PetRequestDto(@NotNull TipoPet tipo,@NotBlank String nome, @NotBlank String raca, @NotNull Integer idade, @NotBlank String cor, @NotNull Float peso) {
}
