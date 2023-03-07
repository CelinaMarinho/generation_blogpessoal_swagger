package com.blogPessoalCelina.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.blogPessoalCelina.model.Usuario;



@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioRepositoryTest {
    
	@Autowired
	private UsuarioRepository usuarioRepository;                           /*foi injetado (@Autowired) um objeto da Interface UsuarioRepository para persistir os objetos no DB de testes.*/
	
	@BeforeAll
	void start(){

		usuarioRepository.deleteAll();

		usuarioRepository.save(new Usuario(0L, "Celina Marinho", "celina@email.com.br", "13465278", "https://i.imgur.com/FETvs2O.jpg"));
		
		usuarioRepository.save(new Usuario(0L, "Valdir Marinho", "valdir@email.com.br", "13465278", "https://i.imgur.com/NtyGneo.jpg"));
		
		usuarioRepository.save(new Usuario(0L, "Rafael Marinho", "rafael@email.com.br", "13465278", "https://i.imgur.com/mB3VM2N.jpg"));

        usuarioRepository.save(new Usuario(0L, "Esmeraldina Pereira", "esmeraldina@email.com.br", "13465278", "https://i.imgur.com/JR7kUFU.jpg"));

	}

	@Test
	@DisplayName("Retorna 1 usuario")
	public void deveRetornarUmUsuario() {

		Optional<Usuario> usuario = usuarioRepository.findByUsuario("celina@email.com.br");

		assertTrue(usuario.get().getUsuario().equals("celina@email.com.br"));
	}

	@Test
	@DisplayName("Retorna 3 usuarios")
	public void deveRetornarTresUsuarios() {

		List<Usuario> listaDeUsuarios = usuarioRepository.findAllByNomeContainingIgnoreCase("Marinho");

		assertEquals(3, listaDeUsuarios.size());
		
		assertTrue(listaDeUsuarios.get(0).getNome().equals("Celina Marinho"));
		assertTrue(listaDeUsuarios.get(1).getNome().equals("Valdir Marinho"));
		assertTrue(listaDeUsuarios.get(2).getNome().equals("Rafael Marinho"));
		
	}

	
	@AfterAll
	public void end() {
		usuarioRepository.deleteAll();
	}
	
}

