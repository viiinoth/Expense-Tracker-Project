package com.example.exam;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddExpenseActivity extends AppCompatActivity {
    private EditText expenseNameEditText, expenseAmountEditText;
    private Spinner categorySpinner;
    private Button saveExpenseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        expenseNameEditText = findViewById(R.id.expense_name);
        expenseAmountEditText = findViewById(R.id.expense_amount);
        categorySpinner = findViewById(R.id.category_spinner);
        saveExpenseButton = findViewById(R.id.save_expense_button);

        saveExpenseButton.setOnClickListener(v -> {
            saveExpense();
        });
    }

    private void saveExpense() {
        String name = expenseNameEditText.getText().toString();
        double amount = Double.parseDouble(expenseAmountEditText.getText().toString());
        String category = categorySpinner.getSelectedItem().toString();
        String date = "2024-11-09"; // Date should be selected with a DatePicker

        // Firebase save
        Expense expense = new Expense(name, amount, category, date);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("expenses");
        String expenseId = databaseReference.push().getKey();
        if (expenseId != null) {
            databaseReference.child(expenseId).setValue(expense)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(AddExpenseActivity.this, "Expense saved!", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(AddExpenseActivity.this, "Failed to save", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}
