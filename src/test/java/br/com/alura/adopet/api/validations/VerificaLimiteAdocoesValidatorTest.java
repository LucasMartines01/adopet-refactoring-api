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
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class VerificaLimiteAdocoesValidatorTest {

    @InjectMocks
    private VerificaLimiteAdocoesValidator validador;

    @Mock
    private AdocaoRepository adocaoRepository;

    @Mock
    private SolicitacaoAdocaoDto dto;


    @Test
    @DisplayName("Não deveria permitir solicitação de adoção, tutor atingiu limite de 5 adoções")
    void teste01() {
        //Arrange
        given(adocaoRepository.countByTutorIdAndStatus(dto.idTutor(),StatusAdocao.APROVADO)).willReturn(5);

        //Act + Assert
        assertThrows(ValidacaoException.class,() ->validador.validar(dto));
    }

    @Test
    @DisplayName("Deveria permitir solicitação de adoção, tutor não atingiu limite de 5 adoções")
    void teste02() {
        //Arrange
        given(adocaoRepository.countByTutorIdAndStatus(dto.idTutor(),StatusAdocao.APROVADO)).willReturn(4);

        //Act + Assert
        assertDoesNotThrow(() -> validador.validar(dto));
    }
}