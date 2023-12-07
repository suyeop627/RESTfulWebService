package com.study.udemyrest.exceptions;

import com.study.udemyrest.model.response.ErrorMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice//controller들에 대한 전역 설정. 컨트롤러의 예외처리나 다양한 컨트롤러에 대한 설정등에 활용
public class AppExceptionsHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value=Exception.class)//처리하려는 exception 지정. 매서드의 인자로도 같은 exception 또는 해당 예외를 포함하는 예외를 받아야함
  public ResponseEntity<Object> handleAnyException(Exception ex, WebRequest request){
    //null 일 수 도 있어서 체크해줘야함.
    String errorMessageDescription = ex.getLocalizedMessage();
    if(errorMessageDescription==null){
      errorMessageDescription = ex.toString();
    }
    ErrorMessage errorMessage = new ErrorMessage(new Date(), errorMessageDescription);
    return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
  }


  //더 세분화된 예외가 정의돼있으면 넓은 범위의 예외보다 세분화된 예외가 잡힘(위에 Exception으로 잡았어도 npe 발생하면 npehandler가 발동함)
  @ExceptionHandler(value={NullPointerException.class,UserServiceException.class})
  public ResponseEntity<Object> handleSpecificException(Exception ex, WebRequest request){
    String errorMessageDescription = ex.getLocalizedMessage();
    if(errorMessageDescription==null){
      errorMessageDescription = ex.toString();
    }

    ErrorMessage errorMessage = new ErrorMessage(new Date(), errorMessageDescription);
    return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

}
