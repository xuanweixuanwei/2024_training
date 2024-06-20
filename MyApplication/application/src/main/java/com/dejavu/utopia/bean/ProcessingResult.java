package com.dejavu.utopia.bean;

/**
 * 存储处理结果的类。
 */
public class ProcessingResult {
    private double totalIncome;
    private double totalExpense;
    private double profit;

    public ProcessingResult(double totalIncome, double totalExpense, double profit) {
        this.totalIncome = totalIncome;
        this.totalExpense = totalExpense;
        this.profit = profit;
    }

    // Getters and setters 省略
}