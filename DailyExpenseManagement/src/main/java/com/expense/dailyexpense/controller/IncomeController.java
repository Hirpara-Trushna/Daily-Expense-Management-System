package com.expense.dailyexpense.controller;

import com.expense.dailyexpense.entity.Category;
import com.expense.dailyexpense.entity.Income;
import com.expense.dailyexpense.entity.User;
import com.expense.dailyexpense.repository.CategoryRepository;
import com.expense.dailyexpense.service.IncomeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/income")
public class IncomeController {

    private final IncomeService incomeService;
    private final CategoryRepository categoryRepository;

    public IncomeController(IncomeService incomeService,
                            CategoryRepository categoryRepository) {
        this.incomeService = incomeService;
        this.categoryRepository = categoryRepository;
    }

    // Simple list of income categories
    private final List<String> categoryNames = Arrays.asList(
            "Salary",
            "Business",
            "Freelance",
            "Gift",
            "Interest",
            "Other"
    );

    // Show all incomes
    @GetMapping
    public String listIncomes(HttpSession session, Model model) {

        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            return "redirect:/login";
        }

        model.addAttribute("incomes",
                incomeService.getIncomesForUser(loggedInUser));

        model.addAttribute("categories", categoryNames);

        return "income";
    }

    // Save Income
    @PostMapping("/add")
    public String addIncome(
            @RequestParam String source,
            @RequestParam Double amount,
            @RequestParam String categoryName,
            @RequestParam(required = false) String date,
            HttpSession session) {

        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            return "redirect:/login";
        }

        LocalDate incomeDate;

        if (date == null || date.isEmpty()) {
            incomeDate = LocalDate.now();
        } else {
            incomeDate = LocalDate.parse(date);
        }

        // Check if category already exists
        Category category = categoryRepository.findByName(categoryName);

        if (category == null) {
            category = new Category();
            category.setName(categoryName);
            category.setType("INCOME");
            category = categoryRepository.save(category);
        }

        Income income = new Income();
        income.setSource(source);
        income.setAmount(amount);
        income.setDate(incomeDate);
        income.setCategory(category);
        income.setUser(loggedInUser);

        incomeService.saveIncome(income);

        return "redirect:/income";
    }

    // Delete Income
    @GetMapping("/delete/{id}")
    public String deleteIncome(@PathVariable Long id,
                               HttpSession session) {

        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            return "redirect:/login";
        }

        incomeService.deleteIncome(id);

        return "redirect:/income";
    }
}