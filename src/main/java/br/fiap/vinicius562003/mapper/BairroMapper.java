package br.fiap.vinicius562003.mapper;

import br.fiap.vinicius562003.domain.Bairro;
import br.fiap.vinicius562003.dto.BairroDTO;
import org.springframework.stereotype.Component;

@Component
public class BairroMapper {

    public Bairro toEntity(BairroDTO dto) {
        Bairro bairro = new Bairro();
        bairro.setId(dto.getId());
        bairro.setNome(dto.getNome());
        bairro.setZona(dto.getZona());
        bairro.setPossuiPiscinao(Boolean.TRUE.equals(dto.getPossuiPiscinao()));
        return bairro;
    }

    public BairroDTO toDTO(Bairro bairro) {
        BairroDTO dto = new BairroDTO();
        dto.setId(bairro.getId());
        dto.setNome(bairro.getNome());
        dto.setZona(bairro.getZona());
        dto.setPossuiPiscinao(Boolean.TRUE.equals(bairro.getPossuiPiscinao()));
        return dto;
    }
}
