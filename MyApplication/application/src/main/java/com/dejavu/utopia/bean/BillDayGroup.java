package com.dejavu.utopia.bean;

import com.dejavu.utopia.room.entity.Bill;

import java.time.LocalDate;
import java.util.TreeSet;

public class BillDayGroup {
    LocalDate date;
    TreeSet<Bill> bills;

    public BillDayGroup(LocalDate date, TreeSet<Bill> bills) {
        this.date = date;
        this.bills = bills;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public TreeSet<Bill> getBills() {
        return bills;
    }

    public void setBills(TreeSet<Bill> bills) {
        this.bills = bills;
    }
}
