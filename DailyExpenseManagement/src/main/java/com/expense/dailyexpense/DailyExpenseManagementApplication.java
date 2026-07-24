package com.expense.dailyexpense;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// This is the entry point of the whole application.
// Run this file (or use "mvn spring-boot:run") to start the website.
@SpringBootApplication
public class DailyExpenseManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(DailyExpenseManagementApplication.class, args);
        System.out.println("=========================================");
        System.out.println(" Daily Expense Management System Started ");
        System.out.println(" Open your browser: http://localhost:8080/login ");
        System.out.println("=========================================");
    }
}
