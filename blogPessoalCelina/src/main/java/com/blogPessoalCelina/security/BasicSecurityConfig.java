package com.blogPessoalCelina.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration                                                            /*Class é do tipo configuração, ou seja, define uma Classe como fonte de definições de Beans*/
@EnableWebSecurity                                                        /*habilita a segurança de forma Global (toda a aplicação) e sobrescreve os Mét que irão redefinir as regras de Segurança da aplicação*/
public class BasicSecurityConfig {                                       /*Essa class é utilizada para sobrescrever a configuração padrão da Spring Security.*/
	
	@Bean                                                                /*No Spring, os obj que formam a espinha dorsal da aplicação e que são gerenciados pelo Spring são chamados de Beans. É um obj instanciado, montado e gerenc pelo Spring. Pode-se criar Benas ao usar a anotação @Bean em um Mét e transformar a instância retornada pelo Mét em um Obj gerenc. pelo Spring. Essas classes, nada mais são do que Classes que irão escrever as regras de funcionamento da  aplicação, que poderão ser utilizadas em qualquer Class, diferente da Injeção de Dependência criada pela anotação @Autowired, que só permite o uso dentro da Class em que foi criada.*/
	public PasswordEncoder passwordEncoder() {                           /*O Mét ~passwordEncoder()~, indica ao Spring Security que a aplicação está baseada em um modelo de criptografia*/
        return new BCryptPasswordEncoder();                              /* o modelo ~BCryptPasswordEncoder()~ se trata de um algoritmo de criptografia do tipo hash*/
    }
	
	@Bean	
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {           /*Esse mét implementa a config de autenticação*/
        return authenticationConfiguration.getAuthenticationManager();                                                                       /*o mét dessa linha é utilizado pelo mét acima para para procurar uma implementação da Interface ~UserDetailsService~ e utilizá-la para identificar se o usuário é válido ou não. No caso, será utilizada a Class UserDetailsServiceImpl, que valida o user no DB (e pq é ele que implementa a Interface UserDetailsService. */
    }
	 
	/*Configs essenciais*/
	 @Bean
	   public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {                  /*O Mét ~SecurityFilterChain filterChain(HttpSecurity http)~ informa ao Spring que a config padrão da Spring Security será substituída por uma nova config (desabilita o form. de login e habilita a autenticação via http).*/
                                                                                                     /*Class HttpSecurity: Permite configurar a segurança baseada na Web para solicitações http específicas*/
	        http
	            .sessionManagement()                                                                 /*.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS): Define que o sistema não guardará sessões para o cliente*/                                                                                                                                                                          
	            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	            .and().csrf().disable()                                                              /*Desabilita a proteção que vem ativa contra ataques do tipo CSRF (Cross-Site-Request-Forgery - interceptação dos dados de autenticação antes da requisição chegar ao servidor). Foi desabilitado para o Spring não bloquear todas as requisições diferentes de GET*/
	            .cors();                                                                             /*Libera o acesso de outras origens, a aplicação poderá ser acessada de outros domínios*/
            
	        /*configs específicas*/
	        http
	            .authorizeHttpRequests((auth) -> auth                                                 /*Expressão Lambda para definir quais endpoints poderão acessar o sistema sem precisar de autenticação. O Obj ~auth~ recebe o endereço (URI) da request e checa se o endpoint necessita ou não de autenticação.*/
	                .antMatchers("/usuarios/logar").permitAll()                                       /*Define os endereços (URI) dos endpoints que estarão acessíveis sem autenticação. Apenas os endpoints logar e cadastrar serão livres de autenticação.*/
	                .antMatchers("/usuarios/cadastrar").permitAll()                                   /*Define os endereços (URI) dos endpoints que estarão acessíveis sem autenticação. Apenas os endpoints logar e cadastrar serão livres de autenticação.*/
	                .antMatchers(HttpMethod.OPTIONS).permitAll()                                      /*O parâmetro ~HttpMethod.OPTIONS~ permite que o cliente (front-end) descubra quais são as opções permitidas e/ou obrigatórias no header da request. Se ele  não for liberado, a aplicação não receberá o Basic Token através do Header da request, impedindo a aplicação de responder as requests protegidas.*/
	                .anyRequest().authenticated())                                                    /*Informa ao sistema que todos os endpoints que não estiverem especificados na lista acima, a autenticação será obrigatória.*/
	            .httpBasic();                                                                         /*Informa ao sistema que o servidor irá receber requests que devem ter o esquema HTTP Basic de autenticação.*/ 

	        return http.build();                                                                      /*Cria e instancia o Obj ~http~ com as configs implementadas.*/

	    }	

}
