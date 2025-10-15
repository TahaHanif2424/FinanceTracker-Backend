package com.FutureConnect.FutureConnect.Expense.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MonthlyIncomeDTO {
    private String userId;
    private int monthlyIncome;
}
