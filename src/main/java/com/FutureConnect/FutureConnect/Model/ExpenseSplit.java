package com.FutureConnect.FutureConnect.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "expense_splits")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseSplit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "group_expense_id", nullable = false)
    private GroupExpense groupExpense;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private int amount;
    private boolean isPaid;
}
