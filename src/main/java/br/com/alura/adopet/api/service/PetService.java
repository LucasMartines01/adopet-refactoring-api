package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.PetResponseDto;
import br.com.alura.adopet.api.repository.PetRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {

    final PetRepository petRepository;

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public List<PetResponseDto> listarTodosDisponiveis() {
        return petRepository.findAllByAdotadoFalse().stream().map(PetResponseDto::new).toList();
    }
}
