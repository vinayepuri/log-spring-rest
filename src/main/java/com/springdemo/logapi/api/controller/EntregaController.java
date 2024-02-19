package com.springdemo.logapi.api.controller;

import com.springdemo.logapi.api.EntregaAssembler;
import com.springdemo.logapi.api.model.DestinatarioModel;
import com.springdemo.logapi.api.model.EntregaModel;
import com.springdemo.logapi.api.model.input.EntregaInput;
import com.springdemo.logapi.domain.model.Entrega;
import com.springdemo.logapi.domain.repository.EntregaRepository;
import com.springdemo.logapi.domain.service.FinalizacaoEntregaService;
import com.springdemo.logapi.domain.service.SolicitacaoEntregaService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/entregas")
public class EntregaController {
    private SolicitacaoEntregaService solicitacaoEntregaService;
    private EntregaRepository entregaRepository;
    private EntregaAssembler entregaAssembler;
    private FinalizacaoEntregaService finalizacaoEntregaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntregaModel solicitar(@Valid @RequestBody EntregaInput entregaInput) {
        Entrega novaEntrega = entregaAssembler.toEntity(entregaInput);
        return entregaAssembler.toModel(solicitacaoEntregaService.solicitar(novaEntrega));
    }

    @GetMapping
    public List<EntregaModel> listar() {
        return entregaAssembler.toCollectionModel(entregaRepository.findAll());
    }

    @GetMapping("/{entregaId}")
    public ResponseEntity<EntregaModel> buscar(@PathVariable Long entregaId) {
        return entregaRepository.findById(entregaId)
//                                .map(entrega -> ResponseEntity.ok(entregaModelBuilder(entrega)))
//                                .map(entrega -> {
//                                    EntregaModel entregaModel = modelMapper.map(entrega, EntregaModel.class);
//                                    return ResponseEntity.ok(entregaModel);
//                                })
                                .map(entrega -> ResponseEntity.ok(entregaAssembler.toModel(entrega)))
                                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{entregaId}/finalizacao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void finalizar(@PathVariable Long entregaId) {
        finalizacaoEntregaService.finalizar(entregaId);
    }

}
