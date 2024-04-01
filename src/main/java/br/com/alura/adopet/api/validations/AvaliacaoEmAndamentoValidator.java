package br.com.alura.adopet.api.validations;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AvaliacaoEmAndamentoValidator implements AdocaoValidator {
    @Autowired
    private AdocaoRepository repository;

    @Override
    public void validar(SolicitacaoAdocaoDto dto) {
            boolean petJaEstaAguardandoAvaliacao =
                    repository.existsByPetIdAndStatus(dto.idPet(), StatusAdocao.AGUARDANDO_AVALIACAO);
            if (petJaEstaAguardandoAvaliacao) {
                throw new ValidacaoException("Pet já está aguardando avaliação para ser adotado!");
            }

    }
}
