package com.bochkov.admin.component;

import com.bochkov.admin.CurrencyConverter;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.convert.IConverter;

import java.io.Serializable;

public class CurrencyLabel extends Label {
    public CurrencyLabel(String id) {
        super(id);
    }

    public CurrencyLabel(String id, Serializable label) {
        super(id, label);
    }

    public CurrencyLabel(String id, IModel<?> model) {
        super(id, model);
    }


    @Override
    protected IConverter<?> createConverter(Class<?> type) {
        return new CurrencyConverter();
    }
}
