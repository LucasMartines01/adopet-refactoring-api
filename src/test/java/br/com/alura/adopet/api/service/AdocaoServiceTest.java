package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import br.com.alura.adopet.api.validations.AdocaoValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdocaoServiceTest {
    @InjectMocks
    private AdocaoService service;
    @Mock
    private AdocaoRepository repository;
    @Mock
    private PetRepository petRepository;

    @Mock
    private TutorRepository tutorRepository;

    @Mock
    private NotificationService notificationService;

    @Spy
    List<AdocaoValidator> validators = new ArrayList<>();

    @Mock
    private Abrigo abrigo;

    private SolicitacaoAdocaoDto dto;

    @Mock
    private Tutor tutor;

    @Mock
    private Pet pet;

    @Captor
    private ArgumentCaptor<Adocao> adocaoArgumentCaptor;

    @Mock
    private AdocaoValidator validator;

    @Mock
    private AdocaoValidator validator2;


    @Test
    @DisplayName("Deve solicitar uma adoção")
    void solicitar01() {
        // Arrange
        this.dto = new SolicitacaoAdocaoDto(10L, 10L, "Motivo");
        when(petRepository.getReferenceById(dto.idPet())).thenReturn(pet);
        when(tutorRepository.getReferenceById(dto.idTutor())).thenReturn(tutor);
        when(pet.getAbrigo()).thenReturn(abrigo);

        // Act
        service.solicitar(dto);
        // Assert
        then(repository).should().save(adocaoArgumentCaptor.capture());
        Adocao adocao = adocaoArgumentCaptor.getValue();
        assertNotNull(adocao);
        assertEquals(pet, adocao.getPet());
        assertEquals(tutor, adocao.getTutor());
        assertEquals(dto.motivo(), adocao.getMotivo());
    }

    @Test
    @DisplayName("Deve chamar validadores ao solicitar uma adoção")
    void solicitar02() {
        // Arrange
        validators.addAll(List.of(validator, validator2));
        this.dto = new SolicitacaoAdocaoDto(10L, 10L, "Motivo");
        when(petRepository.getReferenceById(dto.idPet())).thenReturn(pet);
        when(tutorRepository.getReferenceById(dto.idTutor())).thenReturn(tutor);
        when(pet.getAbrigo()).thenReturn(abrigo);

        // Act
        service.solicitar(dto);
        // Assert
        then(validator).should().validar(dto);
        then(validator2).should().validar(dto);
    }
}