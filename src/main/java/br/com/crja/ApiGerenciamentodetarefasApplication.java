package br.com.crja;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("br.com.crja")
public class ApiGerenciamentodetarefasApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGerenciamentodetarefasApplication.class, args);
	}

}
