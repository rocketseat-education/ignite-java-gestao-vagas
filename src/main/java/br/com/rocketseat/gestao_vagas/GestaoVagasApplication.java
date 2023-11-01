package br.com.rocketseat.gestao_vagas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Gestão de Vagas", description = "API responsável pela gestão de vagas", version = "1"))
@SecurityScheme(name = "jwt_auth", scheme = "bearer", bearerFormat = "JWT", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
public class GestaoVagasApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestaoVagasApplication.class, args);
	}

}
