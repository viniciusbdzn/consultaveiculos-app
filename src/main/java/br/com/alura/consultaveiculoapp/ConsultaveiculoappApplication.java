package br.com.alura.consultaveiculoapp;

import br.com.alura.consultaveiculoapp.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConsultaveiculoappApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ConsultaveiculoappApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal();
		principal.exibeMenu();
	}
}
