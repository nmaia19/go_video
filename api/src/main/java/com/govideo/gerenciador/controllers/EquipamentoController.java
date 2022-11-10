package com.govideo.gerenciador.controllers;

import com.govideo.gerenciador.dtos.EquipamentoDTO;
import com.govideo.gerenciador.forms.EquipamentoForm;
import com.govideo.gerenciador.services.EquipamentoService;
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

@RestController
@RequestMapping("/equipamentos")
public class EquipamentoController {

    @Autowired
    EquipamentoService equipamentoService;

    @GetMapping
    public ResponseEntity<Page<EquipamentoDTO>> consultar(@PageableDefault(sort = "id", direction = Sort.Direction.DESC, page = 0, size = 5) Pageable paginacao) {
        Page<EquipamentoDTO> equipamentosDtos = equipamentoService.consultar(paginacao);
        return ResponseEntity.ok().body(equipamentosDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EquipamentoDTO> consultarPorId(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(equipamentoService.consultarPorIdRetornarDTO(id));
    }

    @GetMapping("/buscarPorStatus/{status}")
    public ResponseEntity<Page<EquipamentoDTO>> consultarPorStatus(@PageableDefault(sort = "id", direction = Sort.Direction.DESC, page = 0, size = 5) Pageable paginacao, @PathVariable("status") String status) {
        return ResponseEntity.ok().body(equipamentoService.consultarPorStatus(status, paginacao));
    }

    @PostMapping
    public ResponseEntity<EquipamentoDTO> salvar(@Valid @RequestBody EquipamentoForm equipamentoForm, UriComponentsBuilder uriBuilder) {
        EquipamentoDTO equipamentoDTO = equipamentoService.cadastrar(equipamentoForm);
        URI uri = uriBuilder.path("/equipamentos/{id}").buildAndExpand(equipamentoDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(equipamentoDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EquipamentoDTO> alterar(@PathVariable Long id, @Valid @RequestBody EquipamentoForm equipamentoForm) {
        return ResponseEntity.ok().body(equipamentoService.alterar(id, equipamentoForm));
    }

    @PutMapping("/alterarStatus/{id}")
    public ResponseEntity<EquipamentoDTO> alterarStatus(@PathVariable Long id, @RequestParam(value = "status", required = true) String status) {
        return ResponseEntity.ok().body(equipamentoService.alterarStatus(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable("id") Long id) {
        equipamentoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
