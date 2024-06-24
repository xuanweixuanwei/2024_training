package com.dejavu.utopia.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;
import androidx.room.Update;

import com.dejavu.utopia.converters.AmountDetailConverter;
import com.dejavu.utopia.converters.DateConverter;
import com.dejavu.utopia.converters.LocalDateConverter;
import com.dejavu.utopia.converters.LocalDateTimeConverter;
import com.dejavu.utopia.room.entity.Bill;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@TypeConverters({LocalDateConverter.class, LocalDateTimeConverter.class,
        AmountDetailConverter.class})
@Dao
public interface BillDao {

    @Insert
    void insert(Bill bill);

    @Update
    void update(Bill bill);

    @Delete
    void delete(Bill bill);


    @Query("SELECT * FROM bills")
    List<Bill> getAllBills();


    @Query("SELECT * FROM bills WHERE account_id = :accountId")
    List<Bill> getAllBillsForAccount(int accountId);

    // 计算某一天的总入账、总出账、净利润
    @Query("SELECT * FROM bills WHERE account_id = :accountId AND date BETWEEN :startDate AND " +
            ":endDate AND transaction_type > 1")
    List<Bill> getTotalIncomeForDay(int accountId, LocalDate startDate, LocalDate endDate);


    @Query("SELECT * FROM bills WHERE account_id = :accountId AND date BETWEEN :startDate AND " +
            ":endDate AND transaction_type < 0")
    List<Bill> getTotalExpenseForDay(int accountId, LocalDate startDate, LocalDate endDate);

    // 计算全部入账、出账
    @Query("SELECT * FROM bills WHERE account_id = :accountId AND transaction_type > 0")
    List<Bill> getTotalIncome(int accountId);

    @Query("SELECT * FROM bills WHERE account_id = :accountId AND transaction_type < 0")
    List<Bill> getTotalExpense(int accountId);

    // 查询某个月的全部账单
    @Query("SELECT * FROM bills WHERE account_id = :accountId AND date BETWEEN :startDate AND :endDate")
    List<Bill> getAllBillsForMonth(int accountId, LocalDate startDate, LocalDate endDate);

    // 查询某个月的全部支出
    @Query("SELECT * FROM bills WHERE account_id = :accountId AND date BETWEEN :startDate AND :endDate AND transaction_type < 0")
    List<Bill> getTotalExpenseForMonth(int accountId, LocalDate startDate, LocalDate endDate);

    // 查询某个月的全部收入
    @Query("SELECT * FROM bills WHERE account_id = :accountId AND date BETWEEN :startDate AND :endDate AND transaction_type > 0")
    List<Bill> getTotalIncomeForMonth(int accountId, LocalDate startDate, LocalDate endDate);

    // 查询特定时间范围内的全部账单
    @Query("SELECT * FROM bills WHERE account_id = :accountId AND date BETWEEN :startDate AND :endDate")
    List<Bill> getAllBillsBetweenDates(int accountId, LocalDate startDate, LocalDate endDate);

    // 查询某一个type的全部账单
    @Query("SELECT * FROM bills WHERE account_id = :accountId AND transaction_type = :transactionType")
    List<Bill> getAllBillsByType(int accountId, int transactionType);
}