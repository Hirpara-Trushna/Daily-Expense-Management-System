package com.expense.dailyexpense.repository;

import com.expense.dailyexpense.entity.Income;
import com.expense.dailyexpense.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IncomeRepository extends JpaRepository<Income, Long> {

    // Finds all incomes that belong to one particular user.
    List<Income> findByUserOrderByDateDesc(User user);
}
