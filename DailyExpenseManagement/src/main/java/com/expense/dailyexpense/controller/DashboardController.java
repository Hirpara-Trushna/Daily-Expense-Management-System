package com.expense.dailyexpense.controller;

import com.expense.dailyexpense.entity.User;
import com.expense.dailyexpense.service.ExpenseService;
import com.expense.dailyexpense.service.IncomeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final ExpenseService expenseService;
    private final IncomeService incomeService;

    public DashboardController(ExpenseService expenseService,
                               IncomeService incomeService) {
        this.expenseService = expenseService;
        this.incomeService = incomeService;
    }

    @GetMapping("/dashboard")
    public String showDashboard(HttpSession session, Model model) {

        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            return "redirect:/login";
        }

        double totalExpense = expenseService.getTotalExpense(loggedInUser);
        double totalIncome = incomeService.getTotalIncome(loggedInUser);
        double balance = totalIncome - totalExpense;

        model.addAttribute("user", loggedInUser);
        model.addAttribute("totalExpense", totalExpense);
        model.addAttribute("totalIncome", totalIncome);
        model.addAttribute("balance", balance);
        model.addAttribute("recentExpenses",
                expenseService.getExpensesForUser(loggedInUser));
        model.addAttribute("recentIncomes",
                incomeService.getIncomesForUser(loggedInUser));

        return "dashboard";
    }

    @GetMapping("/report")
    public String showReport(HttpSession session, Model model) {

        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            return "redirect:/login";
        }

        model.addAttribute("user", loggedInUser);
        model.addAttribute("expenses",
                expenseService.getExpensesForUser(loggedInUser));
        model.addAttribute("incomes",
                incomeService.getIncomesForUser(loggedInUser));
        model.addAttribute("totalExpense",
                expenseService.getTotalExpense(loggedInUser));
        model.addAttribute("totalIncome",
                incomeService.getTotalIncome(loggedInUser));

        return "report";
    }
}