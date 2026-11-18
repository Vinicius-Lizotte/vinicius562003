package br.fiap.vinicius562003.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TrechoDTO {

    private Long id;

    @NotBlank(message = "O logradouro é obrigatório")
    @Size(max = 120, message = "O logradouro deve ter até 120 caracteres")
    private String logradouro;

    @NotBlank(message = "Selecione o tipo de calçada")
    private String tipoCalcada;

    @NotNull(message = "A altitude é obrigatória")
    @Min(value = 0, message = "A altitude deve estar entre 0 e 2000 m")
    @Max(value = 2000, message = "A altitude deve estar entre 0 e 2000 m")
    private Integer altitude;

    @NotNull(message = "Selecione o bairro")
    private Long bairroId;

    private String bairroNome;
}
