package com.example.finance_tracker;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;
import androidx.annotation.NonNull;

/**
 * User Entity - Represents the app user
 * Can be used for multi-user support or user preferences
 *
 * @author Adrian
 */
@Entity(tableName = "users")
public class User {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    private int userId;

    @NonNull
    @ColumnInfo(name = "username")
    private String username;

    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "phone_number")
    private String phoneNumber;

    @ColumnInfo(name = "default_currency")
    private String defaultCurrency;

    @ColumnInfo(name = "profile_image_url")
    private String profileImageUrl;


    // ==================== CONSTRUCTORS ====================

    public User(@NonNull String username) {
        this.username = username;
        this.defaultCurrency = "KES";
    }

    public User(int userId, @NonNull String username, String email,
                String phoneNumber, String defaultCurrency, String profileImageUrl) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.defaultCurrency = defaultCurrency;
        this.profileImageUrl = profileImageUrl;
    }


    // ==================== GETTERS & SETTERS ====================

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @NonNull
    public String getUsername() {
        return username;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDefaultCurrency() {
        return defaultCurrency;
    }

    public void setDefaultCurrency(String defaultCurrency) {
        this.defaultCurrency = defaultCurrency;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }


    // ==================== OVERRIDE METHODS ====================

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}