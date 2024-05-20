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


import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class TutorAvaliacaoEmAndamentoValidatorTest {

    @InjectMocks
    TutorAvaliacaoEmAndamentoValidator validator;

    @Mock
    private SolicitacaoAdocaoDto dto;

    @Mock
    private AdocaoRepository repository;

    @Test
    @DisplayName("Não deveria permitir solicitação de adoção, tutor já possui outra adoção aguardando avaliação")
    void teste01(){
        //Arrange
        given(repository.existsByTutorIdAndStatus(dto.idTutor(), StatusAdocao.AGUARDANDO_AVALIACAO)).willReturn(true);

        //Act + Assert
        assertThrows(ValidacaoException.class, () -> validator.validar(dto));
    }

    @Test
    @DisplayName("Deveria permitir solicitação de adoção, tutor não possui outra adoção aguardando avaliação")
    void teste02(){
        //Arrange
        given(repository.existsByTutorIdAndStatus(dto.idTutor(), StatusAdocao.AGUARDANDO_AVALIACAO)).willReturn(false);

        //Act + Assert
        assertDoesNotThrow(() -> validator.validar(dto));
    }

}