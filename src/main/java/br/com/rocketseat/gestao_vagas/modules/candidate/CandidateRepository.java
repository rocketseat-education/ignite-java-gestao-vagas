package br.com.rocketseat.gestao_vagas.modules.candidate;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateRepository extends JpaRepository<CandidateEntity, UUID> {

}
