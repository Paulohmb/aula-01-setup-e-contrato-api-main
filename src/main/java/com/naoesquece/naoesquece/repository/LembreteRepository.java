package com.naoesquece.naoesquece.repository;

import com.naoesquece.naoesquece.model.Lembrete;
import com.naoesquece.naoesquece.model.Prioridade;
import com.naoesquece.naoesquece.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LembreteRepository extends JpaRepository<Lembrete, Long> {
    // Derived Query Methods gerados pelo Spring Data
    List<Lembrete> findByStatus(Status status);

    List<Lembrete> findByPrioridade(Prioridade prioridade);

    List<Lembrete> findByStatusAndPrioridade(Status status, Prioridade prioridade);

    List<Lembrete> findByDataHoraBetween(LocalDateTime inicio, LocalDateTime fim);

    // Consulta de lembretes pendentes atrasados
    List<Lembrete> findByStatusAndDataHoraBefore(Status status, LocalDateTime dataHora);

    // Consulta customizada com JPQL
    @Query("SELECT l FROM Lembrete l WHERE l.status = :status AND l.prioridade = :prioridade ORDER BY l.dataHora ASC")
    List<Lembrete> buscarOrdenadosPorData(@Param("status") Status status,
                                          @Param("prioridade") Prioridade prioridade);
}
