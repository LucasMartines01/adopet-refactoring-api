package br.com.alura.adopet.api.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.alura.adopet.api.dto.AbrigoResponseDto;
import br.com.alura.adopet.api.dto.CadastrarAbrigoDto;
import br.com.alura.adopet.api.service.AbrigoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

@WebMvcTest(AbrigoController.class)
public class AbrigoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AbrigoService abrigoService;

    @InjectMocks
    private AbrigoController abrigoController;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(abrigoController).build();
    }

    @Test
    void testListar() throws Exception {
        AbrigoResponseDto abrigo1 = new AbrigoResponseDto(1L, "SóDogs");

        AbrigoResponseDto abrigo2 = new AbrigoResponseDto(2L, "SóCats");

        List<AbrigoResponseDto> abrigos = Arrays.asList(abrigo1, abrigo2);

        when(abrigoService.findAll()).thenReturn(abrigos);

        mockMvc.perform(get("/abrigos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0]").exists())
                .andExpect(jsonPath("$[1]").exists());
    }

    @Test
    void testCadastrarSuccess() throws Exception {
        CadastrarAbrigoDto dto = new CadastrarAbrigoDto("Abrigo Teste", "(11)98765-4321", "teste@abrigo.com");

        doNothing().when(abrigoService).cadastrar(dto);

        mockMvc.perform(post("/abrigos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void testCadastrarFail() throws Exception {
        CadastrarAbrigoDto dto = new CadastrarAbrigoDto("Abrigo Teste", "(11)98765-4321", "teste@abrigo.com");

        doThrow(new RuntimeException("Abrigo já cadastrado")).when(abrigoService).cadastrar(dto);

        mockMvc.perform(post("/abrigos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Abrigo já cadastrado"));
    }

    @Test
    void testCadastrarInvalidData() throws Exception {
        CadastrarAbrigoDto dto = new CadastrarAbrigoDto("", "1234", "emailinvalido");

        mockMvc.perform(post("/abrigos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().is4xxClientError());
    }
}
