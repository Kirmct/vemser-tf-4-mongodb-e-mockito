package br.com.dbc.vemser.ecommerce.service;

import br.com.dbc.vemser.ecommerce.dto.financeiro.FinanceiroDTO;
import br.com.dbc.vemser.ecommerce.entity.FinanceiroEntity;
import br.com.dbc.vemser.ecommerce.repository.FinanceiroRepository;
import br.com.dbc.vemser.ecommerce.utils.HistoricoBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FinanceiroServiceTest {

    @InjectMocks
    private FinanceiroService financeiroService;

    @Mock
    private FinanceiroRepository financeiroRepository;

    @Mock
    private HistoricoBuilder historicoBuilder;

    @Test
    void findAll() {
        List<FinanceiroEntity> financeiroDTOS = new ArrayList<>();
        FinanceiroEntity financeiroDTO = new FinanceiroEntity("123", 50.0, LocalDate.now(), 1);
        financeiroDTOS.add(financeiroDTO);

        when(financeiroRepository.findAll()).thenReturn(financeiroDTOS);

        List<FinanceiroDTO> result = financeiroService.findAll();

        Assertions.assertNotNull(result);


    }
}