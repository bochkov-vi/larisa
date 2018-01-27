package com.bochkov.admin.component.action;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.util.io.IClusterable;

import java.util.Optional;

public interface IAction extends IClusterable {
    void doAction(Form form, Optional<AjaxRequestTarget> target);
}
