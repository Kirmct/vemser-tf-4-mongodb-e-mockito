package br.com.dbc.vemser.ecommerce.service;

import br.com.dbc.vemser.ecommerce.dto.historico.HistoricoContadorDTO;
import br.com.dbc.vemser.ecommerce.dto.historico.HistoricoDTO;
import br.com.dbc.vemser.ecommerce.entity.Historico;
import br.com.dbc.vemser.ecommerce.entity.enums.Cargo;
import br.com.dbc.vemser.ecommerce.entity.enums.Setor;
import br.com.dbc.vemser.ecommerce.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.ecommerce.repository.HistoricoRepository;
import br.com.dbc.vemser.ecommerce.utils.HistoricoBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HistoricoService {

    private final HistoricoRepository historicoRepository;
    private final ObjectMapper objectMapper;
    private final HistoricoBuilder historicoBuilder;

    @SneakyThrows
    private void addLog(String mensagem) {
        historicoBuilder.inserirHistorico(mensagem, Setor.HISTORICO);
    }

    public List<HistoricoDTO> findAll(){
        addLog("Buscou por todos o histórico");
        return convertToDTOList(historicoRepository.findAll());
    }

    public HistoricoDTO findById(String idHistorico) throws RegraDeNegocioException {
        addLog("Buscou pelo histórico: " + idHistorico);
        return convertToDTO(historicoRepository.findById(idHistorico).orElseThrow(
                () -> new RegraDeNegocioException("Histórico não encontrado")
        ));
    }

    public List<HistoricoDTO> findByCargo(Cargo cargo){
        addLog("Buscou histórico pelo cargo: " + cargo.toString());
        return convertToDTOList(historicoRepository.findAllByCargo(cargo));
    }
    public List<HistoricoContadorDTO> groupByCargoAndCount() {
        return historicoRepository.groupByCargoAndCount().stream()
                .map(log -> new HistoricoContadorDTO(log.getCargo(), log.getQuantidade()))
                .collect(Collectors.toList());
    }

    public List<HistoricoDTO> findBySetor(Setor setor){
        addLog("Buscou histórico pelo setor: " + setor.toString());
        return convertToDTOList(historicoRepository.findAllBySetor(setor));
    }


    public HistoricoDTO convertToDTO(Historico historico){
        HistoricoDTO historicoDTO = objectMapper.convertValue(historico, HistoricoDTO.class);
        historicoDTO.setSetor(historico.getSetor());
        return historicoDTO;
    }
    public List<HistoricoDTO> convertToDTOList(List<Historico> historico){
        return historico.stream().map(
                hist -> convertToDTO(hist)
        ).collect(Collectors.toList());
    }

}
