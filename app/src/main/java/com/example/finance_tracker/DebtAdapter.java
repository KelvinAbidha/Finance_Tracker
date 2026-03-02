package com.example.finance_tracker;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DebtAdapter extends RecyclerView.Adapter<DebtAdapter.DebtViewHolder> {

    // =========================
    // Click Interfaces
    // =========================

    // Normal click → open details
    public interface OnDebtClickListener {
        void onDebtClick(debt d);
    }

    // Long click → show action options
    public interface OnDebtLongClickListener {
        void onDebtLongClick(debt d);
    }

    // =========================
    // Fields
    // =========================

    private List<debt> debtList;
    private final OnDebtClickListener clickListener;
    private final OnDebtLongClickListener longClickListener;

    // =========================
    // Constructor
    // =========================

    public DebtAdapter(List<debt> debtList,
                       OnDebtClickListener clickListener,
                       OnDebtLongClickListener longClickListener) {

        this.debtList = debtList;
        this.clickListener = clickListener;
        this.longClickListener = longClickListener;
    }

    // =========================
    // Update List from Room
    // =========================

    public void setDebts(List<debt> debts) {
        this.debtList = debts;
        notifyDataSetChanged();
    }

    // =========================
    // Create ViewHolder
    // =========================

    @NonNull
    @Override
    public DebtViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_debt, parent, false);
        return new DebtViewHolder(view);
    }

    // =========================
    // Bind Data to Each Row
    // =========================

    @Override
    public void onBindViewHolder(@NonNull DebtViewHolder holder, int position) {

        debt currentDebt = debtList.get(position);

        // Display basic info
        holder.nameText.setText(currentDebt.getPersonName());
        holder.amountText.setText(currentDebt.getFormattedAmount());

        // Color coding: Red for "I Owe", Green for "Owed to Me"
        if (currentDebt.getDebtType() == DebtType.I_OWE) {
            holder.amountText.setTextColor(Color.parseColor("#DC2626")); // Red
        } else {
            holder.amountText.setTextColor(Color.parseColor("#16A34A")); // Green
        }

        // Visual cue for settled debts
        holder.itemView.setAlpha(currentDebt.isSettled() ? 0.5f : 1.0f);

        // CLICK → Open Details
        holder.itemView.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onDebtClick(currentDebt);
            }
        });

        // LONG CLICK → Show Options
        holder.itemView.setOnLongClickListener(v -> {
            if (longClickListener != null) {
                longClickListener.onDebtLongClick(currentDebt);
            }
            return true;
        });
    }

    // =========================
    // Item Count
    // =========================

    @Override
    public int getItemCount() {
        return debtList != null ? debtList.size() : 0;
    }

    // =========================
    // ViewHolder
    // =========================

    public static class DebtViewHolder extends RecyclerView.ViewHolder {

        TextView nameText;
        TextView amountText;

        public DebtViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.tv_person_name);
            amountText = itemView.findViewById(R.id.tv_amount);
        }
    }
}