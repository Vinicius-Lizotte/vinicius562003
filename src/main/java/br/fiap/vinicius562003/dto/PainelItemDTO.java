package br.fiap.vinicius562003.dto;

public record PainelItemDTO(
        String logradouro,
        String tipoCalcada,
        Integer altitude,
        Integer ultimaLamina,
        String ultimoStatus
) {
}
