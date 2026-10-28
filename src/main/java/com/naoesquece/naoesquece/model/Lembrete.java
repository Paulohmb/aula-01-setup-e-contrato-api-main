package com.naoesquece.naoesquece.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Lembrete {
    private Long id;
    private String titulo;
    private String descricao;
    private LocalDateTime dataHora;
    private Prioridade prioridade;
    private Status status = Status.PENDENTE;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
}
