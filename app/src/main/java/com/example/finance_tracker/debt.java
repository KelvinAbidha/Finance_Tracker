package com.example.finance_tracker;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

@Entity(tableName = "debts")
@TypeConverters(DateConverter.class)
public class debt {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "debt_id")
    private int debtId;

    @NonNull
    @ColumnInfo(name = "person_name")
    private String personName;

    @ColumnInfo(name = "amount")
    private double amount;

    @NonNull
    @ColumnInfo(name = "debt_type")
    private DebtType debtType;

    @NonNull
    @ColumnInfo(name = "date_created")
    private Date dateCreated;

    @ColumnInfo(name = "due_date")
    private Date dueDate;

    @NonNull
    @ColumnInfo(name = "status")
    private DebtStatus status;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "payment_method")
    private PaymentMethod paymentMethod;

    @ColumnInfo(name = "contact_info")
    private String contactInfo;

    @ColumnInfo(name = "date_updated")
    private Date dateUpdated;

    @ColumnInfo(name = "currency")
    private String currency;

    @ColumnInfo(name = "is_settled")
    private boolean isSettled;

    @ColumnInfo(name = "amount_paid")
    private double amountPaid;


    // ==================== CONSTRUCTORS ====================

    public debt() {}

    public debt(@NonNull String personName, double amount, @NonNull DebtType debtType) {
        this.personName = personName;
        this.amount = amount;
        this.debtType = debtType;
        this.dateCreated = new Date();
        this.status = DebtStatus.PENDING;
        this.currency = "KES";
        this.isSettled = false;
        this.amountPaid = 0.0;
        this.dateUpdated = new Date();
    }


    // ==================== GETTERS & SETTERS ====================

    public int getDebtId() { return debtId; }
    public void setDebtId(int debtId) { this.debtId = debtId; }

    @NonNull
    public String getPersonName() { return personName; }
    public void setPersonName(@NonNull String personName) { this.personName = personName; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    @NonNull
    public DebtType getDebtType() { return debtType; }
    public void setDebtType(@NonNull DebtType debtType) { this.debtType = debtType; }

    @NonNull
    public Date getDateCreated() { return dateCreated; }
    public void setDateCreated(@NonNull Date dateCreated) { this.dateCreated = dateCreated; }

    public Date getDueDate() { return dueDate; }
    public void setDueDate(Date dueDate) { this.dueDate = dueDate; }

    @NonNull
    public DebtStatus getStatus() { return status; }
    public void setStatus(@NonNull DebtStatus status) { this.status = status; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public PaymentMethod getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(PaymentMethod paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getContactInfo() { return contactInfo; }
    public void setContactInfo(String contactInfo) { this.contactInfo = contactInfo; }

    public Date getDateUpdated() { return dateUpdated; }
    public void setDateUpdated(Date dateUpdated) { this.dateUpdated = dateUpdated; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public boolean isSettled() { return isSettled; }

    public void setSettled(boolean settled) {
        this.isSettled = settled;
        if (settled) this.status = DebtStatus.PAID;
    }

    public double getAmountPaid() { return amountPaid; }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
        updateStatusBasedOnPayment();
    }


    // ==================== LOGIC METHODS ====================

    private void updateStatusBasedOnPayment() {
        if (amountPaid >= amount) {
            this.status = DebtStatus.PAID;
            this.isSettled = true;
        } else if (amountPaid > 0) {
            this.status = DebtStatus.PARTIALLY_PAID;
        } else {
            this.status = DebtStatus.PENDING;
        }
    }

    public double getRemainingAmount() {
        return amount - amountPaid;
    }

    //  Added so your Adapter will not crash
    public String getFormattedAmount() {
        return currency + " " + String.format("%.2f", getRemainingAmount());
    }
}