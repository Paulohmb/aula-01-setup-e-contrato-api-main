package com.naoesquece.naoesquece.dto;

import com.naoesquece.naoesquece.model.Lembrete;
import com.naoesquece.naoesquece.model.Prioridade;
import com.naoesquece.naoesquece.model.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LembreteResponseDTO {
    private Long id;
    private String titulo;
    private String descricao;
    private LocalDateTime dataHora;
    private Prioridade prioridade;
    private Status status;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;

    public static LembreteResponseDTO fromEntity(Lembrete lembrete) {
        if (lembrete == null) {
            return null;
        }
        return new LembreteResponseDTO(
                lembrete.getId(),
                lembrete.getTitulo(),
                lembrete.getDescricao(),
                lembrete.getDataHora(),
                lembrete.getPrioridade(),
                lembrete.getStatus(),
                lembrete.getCriadoEm(),
                lembrete.getAtualizadoEm()
        );
    }
}
