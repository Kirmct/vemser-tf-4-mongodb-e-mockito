package br.com.dbc.vemser.ecommerce.service;

import br.com.dbc.vemser.ecommerce.dto.financeiro.FinanceiroDTO;
import br.com.dbc.vemser.ecommerce.dto.financeiro.FinanceiroPorSetorDTO;
import br.com.dbc.vemser.ecommerce.entity.enums.Setor;
import br.com.dbc.vemser.ecommerce.repository.FinanceiroRepository;
import br.com.dbc.vemser.ecommerce.utils.ConversorMapper;
import br.com.dbc.vemser.ecommerce.utils.HistoricoBuilder;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor

@Service
public class FinanceiroService {

    private final FinanceiroRepository financeiroRepository;
    private final HistoricoBuilder historicoBuilder;

    @SneakyThrows
    private void addLog(String mensagem) {
        historicoBuilder.inserirHistorico(mensagem, Setor.HISTORICO);
    }

    public List<FinanceiroDTO> findAll() {
        addLog("Buscou o histórico de financeiro.");
        return financeiroRepository.findAll().stream()
                .map(financeiro ->
                        ConversorMapper.converterFinanceiro(financeiro)).toList();
    }

}
