package com.generator.randomusersgenerator.controllers.controlleradvice;

import com.generator.randomusersgenerator.controllers.UserController;
import com.generator.randomusersgenerator.exceptions.EmptyUserRepositoryException;
import com.generator.randomusersgenerator.exceptions.EmailAlreadyExists;
import com.generator.randomusersgenerator.exceptions.UserDoesNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = UserController.class)
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserControllerAdvice {

//    public record FieldValidationError(String field, String message) {
//    }
    //    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public List<FieldValidationError> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
//        List<FieldValidationError> validationErrorList = new ArrayList<>();
//        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
//            validationErrorList.add(new FieldValidationError(fieldError.getField(), fieldError.getDefaultMessage()));
//        }
//        return validationErrorList;
//    }

    @ExceptionHandler(UserDoesNotExistException.class)
    public String handleUserDoesNotExistException(UserDoesNotExistException userDoesNotExistException) {
        return "message: " + userDoesNotExistException.getMessage();
    }

    @ExceptionHandler(EmptyUserRepositoryException.class)
    public String handleEmptyUserRepositoryException(EmptyUserRepositoryException emptyUserRepository) {
        return "message: " + emptyUserRepository.getMessage();
    }

    @ExceptionHandler(EmailAlreadyExists.class)
    public String handleEmailAlreadyExistsException(EmailAlreadyExists emailAlreadyExists) {
        return "message: " + emailAlreadyExists.getMessage();
    }

    @ExceptionHandler(RuntimeException.class)
    public String runtimeException(RuntimeException runtimeException) {
        return "message: " + runtimeException.getMessage();
    }
}
