package ru.arkhipov.MySecondTestApp.service;

import ru.arkhipov.MySecondTestApp.model.Positions;

public interface AnnualBonusService {
    double calculate(Positions positions, double salary, double bonus, int workDay);

    double calculale(Positions positions, double salary, double bonus, int workDays);
}
