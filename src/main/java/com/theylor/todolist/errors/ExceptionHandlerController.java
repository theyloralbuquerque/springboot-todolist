package com.theylor.todolist.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice // Define a classe como uma manager de exceções.
public class ExceptionHandlerController {

	/* Esse método trata uma exceção HttpMessageNotReadableException, que exibe uma mensagem de 
	 * erro caso o usuário digite um title com mais de 50 caracteres.
	 */
	@ExceptionHandler(HttpMessageNotReadableException.class) // Especifica a exceção que o método irá tratar.  
	public ResponseEntity<String> handleHttpMessaNotReadableException(HttpMessageNotReadableException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMostSpecificCause().getMessage());
	}
	
}
