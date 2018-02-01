package com.bochkov.admin.component.action;

import org.apache.wicket.Page;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.cycle.RequestCycle;

public interface NavigateAction<T> {
    static <T> NavigateAction<T> goBack(Page page) {
        return new NavigateAction<T>() {
            @Override
            public void navigate(RequestCycle circle, IModel model) {
                doCancel();
            }

            public void doCancel() {
                RequestCycle.get().setResponsePage(page);
            }
        };


    }

    void navigate(RequestCycle circle, IModel<T> model);
}
