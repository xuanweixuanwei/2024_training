package com.dejavu.utopia.bean;

import com.dejavu.utopia.room.entity.Bill;

public class BillUpdatePair {
    private Bill oldBill;
    private Bill newBill;

    public BillUpdatePair(Bill oldBill, Bill newBill) {
        this.oldBill = oldBill;
        this.newBill = newBill;
    }

    public Bill getOldBill() {
        return oldBill;
    }

    public Bill getNewBill() {
        return newBill;
    }
}
