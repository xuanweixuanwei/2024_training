package com.dejavu.utopia.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.dejavu.utopia.R;
import com.dejavu.utopia.bean.BillType;

import java.util.List;

public class BillTypeAdapter extends RecyclerView.Adapter<BillTypeViewHolder> {
    private List<BillType> dataList;
    private int selectedPosition = -1;

    public BillTypeAdapter(List<BillType> dataList) {
        this.dataList = dataList;
    }

    @Override
    public BillTypeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bill_type_radio_button, parent, false);
        return new BillTypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BillTypeViewHolder holder, int position) {
        BillType billType = dataList.get(position);
        holder.bind(billType);
        // 设置选中状态
        holder.itemView.setSelected(position == selectedPosition);
        holder.itemView.setOnClickListener(v -> {
            int previousPosition = selectedPosition;
            selectedPosition = holder.getAdapterPosition();
            notifyItemChanged(previousPosition);
            notifyItemChanged(selectedPosition);
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
