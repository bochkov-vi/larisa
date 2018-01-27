package com.bochkov.admin.component.button;

import org.apache.wicket.model.IModel;
import org.apache.wicket.request.cycle.RequestCycle;

public interface NavigateAction<T> {
    void navigate(RequestCycle circle, IModel<T> model);
}
