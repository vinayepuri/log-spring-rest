package com.springdemo.logapi.domain.service;

import com.springdemo.logapi.domain.exception.NegocioException;
import com.springdemo.logapi.domain.model.Entrega;
import com.springdemo.logapi.domain.model.Ocorrencia;
import com.springdemo.logapi.domain.repository.EntregaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class RegistroOcorrenciaService {

    private BuscaEntregaService buscaEntregaService;

    @Transactional
    public Ocorrencia registrar(Long entregaId, String descricao) {
        Entrega entrega = buscaEntregaService.buscar(entregaId);

        return entrega.adicionarOcorrencia(descricao);
    }
}
