package br.fiap.vinicius562003.service;

import lombok.AllArgsConstructor;
import br.fiap.vinicius562003.domain.Bairro;
import br.fiap.vinicius562003.domain.Trecho;
import br.fiap.vinicius562003.dto.TrechoDTO;
import br.fiap.vinicius562003.mapper.TrechoMapper;
import br.fiap.vinicius562003.repository.LeituraRepository;
import br.fiap.vinicius562003.repository.TrechoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class TrechoService {

    private final TrechoRepository trechoRepository;
    private final LeituraRepository leituraRepository;
    private final TrechoMapper trechoMapper;
    private final BairroService bairroService;

    public List<TrechoDTO> listar() {
        return trechoRepository.findAllByOrderByAltitudeAscLogradouroAsc()
                .stream()
                .map(trechoMapper::toDTO)
                .toList();
    }

    public TrechoDTO buscarPorId(Long id) {
        return trechoMapper.toDTO(buscarEntidade(id));
    }

    public Trecho buscarEntidade(Long id) {
        return trechoRepository.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Trecho não encontrado (id " + id + ")."));
    }

    @Transactional
    public TrechoDTO salvar(TrechoDTO dto) {
        if (dto.getId() != null) {
            buscarEntidade(dto.getId());
        }
        Bairro bairro = bairroService.buscarEntidade(dto.getBairroId());
        Trecho salvo = trechoRepository.save(trechoMapper.toEntity(dto, bairro));
        return trechoMapper.toDTO(salvo);
    }

    @Transactional
    public void excluir(Long id) {
        Trecho trecho = buscarEntidade(id);
        if (leituraRepository.existsByTrechoId(id)) {
            leituraRepository.deleteByTrechoId(id);
        }
        trechoRepository.delete(trecho);
    }
}
