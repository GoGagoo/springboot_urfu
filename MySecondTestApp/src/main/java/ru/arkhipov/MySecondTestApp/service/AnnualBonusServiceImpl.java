package ru.arkhipov.MySecondTestApp.service;

import org.springframework.stereotype.Service;
import ru.arkhipov.MySecondTestApp.model.Positions;

@Service
public class AnnualBonusServiceImpl implements AnnualBonusService {
    @Override
    public double calculate(Positions positions, double salary, double bonus, int workDay) {
        return 0;
    }

    @Override
    public double calculale(Positions positions, double salary, double bonus, int workDays) {
        return salary * bonus * 365 * positions.getPositionCoefficient() / workDays;
    }
}
