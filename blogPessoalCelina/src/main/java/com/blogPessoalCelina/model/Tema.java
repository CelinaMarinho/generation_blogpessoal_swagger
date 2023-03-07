package com.blogPessoalCelina.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tb_temas")
public class Tema {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Por favor, digite algo na descrição do tema")              /*NotNull + usado pra num, NotBlank para texto*/
	@Size(min=3, max=50, message="O tema deve ter entre 3 e 50 caracteres.")
	private String descricao;
	
	@OneToMany(mappedBy = "tema", cascade = CascadeType.REMOVE)                     /*A Class Tema será o lado  1 e terá uma Collection List com os objs da Class Postagem. Essa Collection vai trazer todos os objs da Class Postagem relacionados com a Class Tema*/
	@JsonIgnoreProperties("tema")                                                   /*param adic pelo fato de ser a propr do relacionamento. MappedBy-> informa o atr da Class Tema (proprietária) criado na Class Postagem (Tema tema) | cascade-> Ao executar uma ação na ent destino (Tema) o mesmo será feito na associada(Postagem) | CascadeType.REMOVE-> se apaga o Tema, apaga as postagens relacionadas. O inverso ñ acontece.*/
	private List<Postagem> postagem;                                                /*Collection List com Objs da Class Postagem, que receberá todos os Objs da Class Postagem associados a cada Obj da Class Tema.*/


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<Postagem> getPostagem() {
		return postagem;
	}

	public void setPostagem(List<Postagem> postagem) {
		this.postagem = postagem;
	}
	
	
}
