package com.expense.dailyexpense.repository;

import com.expense.dailyexpense.entity.Expense;
import com.expense.dailyexpense.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    // Finds all expenses that belong to one particular user.
    List<Expense> findByUserOrderByDateDesc(User user);
}
