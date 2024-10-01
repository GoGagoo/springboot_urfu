package ru.arkhipov.MySecondTestApp.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.arkhipov.MySecondTestApp.model.Response;
import ru.arkhipov.MySecondTestApp.util.DateTimeUtil;

import java.util.Date;

@Service
@Qualifier("ModifySystemIdResponseService")
public class ModifySystemIdResponseService implements ModifyResponseService {
    @Override
    public Response modify(Response response) {
        response.setSystemTime(DateTimeUtil.getCustomFormat().format(new Date()));

        return response;
    }
}