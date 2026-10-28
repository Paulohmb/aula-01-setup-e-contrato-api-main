package com.naoesquece.naoesquece.service;

import com.naoesquece.naoesquece.dto.LembreteRequestDTO;
import com.naoesquece.naoesquece.dto.LembreteResponseDTO;
import com.naoesquece.naoesquece.exception.ResourceNotFoundException;
import com.naoesquece.naoesquece.model.Lembrete;
import com.naoesquece.naoesquece.model.Prioridade;
import com.naoesquece.naoesquece.model.Status;
import com.naoesquece.naoesquece.repository.LembreteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class LembreteService {
    private final LembreteRepository lembreteRepository;

    public LembreteService(LembreteRepository lembreteRepository) {
        this.lembreteRepository = lembreteRepository;
    }

    @Transactional
    public LembreteResponseDTO cadastrar(LembreteRequestDTO dto) {
        Lembrete lembrete = new Lembrete();
        lembrete.setTitulo(dto.getTitulo());
        lembrete.setDescricao(dto.getDescricao());
        lembrete.setDataHora(dto.getDataHora());
        lembrete.setPrioridade(dto.getPrioridade());
        lembrete.setStatus(Status.PENDENTE);
        lembrete.setCriadoEm(LocalDateTime.now());
        lembrete.setAtualizadoEm(LocalDateTime.now());
        return LembreteResponseDTO.fromEntity(lembreteRepository.save(lembrete));
    }

    public List<LembreteResponseDTO> listarTodos() {
        List<Lembrete> lembretes = lembreteRepository.findAll();
        List<LembreteResponseDTO> dtos = new ArrayList<>();
        for (Lembrete l : lembretes) {
            dtos.add(LembreteResponseDTO.fromEntity(l));
        }
        return dtos;
    }

    public List<LembreteResponseDTO> listarComFiltros(Status status, Prioridade prioridade) {
        List<Lembrete> resultado;
        if (status != null && prioridade != null) {
            resultado = lembreteRepository.findByStatusAndPrioridade(status, prioridade);
        } else if (status != null) {
            resultado = lembreteRepository.findByStatus(status);
        } else if (prioridade != null) {
            resultado = lembreteRepository.findByPrioridade(prioridade);
        } else {
            resultado = lembreteRepository.findAll();
        }

        List<LembreteResponseDTO> dtos = new ArrayList<>();
        for (Lembrete l : resultado) {
            dtos.add(LembreteResponseDTO.fromEntity(l));
        }
        return dtos;
    }

    public List<LembreteResponseDTO> buscarPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        List<Lembrete> lembretes = lembreteRepository.findByDataHoraBetween(inicio, fim);
        List<LembreteResponseDTO> dtos = new ArrayList<>();
        for (Lembrete l : lembretes) {
            dtos.add(LembreteResponseDTO.fromEntity(l));
        }
        return dtos;
    }

    public List<LembreteResponseDTO> listarAtrasados() {
        List<Lembrete> lembretes = lembreteRepository.findByStatusAndDataHoraBefore(
                Status.PENDENTE, LocalDateTime.now());
        List<LembreteResponseDTO> dtos = new ArrayList<>();
        for (Lembrete l : lembretes) {
            dtos.add(LembreteResponseDTO.fromEntity(l));
        }
        return dtos;
    }

    public LembreteResponseDTO buscarPorId(Long id) {
        return LembreteResponseDTO.fromEntity(obterEntidadePorId(id));
    }

    @Transactional
    public LembreteResponseDTO atualizar(Long id, LembreteRequestDTO dto) {
        Lembrete lembrete = obterEntidadePorId(id);
        lembrete.setTitulo(dto.getTitulo());
        lembrete.setDescricao(dto.getDescricao());
        lembrete.setDataHora(dto.getDataHora());
        lembrete.setPrioridade(dto.getPrioridade());
        lembrete.setAtualizadoEm(LocalDateTime.now());
        return LembreteResponseDTO.fromEntity(lembreteRepository.save(lembrete));
    }

    @Transactional
    public LembreteResponseDTO concluir(Long id) {
        Lembrete lembrete = obterEntidadePorId(id);
        lembrete.setStatus(Status.CONCLUIDO);
        lembrete.setAtualizadoEm(LocalDateTime.now());
        return LembreteResponseDTO.fromEntity(lembreteRepository.save(lembrete));
    }

    @Transactional
    public void excluir(Long id) {
        lembreteRepository.delete(obterEntidadePorId(id));
    }

    private Lembrete obterEntidadePorId(Long id) {
        return lembreteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lembrete não encontrado com ID: " + id));
    }
}
