package me.matamor.generalapi.api.utils;

import java.text.DecimalFormat;

public final class MoneyFormat {

    private static String HUNDRED = "K";
    private static String MILLION = "M";
    private static String BILLION = "B";
    private static String TRILLION = "T";
    private static String QUADRILLION = "Q";

    private static final DecimalFormat format = new DecimalFormat("##.#");

    public static String format(double d) {
        return format.format(d);
    }

    public static String fixMoney(double d) {
        if (d < 1000.0D) {
            return format(d);
        }

        if (d < 1000000.0D) {
            return format(d / 1000.0D) + HUNDRED;
        }

        if (d < 1.0E9D) {
            return format(d / 1000000.0D) + MILLION;
        }

        if (d < 1.0E12D) {
            return format(d / 1.0E9D) + BILLION;
        }

        if (d < 1.0E15D) {
            return format(d / 1.0E12D) + TRILLION;
        }

        if (d < 1.0E18D) {
            return format(d / 1.0E15D) + QUADRILLION;
        }

        return String.valueOf(d);
    }
}
