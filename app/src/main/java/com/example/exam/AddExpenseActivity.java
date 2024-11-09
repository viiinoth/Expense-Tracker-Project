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
import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.widget.ArrayAdapter; // Added for spinner
import java.util.Calendar;

public class AddExpenseActivity extends AppCompatActivity {
    private EditText expenseNameEditText, expenseAmountEditText;
    private Spinner categorySpinner;
    private Button saveExpenseButton, expenseDateButton;
    private int mYear, mMonth, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        expenseNameEditText = findViewById(R.id.expense_name);
        expenseAmountEditText = findViewById(R.id.expense_amount);
        categorySpinner = findViewById(R.id.category_spinner);
        saveExpenseButton = findViewById(R.id.save_expense_button);
        expenseDateButton = findViewById(R.id.expense_date_button);

        // Populate the spinner with categories
        populateCategorySpinner();

        // Set default date when activity is created
        setDefaultDate();

        // Show DatePicker when the button is clicked
        expenseDateButton.setOnClickListener(v -> showDatePicker());

        // Save the expense when the save button is clicked
        saveExpenseButton.setOnClickListener(v -> saveExpense());
    }

    private void populateCategorySpinner() {
        // List of categories to be displayed in the spinner
        String[] categories = {"Select Category", "Food", "Transport", "Accommodation"};

        // Create an ArrayAdapter to bind the data to the Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set the adapter to the spinner
        categorySpinner.setAdapter(adapter);
    }

    private void setDefaultDate() {
        // Set the current date as the default date on the button
        Calendar calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);

        String defaultDate = mDay + "/" + (mMonth + 1) + "/" + mYear;
        expenseDateButton.setText(defaultDate);
    }

    private void showDatePicker() {
        // Show DatePicker dialog when the button is clicked
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                AddExpenseActivity.this,
                (view, year, monthOfYear, dayOfMonth) -> {
                    // Set the selected date to the button
                    String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                    expenseDateButton.setText(selectedDate);
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;
                },
                mYear, mMonth, mDay);

        datePickerDialog.show();
    }

    private void saveExpense() {
        // Get values from the input fields
        String name = expenseNameEditText.getText().toString();
        String amountString = expenseAmountEditText.getText().toString();
        String category = categorySpinner.getSelectedItem().toString();
        String date = expenseDateButton.getText().toString();  // Get the selected date from the button

        if (name.isEmpty() || amountString.isEmpty() || category.equals("Select Category") || date.equals("Pick Date")) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Convert amount to double
        double amount = Double.parseDouble(amountString);

        // Create an Expense object
        Expense expense = new Expense(name, amount, category, date);

        // Get a reference to Firebase Database
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("expenses");
        String expenseId = databaseReference.push().getKey();

        if (expenseId != null) {
            // Save the expense data to Firebase
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
