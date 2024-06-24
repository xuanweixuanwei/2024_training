package com.dejavu.utopia.adapter;

import android.content.res.ColorStateList;
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
        // 更新TextView的文字颜色
        updateTextColor(ContextCompat.getColor(itemView.getContext(), R.color.black), ContextCompat.getColor(itemView.getContext(), R.color.base_color_purple));
    }

    // 在BillTypeViewHolder类中添加一个方法来更新TextView的颜色
    private void updateTextColor(int selectedColor, int defaultColor) {
        // 创建一个颜色选择器
        int[][] states = new int[][]{
                new int[]{android.R.attr.state_selected},
                new int[]{}
        };
        int[] colors = new int[]{
                selectedColor, // 选中时的颜色
                defaultColor  // 默认颜色
        };

        ColorStateList colorStateList = new ColorStateList(states, colors);
        text.setTextColor(colorStateList);
    }
}
