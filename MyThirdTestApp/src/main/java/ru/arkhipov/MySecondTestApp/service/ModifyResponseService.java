package ru.arkhipov.MySecondTestApp.service;

import org.springframework.stereotype.Service;
import ru.arkhipov.MySecondTestApp.model.Response;

@Service
public interface ModifyResponseService {
    Response modify(Response response);
}
