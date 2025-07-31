package com.example.Reyada.crm.expenses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("crm/expenses")
public class ExpensesController {
    @Autowired
    private ExpensesServices services;

    @PostMapping("/create")
    public ResponseEntity<?> creteNewExpense(@RequestBody ExpenseEntity expense){
        return ResponseEntity.ok(services.createNewExpense(expense));
    }

    @GetMapping
    public ResponseEntity<?> getAllExpenses(){
        return ResponseEntity.ok(services.getAllexpenses());
    }
}
