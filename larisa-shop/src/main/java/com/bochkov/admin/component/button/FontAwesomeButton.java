package com.bochkov.admin.component.button;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.BootstrapAjaxFallbackButton;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.icon.FontAwesomeCssReference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.form.Form;

import java.util.Optional;

public abstract class FontAwesomeButton extends BootstrapAjaxFallbackButton {


    public FontAwesomeButton(String id, Form<?> form) {
        super(id, form, Buttons.Type.Default);
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.render(CssHeaderItem.forReference(FontAwesomeCssReference.instance()));
    }


    @Override
    protected void onSubmit(Optional<AjaxRequestTarget> target) {

    }

    @Override
    protected void onError(Optional<AjaxRequestTarget> target) {

    }
}

