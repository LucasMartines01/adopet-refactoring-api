package br.com.alura.adopet.api.dto;

import br.com.alura.adopet.api.model.Abrigo;

public record AbrigoResponseDto(Long id, String nome) {
    public AbrigoResponseDto(Abrigo abrigo) {
        this(abrigo.getId(), abrigo.getNome());
    }
}
