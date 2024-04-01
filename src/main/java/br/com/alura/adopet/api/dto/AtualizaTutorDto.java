package br.com.alura.adopet.api.dto;

import jakarta.validation.constraints.NotNull;

public record AtualizaTutorDto(@NotNull Long id, String nome, String telefone, String email) {
}
