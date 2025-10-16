package com.FutureConnect.FutureConnect.Expense;

import com.FutureConnect.FutureConnect.Auth.UserRepository;
import com.FutureConnect.FutureConnect.Model.Expense;
import com.FutureConnect.FutureConnect.Model.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
public class ExpenseService {

  @Autowired private ExpenseRepository expenseRepository;

  @Autowired private UserRepository userRepository;

  public int getBalance(String user_id) {
    if (user_id == null || user_id.isEmpty()) {
      throw new IllegalArgumentException("user_id cannot be empty");
    }
    UUID uuid = UUID.fromString(user_id);
    Optional<User> userOpt = userRepository.findById(uuid);

    if (userOpt.isEmpty()) {
      throw new IllegalArgumentException("User not found");
    }

    Optional<Expense> expense = expenseRepository.findByUserId(uuid);
    int balance = 0;
    if (expense.isPresent()) {
      balance = expense.get().getCurrentBalance();
    }
    return balance;
  }

  public int getMonthlyExpense(String user_id) {
    if (user_id == null || user_id.isEmpty()) {
      throw new IllegalArgumentException("user_id cannot be empty");
    }
    UUID uuid = UUID.fromString(user_id);
    Optional<User> userOpt = userRepository.findById(uuid);

    if (userOpt.isEmpty()) {
      throw new IllegalArgumentException("User not found");
    }

    Optional<Expense> expense = expenseRepository.findByUserId(uuid);
    int monthlyExpense = 0;
    if (expense.isPresent()) {
      monthlyExpense = expense.get().getMonthlyExpense();
    }
    return monthlyExpense;
  }

  public void addBalance(String user_id, int balance) {
    if (user_id == null || user_id.isEmpty()) {
      throw new IllegalArgumentException("user_id cannot be empty");
    }
    UUID uuid = UUID.fromString(user_id);
    Optional<User> userOpt = userRepository.findById(uuid);
    if (userOpt.isEmpty()) {
      throw new IllegalArgumentException("User not found");
    }
    Optional<Expense> expense = expenseRepository.findByUserId(uuid);
    if (expense.isPresent()) {
      expense.get().setCurrentBalance(balance);
      expenseRepository.save(expense.get());
    } else {
      Expense newExpense = new Expense();
      newExpense.setUser(userOpt.get());
      newExpense.setCurrentBalance(balance);
      newExpense.setMonthlyExpense(0);
      expenseRepository.save(newExpense);
    }
  }

  public void addMonthlyIncome(String user_id, int monthlyIncome) {
    if (user_id == null || user_id.isEmpty()) {
      throw new IllegalArgumentException("user_id cannot be empty");
    }
    UUID uuid = UUID.fromString(user_id);
    Optional<User> userOpt = userRepository.findById(uuid);
    if (userOpt.isEmpty()) {
      throw new IllegalArgumentException("User not found");
    }
    Optional<Expense> expense = expenseRepository.findByUserId(uuid);
    if (expense.isPresent()) {
      expense.get().setMonthlyExpense(monthlyIncome);
      expenseRepository.save(expense.get());
    } else {
      Expense newExpense = new Expense();
      newExpense.setUser(userOpt.get());
      newExpense.setCurrentBalance(0);
      newExpense.setMonthlyExpense(monthlyIncome);
      expenseRepository.save(newExpense);
    }
  }
}
