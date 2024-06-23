package com.dejavu.utopia.view;
import android.content.Context;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.core.content.ContextCompat;

import com.dejavu.utopia.R;

public class BillTypeRadioButton extends AppCompatRadioButton {

    private ImageView icon;
    private TextView text;
    private boolean isChecked;

    public BillTypeRadioButton(Context context) {
        this(context, null);
    }

    public BillTypeRadioButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BillTypeRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init( );
    }
    private void init( ) {
        // 设置StateListDrawable
        StateListDrawable stateListDrawable = new StateListDrawable();
        // 设置按下状态的背景
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, getResources().getDrawable(R.drawable.drawable_shop));
        // 设置选中状态的背景
        stateListDrawable.addState(new int[]{android.R.attr.state_checked}, getResources().getDrawable(R.drawable.drawable_shop));
        // 设置默认状态的背景
        stateListDrawable.addState(new int[]{}, getResources().getDrawable(R.drawable.drawable_shopping));

        // 设置背景
        setBackgroundDrawable(stateListDrawable);
    }



    private void updateView() {
        // 根据选中状态改变UI
        if (isChecked) {
            text.setTextColor(getResources().getColor(android.R.color.black)); // 文字变为黑色
            icon.setImageDrawable(getResources().getDrawable(R.drawable.drawable_shop)); // 更换为选中状态的图标
            // 如果需要改变大小，可以通过LayoutParams动态设置
            // ViewGroup.LayoutParams params = icon.getLayoutParams();
            // params.width *= 1.2f; // 举例，放大1.2倍
            // params.height *= 1.2f;
            // icon.setLayoutParams(params);
        } else {
            text.setTextColor(getResources().getColor(android.R.color.darker_gray)); // 文字变回灰色
            icon.setImageDrawable(getResources().getDrawable(R.drawable.drawable_shop)); // 换回未选中状态的图标
        }
    }

    public void updateDrawableResources(int normalDrawableResId, int selectedDrawableResId) {
        StateListDrawable selector = new StateListDrawable();
        selector.addState(new int[]{android.R.attr.state_checked}, ContextCompat.getDrawable(getContext(), selectedDrawableResId));
        selector.addState(new int[]{-android.R.attr.state_checked}, ContextCompat.getDrawable(getContext(), normalDrawableResId));
        this.setButtonDrawable(selector);
    }
}
