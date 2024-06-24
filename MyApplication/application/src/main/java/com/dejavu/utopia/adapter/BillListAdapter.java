package com.dejavu.utopia.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dejavu.utopia.R;
import com.dejavu.utopia.enumType.TransactionType;
import com.dejavu.utopia.room.entity.Bill;

import java.util.List;

public class BillListAdapter extends RecyclerView.Adapter<BillListAdapter.BillViewHolder> {

    private List<Bill> billList;

    public BillListAdapter (List<Bill> billList) {
        this.billList = billList;
    }

    @NonNull
    @Override
    public BillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bill_item , parent, false);
        return new BillViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillViewHolder holder, int position) {
        Bill bill = billList.get(position);
        // Set UI elements using bill data
        holder.billTypeIcon.setImageResource(R.drawable.drawable_at_sign);
        holder.billType.setText(bill.getType());
        holder.billTime.setText(bill.getTransactionTime().toString());
        holder.billNote.setText(bill.getNote());
        if(bill.getTransactionType()== TransactionType.INCOME.getType()) {
            holder.billMoney.setText(String.format("%+.2f", bill.getAmountDetail().getTotalAmount()));

        }
        if(bill.getTransactionType()== TransactionType.EXPENSE.getType()) {
            holder.billMoney.setText(String.format("%-.2f",
                    bill.getAmountDetail().getTotalAmount()));
        }
    }

    @Override
    public int getItemCount() {
        return billList.size();
    }

    public void updateAdapter(List<Bill> newBills) {
        billList = newBills;
        notifyDataSetChanged();
    }

    public static class BillViewHolder extends RecyclerView.ViewHolder {
        ImageView billTypeIcon;
        TextView billType, billTime, billNote, billMoney;

        public BillViewHolder(@NonNull View itemView) {
            super(itemView);
            billTypeIcon = itemView.findViewById(R.id.iv_bill_type_icon);
            billType = itemView.findViewById(R.id.tv_bill_type);
            billTime = itemView.findViewById(R.id.tv_bill_time);
            billNote = itemView.findViewById(R.id.tv_bill_note);
            billMoney = itemView.findViewById(R.id.tv_bill_money);
        }
    }
}
