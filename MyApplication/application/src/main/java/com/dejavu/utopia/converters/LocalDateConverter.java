package com.dejavu.utopia.converters;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.TypeConverter;

import java.time.LocalDate;

public class LocalDateConverter {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @TypeConverter
    public static long toLong(LocalDate date) {

            return date.toEpochDay();

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @TypeConverter
    public static LocalDate fromLong(long epochDay) {
        return LocalDate.ofEpochDay(epochDay);
    }
}