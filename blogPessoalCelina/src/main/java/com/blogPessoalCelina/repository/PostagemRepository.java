package com.blogPessoalCelina.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.blogPessoalCelina.model.Postagem;

@Repository
public interface PostagemRepository extends JpaRepository<Postagem, Long>{    /* o 1º param é o que será mapeado dentro do DB, ou seja, a entidade(tb_postagens). O Long representa o atributo que recebeu a anotação @Id, ou seja, a PK*/ 
	
	public List<Postagem> findAllByTituloContainingIgnoreCase(@Param("titulo") String titulo);
}

	
	                                                                          



/*Query Method são Métodos de Consulta personalizados, que permitem criar consultas específicas com qualquer Atributo da Classe associada a Interface Repositório (Postagem).*/