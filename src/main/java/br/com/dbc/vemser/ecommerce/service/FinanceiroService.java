package br.com.dbc.vemser.ecommerce.service;

import br.com.dbc.vemser.ecommerce.dto.financeiro.FinanceiroDTO;
import br.com.dbc.vemser.ecommerce.dto.financeiro.FinanceiroPorSetorDTO;
import br.com.dbc.vemser.ecommerce.repository.FinanceiroRepository;
import br.com.dbc.vemser.ecommerce.utils.ConversorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor

@Service
public class FinanceiroService {

    private final FinanceiroRepository financeiroRepository;

    public List<FinanceiroDTO> findAll() {

        return financeiroRepository.findAll().stream()
                .map(financeiro ->
                        ConversorMapper.converterFinanceiro(financeiro)).toList();
    }



}
