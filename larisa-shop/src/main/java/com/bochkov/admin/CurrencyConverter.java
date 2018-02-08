package com.bochkov.admin;

import org.apache.wicket.util.convert.ConversionException;
import org.apache.wicket.util.convert.IConverter;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class CurrencyConverter implements IConverter<Number> {
    @Override
    public Number convertToObject(String value, Locale locale) throws ConversionException {
        try {
            return NumberFormat.getCurrencyInstance(locale).parse(value);
        } catch (ParseException e) {
            return null;
        }
    }

    @Override
    public String convertToString(Number value, Locale locale) {
        return NumberFormat.getCurrencyInstance(locale).format(value);
    }
}
