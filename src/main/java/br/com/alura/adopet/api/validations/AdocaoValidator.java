package br.com.alura.adopet.api.validations;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;

public interface AdocaoValidator {
    void validar(SolicitacaoAdocaoDto dto);
}
