package com.example.finance_tracker;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import androidx.room.ColumnInfo;
import androidx.annotation.NonNull;

import com.example.finance_tracker.utils.DateConverter;

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

    // Currency (default: KES for Kenya, USD, EUR, etc.)
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
     * Full constructor with all fields
     */
    public Debt(int debtId, @NonNull String personName, double amount,
                @NonNull DebtType debtType, @NonNull Date dateCreated,
                Date dueDate, @NonNull DebtStatus status, String description,
                PaymentMethod paymentMethod, String contactInfo,
                Date dateUpdated, String currency, boolean isSettled,
                double amountPaid) {
        this.debtId = debtId;
        this.personName = personName;
        this.amount = amount;
        this.debtType = debtType;
        this.dateCreated = dateCreated;
        this.dueDate = dueDate;
        this.status = status;
        this.description = description;
        this.paymentMethod = paymentMethod;
        this.contactInfo = contactInfo;
        this.dateUpdated = dateUpdated;
        this.currency = currency;
        this.isSettled = isSettled;
        this.amountPaid = amountPaid;
    }

    /**
     * Minimal constructor for creating new debts
     */
    public Debt(@NonNull String personName, double amount, @NonNull DebtType debtType) {
        this.personName = personName;
        this.amount = amount;
        this.debtType = debtType;
        this.dateCreated = new Date(); // Current date/time
        this.status = DebtStatus.PENDING;
        this.currency = "KES"; // Default currency
        this.isSettled = false;
        this.amountPaid = 0.0;
        this.dateUpdated = new Date();
    }

    /**
     * Constructor with common fields
     */
    public Debt(@NonNull String personName, double amount, @NonNull DebtType debtType,
                Date dueDate, String description) {
        this(personName, amount, debtType);
        this.dueDate = dueDate;
        this.description = description;
    }


    // ==================== GETTERS ====================

    public int getDebtId() {
        return debtId;
    }

    @NonNull
    public String getPersonName() {
        return personName;
    }

    public double getAmount() {
        return amount;
    }

    @NonNull
    public DebtType getDebtType() {
        return debtType;
    }

    @NonNull
    public Date getDateCreated() {
        return dateCreated;
    }

    public Date getDueDate() {
        return dueDate;
    }

    @NonNull
    public DebtStatus getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public String getCurrency() {
        return currency;
    }

    public boolean isSettled() {
        return isSettled;
    }

    public double getAmountPaid() {
        return amountPaid;
    }


    // ==================== SETTERS ====================

    public void setDebtId(int debtId) {
        this.debtId = debtId;
    }

    public void setPersonName(@NonNull String personName) {
        this.personName = personName;
        this.dateUpdated = new Date();
    }

    public void setAmount(double amount) {
        this.amount = amount;
        this.dateUpdated = new Date();
    }

    public void setDebtType(@NonNull DebtType debtType) {
        this.debtType = debtType;
        this.dateUpdated = new Date();
    }

    public void setDateCreated(@NonNull Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
        this.dateUpdated = new Date();
    }

    public void setStatus(@NonNull DebtStatus status) {
        this.status = status;
        this.dateUpdated = new Date();
    }

    public void setDescription(String description) {
        this.description = description;
        this.dateUpdated = new Date();
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
        this.dateUpdated = new Date();
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
        this.dateUpdated = new Date();
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setSettled(boolean settled) {
        isSettled = settled;
        if (settled) {
            this.status = DebtStatus.PAID;
        }
        this.dateUpdated = new Date();
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
        updateStatusBasedOnPayment();
        this.dateUpdated = new Date();
    }


    // ==================== BUSINESS LOGIC METHODS ====================

    /**
     * Calculate the remaining amount to be paid
     * @return Remaining debt amount
     */
    public double getRemainingAmount() {
        return amount - amountPaid;
    }

    /**
     * Check if the debt is overdue
     * @return true if debt is past due date and not settled
     */
    public boolean isOverdue() {
        if (dueDate == null || isSettled) {
            return false;
        }
        return new Date().after(dueDate) && !isSettled;
    }

    /**
     * Mark debt as fully paid
     */
    public void markAsPaid() {
        this.isSettled = true;
        this.status = DebtStatus.PAID;
        this.amountPaid = this.amount;
        this.dateUpdated = new Date();
    }

    /**
     * Record a partial payment
     * @param paymentAmount Amount being paid
     */
    public void recordPartialPayment(double paymentAmount) {
        this.amountPaid += paymentAmount;
        if (this.amountPaid >= this.amount) {
            markAsPaid();
        } else {
            this.status = DebtStatus.PARTIALLY_PAID;
        }
        this.dateUpdated = new Date();
    }

    /**
     * Update status based on amount paid
     */
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

    /**
     * Get payment progress as percentage
     * @return Percentage of debt paid (0-100)
     */
    public double getPaymentProgress() {
        if (amount == 0) return 0;
        return (amountPaid / amount) * 100;
    }

    /**
     * Check if this is money owed to me
     * @return true if someone owes me money
     */
    public boolean isOwedToMe() {
        return debtType == DebtType.OWED_TO_ME;
    }

    /**
     * Check if this is money I owe
     * @return true if I owe someone money
     */
    public boolean isIOwe() {
        return debtType == DebtType.I_OWE;
    }

    /**
     * Get formatted amount with currency
     * @return Formatted string like "KES 1,500.00"
     */
    public String getFormattedAmount() {
        return String.format("%s %.2f", currency, amount);
    }

    /**
     * Get days until due (negative if overdue)
     * @return Number of days until due date, or null if no due date
     */
    public Long getDaysUntilDue() {
        if (dueDate == null) return null;

        long diff = dueDate.getTime() - new Date().getTime();
        return diff / (1000 * 60 * 60 * 24);
    }


    // ==================== OVERRIDE METHODS ====================

    @Override
    public String toString() {
        return "Debt{" +
                "debtId=" + debtId +
                ", personName='" + personName + '\'' +
                ", amount=" + amount +
                ", debtType=" + debtType +
                ", status=" + status +
                ", dateCreated=" + dateCreated +
                ", dueDate=" + dueDate +
                ", isSettled=" + isSettled +
                ", amountPaid=" + amountPaid +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Debt debt = (Debt) o;
        return debtId == debt.debtId;
    }

    @Override
    public int hashCode() {
        return debtId;
    }
}