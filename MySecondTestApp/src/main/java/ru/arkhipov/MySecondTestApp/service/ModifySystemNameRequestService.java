package ru.arkhipov.MySecondTestApp.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.arkhipov.MySecondTestApp.model.Request;
import ru.arkhipov.MySecondTestApp.model.Systems;

@Service
public class ModifySystemNameRequestService implements ModifyRequestService {
    private static final String URL = "http://localhost:8084/feedback";

    @Override
    public void modify(Request request) {
        request.setSystemName(Systems.valueOf("Service 1"));

        HttpEntity<Request> httpEntity = new HttpEntity<>(request);

        new RestTemplate().exchange(URL),
                HttpMethod.POST,
                httpEntity,
                new ParameterizedTypeReference<>() {
                });

    }
}
