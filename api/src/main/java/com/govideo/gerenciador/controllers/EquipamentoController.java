package com.govideo.gerenciador.controllers;

import com.govideo.gerenciador.dtos.EquipamentoDTO;
import com.govideo.gerenciador.forms.EquipamentoForm;
import com.govideo.gerenciador.services.EquipamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/equipamentos")
public class EquipamentoController {

    @Autowired
    EquipamentoService equipamentoService;

    @GetMapping
    public ResponseEntity<Page<EquipamentoDTO>> consultar(@PageableDefault(sort = "id", direction = Sort.Direction.DESC, page = 0, size = 5) Pageable paginacao) {
        Page<EquipamentoDTO> equipamentosDtos = equipamentoService.consultar(paginacao);
        return ResponseEntity.status(HttpStatus.OK).body(equipamentosDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EquipamentoDTO> consultarPorId(@PathVariable("id") Long id){
        return  ResponseEntity.ok().body(equipamentoService.consultarPorId(id));
    }

    @PostMapping
    public ResponseEntity<EquipamentoDTO> salvar(@Valid @RequestBody EquipamentoForm equipamentoForm){
        return ResponseEntity.status(HttpStatus.CREATED).body(equipamentoService.cadastrar(equipamentoForm));
    }

}
