package com.dejavu.utopia.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Index;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.dejavu.utopia.room.entity.Account;
import com.dejavu.utopia.room.entity.Bill;

import java.util.List;

@Dao
public interface AccountDao {

    @Insert
    void insert(Account account);

    @Delete
    void delete(Account account);

    @Update
    void update(Account account);

    @Query("DELETE FROM ACCOUNTS")
    void deleteAll();

    @Query("SELECT * FROM accounts WHERE username = :username AND password = :password")
    Account checkAccountCredentials(String username, String password);

    @Query("SELECT * FROM bills WHERE account_id = :accountId")
    List<Bill> getAllBillsForAccount(int accountId);

    @Query("SELECT * FROM accounts")
    List<Account> queryAllAccount();
}