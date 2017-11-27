package com.bochkov.shop.page;

import org.apache.wicket.markup.html.GenericWebPage;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class TemplatePage<T> extends GenericWebPage<T> {
    public TemplatePage() {
    }

    public TemplatePage(IModel<T> model) {
        super(model);
    }

    public TemplatePage(PageParameters parameters) {
        super(parameters);
    }
}
