package br.edu.iff.ccc.webproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe de entrada (main) da aplicação.
 *
 * Ao rodar este arquivo, o Spring Boot sobe um servidor Tomcat embutido
 * (por padrão em http://localhost:8080) e registra automaticamente todos
 * os @Controller encontrados no pacote e subpacotes (component scan).
 */
@SpringBootApplication
public class WebprojectApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebprojectApplication.class, args);
    }

}
