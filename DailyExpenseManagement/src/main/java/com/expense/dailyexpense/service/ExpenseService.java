package com.expense.dailyexpense.service;

import com.expense.dailyexpense.entity.Expense;
import com.expense.dailyexpense.entity.User;
import com.expense.dailyexpense.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    // Get all expenses for a user
    public List<Expense> getExpensesForUser(User user) {
        return expenseRepository.findByUserOrderByDateDesc(user);
    }

    // Save expense
    public void saveExpense(Expense expense) {
        expenseRepository.save(expense);
    }

    // Delete expense
    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }

    // Get expense by ID
    public Expense getExpenseById(Long id) {
        Optional<Expense> expense = expenseRepository.findById(id);
        return expense.orElse(null);
    }

    // Calculate total expense
    public double getTotalExpense(User user) {

        List<Expense> expenses = getExpensesForUser(user);

        double total = 0;

        for (Expense expense : expenses) {
            if (expense.getAmount() != null) {
                total += expense.getAmount();
            }
        }

        return total;
    }
}