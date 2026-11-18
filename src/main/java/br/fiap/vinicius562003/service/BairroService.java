package br.fiap.vinicius562003.service;

import lombok.AllArgsConstructor;
import br.fiap.vinicius562003.domain.Bairro;
import br.fiap.vinicius562003.dto.BairroDTO;
import br.fiap.vinicius562003.mapper.BairroMapper;
import br.fiap.vinicius562003.repository.BairroRepository;
import br.fiap.vinicius562003.repository.TrechoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class BairroService {

    private final BairroRepository bairroRepository;
    private final TrechoRepository trechoRepository;
    private final BairroMapper bairroMapper;

    public List<BairroDTO> listar() {
        return bairroRepository.findAllByOrderByNomeAsc()
                .stream()
                .map(bairroMapper::toDTO)
                .toList();
    }

    public BairroDTO buscarPorId(Long id) {
        return bairroMapper.toDTO(buscarEntidade(id));
    }

    public Bairro buscarEntidade(Long id) {
        return bairroRepository.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Bairro não encontrado (id " + id + ")."));
    }

    @Transactional
    public BairroDTO salvar(BairroDTO dto) {
        if (dto.getId() != null) {
            buscarEntidade(dto.getId());
        }
        Bairro salvo = bairroRepository.save(bairroMapper.toEntity(dto));
        return bairroMapper.toDTO(salvo);
    }

    @Transactional
    public void excluir(Long id) {
        Bairro bairro = buscarEntidade(id);
        if (trechoRepository.existsByBairroId(id)) {
            throw new RegraNegocioException("O bairro " + bairro.getNome()
                    + " possui trechos cadastrados e não pode ser excluído.");
        }
        bairroRepository.delete(bairro);
    }
}
