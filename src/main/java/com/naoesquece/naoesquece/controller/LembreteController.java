package com.naoesquece.naoesquece.controller;

import com.naoesquece.naoesquece.dto.LembreteRequestDTO;
import com.naoesquece.naoesquece.dto.LembreteResponseDTO;
import com.naoesquece.naoesquece.exception.ResourceNotFoundException;
import com.naoesquece.naoesquece.model.Lembrete;
import com.naoesquece.naoesquece.model.Status;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/lembretes")
public class LembreteController {
    private final List<Lembrete> lembretes = new ArrayList<>();
    private final AtomicLong geradorId = new AtomicLong(1);

    @PostMapping
    public ResponseEntity<LembreteResponseDTO> cadastrar(@Valid @RequestBody LembreteRequestDTO request) {
        Lembrete lembrete = new Lembrete();
        lembrete.setId(geradorId.getAndIncrement());
        lembrete.setTitulo(request.getTitulo());
        lembrete.setDescricao(request.getDescricao());
        lembrete.setDataHora(request.getDataHora());
        lembrete.setPrioridade(request.getPrioridade());
        lembrete.setStatus(Status.PENDENTE);
        lembrete.setCriadoEm(LocalDateTime.now());
        lembrete.setAtualizadoEm(LocalDateTime.now());

        lembretes.add(lembrete);
        return ResponseEntity.status(HttpStatus.CREATED).body(LembreteResponseDTO.fromEntity(lembrete));
    }

    @GetMapping
    public ResponseEntity<List<LembreteResponseDTO>> listar() {
        List<LembreteResponseDTO> dtos = new ArrayList<>();
        for (Lembrete l : lembretes) {
            dtos.add(LembreteResponseDTO.fromEntity(l));
        }
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LembreteResponseDTO> buscarPorId(@PathVariable Long id) {
        Lembrete lembrete = obterLembreteOuLancarExcecao(id);
        return ResponseEntity.ok(LembreteResponseDTO.fromEntity(lembrete));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Lembrete> atualizar(@PathVariable Long id, @RequestBody Lembrete lembreteAtualizado) {
        Lembrete lembreteExistente = obterLembreteOuLancarExcecao(id);
        lembreteExistente.setTitulo(lembreteAtualizado.getTitulo());
        lembreteExistente.setDescricao(lembreteAtualizado.getDescricao());
        lembreteExistente.setDataHora(lembreteAtualizado.getDataHora());
        lembreteExistente.setPrioridade(lembreteAtualizado.getPrioridade());
        if (lembreteAtualizado.getStatus() != null) {
            lembreteExistente.setStatus(lembreteAtualizado.getStatus());
        }
        lembreteExistente.setAtualizadoEm(LocalDateTime.now());
        return ResponseEntity.ok(lembreteExistente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        Lembrete lembrete = obterLembreteOuLancarExcecao(id);
        lembretes.remove(lembrete);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/concluir")
    public ResponseEntity<Lembrete> concluir(@PathVariable Long id) {
        Lembrete lembrete = obterLembreteOuLancarExcecao(id);
        lembrete.setStatus(Status.CONCLUIDO);
        lembrete.setAtualizadoEm(LocalDateTime.now());
        return ResponseEntity.ok(lembrete);
    }

    private Lembrete obterLembreteOuLancarExcecao(Long id) {
        for (Lembrete l : lembretes) {
            if (l.getId().equals(id)) {
                return l;
            }
        }
        throw new ResourceNotFoundException("Lembrete não encontrado com ID: " + id);
    }
}
