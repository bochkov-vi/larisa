package com.bochkov.admin.component.button;

import de.agilecoders.wicket.extensions.markup.html.bootstrap.icon.FontAwesomeIconType;
import org.apache.wicket.markup.html.form.Form;

public abstract class DeleteButton extends FontAwesomeButton {
    public DeleteButton(String id, Form<?> form) {
        super(id, form);
        setIconType(FontAwesomeIconType.close);
    }
}
