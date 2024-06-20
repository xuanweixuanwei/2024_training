package com.dejavu.utopia.utils;

/*
 * 处理账单
 * */

import com.dejavu.utopia.bean.ProcessingResult;
import com.dejavu.utopia.enumType.TransactionType;
import com.dejavu.utopia.room.entity.Bill;

import java.util.List;

public class BillProcessorUtil {

    /**
     * 计算Bill列表的总入账、总支出及利润。
     *
     * @param bills Bill对象的列表
     * @return 包含总入账、总支出和利润的ProcessingResult对象
     */
    public static ProcessingResult processBills(List<Bill> bills) {
        double totalIncome = 0.0;
        double totalExpense = 0.0;

        for (Bill bill : bills) {
            double amount = bill.getAmountDetail().getTotalAmount();
            if (bill.getTransactionType() == TransactionType.INCOME.getType()) {
                totalIncome += amount;
            } else if (bill.getTransactionType() == TransactionType.EXPENSE.getType()) {
                totalExpense += amount;
            }
        }

        double profit = totalIncome - totalExpense; // 计算利润

        return new ProcessingResult(totalIncome, totalExpense, profit);
    }



}