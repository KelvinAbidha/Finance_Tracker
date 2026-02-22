package com.example.finance_tracker;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import androidx.room.ColumnInfo;
import androidx.annotation.NonNull;

import java.util.Date;

/**
 * Debt Entity - Represents a single debt record
 * Room will create a table named "debts" from this class
 *
 * @author Adrian
 */
@Entity(tableName = "debts")
@TypeConverters(DateConverter.class)
public class Debt {

    // Primary Key - Auto-generated unique ID for each debt
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "debt_id")
    private int debtId;

    // Name of the person (creditor or debtor)
    @NonNull
    @ColumnInfo(name = "person_name")
    private String personName;

    // Amount of money owed
    @NonNull
    @ColumnInfo(name = "amount")
    private double amount;

    // Type of debt: OWED_TO_ME or I_OWE
    @NonNull
    @ColumnInfo(name = "debt_type")
    private DebtType debtType;

    // Date when the debt was created
    @NonNull
    @ColumnInfo(name = "date_created")
    private Date dateCreated;

    // Optional: Due date for repayment
    @ColumnInfo(name = "due_date")
    private Date dueDate;

    // Status: PENDING, PAID, PARTIALLY_PAID, OVERDUE
    @NonNull
    @ColumnInfo(name = "status")
    private DebtStatus status;

    // Optional: Description or notes about the debt
    @ColumnInfo(name = "description")
    private String description;

    // Optional: Payment method (CASH, MOBILE_MONEY, BANK_TRANSFER)
    @ColumnInfo(name = "payment_method")
    private PaymentMethod paymentMethod;

    // Optional: Contact information (phone number or email)
    @ColumnInfo(name = "contact_info")
    private String contactInfo;

    // Date when the debt was last updated
    @ColumnInfo(name = "date_updated")
    private Date dateUpdated;

    // Currency (default: KES for Kenya)
    @ColumnInfo(name = "currency")
    private String currency;

    // Tracks if the debt has been settled
    @ColumnInfo(name = "is_settled")
    private boolean isSettled;

    // Amount paid so far (for partial payments)
    @ColumnInfo(name = "amount_paid")
    private double amountPaid;


    // ==================== CONSTRUCTORS ====================

    /**
     * Required by Room to recreate objects
     */
    public Debt() {}

    /**
     * Minimal constructor for creating new debts
     */
    public Debt(@NonNull String personName, double amount, @NonNull DebtType debtType) {
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
}