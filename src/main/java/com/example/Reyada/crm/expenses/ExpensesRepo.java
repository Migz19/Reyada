package com.example.Reyada.crm.expenses;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpensesRepo extends JpaRepository<ExpenseEntity,Integer> {
}
