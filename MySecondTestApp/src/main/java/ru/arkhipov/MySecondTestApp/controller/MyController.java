package ru.arkhipov.MySecondTestApp.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.arkhipov.MySecondTestApp.exception.UnsupportedCodeException;
import ru.arkhipov.MySecondTestApp.exception.ValidationFailedException;
import ru.arkhipov.MySecondTestApp.model.Request;
import ru.arkhipov.MySecondTestApp.model.Response;
import ru.arkhipov.MySecondTestApp.service.ValidationService;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class MyController {
    private final ValidationService validationService;

    public MyController(ValidationService validationService) {
        this.validationService = validationService;
    }

    @PostMapping(value = "/feedback")
    public ResponseEntity<Response> feedback (@Valid @RequestBody Request request,
                                              BindingResult bindingResult) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        Response response = Response.builder()
                .uid(request.getUid())
                .operationUid(request.getOperationUid())
                .systemTime(simpleDateFormat.format(new Date()))
                .code("success")
                .errorCode("")
                .errorMessage("")
                .build();

        try {
            if (Integer.parseInt(request.getUid()) == 123) {
                throw new UnsupportedCodeException("UID 123 не поддерживается");
            }

            validationService.isValid(bindingResult);
        } catch (UnsupportedCodeException e) {
            response.setCode("FAILED");
            response.setErrorCode("UnsupportedCodeException");
            response.setErrorMessage(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (ValidationFailedException e) {
            response.setCode("FAILED");
            response.setErrorCode("ValidationException");
            response.setErrorMessage("Ошибка валидации");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            response.setCode("FAILED");
            response.setErrorCode("UnknownException");
            response.setErrorMessage("Произошла непредвиденная ошибка");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
