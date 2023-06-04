package com.stackroute.kanban.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.CONFLICT,reason = "Id Already Exist")
public class IdAlreadyExistingException extends Exception{

}
