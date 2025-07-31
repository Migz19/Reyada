package com.example.Reyada.crm.expenses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpensesServices {

    @Autowired
    private ExpensesRepo repo;

    public ExpenseEntity createNewExpense(ExpenseEntity entity){
        return repo.save(entity);
    }

    public List<ExpenseEntity> getAllexpenses(){
        return repo.findAll();
    }
}
