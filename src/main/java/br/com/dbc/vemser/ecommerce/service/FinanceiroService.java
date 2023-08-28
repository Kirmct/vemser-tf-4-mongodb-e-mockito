package br.com.dbc.vemser.ecommerce.service;

import br.com.dbc.vemser.ecommerce.entity.FinanceiroEntity;
import br.com.dbc.vemser.ecommerce.repository.FinanceiroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor

@Service
public class FinanceiroService {

    private final FinanceiroRepository financeiroRepository;

    public List<FinanceiroEntity> findAll() {
        return financeiroRepository.findAll();
    }

}
