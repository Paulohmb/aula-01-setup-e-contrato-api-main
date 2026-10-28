package com.naoesquece.naoesquece;

import com.naoesquece.naoesquece.controller.LembreteController;
import com.naoesquece.naoesquece.model.Lembrete;
import com.naoesquece.naoesquece.model.Prioridade;
import com.naoesquece.naoesquece.model.Status;
import com.naoesquece.naoesquece.repository.LembreteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class VerificacaoFiltrosTest {
    @Autowired LembreteRepository repo;
    @Autowired LembreteController controller;

    @Test
    void verificaFiltrosPeriodoEAtrasados() {
        LocalDateTime agora = LocalDateTime.now();
        Lembrete atrasado = criar("Atrasado", Status.PENDENTE, Prioridade.ALTA, agora.minusDays(1));
        Lembrete futuro = criar("Futuro", Status.PENDENTE, Prioridade.BAIXA, agora.plusDays(1));
        Lembrete concluido = criar("Concluido", Status.CONCLUIDO, Prioridade.ALTA, agora.minusDays(2));
        repo.saveAllAndFlush(java.util.List.of(atrasado, futuro, concluido));

        assertEquals(3, controller.listar(null, null).getBody().size());
        assertEquals(2, controller.listar(Status.PENDENTE, null).getBody().size());
        assertEquals(2, controller.listar(null, Prioridade.ALTA).getBody().size());
        assertEquals("Atrasado", controller.listar(Status.PENDENTE, Prioridade.ALTA).getBody().getFirst().getTitulo());
        assertTrue(controller.listar(Status.CONCLUIDO, Prioridade.BAIXA).getBody().isEmpty());
        assertEquals("Atrasado", controller.buscarPorPeriodo(agora.minusDays(1).minusSeconds(1), agora.minusDays(1).plusSeconds(1)).getBody().getFirst().getTitulo());
        assertTrue(controller.buscarPorPeriodo(agora.plusDays(2), agora.plusDays(3)).getBody().isEmpty());
        assertEquals(1, controller.listarAtrasados().getBody().size());
        assertEquals("Atrasado", controller.listarAtrasados().getBody().getFirst().getTitulo());
    }

    private Lembrete criar(String titulo, Status status, Prioridade prioridade, LocalDateTime data) {
        Lembrete lembrete = new Lembrete();
        lembrete.setTitulo(titulo);
        lembrete.setStatus(status);
        lembrete.setPrioridade(prioridade);
        lembrete.setDataHora(data);
        return lembrete;
    }
}
