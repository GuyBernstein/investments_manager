package com.example.investments_manager.service;

import com.example.investments_manager.model.Investment;

import java.time.LocalDate;
import java.time.Month;

public class BalanceService {
    private static double gains(Investment investment){
        int investmentDayDate = investment.getDate().getDayOfMonth();
        Month investmentMonthDate = investment.getDate().getMonth();
        Month todayMonth = LocalDate.now().getMonth();
        int diff = todayMonth.getValue() - investmentMonthDate.getValue();
        if(investmentDayDate > LocalDate.now().getDayOfMonth())
            diff++;

        double gained = 0;
        if(diff > 0)
            gained = investment.getAmount() * 0.0052;

        for(int j = 1; j< diff; j++){
            gained *= 1.0052;
        }

        return gained;
    }

    public static double balance(Investment investment){
        return !investment.isWithdrawn() ? gains(investment) + investment.getAmount() : gains(investment);
    }


}
