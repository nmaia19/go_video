package com.govideo.gerenciador.controllers;

import com.govideo.gerenciador.dtos.EquipamentoDTO;
import com.govideo.gerenciador.dtos.UsuarioDTO;
import com.govideo.gerenciador.forms.EquipamentoForm;
import com.govideo.gerenciador.forms.UsuarioForm;
import com.govideo.gerenciador.services.UsuarioService;
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

@Tag(name = "Usuarios Endpoint")
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @GetMapping
    @Operation(summary = "Listar todos os usuarios")
    public ResponseEntity<Page<UsuarioDTO>> consultar(@PageableDefault(sort = "id", direction = Sort.Direction.DESC, page = 0, size = 5) Pageable paginacao) {
        Page<UsuarioDTO> usuariosDtos = usuarioService.consultar(paginacao);
        return ResponseEntity.ok().body(usuariosDtos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar usuario por ID")
    public ResponseEntity<UsuarioDTO> consultarPorId(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(usuarioService.consultarPorIdRetornarDTO(id));
    }

    @GetMapping("/buscarPorStatus/{status}")
    @Operation(summary = "Listar usuarios por status")
    public ResponseEntity<Page<UsuarioDTO>> consultarPorStatus(@PageableDefault(sort = "id", direction = Sort.Direction.DESC, page = 0, size = 5) Pageable paginacao, @PathVariable("status") String status) {
        return ResponseEntity.ok().body(usuarioService.consultarPorStatus(status, paginacao));
    }

    @PostMapping
    @Operation(summary = "Cadastrar usuario")
    public ResponseEntity<UsuarioDTO> cadastrar(@Valid @RequestBody UsuarioForm usuarioForm, UriComponentsBuilder uriBuilder) {
        UsuarioDTO usuarioDTO = usuarioService.cadastrar(usuarioForm);
        URI uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(usuarioDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(usuarioDTO);
    }
}
