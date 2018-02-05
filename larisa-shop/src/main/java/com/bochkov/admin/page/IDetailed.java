package com.bochkov.admin.page;

import com.bochkov.admin.component.details.Details;
import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;

public interface IDetailed<T> {
    default Component createDetailsPanel(String id, IModel<T> model) {
        return new Details(id, model);
    }
}
