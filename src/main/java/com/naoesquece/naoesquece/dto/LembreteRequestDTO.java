package com.naoesquece.naoesquece.dto;

import com.naoesquece.naoesquece.model.Prioridade;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LembreteRequestDTO {
    @NotBlank(message = "O título é obrigatório")
    @Size(min = 3, max = 100, message = "O título deve ter entre 3 e 100 caracteres")
    private String titulo;

    @Size(max = 255, message = "A descrição não pode exceder 255 caracteres")
    private String descricao;

    @NotNull(message = "A data e hora são obrigatórias")
    @FutureOrPresent(message = "A data e hora do lembrete não podem estar no passado")
    private LocalDateTime dataHora;

    @NotNull(message = "A prioridade é obrigatória")
    private Prioridade prioridade;
}
