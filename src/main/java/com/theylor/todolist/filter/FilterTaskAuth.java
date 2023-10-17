package com.theylor.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.theylor.todolist.user.UserRepository;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
		throws ServletException, IOException {
		
		var servletPath = request.getServletPath();
		
		if (servletPath.startsWith("/tasks/")) {
			// Pega a autenticação lá do Postman e armazena em authorization (usuario e senha)
			var authorization = request.getHeader("Authorization");
			
			// Substitui o tamanho de  caracteres que "Basic", removendo os espaços em branco (trim()), por nada. 
			var authEncoded = authorization.substring("Basic".length()).trim();
			
			// Decodifica código Base64 em um array de bytes.
			byte[] authDecode = Base64.getDecoder().decode(authEncoded);
			
			// Transforma o array de bytes em uma String.'
			var authString = new String(authDecode);
			
			// Cria um array de String separando pelo ":".
			String[] credentials = authString.split(":");
			
			String username = credentials[0];
			String password = credentials[1];
			
			System.out.println("Authorization");
			System.out.println(username);
			System.out.println(password);
			
			// Validar usuario
			var user = this.userRepository.findByUsername(username);
			
			if (user == null) {
				// Envia uma resposta de erro com um status HTTP 401 (Não Autorizado) para o cliente.
				response.sendError(401);
			}
			else {
				// Validar senha
				var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());	
				if (passwordVerify.verified) {
					// Seta (define) o idUser com valor do id do objeto user.
					request.setAttribute("idUser", user.getId());
					// Permite que a requisição continue seu fluxo normal de processamento.
					filterChain.doFilter(request, response);
				}
				else {
					// Envia uma resposta de erro com um status HTTP 401 (Não Autorizado) para o cliente.
					response.sendError(401);
				}
			}
		}
		else {
			// Permite que a requisição continue seu fluxo normal de processamento.
			filterChain.doFilter(request, response);
		}
				
	}
		
		

}
