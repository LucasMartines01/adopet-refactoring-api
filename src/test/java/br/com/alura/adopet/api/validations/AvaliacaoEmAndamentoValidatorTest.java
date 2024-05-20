package br.com.alura.adopet.api.validations;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class AvaliacaoEmAndamentoValidatorTest {
    @InjectMocks
    private AvaliacaoEmAndamentoValidator validador;

    @Mock
    private AdocaoRepository adocaoRepository;

    @Mock
    private SolicitacaoAdocaoDto dto;

    @Test
    @DisplayName("Não deveria permitir solicitação de adoção de pet com pedido em andamento")
    void teste01() {
        //Arrange
        given(adocaoRepository.existsByPetIdAndStatus(
                dto.idPet(),
                StatusAdocao.AGUARDANDO_AVALIACAO)
        ).willReturn(true);

        //Act + Assert
        assertThrows(ValidacaoException.class, () -> validador.validar(dto));
    }

    @Test
    @DisplayName("Deveria permitir solicitação de adoção de pet com pedido inexistente")
    void teste02() {
        //Arrange
        given(adocaoRepository.existsByPetIdAndStatus(
                dto.idPet(),
                StatusAdocao.AGUARDANDO_AVALIACAO
        )).willReturn(false);

        //Act + Assert
        assertDoesNotThrow(()->validador.validar(dto));
    }

}