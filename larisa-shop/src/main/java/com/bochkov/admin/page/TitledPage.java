package com.bochkov.admin.page;

import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public abstract class TitledPage<T> extends BasePage<T> {

    public TitledPage() {
    }

    public TitledPage(IModel<T> model) {
        super(model);
    }

    public TitledPage(PageParameters parameters) {
        super(parameters);
    }

}
