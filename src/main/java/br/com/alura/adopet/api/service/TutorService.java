package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.AtualizaTutorDto;
import br.com.alura.adopet.api.dto.TutorDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.TutorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TutorService {
    @Autowired
    TutorRepository repository;
    @Transactional
    public void cadastrar(TutorDto dto){
        boolean jaCadastrado = repository.existsByTelefoneOrEmail(dto.telefone(), dto.email());

        if (jaCadastrado) {
            throw new ValidacaoException("Tutor já cadastrado");
        }
        Tutor tutor = new Tutor(dto);
        repository.save(tutor);
    }

    public void atualizar(AtualizaTutorDto dto){
        Tutor tutor = repository.findById(dto.id())
                .orElseThrow(() -> new ValidacaoException("Tutor não encontrado"));
        tutor.atualizar(dto);

        repository.save(tutor);
    }
}
