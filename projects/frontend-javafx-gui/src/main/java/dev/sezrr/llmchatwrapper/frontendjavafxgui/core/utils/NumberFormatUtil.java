package dev.sezrr.llmchatwrapper.frontendjavafxgui.core.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class NumberFormatUtil {
    public static DecimalFormat getDecimalFormat() {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        DecimalFormat decimalFormat = new DecimalFormat("#0.00", symbols);
        decimalFormat.setGroupingUsed(true);
        decimalFormat.setMinimumFractionDigits(2);
        decimalFormat.setMaximumFractionDigits(2);
        return decimalFormat;
    }
    
    public static String formatNumber(double number) {
        return getDecimalFormat().format(number);
    }
}
