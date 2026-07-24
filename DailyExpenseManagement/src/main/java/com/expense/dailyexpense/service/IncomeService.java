package com.expense.dailyexpense.service;

import com.expense.dailyexpense.entity.Income;
import com.expense.dailyexpense.entity.User;
import com.expense.dailyexpense.repository.IncomeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IncomeService {

    private final IncomeRepository incomeRepository;

    public IncomeService(IncomeRepository incomeRepository) {
        this.incomeRepository = incomeRepository;
    }

    // Get all incomes for a user
    public List<Income> getIncomesForUser(User user) {
        return incomeRepository.findByUserOrderByDateDesc(user);
    }

    // Save income
    public void saveIncome(Income income) {
        incomeRepository.save(income);
    }

    // Delete income
    public void deleteIncome(Long id) {
        incomeRepository.deleteById(id);
    }

    // Get income by ID
    public Income getIncomeById(Long id) {
        Optional<Income> income = incomeRepository.findById(id);
        return income.orElse(null);
    }

    // Calculate total income
    public double getTotalIncome(User user) {

        List<Income> incomes = getIncomesForUser(user);

        double total = 0;

        for (Income income : incomes) {
            if (income.getAmount() != null) {
                total += income.getAmount();
            }
        }

        return total;
    }
}