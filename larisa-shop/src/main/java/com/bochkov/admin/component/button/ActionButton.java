package com.bochkov.admin.component.button;

import com.bochkov.admin.component.action.IAction;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;

import java.util.Optional;

public class ActionButton extends FontAwesomeButton {

    IAction action;

    public ActionButton(String id, Form<?> form) {
        super(id, form);
    }

    public ActionButton action(IAction action) {
        this.action = action;
        return this;
    }

    @Override
    protected void onSubmit(Optional<AjaxRequestTarget> target) {
        super.onSubmit(target);
    }
}
