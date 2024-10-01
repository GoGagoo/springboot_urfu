package ru.arkhipov.MySecondTestApp.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.arkhipov.MySecondTestApp.exception.UnsupportedCodeException;
import ru.arkhipov.MySecondTestApp.exception.ValidationFailedException;
import ru.arkhipov.MySecondTestApp.model.*;
import ru.arkhipov.MySecondTestApp.service.ModifyResponseService;
import ru.arkhipov.MySecondTestApp.service.ModifySystemIdResponseService;
import ru.arkhipov.MySecondTestApp.service.ValidationService;
import ru.arkhipov.MySecondTestApp.util.DateTimeUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@RestController
public class MyController {
    private final ValidationService validationService;
    private final ModifyResponseService modifyResponseService;
    @Autowired

    public MyController(ValidationService validationService,
                       @Qualifier("ModifySystemIdResponseService") ModifyResponseService modifyResponseService) {
        this.validationService = validationService;
        this.modifyResponseService = modifyResponseService;
    }

    @PostMapping(value = "/feedback")
    public ResponseEntity<Response> feedback (@Valid @RequestBody Request request,
                                              BindingResult bindingResult)
        throws ParseException {
            log.info("request: {}", request);

            Response response = Response.builder()
                    .uid(request.getUid())
                    .operationUid(request.getOperationUid())
                    .systemTime(DateTimeUtil.getCustomFormat().format(new Date()))
                    .code(Codes.SUCCESS)
                    .errorCode(ErrorCodes.EMPTY)
                    .errorMessage(ErrorMessages.EMPTY)
                    .build();
            log.info("request: {}", request);
            try {
                if (Integer.parseInt(request.getUid()) == 123) {
                    throw new UnsupportedCodeException("UID 123 не поддерживается");
                }

                validationService.isValid(bindingResult);
            } catch (UnsupportedCodeException e) {
                response.setCode(Codes.FAILED);
                response.setErrorCode(ErrorCodes.VALIDATION_EXCEPTION);
                response.setErrorMessage(ErrorMessages.VALIDATION);
                log.error("Error mess:", e);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            } catch (ValidationFailedException e) {
                response.setCode(Codes.FAILED);
                response.setErrorCode(ErrorCodes.VALIDATION_EXCEPTION);
                response.setErrorMessage(ErrorMessages.UNKNOWN);
                log.error("Error mess:", e);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            } catch (Exception e) {
                response.setCode(Codes.FAILED);
                response.setErrorCode(ErrorCodes.VALIDATION_EXCEPTION);
                response.setErrorMessage(ErrorMessages.UNKNOWN);
                log.error("Error mess:", e);
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            modifyResponseService.modify(response);
            log.info("response: {}", response);
            SimpleDateFormat deltaTimeFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            Date deltaTime = deltaTimeFormat.parse(request.getSystemTime());
            log.info("delta time, ms: {}", (new Date()).getTime() - deltaTime.getTime());

            return new ResponseEntity<>(modifyResponseService.modify(response), HttpStatus.OK);
        }
    }
