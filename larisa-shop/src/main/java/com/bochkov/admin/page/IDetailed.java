package com.bochkov.admin.page;

import com.bochkov.admin.component.details.Details;
import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;

public interface IDetailed<T> {
    static <T> Component details(String id, IModel<T> model) {
        return new Details(id, model);
    }

    default Component createDetailsPanel(String id, IModel<T> model) {
        return details(id, model);
    }
}
