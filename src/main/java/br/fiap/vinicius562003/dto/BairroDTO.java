package br.fiap.vinicius562003.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BairroDTO {

    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 80, message = "O nome deve ter até 80 caracteres")
    private String nome;

    @NotBlank(message = "Selecione a zona")
    private String zona;

    private Boolean possuiPiscinao = false;
}
