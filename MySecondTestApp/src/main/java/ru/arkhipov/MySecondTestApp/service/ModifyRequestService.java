package ru.arkhipov.MySecondTestApp.service;

import org.springframework.stereotype.Service;
import ru.arkhipov.MySecondTestApp.model.Request;

@Service
public interface ModifyRequestService {

    void modify(Request request);
}
