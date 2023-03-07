package com.blogPessoalCelina.security;

import java.util.Collection;
import java.util.List;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.blogPessoalCelina.model.Usuario;

/*esta class implementa a UserDetails, que tem como objetivo principal fornecer infos básicas do user para o Spring Sec. (usuário, senha, dir de acesso(roles), restrições da conta*/
/*Após a autent. (login) do user, a Class Service UserDetailsServiceImpl instanciará um novo Objeto da Class UserDetailsImpl com os respectivos Atributos preenchidos com os dados do usuário autenticado (usuario e senha).*/                           


public class UserDetailsImpl implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	
			private String UserName;
			private String password;
			
			private List<GrantedAuthority> authorities;
			
			public UserDetailsImpl (Usuario user) {
				this.UserName = user.getUsuario();
				this.password = user.getSenha();				
				
			}
			
			public UserDetailsImpl () {}
			
			@Override
			public Collection<? extends GrantedAuthority> getAuthorities(){                /*mét semmpre retorna uma lista vazia (collection/list) com as roles dos users, pq este Atributo não pode ser Nulo. Com o objetivo de simplificar a implementação, todo o user autenticado terá todos os direitos de acesso sobre a aplicação.*/
				
				return authorities;
				
			}
			
			@Override
			public String getPassword() {				
				return password;
				
			}
			
			@Override
			public String getUsername() {				
				return UserName;
				
			}
			
			@Override
			public boolean isAccountNonExpired() {                                /*uma conta expirada não pode ser autenticada (aí retornaria false)*/
				return true;
			}
			
			@Override
			public boolean isAccountNonLocked() {                                /*indica se o user está blocked ou não. Se estiver blocked não pode autenticar (return false)*/
				return true;
			}
			
			@Override
			public boolean isCredentialsNonExpired() {                           /*senha expirada não autentica (return false)*/
				return true;
			}
			
			@Override
			public boolean isEnabled() {                                          /*indica se o user está habilitado ou não. Desabilitado não autentica (return false)*/
				return true;
			}	


}
