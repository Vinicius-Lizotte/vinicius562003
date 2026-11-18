package br.fiap.vinicius562003.service;

import lombok.AllArgsConstructor;
import br.fiap.vinicius562003.domain.Leitura;
import br.fiap.vinicius562003.domain.Trecho;
import br.fiap.vinicius562003.dto.LeituraDTO;
import br.fiap.vinicius562003.mapper.LeituraMapper;
import br.fiap.vinicius562003.repository.LeituraRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class LeituraService {

    public static final int LIMIAR_ALERTA = 16;

    private final LeituraRepository leituraRepository;
    private final LeituraMapper leituraMapper;
    private final TrechoService trechoService;

    public String calcularStatus(int laminaCm) {
        if (laminaCm >= LIMIAR_ALERTA) {
            return "ALERTA";
        }
        if (laminaCm >= LIMIAR_ALERTA / 2) {
            return "ATENCAO";
        }
        return "NORMAL";
    }

    public List<LeituraDTO> listar() {
        return leituraRepository.findAllByOrderByDataHoraDesc()
                .stream()
                .map(leituraMapper::toDTO)
                .toList();
    }

    public LeituraDTO buscarPorId(Long id) {
        return leituraMapper.toDTO(buscarEntidade(id));
    }

    public Leitura buscarEntidade(Long id) {
        return leituraRepository.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Leitura não encontrada (id " + id + ")."));
    }

    @Transactional
    public LeituraDTO salvar(LeituraDTO dto) {
        if (dto.getId() != null) {
            buscarEntidade(dto.getId());
        }
        if (dto.getDataHora() != null && dto.getDataHora().isAfter(LocalDateTime.now())) {
            throw new RegraNegocioException("A data e hora da leitura não podem estar no futuro.");
        }
        Trecho trecho = trechoService.buscarEntidade(dto.getTrechoId());
        String status = calcularStatus(dto.getLaminaCm());
        Leitura salva = leituraRepository.save(leituraMapper.toEntity(dto, trecho, status));
        return leituraMapper.toDTO(salva);
    }

    @Transactional
    public void excluir(Long id) {
        leituraRepository.delete(buscarEntidade(id));
    }
}
