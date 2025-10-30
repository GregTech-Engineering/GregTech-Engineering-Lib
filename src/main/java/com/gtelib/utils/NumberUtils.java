package com.gtelib.utils;

import com.gregtechceu.gtceu.api.GTValues;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;

public final class NumberUtils {

    private NumberUtils() {}

    @NotNull
    private static String getString(double number) {
        DecimalFormat DF = new DecimalFormat("#.##");
        String[] UNITS = { "", "K", "M", "G", "T", "P", "E", "Z", "Y", "R", "Q" };
        double temp = number;
        int unitIndex = 0;
        while (temp >= 1000 && unitIndex < UNITS.length - 1) {
            temp /= 1000;
            unitIndex++;
        }
        return DF.format(temp) + UNITS[unitIndex];
    }

    public static String formatDouble(double number) {
        return getString(number);
    }

    public static String formatLong(long number) {
        return getString(number);
    }

    public static MutableComponent numberText(long number) {
        return Component.literal(formatLong(number));
    }

    public static int chanceOccurrences(int count, int chance, int max) {
        int occurrences = 0;
        for (int i = 0; i < count; i++) {
            if (occurrences == max) return max;
            if (GTValues.RNG.nextInt(chance) == 0) {
                occurrences++;
            }
        }
        return occurrences;
    }
}
