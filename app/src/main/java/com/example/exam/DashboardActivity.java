// DashboardActivity.java
package com.example.exam;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;

public class DashboardActivity extends AppCompatActivity {
    private RecyclerView categoryBreakdown;
    private TextView totalExpense;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        totalExpense = findViewById(R.id.total_expense);
        categoryBreakdown = findViewById(R.id.category_breakdown);
        categoryBreakdown.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        loadExpenseSummary();

        FloatingActionButton addExpenseFab = findViewById(R.id.add_expense_fab);
        addExpenseFab.setOnClickListener(view -> {
            Intent intent = new Intent(DashboardActivity.this, AddEditExpenseActivity.class);
            startActivity(intent);
        });
    }

    private void loadExpenseSummary() {
        // Logic to retrieve and display total expense and category-wise breakdown from Firebase
    }
}
