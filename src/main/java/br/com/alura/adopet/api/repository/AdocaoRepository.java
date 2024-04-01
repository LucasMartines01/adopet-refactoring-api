package br.com.alura.adopet.api.repository;

import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.StatusAdocao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdocaoRepository extends JpaRepository<Adocao, Long> {
    public boolean existsByPetIdAndStatus(Long petId, StatusAdocao status);

    public boolean existsByTutorIdAndStatus(Long tutorId, StatusAdocao status);

    public int countByTutorIdAndStatus(Long tutorId, StatusAdocao status);
}
