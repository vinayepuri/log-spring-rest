package com.springdemo.logapi.domain.service;

import com.springdemo.logapi.domain.model.Entrega;
import com.springdemo.logapi.domain.repository.EntregaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class FinalizacaoEntregaService {
    private BuscaEntregaService buscaEntregaService;
    private EntregaRepository entregaRepository;

    public void finalizar(Long entregaId) {
        Entrega entrega = buscaEntregaService.buscar(entregaId);
        entrega.finalizar();
        entregaRepository.save(entrega);
    }
}
