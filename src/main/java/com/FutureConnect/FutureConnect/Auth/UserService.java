package com.FutureConnect.FutureConnect.Auth;

import com.FutureConnect.FutureConnect.Expense.ExpenseService;
import com.FutureConnect.FutureConnect.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired private UserRepository userRepository;

  @Autowired private ExpenseService expenseService;
  @Autowired private PasswordEncoder passwordEncoder;

  public User signup(User user) {

    if (user.getEmail().isEmpty() || user.getPassword().isEmpty() || user.getName().isEmpty()) {
      throw new IllegalArgumentException("Invalid email or password");
    }
    if (userRepository.findByEmail(user.getEmail()).isPresent()) {
      throw new RuntimeException("Email already exists");
    }
    //        if(user.getPassword().length() < 6){
    //            throw new IllegalArgumentException("Password must be at least 6 characters");
    //        }

    user.setPassword(passwordEncoder.encode(user.getPassword()));

    User savedUser = userRepository.save(user);

    // Initialize balance and monthly expense to 0
    expenseService.addBalance(savedUser.getId().toString(), 0);

    return savedUser;
  }

  public User login(User user) {
    if (user.getEmail().isEmpty() || user.getPassword().isEmpty()) {
      throw new IllegalArgumentException("Invalid email or password");
    }
    if (userRepository.findByEmail(user.getEmail()).isPresent()) {
      System.out.println("Logged in");
    }
    User findedUser =
        userRepository
            .findByEmail(user.getEmail())
            .orElseThrow(() -> new RuntimeException("User not found"));
    if (passwordEncoder.matches(user.getPassword(), findedUser.getPassword())) {
      return findedUser;
    }
    throw new RuntimeException("Invalid password");
  }

}
