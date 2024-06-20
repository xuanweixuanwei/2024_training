package com.dejavu.utopia.converters;

import androidx.room.TypeConverter;

import com.dejavu.utopia.bean.AmountDetail;

import java.util.Arrays;

public class AmountDetailConverter {

    @TypeConverter
    public static String fromAmountDetailToString(AmountDetail amountDetail) {
        return Arrays.toString(new int[]{amountDetail.getYuan(), amountDetail.getJiao(), amountDetail.getFen()});
    }

    @TypeConverter
    public static AmountDetail fromStringToAmountDetail(String str) {
        String[] parts = str.substring(1, str.length()-1).split(",");
        if (parts.length == 3) {
            try {
                int yuan = Integer.parseInt(parts[0].trim());
                int jiao = Integer.parseInt(parts[1].trim());
                int fen = Integer.parseInt(parts[2].trim());
                return new AmountDetail(yuan, jiao, fen);
            } catch (NumberFormatException e) {
                throw new RuntimeException("Error converting string to AmountDetail", e);
            }
        } else {
            throw new IllegalArgumentException("Invalid string format for AmountDetail");
        }
    }
}