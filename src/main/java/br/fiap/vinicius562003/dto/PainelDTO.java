package br.fiap.vinicius562003.dto;

import java.util.List;

public record PainelDTO(
        String bairroNome,
        long qtdNormal,
        long qtdAtencao,
        long qtdAlerta,
        List<PainelItemDTO> itens
) {
}
