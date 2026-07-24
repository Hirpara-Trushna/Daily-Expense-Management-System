package com.expense.dailyexpense.entity;

import jakarta.persistence.*;

// Represents a category like "Food", "Travel", "Salary", "Rent" etc.
// The "type" field tells us if it belongs to EXPENSE or INCOME.
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String type; // "EXPENSE" or "INCOME"

    public Category() {
    }

    public Category(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
