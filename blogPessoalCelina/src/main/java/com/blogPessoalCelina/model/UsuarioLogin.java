package com.blogPessoalCelina.model;

public class UsuarioLogin {                                         /*Define que o cliente ao tentar autenticar (login) no sistema, forneça apenas o usuário (e-mail) e a senha. Essa Class tb pode ser definida como uma DTO (Data trasfer object), que é uma Class que é utilizada para transitar dados do sistema sem revelar sua Class Model para o cliente.*/


	private Long id;
	private String nome;
	private String usuario;
	private String senha;
	private String foto;
	private String token;                                          /*atributo diferente da Class Usuario*/
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	
}
