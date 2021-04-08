package e.aman.demo.exceptions;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import e.aman.demo.models.ErrorModel;

@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler{
	
	/*
	 * For all types of exceptions
	 * */
	@ExceptionHandler(value = {Exception.class})
	public ResponseEntity<Object> handleAnyException(Exception ex , WebRequest request){
	
		ErrorModel error = new ErrorModel();
		error.setTimestamp(new Date());
		error.setMessage("custom error");
		
		return new ResponseEntity<>(error , new HttpHeaders() , HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	
	/*
	 * For custom null pointer exceptions
	 * */
	@ExceptionHandler(value = {CustomNullPointerException.class})
	public ResponseEntity<Object> handleNullException(CustomNullPointerException ex , WebRequest request){
	
		ErrorModel error = new ErrorModel();
		error.setTimestamp(new Date());
		error.setMessage(ex.getLocalizedMessage().toString());
		
		return new ResponseEntity<>(error , new HttpHeaders() , HttpStatus.NOT_FOUND);
		
	}

}
