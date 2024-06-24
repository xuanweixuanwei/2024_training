package com.dejavu.utopia.room.entity;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.TypeConverters;

import com.dejavu.utopia.bean.AmountDetail;
import com.dejavu.utopia.converters.AmountDetailConverter;
import com.dejavu.utopia.converters.DateConverter;
import com.dejavu.utopia.converters.LocalDateConverter;
import com.dejavu.utopia.converters.LocalDateTimeConverter;
import com.dejavu.utopia.enumType.TransactionType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity(tableName = "bills",
        foreignKeys = @ForeignKey(entity = Account.class,
                parentColumns = "id",
                childColumns = "account_id",
                onDelete = ForeignKey.CASCADE),
        indices = {@Index("account_id")})
public class Bill implements Comparable<Bill> {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @TypeConverters(LocalDateConverter.class)
    @ColumnInfo(name = "date")
    private LocalDate transactionTime;

    @TypeConverters(LocalDateTimeConverter.class)
    @ColumnInfo(name = "added_time")
    private LocalDateTime addedTime;

    @TypeConverters(AmountDetailConverter.class)
    @ColumnInfo(name = "amount_detail")
    private AmountDetail amountDetail;

    @ColumnInfo(name = "note")
    private String note;

    @ColumnInfo(name = "account_id")
    private int accountId;

    // 新增字段：账单类型
    @ColumnInfo(name = "transaction_type")
    private int transactionType;

    @ColumnInfo(name = "bill_type")
    private String type;

    public Bill() {
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Ignore
    public Bill(LocalDate transactionTime, AmountDetail amountDetail, String note,
                int accountId,String billType) {
        this.transactionTime = transactionTime;
        this.amountDetail = amountDetail;
        this.note = note;
        this.accountId = accountId;
        this.addedTime = LocalDateTime.now();
        this.type = billType;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Ignore
    public Bill(LocalDate transactionTime, AmountDetail amountDetail, String note, int accountId,
                int transactionType,String billType) {
        this.transactionTime = transactionTime;
        this.amountDetail = amountDetail;
        this.note = note;
        this.accountId = accountId;
        this.transactionType = transactionType;
        this.addedTime = LocalDateTime.now();
        this.type = billType;

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int compareTo(Bill other) {
        // 比较transactionTime
        int dateComparison = this.transactionTime.compareTo(other.transactionTime);

        // 如果transactionTime相同，则继续比较addedTime
        if (dateComparison == 0) {
            return this.addedTime.compareTo(other.addedTime);
        } else {
            return dateComparison;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(LocalDate transactionTime) {
        this.transactionTime = transactionTime;
    }

    public LocalDateTime getAddedTime() {
        return addedTime;
    }

    public void setAddedTime(LocalDateTime addedTime) {
        this.addedTime = addedTime;
    }

    public AmountDetail getAmountDetail() {
        return amountDetail;
    }

    public void setAmountDetail(AmountDetail amountDetail) {
        this.amountDetail = amountDetail;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(int transactionType) {
        this.transactionType = transactionType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}