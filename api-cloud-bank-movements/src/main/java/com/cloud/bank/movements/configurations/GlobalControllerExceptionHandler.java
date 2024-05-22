package com.cloud.bank.movements.configurations;

import com.cloud.bank.movements.domainAccounts.exceptions.AccountAlreadyExistsException;
import com.cloud.bank.movements.domainMovements.exceptions.AccountNonExistsException;
import com.cloud.bank.movements.domainMovements.exceptions.OverdrawnAccountException;
import com.cloud.bank.movements.dto.BaseResponse;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(ConversionFailedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleConversion(RuntimeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OverdrawnAccountException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BaseResponse> handleClientAlreadyExistsByIdentification(OverdrawnAccountException ex) {
        return new ResponseEntity<>(
            BaseResponse.builder()
                .message("Saldo no disponible")
                .code("101")
                .build(),
            HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccountAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<BaseResponse> handleClientNotFoundException(AccountAlreadyExistsException ex) {
        return new ResponseEntity<>(
            BaseResponse.builder()
                .message("Account already exists")
                .code("102")
                .build(),
            HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccountNonExistsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<BaseResponse> handleClientNotFoundException(AccountNonExistsException ex) {
        return new ResponseEntity<>(
            BaseResponse.builder()
                .message("Account does not exist")
                .code("103")
                .build(),
            HttpStatus.NOT_FOUND);
    }

}
