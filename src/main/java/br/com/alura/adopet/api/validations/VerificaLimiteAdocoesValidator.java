package br.com.alura.adopet.api.validations;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VerificaLimiteAdocoesValidator implements AdocaoValidator {
    @Autowired
    private AdocaoRepository repository;

    @Override
    public void validar(SolicitacaoAdocaoDto dto) {
        int adocoes = repository.countByTutorIdAndStatus(dto.idTutor(), StatusAdocao.APROVADO);
        if (adocoes == 5) {
            throw new ValidacaoException("Tutor chegou ao limite máximo de 5 adoções!");
        }

    }
}
