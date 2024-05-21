package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.PetResponseDto;
import br.com.alura.adopet.api.model.TipoPet;
import br.com.alura.adopet.api.service.PetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PetController.class)
class PetControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    PetService petService;

    @InjectMocks
    PetController petController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(petController).build();
    }

    @Test
    void testListarTodosDisponiveis() throws Exception {
        PetResponseDto petResponseDto = new PetResponseDto(1L,TipoPet.CACHORRO, "Rex", "Vira-lata", 10);
        when(petService.listarTodosDisponiveis()).thenReturn(List.of(petResponseDto));

        mockMvc.perform(get("/pets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].tipo").value("CACHORRO"))
                .andExpect(jsonPath("$[0].nome").value("Rex"))
                .andExpect(jsonPath("$[0].raca").value("Vira-lata"))
                .andExpect(jsonPath("$[0].idade").value(10));

    }
}