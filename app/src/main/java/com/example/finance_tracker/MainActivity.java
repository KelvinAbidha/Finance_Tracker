package com.example.finance_tracker;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executors;

import java.text.SimpleDateFormat;
import java.util.Locale;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private TextView tvTotalBalance;
    private DebtDao debtDao;
    private DebtAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        tvTotalBalance = findViewById(R.id.tvTotalBalance);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.tvTitle), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // DB
        AppDatabase db = AppDatabase.getDatabase(this);
        debtDao = db.debtDao();

        // RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerViewDebts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new DebtAdapter(
                new ArrayList<>(),

                // CLICK → Show Details Popup
                d -> showDebtDetailsDialog(d),

                // LONG CLICK → Show Action Options
                d -> showDebtOptionsDialog(d)
        );

        recyclerView.setAdapter(adapter);

        // Observe debts
        debtDao.getAllDebts().observe(this, debts -> {
            if (debts != null) {
                adapter.setDebts(debts);

                double totalBalance = DebtCalculator.calculateTotalBalance(debts);
                String formatted = String.format("Total Balance: KES %.2f", totalBalance);
                tvTotalBalance.setText(formatted);

                // Update balance color: Red if negative (you owe more), Green if positive (more is owed to you)
                if (totalBalance < 0) {
                    tvTotalBalance.setTextColor(Color.parseColor("#DC2626")); // Red
                } else {
                    tvTotalBalance.setTextColor(Color.parseColor("#16A34A")); // Green
                }
            }
        });

        // Add button
        FloatingActionButton fab = findViewById(R.id.fabAddDebt);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddDebtActivity.class);
            startActivity(intent);
        });
    }
    private String formatDate(Date date) {
        if (date == null) return "N/A";
        return new SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(date);
    }
    private void showDebtDetailsDialog(debt d) {


        String message =
                "Name: " + d.getPersonName() +
                        "\nAmount: " + d.getFormattedAmount() +
                        "\nDate Added: " + formatDate(d.getDateCreated()) +
                        "\nDue Date: " + formatDate(d.getDueDate());

        new AlertDialog.Builder(this)
                .setTitle("Debt Details")
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .show();
    }
    private void showDebtOptionsDialog(debt d) {
        String[] options = {"Mark as Paid", "Delete", "Cancel"};

        new AlertDialog.Builder(this)
                .setTitle(d.getPersonName())
                .setItems(options, (dialog, which) -> {

                    if (which == 0) {
                        markDebtAsPaid(d);
                    } else if (which == 1) {
                        deleteDebt(d);
                    }
                    // Cancel does nothing
                })
                .show();
    }


    // ========== Nesh: Settlement Logic ==========

    private void showMarkAsPaidDialog(debt d) {
        new AlertDialog.Builder(this)
                .setTitle("Mark as Paid")
                .setMessage("Mark " + d.getPersonName() + " (" + d.getFormattedAmount() + ") as fully paid?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    markDebtAsPaid(d);
                    dialog.dismiss();
                })
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void markDebtAsPaid(debt d) {
        Executors.newSingleThreadExecutor().execute(() -> {
            debtDao.markDebtAsPaid(d.getDebtId(), new Date());
        });
    }

    private void deleteDebt(debt d) {
        Executors.newSingleThreadExecutor().execute(() -> {
            debtDao.delete(d);
        });
    }
}