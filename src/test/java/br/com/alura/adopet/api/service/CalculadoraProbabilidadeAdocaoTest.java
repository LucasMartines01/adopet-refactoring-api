package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.PetRequestDto;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.ProbabilidadeAdocao;
import br.com.alura.adopet.api.model.TipoPet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class CalculadoraProbabilidadeAdocaoTest {
    Pet pet;

    @BeforeEach
    void setup() {
        pet = new Pet(
                new PetRequestDto(
                        TipoPet.CACHORRO, "Rex", "Vira-lata", 4, "Marrom", 4f
                )
        );
    }

    @Test
    @DisplayName("Deve calcular a probabilidade de adoção de um pet com 4 anos e 4kg como ALTA")
    void probabilidadeAlta() {
        CalculadoraProbabilidadeAdocao calculadora = new CalculadoraProbabilidadeAdocao();
        ProbabilidadeAdocao probabilidade = calculadora.calcular(pet);

        assertEquals(ProbabilidadeAdocao.ALTA, probabilidade);
    }

    @Test
    @DisplayName("Deve calcular a probabilidade de adoção de um pet com 15 anos e 4kg como MEDIA")
    void probabilidadeMedia() {
        CalculadoraProbabilidadeAdocao calculadora = new CalculadoraProbabilidadeAdocao();
        pet.setIdade(15);
        ProbabilidadeAdocao probabilidade = calculadora.calcular(pet);

        assertEquals(ProbabilidadeAdocao.MEDIA, probabilidade);
    }

    @Test
    @DisplayName("Deve calcular a probabilidade de adoção de um pet com 15 anos e 16kg como BAIXA")
    void probabilidadeBaixa() {

        CalculadoraProbabilidadeAdocao calculadora = new CalculadoraProbabilidadeAdocao();
        pet.setIdade(15);
        pet.setPeso(16f);
        ProbabilidadeAdocao probabilidade = calculadora.calcular(pet);
        assertEquals(ProbabilidadeAdocao.BAIXA, probabilidade);
    }
}