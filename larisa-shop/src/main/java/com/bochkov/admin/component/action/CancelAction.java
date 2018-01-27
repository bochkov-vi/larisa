package com.bochkov.admin.component.action;

import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.cycle.RequestCycle;

import java.util.Optional;

public abstract class CancelAction implements IAction {
    @Override
    public void doAction(Form form, Optional<AjaxRequestTarget> target) {
        Page page = getCancelPage();
        if (page == null) {
            WebApplication.get().getHomePage();
        }
        RequestCycle.get().setResponsePage(getCancelPage());
    }

    public abstract Page getCancelPage();
}
