package com.example.investments_manager.model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static com.example.investments_manager.service.BalanceService.balance;

public class Withdrawal {
    private static final HashMap<Integer, Withdrawal> instances = new HashMap<>();
    private static final AtomicInteger idCounter = new AtomicInteger(1);

    private int id;
    private double withdrawalAmount;
    private int initialAmount;
    private LocalDate investmentDate;

    public Withdrawal(LocalDate date, int amount, int investmentId) {
        Investment investment = Investment.getInstance(investmentId);
        if (investment == null)
            throw new IllegalArgumentException("No such investment with this id");

        if (amount != investment.getAmount()) {
            throw new IllegalArgumentException("Partial Withdrawal is not allowed");
        }
        if (date.isAfter(LocalDate.now()) || date.isAfter(investment.getDate()))
            throw new IllegalArgumentException("date must be in the past and not before the investment date");
        this.withdrawalAmount = balance(investment);
        this.initialAmount = investment.getAmount();
        this.investmentDate = investment.getDate();
        investment.setWithdrawn(true);
        this.id = idCounter.getAndIncrement();
        instances.put(id, this);
    }

    public double getWithdrawal() {
        double gains = withdrawalAmount - initialAmount;
        double taxes = calculateTaxes(gains);
        return withdrawalAmount - taxes;
    }

    private double calculateTaxes(double gains) {
        if (investmentDate.isBefore(LocalDate.now().minusYears(2)))
            // If older than two years, the percentage will be 15% (tax = 30.00).
            return gains * 0.15;

        if (investmentDate.isBefore(LocalDate.now().minusYears(1)))
            // If it is between one and two years old, the percentage will be 18.5% (tax = 37.00).
            return gains * 0.185;

        // If it is less than one year old, the percentage will be 22.5% (tax = 45.00).
        return gains * 0.225;
    }

    @Override
    public String toString(){
        return "id: " + id + ", amount: " + getWithdrawal();
    }

}

