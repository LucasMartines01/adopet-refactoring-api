package br.com.alura.adopet.api.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


import br.com.alura.adopet.api.dto.AprovacaoAdocaoDto;
import br.com.alura.adopet.api.dto.ReprovacaoAdocaoDto;
import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.service.AdocaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@WebMvcTest(AdocaoController.class)
public class AdocaoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private AdocaoService adocaoService;

    @InjectMocks
    private AdocaoController adocaoController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(adocaoController).build();
    }

    @Test
    void testSolicitarSuccess() throws Exception {
        // preencha o dto com valores válidos para teste

        doNothing().when(adocaoService).solicitar(any(SolicitacaoAdocaoDto.class));

        mockMvc.perform(post("/adocoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"idPet\": 1, \"idTutor\": 1, \"motivo\": \"Quero adotar um pet\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void testSolicitarFail() throws Exception {

        doThrow(new RuntimeException("Erro ao solicitar adoção")).when(adocaoService).solicitar(any(SolicitacaoAdocaoDto.class));

        mockMvc.perform(post("/adocoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"idPet\": 1, \"idTutor\": 1, \"motivo\": \"Quero adotar um pet\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Erro ao solicitar adoção"));
    }

    @Test
    void testAprovarSuccess() throws Exception {
        // preencha o dto com valores válidos para teste

        doNothing().when(adocaoService).aprovar(any(AprovacaoAdocaoDto.class));

        mockMvc.perform(put("/adocoes/aprovar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"idAdocao\": 1}"))
                .andExpect(status().isOk());
    }

    @Test
    void testReprovarSuccess() throws Exception {
        // preencha o dto com valores válidos para teste

        doNothing().when(adocaoService).reprovar(any(ReprovacaoAdocaoDto.class));

        mockMvc.perform(put("/adocoes/reprovar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"idAdocao\": 1, \"justificativa\": \"Não gostei do pet\"}"))
                .andExpect(status().isOk());
    }
}
