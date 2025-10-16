package com.FutureConnect.FutureConnect.Expense;

import com.FutureConnect.FutureConnect.Expense.DTO.BalanceDTO;
import com.FutureConnect.FutureConnect.Expense.DTO.MonthlyIncomeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/expense")
public class ExpenseController {

  @Autowired private ExpenseService expenseService;

  @GetMapping("/balance/{userId}")
  public ResponseEntity<Integer> getBalance(@PathVariable String userId) {
    int balance = expenseService.getBalance(userId);
    return ResponseEntity.ok(balance);
  }

  @GetMapping("/monthly/{userId}")
  public ResponseEntity<Integer> getMonthlyExpense(@PathVariable String userId) {
    int monthlyExpense = expenseService.getMonthlyExpense(userId);
    return ResponseEntity.ok(monthlyExpense);
  }

  @PutMapping("/balance")
  public ResponseEntity<String> addExpense(@RequestBody BalanceDTO expense) {
    expenseService.addBalance(expense.getUserId(), expense.getBalance());
    return ResponseEntity.ok("Balance updated successfully");
  }

  @PutMapping("/monthly-income")
  public ResponseEntity<String> addMonthlyIncome(@RequestBody MonthlyIncomeDTO monthlyIncomeDTO) {
    expenseService.addMonthlyIncome(
        monthlyIncomeDTO.getUserId(), monthlyIncomeDTO.getMonthlyIncome());
    return ResponseEntity.ok("Monthly income updated successfully");
  }
}
