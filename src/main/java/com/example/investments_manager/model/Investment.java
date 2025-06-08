package com.example.investments_manager.model;



import java.time.LocalDate;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static com.example.investments_manager.service.BalanceService.balance;

public class Investment {
    private static final HashMap<Integer, Investment> instances = new HashMap<>();
    private static final AtomicInteger idConter = new AtomicInteger(1);

    private final int id;
    private LocalDate date;
    private String owner;
    private int amount;
    private boolean isWithdrawn;

    public Investment(LocalDate date, String owner, int amount) {
        this.id = idConter.getAndIncrement();
        setDate(date);
        setAmount(amount);
        this.owner = owner;
        this.isWithdrawn = false;

        instances.put(id, this);
    }

    public boolean isWithdrawn() {
        return isWithdrawn;
    }

    public void setWithdrawn(boolean withdrawn) {
        isWithdrawn = withdrawn;
    }

    public static Investment getInstance(int id){
        return instances.get(id);
    }

    public LocalDate getDate() {
        return date;
    }

    public String getOwner() {
        return owner;
    }

    public int getAmount() {
        return amount;
    }

    public static HashMap<Integer, Investment> getInstances() {
        return instances;
    }

    public static AtomicInteger getIdConter() {
        return idConter;
    }

    public int getId() {
        return id;
    }

    public void setDate(LocalDate date) {
        if(date.isAfter(LocalDate.now()))
            throw new IllegalArgumentException("Investment date must be today or in the past");
        this.date = date;
    }

    public void setAmount(int amount) {
        if(amount < 0)
            throw new IllegalArgumentException("Investment amount cannot be negative");
        this.amount = amount;
    }

    @Override
    public String toString(){
        return "id: " + id + ", date: " + date + ", owner: " + owner + ", balance: " + balance(this) + ", isWithdrawn: " + isWithdrawn;
    }
}
