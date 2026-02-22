package com.example.finance_tracker;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddDebtActivity extends AppCompatActivity {

    private EditText editPersonName, editAmount;
    private RadioGroup radioGroupDebtType;
    private Button buttonSave;

    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_debt);

        editPersonName = findViewById(R.id.editPersonName);
        editAmount = findViewById(R.id.editAmount);
        radioGroupDebtType = findViewById(R.id.radioGroupDebtType);
        buttonSave = findViewById(R.id.buttonSave);

        db = AppDatabase.getDatabase(this);

        buttonSave.setOnClickListener(v -> saveDebt());
    }

    private void saveDebt() {

        String personName = editPersonName.getText().toString().trim();
        String amountText = editAmount.getText().toString().trim();

        if (TextUtils.isEmpty(personName) || TextUtils.isEmpty(amountText)) {
            Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountText);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Enter a valid amount", Toast.LENGTH_SHORT).show();
            return;
        }

        int selectedId = radioGroupDebtType.getCheckedRadioButtonId();
        if (selectedId == -1) {
            Toast.makeText(this, "Select debt type", Toast.LENGTH_SHORT).show();
            return;
        }

        RadioButton selectedRadio = findViewById(selectedId);
        DebtType debtType;

        if (selectedRadio.getText().toString().equals("Owed To Me")) {
            debtType = DebtType.OWED_TO_ME;
        } else {
            debtType = DebtType.I_OWE;
        }

        Debt newDebt = new Debt(personName, amount, debtType);

        new Thread(() -> {
            db.debtDao().insert(newDebt);

            runOnUiThread(() -> {
                Toast.makeText(this, "Debt Saved", Toast.LENGTH_SHORT).show();
                finish();
            });
        }).start();
    }
}
