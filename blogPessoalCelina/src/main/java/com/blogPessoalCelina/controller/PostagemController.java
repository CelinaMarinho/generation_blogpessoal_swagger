package com.blogPessoalCelina.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.blogPessoalCelina.model.Postagem;
import com.blogPessoalCelina.repository.PostagemRepository;
import com.blogPessoalCelina.repository.TemaRepository;

@RestController /*recebe e request e responde com um HTTP Status + response do processamento (body)*/
@RequestMapping("/postagens") /*mapeia as solicitações via URL, define a URL padrão do recurso(depois da /) envia a request para a classe responsável pelo recurso*/
@CrossOrigin(origins = "*", allowedHeaders = "*" )
public class PostagemController {
	
	@Autowired /*A Injeção de Dependência define quais Classes serão instanciadas e em quais lugares serão Injetadas quando houver necessidade.*/
	private PostagemRepository postagemRepository;                        /*e quando houver a necessidade o Spring Framework cria um novo Objeto da Classe Postagem a partir da Interface PostagemRepository, permitindo o uso de todos os Métodos da Interface*/
    
	@Autowired
	private TemaRepository temaRepository;                                 /*Para ter acesso aos Mét das Class Tema e TemaController, precisa inserir uma  Injeção de Dependência do Recurso Tema logo abaixo da  Inj de Depend do Recurso Postagem.*/
	
    @GetMapping /*mapeia todas as requests GET*/                         /*mapeia todas as requisições get enviadas para a rota específica dentro do recurso Postagem que será respondida pelo getAll*/
	public ResponseEntity<List<Postagem>> getAll(){	                      /* retorna todos os Objetos da Classe Postagem persistidos no DB, na tabela tb_postagens.*/
    	return ResponseEntity.ok(postagemRepository.findAll());          /*executa o findAll(), retorna obj da classe Postagem dentro da LIST. A List sempre será gerada (vazia ou não), o Método sempre retornará o Status 200 🡪 OK.*/
    }
    
    @GetMapping("/{id}")                                                      /*mapeia todas as requisições get enviadas para a rota específica dentro do recurso Postagem que será respondida pelo getById*. Http://localhost:8080/postagens/id, onde id é uma Variável de Caminho (Path Variable), que receberá o id da Postagem que será Consultada.*/
    public ResponseEntity<Postagem> getById(@PathVariable Long id){           /*mét tem um param do tipo Long(id)*/
    	
    	return postagemRepository.findById(id)                                   /*o mét retornará o obj da Postagem caso encontrado, se não retorna Nulo*/
    			.map(resposta -> ResponseEntity.ok(resposta))                    /*o retorno do mét não pode ser nulo. o map(optional) encapsula e retona se o obj está presente ou não*. Se encontrado, insere o Objeto mapeado no Corpo da Resposta e ResponseEntity.ok(resp); e retorna o HTTP Status OK🡪200. Mét seja declarado no mesmo lugar em que será usado e a sua declaração seja implícita*/
    			.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());    /*Não encontrado = NOT_FOUND e o met build() constrói a resposta com o HTTPStatus retornado*/
    }
    
    @GetMapping("/titulo/{titulo}")
    public ResponseEntity<List<Postagem>>  getByTitulo(@PathVariable String titulo) {                  /*retorna um obj da class List contendo todos os objs da class Postagem persistidos no DB, na table tb_postagens onde o atrib titulo contenha a Sting enviada com param do método */
    		return ResponseEntity.ok(postagemRepository.findAllByTituloContainingIgnoreCase(titulo));
    	
    }
    
    @PostMapping  /*responde todas as requests POST*/	                    
    public ResponseEntity<Postagem> post(@Valid @RequestBody Postagem postagem){      /*@Valid - valida o obj enviado como resposta, de acordo com as regras da Model.*/
    	if(temaRepository.existsById(postagem.getTema().getId()))
    		return ResponseEntity.status(HttpStatus.CREATED).body(postagemRepository.save(postagem));                                        /*Executa o met save(postagem) que é o padrão e retorna STATUS CREATED 201 se o obj foi persistido no DB*/
    	
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    	
    }
    
    @PutMapping
    public ResponseEntity<Postagem> put(@Valid @RequestBody Postagem postagem) {
    	if (postagemRepository.existsById(postagem.getId())) {
    		
    		if (temaRepository.existsById(postagem.getTema().getId()))
    			return ResponseEntity.status(HttpStatus.OK).body(postagemRepository.save(postagem));
    		
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();    		
    	}
    	
    	return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    	
    	/*return postagemRepository.findById(postagem.getId())
    		.map(resposta -> ResponseEntity.status(HttpStatus.OK).body(postagemRepository.save(postagem)))
			.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());*/
    }
    
    @ResponseStatus(HttpStatus.NO_CONTENT)                                            /*o mét terá uma response específica, que em caso positivo será o 203*/
    @DeleteMapping("/{id}")    
    public void delete(@PathVariable Long id) {                                      /*insere o valor passado  no endpoint para a variável de caminho id */
    	Optional<Postagem> postagem = postagemRepository.findById(id);              /*Cria um Objeto Optional - postagem -  da Classe Postagem*/ 
    	
    	if(postagem.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		postagemRepository.deleteById(id);
    	
    }
    
}
