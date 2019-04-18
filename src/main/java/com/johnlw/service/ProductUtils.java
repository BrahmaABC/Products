package com.johnlw.service;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.Objects;

public class ProductUtils {

    public static String getHexColor(String color) {
        Color actualColor;
        try {
            Field field = Class.forName("java.awt.Color").getField(color.toLowerCase());
            actualColor = (Color) field.get(null);
        } catch (Exception e) {
            actualColor = null; // Not defined
        }

        if (Objects.nonNull(actualColor))
            return ColorToHex(actualColor);
        return "";
    }

    public static String ColorToHex(Color color) {
        String rgb = Integer.toHexString(color.getRGB());
        return rgb.substring(2, rgb.length()).toUpperCase();
    }

    public static String getPrice(String value) {
        if (value != null && !value.isEmpty()) {
            if (value.contains(".")) {
                Double doubleValue = Double.valueOf(value);
                return "£" + doubleValue.intValue();

            } else {
                if (Integer.valueOf(value) > 10) {
                    return "£" + value;
                } else {

                    return "£" + value + ".00";
                }
            }
        }
        return "£" + "0.00";
    }


    public static String getCurrency(String currency) {
        if (currency.equals("GBP"))
            return "£";
        else
            return "£";
    }

}
