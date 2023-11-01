package br.com.rocketseat.gestao_vagas.modules.candidate.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.rocketseat.gestao_vagas.modules.candidate.CandidateEntity;
import br.com.rocketseat.gestao_vagas.modules.candidate.dto.ProfileCandidateResponseDTO;
import br.com.rocketseat.gestao_vagas.modules.candidate.useCases.CreateCandidateUseCase;
import br.com.rocketseat.gestao_vagas.modules.candidate.useCases.ListAllJobsByFilterUseCase;
import br.com.rocketseat.gestao_vagas.modules.candidate.useCases.ProfileCandidateUseCase;
import br.com.rocketseat.gestao_vagas.modules.company.entities.JobEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/candidate")
@Tag(name = "Candidato", description = "Informações do candidato")
public class CandidateController {
  @Autowired
  private CreateCandidateUseCase createCandidateUseCase;

  @Autowired
  private ProfileCandidateUseCase profileCandidateUseCase;

  @Autowired
  private ListAllJobsByFilterUseCase listAllJobsByFilterUseCase;

  @PostMapping("/")
  @Operation(summary = "Cadastro de candidato", description = "Essa função é responsável por cadastrar um candidato")
  @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(schema = @Schema(implementation = CandidateEntity.class))
      }),
      @ApiResponse(responseCode = "400", description = "Usuário já existe")
  })
  public ResponseEntity<Object> create(@Valid @RequestBody CandidateEntity candidateEntity) {
    try {
      var result = createCandidateUseCase.execute(candidateEntity);
      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping("/")
  @PreAuthorize("hasRole('CANDIDATE')")
  @Operation(summary = "Perfil do candidato", description = "Essa função é responsável por buscar as informações do perfil do candidato")
  @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(schema = @Schema(implementation = ProfileCandidateResponseDTO.class))
      }),
      @ApiResponse(responseCode = "400", description = "User not found")
  })
  @SecurityRequirement(name = "jwt_auth")
  public ResponseEntity<Object> get(HttpServletRequest request) {
    var idCandidate = request.getAttribute("candidate_id");

    try {
      var profile = this.profileCandidateUseCase
          .execute(UUID.fromString(idCandidate.toString()));
      return ResponseEntity.ok().body(profile);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping("/job")
  @PreAuthorize("hasRole('CANDIDATE')")
  @Operation(summary = "Listagem de vagas disponível para o candidato", description = "Essa função é responsável por listar todas as vagas disponíveis, baseada no filtro")
  @SecurityRequirement(name = "jwt_auth")
  @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(array = @ArraySchema(schema = @Schema(implementation = JobEntity.class)))
      })
  })
  public List<JobEntity> findJobByFilter(@RequestParam String filter) {
    return this.listAllJobsByFilterUseCase.execute(filter);
  }
}
