package com.govideo.gerenciador.controllers;

import com.govideo.gerenciador.dtos.EmprestimoDTO;
import com.govideo.gerenciador.dtos.EquipamentoDTO;
import com.govideo.gerenciador.forms.EquipamentoForm;
import com.govideo.gerenciador.services.EmprestimoService;
import com.govideo.gerenciador.services.EquipamentoService;
import io.swagger.v3.oas.annotations.Operation;
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

@Tag(name = "Empréstimos Endpoint")
@RestController
@RequestMapping("/emprestimos")
public class EmprestimoController {

    @Autowired
    EmprestimoService emprestimoService;

    @GetMapping
    @Operation(summary = "Listar todos os empréstimos")
    public ResponseEntity<Page<EmprestimoDTO>> consultar(@PageableDefault(sort = "id", direction = Sort.Direction.DESC, page = 0, size = 5) Pageable paginacao) {
        Page<EmprestimoDTO> emprestimoDTOS = emprestimoService.consultar(paginacao);
        return ResponseEntity.ok().body(emprestimoDTOS);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar empréstimo por ID")
    public ResponseEntity<EmprestimoDTO> consultarPorId(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(emprestimoService.consultarPorIdRetornarDTO(id));
    }

    @GetMapping("/encerrados")
    @Operation(summary = "Listar empréstimos encerrados")
    public ResponseEntity<Page<EmprestimoDTO>> consultarEncerrados(@PageableDefault(sort = "id", direction = Sort.Direction.DESC, page = 0, size = 5) Pageable paginacao) {
        return ResponseEntity.ok().body(emprestimoService.consultarEncerrados(paginacao));
    }

    @PostMapping("/{idEquipamento}")
    @Operation(summary = "Cadastrar empréstimo")
    public ResponseEntity<EmprestimoDTO> cadastrar(@PathVariable("idEquipamento") Long idEquipamento, UriComponentsBuilder uriBuilder) {
        EmprestimoDTO emprestimoDTO = emprestimoService.cadastrar(idEquipamento);
        URI uri = uriBuilder.path("/emprestimos/{id}").buildAndExpand(emprestimoDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(emprestimoDTO);
    }

    @PutMapping("/encerrar/{id}")
    @Operation(summary = "Encerrar empréstimo")
    public ResponseEntity<EmprestimoDTO> encerrar(@PathVariable Long id) {
        return ResponseEntity.ok().body(emprestimoService.encerrar(id));
    }

}
