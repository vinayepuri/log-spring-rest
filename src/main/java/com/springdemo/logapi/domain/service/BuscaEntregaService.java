package com.springdemo.logapi.domain.service;

import com.springdemo.logapi.domain.exception.EntidadeNaoEncontradaException;
import com.springdemo.logapi.domain.exception.NegocioException;
import com.springdemo.logapi.domain.model.Entrega;
import com.springdemo.logapi.domain.repository.EntregaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class BuscaEntregaService {
    private EntregaRepository entregaRepository;

    public Entrega buscar(Long entregaId) {
        return entregaRepository.findById(entregaId)
                                .orElseThrow(() -> new EntidadeNaoEncontradaException("Entrega não encontrada"));
    }
}
