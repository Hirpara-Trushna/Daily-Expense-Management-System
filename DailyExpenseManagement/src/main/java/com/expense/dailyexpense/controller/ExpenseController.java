package com.expense.dailyexpense.controller;

import com.expense.dailyexpense.entity.Category;
import com.expense.dailyexpense.entity.Expense;
import com.expense.dailyexpense.entity.User;
import com.expense.dailyexpense.repository.CategoryRepository;
import com.expense.dailyexpense.service.ExpenseService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;
    private final CategoryRepository categoryRepository;

    public ExpenseController(ExpenseService expenseService,
                             CategoryRepository categoryRepository) {
        this.expenseService = expenseService;
        this.categoryRepository = categoryRepository;
    }

    // Simple category list
    private final List<String> categoryNames = Arrays.asList(
            "Food",
            "Travel",
            "Shopping",
            "Bills",
            "Entertainment",
            "Health",
            "Other"
    );

    // Show all expenses
    @GetMapping
    public String listExpenses(HttpSession session, Model model) {

        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            return "redirect:/login";
        }

        model.addAttribute("expenses",
                expenseService.getExpensesForUser(loggedInUser));

        return "expense";
    }

    // Open Add Expense page
    @GetMapping("/add")
    public String showAddExpenseForm(HttpSession session, Model model) {

        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            return "redirect:/login";
        }

        model.addAttribute("categories", categoryNames);

        return "addExpense";
    }

    // Save Expense
    @PostMapping("/add")
    public String addExpense(
            @RequestParam String description,
            @RequestParam Double amount,
            @RequestParam String categoryName,
            @RequestParam(required = false) String date,
            HttpSession session) {

        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            return "redirect:/login";
        }

        LocalDate expenseDate;

        if (date == null || date.isEmpty()) {
            expenseDate = LocalDate.now();
        } else {
            expenseDate = LocalDate.parse(date);
        }

        // Check if category already exists
        Category category = categoryRepository.findByName(categoryName);

        if (category == null) {
            category = new Category();
            category.setName(categoryName);
            category.setType("EXPENSE");
            category = categoryRepository.save(category);
        }

        Expense expense = new Expense();
        expense.setDescription(description);
        expense.setAmount(amount);
        expense.setDate(expenseDate);
        expense.setCategory(category);
        expense.setUser(loggedInUser);

        expenseService.saveExpense(expense);

        return "redirect:/expenses";
    }

    // Delete Expense
    @GetMapping("/delete/{id}")
    public String deleteExpense(@PathVariable Long id,
                                HttpSession session) {

        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            return "redirect:/login";
        }

        expenseService.deleteExpense(id);

        return "redirect:/expenses";
    }
}