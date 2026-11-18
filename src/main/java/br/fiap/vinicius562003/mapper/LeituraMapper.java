package br.fiap.vinicius562003.mapper;

import br.fiap.vinicius562003.domain.Leitura;
import br.fiap.vinicius562003.domain.Trecho;
import br.fiap.vinicius562003.dto.LeituraDTO;
import org.springframework.stereotype.Component;

@Component
public class LeituraMapper {

    public Leitura toEntity(LeituraDTO dto, Trecho trecho, String status) {
        Leitura leitura = new Leitura();
        leitura.setId(dto.getId());
        leitura.setDataHora(dto.getDataHora());
        leitura.setLaminaCm(dto.getLaminaCm());
        leitura.setStatus(status);
        leitura.setTrecho(trecho);
        return leitura;
    }

    public LeituraDTO toDTO(Leitura leitura) {
        LeituraDTO dto = new LeituraDTO();
        dto.setId(leitura.getId());
        dto.setTrechoId(leitura.getTrecho().getId());
        dto.setTrechoDescricao(leitura.getTrecho().getLogradouro()
                + " (" + leitura.getTrecho().getBairro().getNome() + ")");
        dto.setDataHora(leitura.getDataHora());
        dto.setLaminaCm(leitura.getLaminaCm());
        dto.setStatus(leitura.getStatus());
        return dto;
    }
}
