package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.CadastrarAbrigoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AbrigoServiceTest {
    @InjectMocks
    private AbrigoService service;

    @Mock
    private AbrigoRepository repository;


    private CadastrarAbrigoDto dto;

    @Mock
    Abrigo abrigo;

    @BeforeEach
    void setup(){
        dto = new CadastrarAbrigoDto("Abrigo 1", "11993302914", "testeadopet@gmail.com");
    }

    @Test
    @DisplayName("Deveria lançar erro ao tentar cadastrar abrigo já existente")
    void teste01(){
        when(repository.existsByNomeOrTelefoneOrEmail(dto.nome(), dto.telefone(), dto.email())).thenReturn(true);
        assertThrows(ValidacaoException.class, () -> service.cadastrar(dto));
    }

    @Test
    @DisplayName("Deveria cadastrar um abrigo com sucesso")
    void teste02(){
        when(repository.existsByNomeOrTelefoneOrEmail(dto.nome(), dto.telefone(), dto.email())).thenReturn(false);
        assertDoesNotThrow(() -> service.cadastrar(dto));
    }

    @Test
    @DisplayName("Deveria lançar um erro ao buscar abrigo inexistente")
    void teste03(){
        assertThrows(ValidacaoException.class, () -> service.buscarAbrigo(dto.nome()));
    }

    @Test
    @DisplayName("Deveria buscar um abrigo com sucesso ao buscar abrigo com nome existente")
    void teste04(){
        when(repository.findByNome(dto.nome())).thenReturn(Optional.of(abrigo));
        assertDoesNotThrow(() -> service.buscarAbrigo(dto.nome()));
    }

    @Test
    @DisplayName("Deveria buscar um abrigo com sucesso ao buscar abrigo com id existente")
    void teste05(){
        when(repository.findById(1L)).thenReturn(Optional.of(abrigo));
        assertDoesNotThrow(() -> service.buscarAbrigo("1"));
    }
}