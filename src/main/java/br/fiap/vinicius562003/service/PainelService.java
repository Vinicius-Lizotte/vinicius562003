package br.fiap.vinicius562003.service;

import lombok.AllArgsConstructor;
import br.fiap.vinicius562003.domain.Bairro;
import br.fiap.vinicius562003.domain.Leitura;
import br.fiap.vinicius562003.dto.PainelDTO;
import br.fiap.vinicius562003.dto.PainelItemDTO;
import br.fiap.vinicius562003.repository.LeituraRepository;
import br.fiap.vinicius562003.repository.TrechoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class PainelService {

    private final BairroService bairroService;
    private final TrechoRepository trechoRepository;
    private final LeituraRepository leituraRepository;

    @Transactional(readOnly = true)
    public PainelDTO montarPainel(Long bairroId) {
        Bairro bairro = bairroService.buscarEntidade(bairroId);

        List<PainelItemDTO> itens = trechoRepository.findByBairroIdOrderByLogradouroAsc(bairroId)
                .stream()
                .map(trecho -> {
                    Leitura ultima = leituraRepository
                            .findFirstByTrechoIdOrderByDataHoraDesc(trecho.getId())
                            .orElse(null);
                    return new PainelItemDTO(
                            trecho.getLogradouro(),
                            trecho.getTipoCalcada(),
                            trecho.getAltitude(),
                            ultima == null ? null : ultima.getLaminaCm(),
                            ultima == null ? null : ultima.getStatus());
                })
                .toList();

        return new PainelDTO(
                bairro.getNome(),
                contar(itens, "NORMAL"),
                contar(itens, "ATENCAO"),
                contar(itens, "ALERTA"),
                itens);
    }

    private long contar(List<PainelItemDTO> itens, String status) {
        return itens.stream()
                .filter(item -> status.equals(item.ultimoStatus()))
                .count();
    }
}
