package ru.arkhipov.MySecondTestApp.service;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import ru.arkhipov.MySecondTestApp.exception.ValidationFailedException;

@Service
public interface ValidationService {
    void isValid(BindingResult bindingResult) throws ValidationFailedException;
}
