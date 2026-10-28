package com.naoesquece.naoesquece.controller;

import com.naoesquece.naoesquece.dto.LembreteRequestDTO;
import com.naoesquece.naoesquece.dto.LembreteResponseDTO;
import com.naoesquece.naoesquece.service.LembreteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lembretes")
public class LembreteController {
    private final LembreteService lembreteService;

    public LembreteController(LembreteService lembreteService) {
        this.lembreteService = lembreteService;
    }

    @PostMapping
    public ResponseEntity<LembreteResponseDTO> cadastrar(@Valid @RequestBody LembreteRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(lembreteService.cadastrar(request));
    }

    @GetMapping
    public ResponseEntity<List<LembreteResponseDTO>> listar() {
        return ResponseEntity.ok(lembreteService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LembreteResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(lembreteService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LembreteResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody LembreteRequestDTO request) {
        return ResponseEntity.ok(lembreteService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        lembreteService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/concluir")
    public ResponseEntity<LembreteResponseDTO> concluir(@PathVariable Long id) {
        return ResponseEntity.ok(lembreteService.concluir(id));
    }
}
