package br.com.alura.adopet.api.validations;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.PetRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValidacaoPetDisponivelValidatorTest {
    @Mock
    SolicitacaoAdocaoDto solicitacaoAdocaoDto;
    @InjectMocks
    ValidacaoPetDisponivelValidator validacaoPetDisponivelValidator;

    @Mock
    PetRepository petRepository;

    @Mock
    Pet pet;

    @Test
    @DisplayName("Não deve lançar exceção para pet disponível")
    void teste1(){
        when(pet.getAdotado()).thenReturn(false);
        when(petRepository.getReferenceById(solicitacaoAdocaoDto.idPet())).thenReturn(pet);
        assertDoesNotThrow(() -> validacaoPetDisponivelValidator.validar(solicitacaoAdocaoDto));
    }

    @Test
    @DisplayName("Deve lançar exceção para pet indisponível")
    void teste2(){
        when(petRepository.getReferenceById(solicitacaoAdocaoDto.idPet())).thenReturn(pet);
        when(pet.getAdotado()).thenReturn(true);
        assertThrows(ValidacaoException.class, () -> validacaoPetDisponivelValidator.validar(solicitacaoAdocaoDto));
    }
}