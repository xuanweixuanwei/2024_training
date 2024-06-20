package com.dejavu.utopia.enumType;

import com.dejavu.utopia.R;

public enum TransactionType {
    INCOME(1,"收入", R.drawable.vector_drawable_income),
    EXPENSE(-1,"支出", R.drawable.vector_drawable_expenses);

    private final int type;
    private final String description;
    private final int iconResId;

    TransactionType(int type,String description, int iconResId) {
        this.type = type;
        this.description = description;
        this.iconResId = iconResId;
    }

    public int getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public int getIconResId() {
        return iconResId;
    }
}