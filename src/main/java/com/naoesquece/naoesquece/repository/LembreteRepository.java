package com.naoesquece.naoesquece.repository;

import com.naoesquece.naoesquece.model.Lembrete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LembreteRepository extends JpaRepository<Lembrete, Long> {
}
