package com.dejavu.utopia.utils;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;

import android.os.Message;
import androidx.annotation.NonNull;

import com.dejavu.utopia.room.AppDatabase;
import com.dejavu.utopia.room.entity.Bill;

import java.util.List;


public class DatabaseThread extends HandlerThread {
    public static final int QUERY_BILLS = 1;
    public static final int QUERY_EXPENDITURE_BY_MONTH = 2;
    public static final int QUERY_INCOME_BY_MONTH = 3;
    public static final int QUERY_TOTAL_INCOME = 4;
    public static final int QUERY_TOTAL_EXPENSE = 5;
    private Handler workerHandler;
    private Handler mainHandler;
    private Context context;
    private static DatabaseThread instance;

    // 私有构造函数，禁止直接实例化
    private DatabaseThread(Handler mainHandler,Context context) {
        super("DatabaseThread");
        this.mainHandler = mainHandler;
        this.context = context;

    }

    // 单例模式获取实例
    public static synchronized DatabaseThread getInstance(Handler mainHandler, Context context) {
        if (instance == null) {
            instance = new DatabaseThread(mainHandler,context);
            instance.start();
            instance.workerHandler =new Handler(instance.getLooper()) {
                int accountId;
                @Override
                public void handleMessage(@NonNull Message msg) {
                    switch (msg.what) {
                        case QUERY_BILLS:
                            accountId = msg.arg1;
                            List<Bill> bills = performDatabaseQuery(accountId, context);
                            Message resultMsg = mainHandler.obtainMessage(QUERY_BILLS, bills);
                            mainHandler.sendMessage(resultMsg);
                            break;

//                    case QUERY_EXPENDITURE_BY_MONTH:
//                        String monthExpenditure = (String) msg.obj;
//                        double expenditure = performQueryExpenditureByMonth(monthExpenditure);
//                        Message resultExpenditureMsg = mainHandler.obtainMessage(QUERY_EXPENDITURE_BY_MONTH, expenditure);
//                        mainHandler.sendMessage(resultExpenditureMsg);
//                        break;
//
//                    case QUERY_INCOME_BY_MONTH:
//                        String monthIncome = (String) msg.obj;
//                        double income = performQueryIncomeByMonth(monthIncome);
//                        Message resultIncomeMsg = mainHandler.obtainMessage(QUERY_INCOME_BY_MONTH, income);
//                        mainHandler.sendMessage(resultIncomeMsg);
//                        break;
                        case QUERY_TOTAL_INCOME:
                            accountId = msg.arg1;
                            double income = performQueryTotalIncome(accountId, context);
                            Message resultIncomeMsg = mainHandler.obtainMessage(QUERY_TOTAL_INCOME, income);
                            mainHandler.sendMessage(resultIncomeMsg);
                            break;
                        case QUERY_TOTAL_EXPENSE:
                            accountId = msg.arg1;
                            double expense = performQueryTotalExpense(accountId, context);
                            Message resultIncomeMsg2 = mainHandler.obtainMessage(QUERY_TOTAL_EXPENSE, expense);
                            mainHandler.sendMessage(resultIncomeMsg2);
                            break;

                        default:
                            super.handleMessage(msg);
                    }
                }
            };
        }
        return instance;
    }
    @Override
    protected void onLooperPrepared() {
        workerHandler = new Handler(getLooper()) {
            int accountId;
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case QUERY_BILLS:
                         accountId = msg.arg1;
                        List<Bill> bills = performDatabaseQuery(accountId, context);
                        Message resultMsg = mainHandler.obtainMessage(QUERY_BILLS, bills);
                        mainHandler.sendMessage(resultMsg);
                        break;

//                    case QUERY_EXPENDITURE_BY_MONTH:
//                        String monthExpenditure = (String) msg.obj;
//                        double expenditure = performQueryExpenditureByMonth(monthExpenditure);
//                        Message resultExpenditureMsg = mainHandler.obtainMessage(QUERY_EXPENDITURE_BY_MONTH, expenditure);
//                        mainHandler.sendMessage(resultExpenditureMsg);
//                        break;
//
//                    case QUERY_INCOME_BY_MONTH:
//                        String monthIncome = (String) msg.obj;
//                        double income = performQueryIncomeByMonth(monthIncome);
//                        Message resultIncomeMsg = mainHandler.obtainMessage(QUERY_INCOME_BY_MONTH, income);
//                        mainHandler.sendMessage(resultIncomeMsg);
//                        break;
                    case QUERY_TOTAL_INCOME:
                        accountId = msg.arg1;
                        double income = performQueryTotalIncome(accountId, context);
                        Message resultIncomeMsg = mainHandler.obtainMessage(QUERY_TOTAL_INCOME, income);
                        mainHandler.sendMessage(resultIncomeMsg);
                        break;
                    case QUERY_TOTAL_EXPENSE:
                        accountId = msg.arg1;
                        double expense = performQueryTotalExpense(accountId,context);
                        Message resultIncomeMsg2 = mainHandler.obtainMessage(QUERY_TOTAL_EXPENSE, expense);
                        mainHandler.sendMessage(resultIncomeMsg2);
                        break;

                    default:
                        super.handleMessage(msg);
                }
            }
        };
    }

    private static double performQueryTotalExpense(int accountId,Context context) {
        List<Bill> totalIncome =
                AppDatabase.getInstance(context).billDao().getTotalExpense(accountId);
        double expense = 0;
        for (Bill b:
                totalIncome) {
            expense+=b.getAmountDetail().getTotalAmount();
        }
        return expense;
    }

    private static double performQueryTotalIncome(int accountId,Context context) {
        List<Bill> totalIncome =
                AppDatabase.getInstance(context).billDao().getTotalIncome(accountId);
        double income = 0;
        for (Bill b:
             totalIncome) {
            income+=b.getAmountDetail().getTotalAmount();
        }
        return income;
    }
    // 用于发送任务消息到后台线程
    public void postDatabaseTask(Message message) {
        workerHandler.sendMessage(message);
    }
    public void queryBills(int accountId) {
        List<Bill> bills = performDatabaseQuery(accountId,context);
        Message msg = workerHandler.obtainMessage(QUERY_BILLS,bills);
        msg.arg1 = accountId;
        workerHandler.sendMessage(msg);
    }

    public void queryExpenseByMonth(String month) {
        Message msg = workerHandler.obtainMessage(QUERY_EXPENDITURE_BY_MONTH, month);
        workerHandler.sendMessage(msg);
    }

    public void queryIncomeByMonth(String month) {
        Message msg = workerHandler.obtainMessage(QUERY_INCOME_BY_MONTH, month);
        workerHandler.sendMessage(msg);
    }

    private static List<Bill> performDatabaseQuery(int accountId,Context context) {
        return AppDatabase.getInstance(context).billDao().getAllBillsForAccount(accountId);
    }

    public void setMainHandler(Handler mainHandler) {
        this.mainHandler = mainHandler;
    }

//    private double performQueryExpenditureByMonth(int id,String month) {
//        return AppDatabase.getInstance(context).billDao().getTotalExpenseForMonth(month);
//    }
//
//    private double performQueryIncomeByMonth(String month) {
//        return AppDatabase.getInstance(context).billDao().getTotalIncomeForMonth(month);
//    }
}
