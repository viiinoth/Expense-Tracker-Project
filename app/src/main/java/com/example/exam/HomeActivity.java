package com.example.exam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ExpenseAdapter expenseAdapter;
    private List<Expense> expenseList;
    private TextView totalExpenseTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        totalExpenseTextView = findViewById(R.id.total_expense);
        recyclerView = findViewById(R.id.expense_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        expenseList = new ArrayList<>();
        expenseAdapter = new ExpenseAdapter(this, expenseList);
        recyclerView.setAdapter(expenseAdapter);

        findViewById(R.id.add_expense_button).setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, AddExpenseActivity.class);
            startActivity(intent);
        });

        // Update the total expense from Firebase data
        updateTotalExpense();
    }

    private void updateTotalExpense() {
        // Firebase code to fetch and sum up the expenses
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("expenses");
        reference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<Expense> expenses = task.getResult().getValue(new GenericTypeIndicator<List<Expense>>() {});
                double totalExpense = 0;
                for (Expense expense : expenses) {
                    totalExpense += expense.getAmount();
                }
                totalExpenseTextView.setText("Total Expense: $" + totalExpense);
            }
        });
    }
}
