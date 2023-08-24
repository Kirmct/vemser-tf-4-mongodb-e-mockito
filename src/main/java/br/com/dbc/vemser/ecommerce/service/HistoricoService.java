package br.com.dbc.vemser.ecommerce.service;

import br.com.dbc.vemser.ecommerce.dto.historico.HistoricoContadorDTO;
import br.com.dbc.vemser.ecommerce.dto.historico.HistoricoDTO;
import br.com.dbc.vemser.ecommerce.entity.Historico;
import br.com.dbc.vemser.ecommerce.entity.enums.Cargo;
import br.com.dbc.vemser.ecommerce.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.ecommerce.repository.HistoricoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HistoricoService {

    private final HistoricoRepository historicoRepository;
    private final ObjectMapper objectMapper;

    public List<HistoricoDTO> findAll(){
        return convertToDTOList(historicoRepository.findAll());
    }

    public HistoricoDTO findById(String idHistorico) throws RegraDeNegocioException {
        return convertToDTO(historicoRepository.findById(idHistorico).orElseThrow(
                () -> new RegraDeNegocioException("Histórico não encontrado")
        ));
    }

    public List<HistoricoDTO> findByCargo(Cargo cargo){
        return convertToDTOList(historicoRepository.findAllByCargo(cargo));
    }
    public List<HistoricoContadorDTO> groupByCargoAndCount() {
        System.out.println(historicoRepository.groupByCargoAndCount());
        return historicoRepository.groupByCargoAndCount().stream()
                .map(log -> new HistoricoContadorDTO(log.getCargo(), log.getQuantidade()))
                .collect(Collectors.toList());
    }


    public HistoricoDTO convertToDTO(Historico historico){
        return objectMapper.convertValue(historico, HistoricoDTO.class);
    }
    public List<HistoricoDTO> convertToDTOList(List<Historico> historico){
        return historico.stream().map(
                hist -> convertToDTO(hist)
        ).collect(Collectors.toList());
    }

}
