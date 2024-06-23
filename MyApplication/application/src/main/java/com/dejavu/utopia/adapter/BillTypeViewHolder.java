package com.dejavu.utopia.adapter;

import android.graphics.drawable.StateListDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.dejavu.utopia.R;
import com.dejavu.utopia.bean.BillType;

public class BillTypeViewHolder extends RecyclerView.ViewHolder {
    private ImageView icon;
    private TextView text;

    public BillTypeViewHolder(View itemView) {
        super(itemView);
        icon = itemView.findViewById(R.id.icon);
        text = itemView.findViewById(R.id.text);
    }

    public void bind(BillType billType) {
        text.setText(billType.getName());
        // 设置Selector
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_selected}, ContextCompat.getDrawable(itemView.getContext(), billType.getIconSelected()));
        stateListDrawable.addState(new int[]{}, ContextCompat.getDrawable(itemView.getContext(), billType.getIconNormal()));
        icon.setImageDrawable(stateListDrawable);
    }
}
