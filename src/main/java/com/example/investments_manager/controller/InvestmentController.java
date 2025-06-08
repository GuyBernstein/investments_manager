package com.example.investments_manager.controller;

import com.example.investments_manager.model.Investment;
import com.example.investments_manager.model.Withdrawal;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static com.example.investments_manager.service.BalanceService.balance;

@RestController
public class InvestmentController {
    @PostMapping("/create")
    public ResponseEntity<String> createInvestment(int year, int month, int day, String owner, int amount){
        LocalDate date = LocalDate.of(year, month, day);
        Investment investment = new Investment(date, owner, amount);
        return ResponseEntity.ok("invested created succesfully: " + investment);
    }

    @GetMapping("/view")
    public ResponseEntity<String> viewInvestment(int investmentId){
        Investment investment = Investment.getInstance(investmentId);
        double balance;
        if(investment != null)
            balance = balance(investment);
        else{
            Investment investment1 = new Investment(LocalDate.now(), "Guy Bernstein", 200);
            balance = balance(investment1);
        }

        return ResponseEntity.ok("Investment balance is: " + balance);
    }

    @GetMapping("/all")
    public ResponseEntity<Collection<Investment>> allInvestments(int page, int size){
        ArrayList<Investment> investmentList = new ArrayList<>(Investment.getInstances().values());

        // Calculate pagination bounds
        int startIndex = page * size;
        int endIndex = Math.min(startIndex + size, investmentList.size());

        // Validate pagination parameters
        if (startIndex >= investmentList.size() || page < 0 || size <= 0) {
            return ResponseEntity.ok(Collections.emptyList());
        }

        // Get paginated subset
        List<Investment> paginatedInvestments = investmentList.subList(startIndex, endIndex);

        return ResponseEntity.ok(paginatedInvestments);
    }

    @PostMapping("/Withdraw/{investmentId}")
    public ResponseEntity<String> withdraw(int year, int month, int day, int amount, int investmentId){
        LocalDate date = LocalDate.of(year, month, day);
        Withdrawal withdrawal = new Withdrawal(date, amount, investmentId);
        return ResponseEntity.ok("withdrawal created succesfully: " + withdrawal);
    }
}
