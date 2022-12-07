package com.govideo.gerenciador.controllers;

import com.govideo.gerenciador.dtos.EquipamentoDTO;
import com.govideo.gerenciador.dtos.RespostaDTO;
import com.govideo.gerenciador.forms.EquipamentoForm;
import com.govideo.gerenciador.services.EquipamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@Tag(name = "Equipamentos Endpoint")
@RestController
@RequestMapping("/equipamentos")
@SecurityRequirement(name = "bearer-key")
public class EquipamentoController {

    @Autowired
    EquipamentoService equipamentoService;

    @GetMapping
    @Operation(summary = "Listar todos os equipamentos")
    public ResponseEntity<Page<EquipamentoDTO>> consultar(@PageableDefault(sort = "id", direction = Sort.Direction.DESC, page = 0, size = 5) Pageable paginacao) {
        Page<EquipamentoDTO> equipamentosDtos = equipamentoService.consultar(paginacao);
        return ResponseEntity.ok().body(equipamentosDtos);
    }

    @GetMapping("/ativos")
    @Operation(summary = "Listar equipamentos ativos")
    public ResponseEntity<Page<EquipamentoDTO>> consultarAtivos(@PageableDefault(sort = "id", direction = Sort.Direction.DESC, page = 0, size = 5) Pageable paginacao) {
        Page<EquipamentoDTO> equipamentosDtos = equipamentoService.consultarAtivos(paginacao);
        return ResponseEntity.ok().body(equipamentosDtos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar equipamento por ID")
    public ResponseEntity<EquipamentoDTO> consultarPorId(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(equipamentoService.consultarPorIdRetornarDTO(id));
    }

    @GetMapping("/buscarPorStatus/{status}")
    @Operation(summary = "Listar equipamentos por status")
    public ResponseEntity<Page<EquipamentoDTO>> consultarPorStatus(@PageableDefault(sort = "id", direction = Sort.Direction.DESC, page = 0, size = 5) Pageable paginacao, @PathVariable("status") String status) {
        return ResponseEntity.ok().body(equipamentoService.consultarPorStatus(status, paginacao));
    }

    @PostMapping
    @Operation(summary = "Cadastrar equipamento")
    public ResponseEntity<EquipamentoDTO> cadastrar(@Valid @RequestBody EquipamentoForm equipamentoForm, UriComponentsBuilder uriBuilder) {
        EquipamentoDTO equipamentoDTO = equipamentoService.cadastrar(equipamentoForm);
        URI uri = uriBuilder.path("/equipamentos/{id}").buildAndExpand(equipamentoDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(equipamentoDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Alterar equipamento")
    public ResponseEntity<EquipamentoDTO> alterar(@PathVariable Long id, @Valid @RequestBody EquipamentoForm equipamentoForm) {
        return ResponseEntity.ok().body(equipamentoService.alterar(id, equipamentoForm));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir ou inativar equipamento")
    public ResponseEntity<RespostaDTO> excluir(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(equipamentoService.excluir(id));
    }
}
