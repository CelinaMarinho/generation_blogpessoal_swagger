package com.blogPessoalCelina.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.blogPessoalCelina.model.Tema;

@Repository
public interface TemaRepository extends JpaRepository<Tema, Long> {                        /* o 1º param é o que será mapeado dentro do DB, ou seja, a entidade(tb_temas). O Long representa o atributo que recebeu a anotação @Id, ou seja, a PK*/
	
	public List<Tema> findAllByDescricaoContainingIgnoreCase(@Param("descricao") String descricao);
}
