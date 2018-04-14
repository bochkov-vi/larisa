package com.bochkov.admin.component;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;

import java.io.Serializable;

public abstract class CurrencyLabeledLink<T extends Serializable> extends LabeledLink<T> {
    public CurrencyLabeledLink(String id, String label, boolean ajaxified) {
        super(id, label, ajaxified);
    }

    public CurrencyLabeledLink(String id, IModel<T> model, IModel labelModel) {
        super(id, model, labelModel);
    }

    public CurrencyLabeledLink(String id, IModel<T> model, IModel labelModel, boolean ajaxified) {
        super(id, model, labelModel, ajaxified);
    }

    public CurrencyLabeledLink(String id, T label) {
        super(id, label);
    }

    @Override
    public Label createLabel(String id, IModel labelModel) {
        return new CurrencyLabel(id, labelModel);
    }
}
