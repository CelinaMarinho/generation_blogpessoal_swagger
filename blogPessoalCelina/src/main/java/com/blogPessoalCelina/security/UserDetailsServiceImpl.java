package com.blogPessoalCelina.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.blogPessoalCelina.model.Usuario;
import com.blogPessoalCelina.repository.UsuarioRepository;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {                                       /*essa class é uma implementação da interface ~UserDetailsService~, responsável por validar a existência de um user no sistema através do DB e retornar um obj da class UserDetailsImpl com os dados encontrados no DB*/
	                                                                                                     /*a interface ~UserDetailsService~ permite autenticar um usuário baseando-se na sua existência no DB. Para isso, é necessário que ao persistir o user no DB, a senha obrigatoriamente deve estar criptografada (implementação da Class UsuarioService), utilizando uma lib de criptografia do Spring Security e o usuario (e-mail) deve ser único no sistema, ou seja, não podem existir 2 usuários com o mesmo e-mail.*/  
	@Autowired
	private UsuarioRepository usuarioRepository; 
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {           /*O Mét loadUserByUsername(String username) recebe o user através da tela de login do sistema e utiliza o mét ~findByUsuario(String usuario)~, implementada na Interface UsuarioRepository, para checar se o user digitado está persistido no DB.*/

		Optional<Usuario> usuario = usuarioRepository.findByUsuario(userName);

		if (usuario.isPresent())
			return new UserDetailsImpl(usuario.get());                                                 /*Caso esteja persistido (isPresent()), executa o construtor da Class ~UserDetailsImpl~, passando o Obj ~usuario~ como parâm. Foi utilizado o Mét get() no Obj usuario por se tratar de um Optional.*/
		else
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);                                  /*Caso o user não exista, será devolvido o HTTP Status 403 - FORBIDDEN*/
	}
	
}







	
	


